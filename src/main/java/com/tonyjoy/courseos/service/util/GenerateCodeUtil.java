package com.tonyjoy.courseos.service.util;

import java.text.DecimalFormat;

/**
 * Created by Zach on 19/03/2018.
 */
public class GenerateCodeUtil {
    public static String generate6StringCode(long id) {
        return  String.format("%06d", id);
    }
}
