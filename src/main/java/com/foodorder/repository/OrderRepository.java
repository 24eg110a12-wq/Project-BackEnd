package com.foodorder.repository;

import com.foodorder.model.FoodOrder;
import com.foodorder.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<FoodOrder, Long> {
    List<FoodOrder> findAllByOrderByCreatedAtDesc();
    List<FoodOrder> findByStatusOrderByCreatedAtDesc(OrderStatus status);
    List<FoodOrder> findByCustomerPhoneOrderByCreatedAtDesc(String customerPhone);
}
