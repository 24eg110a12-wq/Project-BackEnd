package com.foodorder.service;

import com.foodorder.dto.OrderItemRequest;
import com.foodorder.dto.OrderRequest;
import com.foodorder.exception.ResourceNotFoundException;
import com.foodorder.model.*;
import com.foodorder.repository.FoodRepository;
import com.foodorder.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final FoodRepository foodRepository;

    public OrderService(OrderRepository orderRepository, FoodRepository foodRepository) {
        this.orderRepository = orderRepository;
        this.foodRepository = foodRepository;
    }

    @Transactional
    public FoodOrder placeOrder(OrderRequest request) {
        FoodOrder order = new FoodOrder();
        order.setCustomerName(request.getCustomerName());
        order.setCustomerPhone(request.getCustomerPhone());
        order.setDeliveryAddress(request.getDeliveryAddress());

        BigDecimal total = BigDecimal.ZERO;
        for (OrderItemRequest line : request.getItems()) {
            Food food = foodRepository.findById(line.getFoodId())
                    .orElseThrow(() -> new ResourceNotFoundException("Food item " + line.getFoodId() + " not found"));
            if (!food.isAvailable()) {
                throw new IllegalArgumentException(food.getName() + " is currently unavailable");
            }
            OrderItem item = new OrderItem();
            item.setFood(food);
            item.setQuantity(line.getQuantity());
            item.setUnitPrice(food.getPrice());
            order.addItem(item);
            total = total.add(food.getPrice().multiply(BigDecimal.valueOf(line.getQuantity())));
        }
        order.setTotalAmount(total);
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<FoodOrder> getAll(OrderStatus status, String phone) {
        if (phone != null && !phone.isBlank()) return orderRepository.findByCustomerPhoneOrderByCreatedAtDesc(phone.trim());
        if (status != null) return orderRepository.findByStatusOrderByCreatedAtDesc(status);
        return orderRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public FoodOrder getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order " + id + " not found"));
    }

    @Transactional
    public FoodOrder updateStatus(Long id, OrderStatus status) {
        FoodOrder order = getById(id);
        if (order.getStatus() == OrderStatus.DELIVERED || order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalArgumentException("Order is already " + order.getStatus() + " and can't be changed");
        }
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Transactional
    public FoodOrder cancel(Long id) {
        FoodOrder order = getById(id);
        if (order.getStatus() != OrderStatus.PLACED) {
            throw new IllegalArgumentException("Only orders that haven't started preparing can be cancelled");
        }
        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }
}
