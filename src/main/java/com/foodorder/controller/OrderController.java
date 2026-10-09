package com.foodorder.controller;

import com.foodorder.dto.OrderRequest;
import com.foodorder.dto.StatusUpdateRequest;
import com.foodorder.model.FoodOrder;
import com.foodorder.model.OrderStatus;
import com.foodorder.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<FoodOrder> place(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(request));
    }

    @GetMapping
    public List<FoodOrder> list(@RequestParam(required = false) OrderStatus status,
                                @RequestParam(required = false) String phone) {
        return orderService.getAll(status, phone);
    }

    @GetMapping("/{id}")
    public FoodOrder get(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @PatchMapping("/{id}/status")
    public FoodOrder updateStatus(@PathVariable Long id, @Valid @RequestBody StatusUpdateRequest body) {
        return orderService.updateStatus(id, body.getStatus());
    }

    @PostMapping("/{id}/cancel")
    public FoodOrder cancel(@PathVariable Long id) {
        return orderService.cancel(id);
    }
}
