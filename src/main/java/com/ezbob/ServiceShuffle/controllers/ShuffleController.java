package com.ezbob.ServiceShuffle.controllers;


import com.ezbob.ServiceShuffle.UrlHelper;
import com.ezbob.ServiceShuffle.models.LogRequest;
import com.ezbob.ServiceShuffle.services.ShuffleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;

@RestController
public class ShuffleController {

    @Autowired
    private ShuffleService shuffleService;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${service.log.name}")
    private String serviceLogName;

    private static final int MAX_SUPPORTED_NUMBER = 1000;
    private static final String CANNOT_CONNECT_TO_SERVICE = "Cannot connect to \'%s\' service.\nPlease contact Ezbob customer service.";
    static final String UNHANDLED_INPUT = "The provided data is unsupported.\nThis service only support numbers that are positive, whole and smaller than " + MAX_SUPPORTED_NUMBER + ".";


    @PostMapping("/shuffle")
    public ResponseEntity<String> shuffle(@RequestParam String number) {
        ResponseEntity<String> responseEntity;

        if (validateInput(number)) {
            int[] shuffledArray = shuffleService.shuffleArray(Integer.parseInt(number));
            LogRequest request = new LogRequest(shuffledArray, new Date());

            try {
                restTemplate.postForObject(UrlHelper.getUrlFromServiceName(serviceLogName, "/log"), request, String.class);
                responseEntity = new ResponseEntity<>(Arrays.toString(shuffledArray), HttpStatus.OK);
            } catch (Exception e) {
                responseEntity = new ResponseEntity<>(String.format(CANNOT_CONNECT_TO_SERVICE, serviceLogName), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            responseEntity = new ResponseEntity<>(UNHANDLED_INPUT, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return responseEntity;
    }

    private boolean validateInput(String number) {
        boolean inputValid = Boolean.TRUE;
        try {
            int parsedNumber = Integer.parseInt(number);
            if (parsedNumber < 0 || parsedNumber > MAX_SUPPORTED_NUMBER) {
                inputValid = Boolean.FALSE;
            }
        } catch (Exception e) {
            inputValid = Boolean.FALSE;
        }

        return inputValid;
    }
}
