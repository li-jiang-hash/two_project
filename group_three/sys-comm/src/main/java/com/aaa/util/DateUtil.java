package com.aaa.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * @Author: 江Sir
 * @Date: 2023/02/13 08:55:23
 * @Description:
 * @Since version:  2022.10
 */
public class DateUtil {
    public static String StringTime() {
        Date date = new Date();
        DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }
}
