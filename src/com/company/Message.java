package com.company;


import sun.util.calendar.CalendarUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by belldell on 17.10.16.
 */

public class Message implements Serializable{
    public String text;
    public Date time;
    public String nameSender = "null";
    public String status = "active";

    public Message(String text) {
        this.text = text;
        this.time = new Date();
    }

    public Message(String text, String nameSender, String status) {
        this.text = text;
        this.time = new Date();
        this.nameSender = nameSender;
        this.status = status;
    }

    public Message(String text, String nameSender, String status, Date time) {
        this.text = text;
        this.time = time;
        this.nameSender = nameSender;
        this.status = status;
    }


    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        CalendarUtils.sprintf0d(sb, time.getHours(), 2).append(':');   // HH
        CalendarUtils.sprintf0d(sb, time.getMinutes(), 2).append(':'); // mm
        CalendarUtils.sprintf0d(sb, time.getSeconds(), 2); // ss
        sb.append("]");
        if(nameSender != null)
            sb.append(" " + nameSender);
        else
            sb.append(" ERROR NAME");
        if(status != null)
            sb.append(" (" + status + ") ");
        sb.append(" : " + text);

        return sb.toString();
    }
}

