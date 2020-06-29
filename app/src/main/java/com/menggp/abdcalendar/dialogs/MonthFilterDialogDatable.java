package com.menggp.abdcalendar.dialogs;

import com.menggp.abdcalendar.datamodel.EventMonthFilter;

/*
    Вспомогательный интерфейс - для реализации взаиможействия между диалогом "MonthFilterDialogFragment" и вызывающими Activity
 */
public interface MonthFilterDialogDatable {

    // Метод обновлениея данных фильтрации по месяцу
    void updMonthFilter(EventMonthFilter monthFilter);

} // end_class
