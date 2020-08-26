package lunchrating.repository;

import lunchrating.model.Menu;

import java.util.List;

public interface MenuRepository {

    List<Menu> getAll(Integer restId);

    Menu get(Integer id);

    boolean delete(Integer id);

    Menu save(Menu menu);
}
