package lunchrating.controller;

import lunchrating.AbstractControllerTest;
import lunchrating.model.Vote;
import lunchrating.service.UserService;
import lunchrating.service.VoteService;
import lunchrating.util.DateTimeUtil;
import lunchrating.util.exception.DeniedToVoteException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static lunchrating.TestUtil.userHttpBasic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteController.REST_URL + '/';

    @Autowired
    private VoteService service;

    @Autowired
    private UserService userService;

    @Autowired
    private DateTimeUtil.MockClock mockClock;

    @Test
    void voting() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "100000/vote")
                .with(userHttpBasic("User1", "user1")))
                .andDo(print())
                .andExpect(status().isNoContent());

        Vote vote = service.getByUserAndDate(userService.loadUserByUsername("User1").getUser(), LocalDate.now());
        assertEquals(100000, vote.getRestaurant().getId());
    }

    @Test
    void voting_RevotePossibly() throws Exception {
        mockClock.setTime("10:00:00");

        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "100000/vote")
                .with(userHttpBasic("User2", "user2")))
                .andDo(print())
                .andExpect(status().isNoContent());

        Vote vote = service.getByUserAndDate(userService.loadUserByUsername("User2").getUser(), LocalDate.now());
        assertEquals(100000, vote.getRestaurant().getId());
    }

    @Test
    void voting_RevoteImpossibly() throws Exception {
        mockClock.setTime("11:00:00");

        assertThrows(DeniedToVoteException.class, () -> {

            mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "100000/vote")
                    .with(userHttpBasic("User2", "user2")))
                    .andDo(print())
                    .andExpect(status().isUnprocessableEntity());
        });
    }
}