package com.menggp.abdcalendar;

import com.menggp.abdcalendar.datamodel.EventTypeFilter;

/*
    Вспомогательный интерфейс - для реализации взаиможействия между диалогом "TypeFilterDialogFragment" и вызывающими Activity
 */
public interface TypeFilterDialogDatable {

    // Метод обновлениея данных фильтрации по типу
    public void updTypeFilterPrefs(EventTypeFilter typeFilter);

} // end_interface
