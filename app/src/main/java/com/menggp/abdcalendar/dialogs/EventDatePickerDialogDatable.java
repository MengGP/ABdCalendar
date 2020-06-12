package com.menggp.abdcalendar.dialogs;

/*
    Вспомогательный интерфейс - для реализации взаиможействия между диалогом "EventDatePickerDialogFragment" и вызывающими Activity
 */
public interface EventDatePickerDialogDatable {

    // Метод - установки выбранной даты события
    void setEventDateOnEdit(int month, int day);

} // end_interface
