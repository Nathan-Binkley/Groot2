package com.restaurant.model;

/**
 * Enum representing common restaurant categories.
 */
public enum RestaurantCategory {
    ITALIAN("Italian"),
    CHINESE("Chinese"),
    MEXICAN("Mexican"),
    INDIAN("Indian"),
    JAPANESE("Japanese"),
    THAI("Thai"),
    FRENCH("French"),
    AMERICAN("American"),
    MEDITERRANEAN("Mediterranean"),
    SEAFOOD("Seafood"),
    STEAKHOUSE("Steakhouse"),
    FAST_FOOD("Fast Food"),
    VEGETARIAN("Vegetarian"),
    VEGAN("Vegan"),
    BARBECUE("Barbecue"),
    BAKERY("Bakery"),
    CAFE("Cafe"),
    DESSERT("Dessert"),
    PIZZA("Pizza"),
    SUSHI("Sushi"),
    OTHER("Other");

    private final String displayName;

    RestaurantCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 