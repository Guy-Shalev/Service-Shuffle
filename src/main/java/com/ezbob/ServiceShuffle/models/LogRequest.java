package com.ezbob.ServiceShuffle.models;

import java.util.Date;

public class LogRequest {

    private int[] array;
    private Date requestDate;

    public LogRequest() {
    }

    public LogRequest(int[] array, Date requestDate) {
        this.array = array;
        this.requestDate = requestDate;
    }

    public int[] getArray() {
        return array;
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }
}
