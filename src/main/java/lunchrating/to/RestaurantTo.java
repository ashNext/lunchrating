package lunchrating.to;

import lunchrating.model.Restaurant;

import java.util.Objects;

public class RestaurantTo {

    private final int id;

    private final String name;

    private final Long rate;

    public RestaurantTo(int id, String name, Long rate) {
        this.id = id;
        this.name = name;
        this.rate = rate;
    }

    public RestaurantTo(Restaurant restaurant, Long rate) {
        this.id=restaurant.getId();
        this.name=restaurant.getName();
        this.rate=rate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getRate() {
        return rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantTo that = (RestaurantTo) o;
        return id == that.id &&
                rate == that.rate &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, rate);
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rate=" + rate +
                '}';
    }
}
