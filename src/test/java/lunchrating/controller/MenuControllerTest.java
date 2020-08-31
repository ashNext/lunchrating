package lunchrating.controller;

import lunchrating.AbstractControllerTest;
import lunchrating.model.Menu;
import lunchrating.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static lunchrating.controller.json.JacksonObjectMapper.getMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MenuControllerTest extends AbstractControllerTest {

    private static final String REST_URL_R100000 = RestaurantController.REST_URL + "/100000" + MenuController.REST_URL_MENU + '/';

    @Autowired
    private MenuService service;

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_R100000))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"id\":100002,\"date\":[2020,8,26]},{\"id\":100003,\"date\":[2020,8,27]}]"));
    }

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_R100000 + "100002"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":100002,\"date\":[2020,8,26]}"));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL_R100000 + "100003"))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertNull(service.get(100003, 100000));
    }

    @Test
    void createWithLocation() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL_R100000)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"date\": \"2020-08-21\"}"))
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
                .content("{\"date\": \"2020-08-01\"}"))
                .andExpect(status().isNoContent());

        Menu menu = service.get(100002, 100000);
        assertEquals(100000, menu.getRestaurant().getId());
        assertEquals("2020-08-01", menu.getDate().toString());
    }

    @Test
    void getWithDishes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_R100000 + "100002/with-dishes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":100002,\"date\":[2020,8,26],\"dishes\":[{\"id\":100006,\"name\":\"Борщ\",\"price\":10000},{\"id\":100007,\"name\":\"Хлеб\",\"price\":11500}]}"));
    }
}