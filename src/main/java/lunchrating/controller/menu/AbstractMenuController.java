package lunchrating.controller.menu;

import lunchrating.model.Menu;
import lunchrating.service.MenuService;
import lunchrating.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

public abstract class AbstractMenuController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public static final String REST_URL_MENU = "/menu";

    @Autowired
    private MenuService service;

    public List<Menu> getAll(int restId) {
        log.info("getAll for restaurant {}", restId);
        return service.getAll(restId);
    }

    public Menu get(int id, int restId) {
        log.info("get menu {} for restaurant {}", id, restId);
        return ValidationUtil.checkNotFoundWithId(service.get(id, restId), id);
    }

    public Menu getOnDate(int restId, @RequestParam(required = false, defaultValue = "today") LocalDate date) {
        log.info("get menu for restaurant {} on date {}", restId, date);
        return ValidationUtil.checkNotFound(service.getWithDishesOnDate(restId, date), "date=" + date);
    }

    public void delete(int id, int restId) {
        log.info("delete menu {} for restaurant {}", id, restId);
        ValidationUtil.checkNotFoundWithId(service.delete(id, restId), id);
    }

    public Menu create(Menu menu, int restId) {
        log.info("create menu {} for restaurant {}", menu, restId);
        ValidationUtil.checkNew(menu);
        return service.create(menu, restId);
    }

    public void update(Menu menu, int id, int restId) {
        log.info("update menu {} for restaurant {}", menu, id);
        ValidationUtil.assureIdConsistent(menu, id);
        service.update(menu, restId);
    }
}
