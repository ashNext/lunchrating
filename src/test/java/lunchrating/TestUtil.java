package lunchrating;

import lunchrating.model.User;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestUtil {
    public static String nowDate(){
        return nowDate(0);
    }

    public static String nowDate(int plusDays){
        return LocalDate.now().plusDays(plusDays).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static RequestPostProcessor userHttpBasic(String userName, String password) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(userName, password);
    }
}
