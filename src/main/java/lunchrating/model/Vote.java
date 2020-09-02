package lunchrating.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "vote")
public class Vote extends AbstractBaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonBackReference
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonIgnore
    private User user;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    public Vote() {
    }

    public Vote(@NotNull Restaurant restaurant, @NotNull User user, @NotNull LocalDate date) {
        this(null, restaurant, user, date);
    }

    public Vote(Integer id, @NotNull Restaurant restaurant, @NotNull User user, @NotNull LocalDate date) {
        super(id);
        this.restaurant = restaurant;
        this.user = user;
        this.date = date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "vote{" +
                "id=" + id +
                ", restaurant=" + restaurant +
                ", user=" + user +
                ", date=" + date +
                '}';
    }
}
