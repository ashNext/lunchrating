package lunchrating.controller;

import lunchrating.AbstractControllerTest;
import lunchrating.controller.menu.AdminMenuController;
import lunchrating.controller.restaurant.AdminRestaurantController;
import lunchrating.model.Menu;
import lunchrating.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static lunchrating.TestUtil.nowDate;
import static lunchrating.TestUtil.userHttpBasic;
import static lunchrating.controller.json.JacksonObjectMapper.getMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminMenuControllerTest extends AbstractControllerTest {

    private static final String REST_URL_R100000 = AdminRestaurantController.REST_URL + "/100000" + AdminMenuController.REST_URL_MENU + '/';

    @Autowired
    private MenuService service;

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_R100000)
                .with(userHttpBasic("Admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(String.format("[{\"id\":100002,\"date\":%s},{\"id\":100003,\"date\":%s}]", nowDate(-1), nowDate())));
    }

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_R100000 + "100002")
                .with(userHttpBasic("Admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(String.format("{\"id\":100002,\"date\":%s}", nowDate(-1))));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL_R100000 + "100003")
                .with(userHttpBasic("Admin", "admin")))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertNull(service.get(100003, 100000));
    }

    @Test
    void createWithLocation() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL_R100000)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"date\": \"2020-08-21\"}")
                .with(userHttpBasic("Admin", "admin")))
                .andReturn();

        Menu created = getMapper().readValue(result.getResponse().getContentAsString(), Menu.class);
        assertEquals("2020-08-21", created.getDate().toString());

        Menu menu = service.get(created.getId(), 100000);
        assertEquals(created.getDate(), menu.getDate());
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL_R100000 + "100002")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"date\": \"2020-08-01\"}")
                .with(userHttpBasic("Admin", "admin")))
                .andExpect(status().isNoContent());

        Menu menu = service.get(100002, 100000);
        assertEquals(100000, menu.getRestaurant().getId());
        assertEquals("2020-08-01", menu.getDate().toString());
    }

    @Test
    void getWithDishes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_R100000 + "100002/with-dishes")
                .with(userHttpBasic("Admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(String.format("{\"id\":100002,\"date\":%s,\"dishes\":[{\"id\":100006,\"name\":\"Борщ\",\"price\":10000},{\"id\":100007,\"name\":\"Хлеб\",\"price\":11500}]}", nowDate(-1))));
    }

    @Test
    void getWithDishesOnDate_WithDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_R100000 + "with-dishes")
                .param("date", nowDate(-1))
                .with(userHttpBasic("Admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(String.format("{\"id\":100002,\"date\":%s,\"dishes\":[{\"id\":100006,\"name\":\"Борщ\",\"price\":10000},{\"id\":100007,\"name\":\"Хлеб\",\"price\":11500}]}", nowDate(-1))));
    }

    @Test
    void getWithDishesOnDate_WithNullDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_R100000 + "with-dishes")
                .with(userHttpBasic("Admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(String.format("{\"id\":100003,\"date\":\"%s\",\"dishes\":[{\"id\":100008,\"name\":\"Картошка\",\"price\":14500},{\"id\":100009,\"name\":\"Котлета\",\"price\":9000},{\"id\":100010,\"name\":\"Салат\",\"price\":16000}]}", nowDate())));
    }
}