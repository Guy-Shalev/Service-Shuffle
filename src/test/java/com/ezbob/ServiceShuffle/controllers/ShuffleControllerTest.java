package com.ezbob.ServiceShuffle.controllers;

import com.ezbob.ServiceShuffle.services.ShuffleService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShuffleController.class)
class ShuffleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ShuffleService ShuffleService;
    @MockBean
    private RestTemplate restTemplate;

    @Test
    void shuffle() throws Exception {
        int[] array = new int[]{4, 2, 3, 5, 1};

        when(ShuffleService.shuffleArray(5)).thenReturn(array);
        when(restTemplate.postForObject(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(null);

        this.mockMvc.perform(post("/shuffle?number=5")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(Arrays.toString(array)));
    }

    @Test
    void shuffleBadInputNegativeNumber() throws Exception {
        this.mockMvc.perform(post("/shuffle?number=-5")).andDo(print()).andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andExpect(content().string(ShuffleController.UNHANDLED_INPUT));

    }

    @Test
    void shuffleBadInputDecimalNumber() throws Exception {
        this.mockMvc.perform(post("/shuffle?number=5.3")).andDo(print()).andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andExpect(content().string(ShuffleController.UNHANDLED_INPUT));

    }

    @Test
    void shuffleBadInputNonNumber() throws Exception {
        this.mockMvc.perform(post("/shuffle?number=xxx")).andDo(print()).andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andExpect(content().string(ShuffleController.UNHANDLED_INPUT));

    }
}