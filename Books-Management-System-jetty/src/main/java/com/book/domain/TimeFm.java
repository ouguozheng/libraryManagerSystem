package com.book.domain;

import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;

/**
 * Created by 10394 on 2017/12/26.
 */
@Repository
public class TimeFm {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public static SimpleDateFormat getTimeFormat() {
        return timeFormat;
    }

    public static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
