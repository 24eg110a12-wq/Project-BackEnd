package com.foodorder.service;

import com.foodorder.exception.ResourceNotFoundException;
import com.foodorder.model.Food;
import com.foodorder.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public List<Food> getAll(String category, String search) {
        if (search != null && !search.isBlank()) return foodRepository.findByNameContainingIgnoreCase(search.trim());
        if (category != null && !category.isBlank()) return foodRepository.findByCategoryIgnoreCase(category);
        return foodRepository.findByAvailableTrue();
    }

    public Food getById(Long id) {
        return foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food item " + id + " not found"));
    }

    public Food create(Food food) {
        food.setId(null);
        return foodRepository.save(food);
    }

    public Food update(Long id, Food updated) {
        Food existing = getById(id);
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setCategory(updated.getCategory());
        existing.setPrice(updated.getPrice());
        existing.setImageUrl(updated.getImageUrl());
        existing.setAvailable(updated.isAvailable());
        return foodRepository.save(existing);
    }

    public void delete(Long id) {
        foodRepository.delete(getById(id));
    }
}
