package com.foodorder.config;

import com.foodorder.model.Food;
import com.foodorder.repository.FoodRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/** Inserts sample menu items the first time the app starts with an empty table. */
@Component
public class DataSeeder implements CommandLineRunner {

    private final FoodRepository foodRepository;

    public DataSeeder(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public void run(String... args) {
        if (foodRepository.count() > 0) return;
        foodRepository.saveAll(List.of(
            food("Margherita Pizza", "Tomato, mozzarella, fresh basil", "Pizza", "9.99"),
            food("Pepperoni Pizza", "Spicy pepperoni, mozzarella", "Pizza", "11.49"),
            food("Veg Burger", "Crisp veg patty, lettuce, house sauce", "Burgers", "6.50"),
            food("Chicken Burger", "Grilled chicken, cheese, pickles", "Burgers", "7.99"),
            food("Paneer Tikka Wrap", "Charred paneer, mint chutney, onions", "Wraps", "6.25"),
            food("Veg Fried Rice", "Wok-tossed rice with vegetables", "Rice & Noodles", "7.25"),
            food("Hakka Noodles", "Stir-fried noodles, cabbage, carrot", "Rice & Noodles", "7.75"),
            food("French Fries", "Salted, served hot", "Sides", "3.25"),
            food("Chocolate Brownie", "Warm brownie, fudge sauce", "Desserts", "4.50"),
            food("Cold Coffee", "Iced, lightly sweetened", "Drinks", "3.99")
        ));
    }

    private Food food(String name, String desc, String category, String price) {
        Food f = new Food();
        f.setName(name);
        f.setDescription(desc);
        f.setCategory(category);
        f.setPrice(new BigDecimal(price));
        f.setAvailable(true);
        return f;
    }
}
