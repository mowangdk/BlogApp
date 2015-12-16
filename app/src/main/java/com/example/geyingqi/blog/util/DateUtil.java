package com.example.geyingqi.blog.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by geyingqi on 12/15/15.
 */
public class DateUtil {
    public static String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm", Locale.CHINA);
        return sdf.format(new Date());
    }
}
