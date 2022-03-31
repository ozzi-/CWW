package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Output {

  private Output() {
    
  }

  public static void output(String out) {
    out = formatOutput(out);
    System.out.println(out); //NOSONAR
  }

  public static void outputError(String out) {
    out = formatOutput(out);
    System.err.println(out); //NOSONAR
  }

  public static void outputException(Exception ex) {
    String out = formatOutput(ex.getClass().getCanonicalName() + ": " + ex.getMessage());
    System.err.println(out); //NOSONAR
  }

  private static String formatOutput(String out) {
    Date cal = Calendar.getInstance(TimeZone.getDefault()).getTime();
    out = new SimpleDateFormat("HH:mm:ss.SSS").format(cal.getTime()) + " | " + out;
    return out;
  }
}
