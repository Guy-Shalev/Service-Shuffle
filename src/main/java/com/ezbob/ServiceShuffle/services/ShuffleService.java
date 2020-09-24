package com.ezbob.ServiceShuffle.services;


import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ShuffleService {

    /**
     * Gets a number and return a shuffled array from 1 to this number.
     *
     * @param arraySize - the size of the array
     * @return - a shuffled Array from 1 to {@param arraySize}
     */
    public int[] shuffleArray(int arraySize) {
        int[] array = createAscendingOrderArray(arraySize);
        Random random = new Random();

        for (int i = arraySize - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            swapArrayIndexes(array, i, j);
        }

        return array;
    }

    private int[] createAscendingOrderArray(int n) {
        int[] orderedArray = new int[n];

        for (int i = 0; i < n; i++) {
            orderedArray[i] = i + 1;
        }

        return orderedArray;
    }

    private void swapArrayIndexes(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
