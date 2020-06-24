package com.menggp.abdcalendar.datamodel;

import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.menggp.abdcalendar.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/*
    Класс реализует различные преобразования используемых в приложении дат
 */
public class DateHandler{

    private static final String LOG_TAG = "DateConverter";

    /*
        Преобразование даты из строки формата БД: MM-DD
            в формат элемета списка: DD month_name
     */
    public static String convertDbToHumanNotation(Resources res, String date) {
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

    /*
        Метод возвращает ArrayList<String> лет начианая с текущего - 0-й элемент строка "без годя/no year"
     */
    public static ArrayList<String> getYearsStrList(Resources res) {
        // Получаем текущий год
        Date currDate = new Date();                                                 // текущая дата
        SimpleDateFormat yearDateFormat = new SimpleDateFormat("yyyy");     // только год
        int nowYear = Integer.parseInt( yearDateFormat.format(currDate) );          // текущий год
        ArrayList<String> yearsStrList = new ArrayList<>();
        yearsStrList.add( res.getString(R.string.no_year) );                        // 0-й элемент строка "без годя/no year"
        for (int i=nowYear; i>=0; i--)
            yearsStrList.add( String.valueOf(i) );
        return yearsStrList;
    } // end_method

    /*
        Метод возвращает ArrayList<String> лет от 0-го года для +100 к текущему - в обратном порядке
    */
    public static ArrayList<String> getYearsFutureStrList() {
        // Получаем текущий год
        Date currDate = new Date();                                                 // текущая дата
        SimpleDateFormat yearDateFormat = new SimpleDateFormat("yyyy");     // только год
        int nowYear = Integer.parseInt( yearDateFormat.format(currDate) );          // текущий год
        ArrayList<String> yearsFutureStrList = new ArrayList<>();
        for (int i=(nowYear+100); i>=0; i--)
            yearsFutureStrList.add( String.valueOf(i) );
        return yearsFutureStrList;
    } // end_method

    /*
        Метод возвращает ArrayList<String> со списком месяцев
     */
    public static ArrayList<String> getMonthStrList(Resources res) {
        ArrayList<String> monthStrList = new ArrayList<>();
        monthStrList.add( res.getString(R.string.january_inf) );
        monthStrList.add( res.getString(R.string.february_inf) );
        monthStrList.add( res.getString(R.string.march_inf) );
        monthStrList.add( res.getString(R.string.april_inf) );
        monthStrList.add( res.getString(R.string.may_inf) );
        monthStrList.add( res.getString(R.string.june_inf) );
        monthStrList.add( res.getString(R.string.july_inf) );
        monthStrList.add( res.getString(R.string.august_inf) );
        monthStrList.add( res.getString(R.string.september_inf) );
        monthStrList.add( res.getString(R.string.october_inf) );
        monthStrList.add( res.getString(R.string.november_inf) );
        monthStrList.add( res.getString(R.string.december_inf) );
        return monthStrList;
    } // end_method

    /*
        Метод возвращает ArrayList<String> со списком дней для заданного месяцы в формате 2х знаков: 01, 02, 03 ... 29,30,31
     */
    public static ArrayList<String> getDayStrList(int month) {
        ArrayList<String> dayStrList = new ArrayList<>();

        // в аргументе month - номер месяца соответсвует реальному
        // Для всех месяцев - дни от 01 до 29
        for (int i=1; i<=29; i++) {
            String currDay = "";
            if ( i<10 ) currDay += "0" + i;
            else currDay += i;
            dayStrList.add( currDay );
        }
        // если февраль - завершаем заполнение
        if ( month==2 ) return dayStrList;
        // иначе добавляем 30е число
        dayStrList.add("30");
        // если 04-апрель ИЛИ 06-июнь ИЛИ 09-сетнябрь ИЛИ 11-ноябрь - завершаем запонение
        if ( month==4 || month==6 || month==9 || month==11 ) return dayStrList;
        // иначе добавляем 31-е число и завершаем обработку
        dayStrList.add("31");

        return dayStrList;
    } // end_method

    /*
        Метод возвращает номер месяца из даты в нотации БД
     */
    public static int getMonthFromDbDate(String date) {
        String monthPart = date.substring(0,2);
        return Integer.parseInt(monthPart);
    } // end_method

    /*
        Метод возвращает номер дня месяца из даты в нотации БД
     */
    public static int getDayFromDbDate(String date) {
        String dayPart = date.substring(3,5);
        return Integer.parseInt(dayPart);
    } // end_method

    /*
        Метод возвращаем номер текущего месяца
     */
    public static int getNowMonth() {
        Date currDate = new Date();                                                 // текущая дата
        SimpleDateFormat monthDateFormat = new SimpleDateFormat("MM");      // только месяц
        int nowMonth = Integer.parseInt( monthDateFormat.format(currDate) );        // текущий месяц

        return nowMonth;
    } // end_method

    /*
        Метод возвращаем номер текущего дня месяца
     */
    public static int getNowDay() {
        Date currDate = new Date();                                                 // текущая дата
        SimpleDateFormat dayDateFormat = new SimpleDateFormat("dd");      // только месяц
        int nowDay = Integer.parseInt( dayDateFormat.format(currDate) );        // текущий месяц

        return nowDay;
    } // end_method

    /*
        Метод возвращает текущую дату в нотации БД
     */
    public static String getNowDayDbNotation() {
        Date currDate = new Date();
        SimpleDateFormat dbDateFormat = new SimpleDateFormat("MM-dd");
        return dbDateFormat.format(currDate);
    } // end_method

    /*
        Метод возвращает строку даты (месяц-день) в нотации БД из даты в формателе Calendar
     */
    public static String getDBNotationDate(Calendar cal) {
        Date date = cal.getTime();
        SimpleDateFormat dbDateFormat = new SimpleDateFormat("MM-dd");
        return dbDateFormat.format(date);
    } // end_method

    /*
        Метод возвращает строку даты (месяц-день) в нотации БД и числового представления месяца и дня
     */
    public static String getDbNotationDate(int month, int day) {
        String date = "";
        // месяц
        if ( month<10 ) date += "0" + month;
        else date += month;
        // разделитель
        date += "-";
        // день
        if ( day<10 ) date += "0" + day;
        else date += day;
        return date;
    } // end_method

    /*
        Метод определяет, является ли год вискосным
     */
    public static boolean isLeapYear(int year) {
        Calendar calendar= Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    } // end_method


} // end_class






















