package com.menggp.abdcalendar;

import com.menggp.abdcalendar.datamodel.EventMonthFilter;
import com.menggp.abdcalendar.datamodel.EventTypeFilter;

/*
    Вспомогательный интерфейс - для реализации взаиможействия между диалогом "MonthFilterDialogFragment" и вызывающими Activity
 */
public interface MonthFilterDialogDatable {

    // Метод обновлениея данных фильтрации по месяцу
    void updMonthFilter(EventMonthFilter monthFilter);


} // end_class
