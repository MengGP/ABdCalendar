package com.menggp.abdcalendar.datamodel;

import android.content.res.Resources;
import android.util.Log;

import com.menggp.abdcalendar.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*
    Класс реализует различные преобразования используемых в приложении дат
 */
public class DateConverter {

    private static final String LOG_TAG = "DateConverter";

    /*
        Преобразование даты из строки формата БД: MM-DD
            в формат элемета списка: DD month_name
     */
    public static String convertDbNotationToItemNotation(Resources res, String date) {
        String result="";
        String dayPart="";
        String monthPart="";

        // Разделим полученную строку с датой на части с днем и месяцем
        dayPart = date.substring(3,5);
        monthPart = date.substring(0,2);

        result += dayPart + " ";

        switch (monthPart) {
            case "01": result += res.getString(R.string.january);  break;
            case "02": result += res.getString(R.string.february);  break;
            case "03": result += res.getString(R.string.march);  break;
            case "04": result += res.getString(R.string.april);  break;
            case "05": result += res.getString(R.string.may);  break;
            case "06": result += res.getString(R.string.june);  break;
            case "07": result += res.getString(R.string.july);  break;
            case "08": result += res.getString(R.string.august);  break;
            case "09": result += res.getString(R.string.september);  break;
            case "10": result += res.getString(R.string.october);  break;
            case "11": result += res.getString(R.string.november);  break;
            case "12": result += res.getString(R.string.december);  break;
        };

        return result;
    } // end_method

    /*
        Метод возвращает количество дней до наступления события
     */
    public static int timeLeftToEvent(String dateFromDb) {
        int result=0;

        SimpleDateFormat shortDateFormat = new SimpleDateFormat("MM-dd");   // Короткий формат представления даты - месяц-день
        SimpleDateFormat yearDateFormat = new SimpleDateFormat("yyyy");     // только год
        SimpleDateFormat stdDateFormat = new SimpleDateFormat("yyyyMMdd");  // стандатный формат - ГодМесяцДень
        Date eventDate = null;
        Date currDate = new Date();// текущая дата
        String eventDateSrt = dateFromDb;                                           // строковое представление текущей даты
        String currDateStr = shortDateFormat.format(currDate);                      // строковое представлени даты события
        // Преобразуем месяц+день - в число INT - для сравнения
        eventDateSrt = eventDateSrt.replace("-","");
        currDateStr = currDateStr.replace("-","");
        int eventDateInt = Integer.parseInt( eventDateSrt );
        int currDateInt = Integer.parseInt( currDateStr );

        // Возможны 3 варианта:
        //      1 - текeщая дата в году меньше даты события - считает число дней до события в этом году
        //      2 - текущая дата в году больше даты события - считает число дней до события в следующем году
        //      3 - даты равны
        if ( currDateInt < eventDateInt ) {
           String nowYear = yearDateFormat.format(currDate);
           eventDateSrt = nowYear+eventDateSrt;
           try {
               eventDate = stdDateFormat.parse(eventDateSrt);
               currDate = stdDateFormat.parse( stdDateFormat.format(currDate ));
           } catch (Exception ex) {
               Log.d(LOG_TAG, ex.getMessage());
           }
           long deltaMs = eventDate.getTime() - currDate.getTime();
           long days =  deltaMs/(24*60*60*1000);
           result = (int) days;

        } else if ( currDateInt > eventDateInt ) {
           String nextYear = yearDateFormat.format(currDate);
           int nextYearInt = Integer.parseInt(nextYear) + 1;
           nextYear = String.valueOf( nextYearInt );
           eventDateSrt = nextYear+eventDateSrt;
           try {
                eventDate = stdDateFormat.parse(eventDateSrt);
                currDate = stdDateFormat.parse( stdDateFormat.format(currDate ));
            } catch (Exception ex) {
                Log.d(LOG_TAG, ex.getMessage());
            }
            long deltaMs = eventDate.getTime() - currDate.getTime();
            long days =  deltaMs/(24*60*60*1000);
            result = (int) days;
        } else result = -1;

        return result;
    } // end_method

    /*
        Метод рассчитывает количество лет от наступления события
     */
    public static int timePastYear(int sinceYear) {
        Date currDate = new Date();                                                 // текущая дата
        SimpleDateFormat yearDateFormat = new SimpleDateFormat("yyyy");     // только год
        int nowYear = Integer.parseInt( yearDateFormat.format(currDate) );          // текущий год
        return nowYear-sinceYear;
    } // end_method

} // end_class






















