package lunchrating.repository;

import lunchrating.model.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaMenuRepository implements MenuRepository {

    private final CrudMenuRepository crudRepository;

    public DataJpaMenuRepository(CrudMenuRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public List<Menu> getAll(Integer restId) {
        return crudRepository.getAll(restId);
    }

    @Override
    public Menu get(Integer id) {
        return crudRepository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(Integer id) {
        return crudRepository.delete(id) != 0;
    }

    @Override
    public Menu save(Menu menu) {
        return crudRepository.save(menu);
    }
}
