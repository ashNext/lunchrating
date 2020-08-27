package lunchrating.repository;

import lunchrating.model.Menu;

import java.util.List;

public interface MenuRepository {

    List<Menu> getAll(Integer restId);

    Menu get(Integer id, Integer restId);

    boolean delete(Integer id, Integer restId);

    Menu save(Menu menu, Integer restId);
}
