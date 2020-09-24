package com.ezbob.ServiceShuffle.services;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ShuffleServiceTest {

    private ShuffleService shuffleService = new ShuffleService();

    @Test
    void shuffleArray() throws Exception {
        int[] array = shuffleService.shuffleArray(5);
        List<Integer> list = Arrays.stream(array).boxed().collect(Collectors.toList());

        assertThat(list, is(not(empty())));
        assertThat(list, containsInAnyOrder(1, 2, 3, 4, 5));
    }
}