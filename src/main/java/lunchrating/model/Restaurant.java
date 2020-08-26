package lunchrating.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "rest")
public class Restaurant extends AbstractNamedEntity {

    public Restaurant() {
    }

    public Restaurant(@NotBlank String name) {
        this(null, name);
    }

    public Restaurant(Integer id, @NotBlank String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
