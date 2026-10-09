package com.foodorder.repository;

import com.foodorder.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByAvailableTrue();
    List<Food> findByCategoryIgnoreCase(String category);
    List<Food> findByNameContainingIgnoreCase(String keyword);
}
