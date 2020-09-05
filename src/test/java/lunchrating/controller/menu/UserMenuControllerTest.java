package lunchrating.controller.menu;

import lunchrating.AbstractControllerTest;
import lunchrating.controller.restaurant.UserRestaurantController;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static lunchrating.TestUtil.nowDate;
import static lunchrating.TestUtil.userHttpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserMenuControllerTest extends AbstractControllerTest {

    private static final String REST_URL_R100000 = UserRestaurantController.REST_URL + "/100000" + UserMenuController.REST_URL_MENU + '/';

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_R100000 + "100002")
                .with(userHttpBasic("User1", "user1")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(String.format("{\"id\":100002,\"date\":\"%s\",\"dishes\":[{\"id\":100006,\"name\":\"Борщ\",\"price\":10000},{\"id\":100007,\"name\":\"Хлеб\",\"price\":11500}]}", nowDate(-1))));
    }

    @Test
    void getOnDate_WithDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_R100000)
                .param("date", nowDate(-1))
                .with(userHttpBasic("User1", "user1")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(String.format("{\"id\":100002,\"date\":%s,\"dishes\":[{\"id\":100006,\"name\":\"Борщ\",\"price\":10000},{\"id\":100007,\"name\":\"Хлеб\",\"price\":11500}]}", nowDate(-1))));
    }

    @Test
    void getOnDate_WithNullDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_R100000)
                .with(userHttpBasic("User1", "user1")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(String.format("{\"id\":100003,\"date\":\"%s\",\"dishes\":[{\"id\":100008,\"name\":\"Картошка\",\"price\":14500},{\"id\":100009,\"name\":\"Котлета\",\"price\":9000},{\"id\":100010,\"name\":\"Салат\",\"price\":16000}]}", nowDate())));
    }

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL_R100000))
                .andExpect(status().isUnauthorized());
    }
}