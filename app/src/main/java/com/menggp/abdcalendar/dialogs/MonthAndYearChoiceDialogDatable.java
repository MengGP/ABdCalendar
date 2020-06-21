package com.menggp.abdcalendar.dialogs;

import java.util.Calendar;

/*
    Вспомогательный интерфейс - для реализации взаиможействия между диалогом "MonthChoiceDialogFragment" и вызывающими Activity
 */
public interface MonthAndYearChoiceDialogDatable {

    // Установка месяца на виде календаря
    void selectMonthAndYearOnCalendarView(Calendar currCal);

} // end_interface
