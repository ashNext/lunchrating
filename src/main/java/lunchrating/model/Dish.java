package lunchrating.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Dish")
public class Dish extends AbstractNamedEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Menu menu;

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 100, max = 9999999) // $0.01 - $99_999.99
    private Integer price;

    public Dish() {
    }

    public Dish(String name, @NotNull Menu menu, @NotNull @Range(min = 100, max = 9999999) Integer price) {
        this(null, name, menu, price);
    }

    public Dish(Integer id, String name, @NotNull Menu menu, @NotNull @Range(min = 100, max = 9999999) Integer price) {
        super(id, name);
        this.menu = menu;
        this.price = price;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}
