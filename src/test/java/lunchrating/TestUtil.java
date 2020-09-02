package lunchrating;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestUtil {
    public static String nowDate(){
        return nowDate(0);
    }

    public static String nowDate(int plusDays){
        return LocalDate.now().plusDays(plusDays).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
