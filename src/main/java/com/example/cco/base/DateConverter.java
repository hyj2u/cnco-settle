package com.example.cco.base;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateConverter {
    public Date stringToDate(String str) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(str);
    }

    public Date stringToDateKepco(String str) throws ParseException {
        str = str + ".01";
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        return format.parse(str);
    }

    public Date stringToYearEnd(String str) throws ParseException {
        str+="-12-01";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(str);
    }
    public Date stringToYearStart(String str) throws ParseException {
        str = Integer.parseInt(str) -1 +"";
        str+="-12-31";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(str);
    }
    public String getNowDate(){
        LocalDateTime now = LocalDateTime.now();
        String formatedNow = now.format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
        return formatedNow;
    }

    public Date getBeforeMonth(Date settlementYmd){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(settlementYmd);
        calendar.add(Calendar.MONTH, -1);
        return  new Date(calendar.getTimeInMillis());
    }
}
