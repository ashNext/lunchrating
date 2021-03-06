package lunchrating.controller.restaurant;

import lunchrating.AbstractControllerTest;
import lunchrating.model.Restaurant;
import lunchrating.service.RestaurantService;
import lunchrating.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static lunchrating.TestUtil.nowDate;
import static lunchrating.TestUtil.userHttpBasic;
import static lunchrating.controller.json.JacksonObjectMapper.getMapper;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminRestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestaurantController.REST_URL + '/';

    @Autowired
    private RestaurantService service;

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic("Admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"id\":100000,\"name\":\"Restaurant 1\"},{\"id\":100001,\"name\":\"Restaurant 2\"}]"));
    }

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "100000")
                .with(userHttpBasic("Admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":100000,\"name\":\"Restaurant 1\"}"));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + "100001")
                .with(userHttpBasic("Admin", "admin")))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(100001));
    }

    @Test
    void createWithLocation() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Rest3\"}")
                .with(userHttpBasic("Admin", "admin")))
                .andReturn();

        Restaurant created = getMapper().readValue(result.getResponse().getContentAsString(), Restaurant.class);
        assertEquals("Rest3", created.getName());

        Restaurant restaurant = service.get(created.getId());
        assertEquals(created.getName(), restaurant.getName());
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "100000")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Restaurant 0\"}")
                .with(userHttpBasic("Admin", "admin")))
                .andExpect(status().isNoContent());

        Restaurant restaurant = service.get(100000);
        assertNotNull(restaurant);
        assertEquals("Restaurant 0", restaurant.getName());
    }

    @Test
    void getWithMenusOnDate_WithNullDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "100000/with-menus")
                .with(userHttpBasic("Admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(String.format("{\"id\":100000,\"name\":\"Restaurant 1\",\"menus\":[{\"id\":100003,\"date\":\"%s\",\"dishes\":[{\"id\":100008,\"name\":\"Картошка\",\"price\":14500},{\"id\":100009,\"name\":\"Котлета\",\"price\":9000},{\"id\":100010,\"name\":\"Салат\",\"price\":16000}]}]}", nowDate())));
    }

    @Test
    void getWithMenusOnDate_WithDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "100000/with-menus")
                .param("date", nowDate(-1))
                .with(userHttpBasic("Admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(String.format("{\"id\":100000,\"name\":\"Restaurant 1\",\"menus\":[{\"id\":100002,\"date\":\"%s\",\"dishes\":[{\"id\":100006,\"name\":\"Борщ\",\"price\":10000},{\"id\":100007,\"name\":\"Хлеб\",\"price\":11500}]}]}", nowDate(-1))));
    }

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic("User1", "user1")))
                .andExpect(status().isForbidden());
    }
}