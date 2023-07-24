package com.api.srs.shared;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {
  public static String nowDate() {
    return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
  }
}
