package com.foodorder.controller;

import com.foodorder.model.Food;
import com.foodorder.service.FoodService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping
    public List<Food> list(@RequestParam(required = false) String category,
                           @RequestParam(required = false) String search) {
        return foodService.getAll(category, search);
    }

    @GetMapping("/{id}")
    public Food get(@PathVariable Long id) {
        return foodService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Food> create(@Valid @RequestBody Food food) {
        return ResponseEntity.status(HttpStatus.CREATED).body(foodService.create(food));
    }

    @PutMapping("/{id}")
    public Food update(@PathVariable Long id, @Valid @RequestBody Food food) {
        return foodService.update(id, food);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        foodService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
