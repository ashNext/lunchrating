package lunchrating.controller;

import lunchrating.AbstractControllerTest;
import lunchrating.model.Dish;
import lunchrating.service.DishService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DishControllerTest extends AbstractControllerTest {

    private static final String REST_URL_R100000_M100002 =
            RestaurantController.REST_URL + "/100000"
                    + MenuController.REST_URL_MENU + "/100002"
                    + DishController.REST_URL_DISH + '/';

    @Autowired
    private DishService service;

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_R100000_M100002))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"id\":100006,\"name\":\"Борщ\",\"price\":10000},{\"id\":100007,\"name\":\"Хлеб\",\"price\":11500}]"));
    }

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_R100000_M100002 + "100006"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":100006,\"name\":\"Борщ\",\"price\":10000}"));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL_R100000_M100002 + "100007"))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertNull(service.get(100007, 100002));
    }

    @Test
    void createWithLocation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL_R100000_M100002)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Сало\",\"price\":20000}")).andReturn();

        Dish dish = service.get(100020, 100002);
        assertNotNull(dish);
        assertEquals("Сало", dish.getName());
        assertEquals(20000, dish.getPrice());
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL_R100000_M100002 + "100006")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Уха\",\"price\":7500}"))
                .andExpect(status().isNoContent());

        Dish dish = service.get(100006, 100002);
        assertEquals("Уха", dish.getName());
        assertEquals(7500, dish.getPrice());
    }
}