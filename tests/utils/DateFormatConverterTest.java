package utils;

import com.gmail.mordress.epamproject.utils.DateFormatConverter;

import java.util.Date;

public class DateFormatConverterTest {

    public static void main(String[] args) {
        String s = "2015-12-18T20:30";
        Date date = DateFormatConverter.stringToDate(s);
        System.out.println(date);
        String seconds = "54";
        Date date1 = DateFormatConverter.stringToDate(s, seconds);
        System.out.println(date1);
    }
}
