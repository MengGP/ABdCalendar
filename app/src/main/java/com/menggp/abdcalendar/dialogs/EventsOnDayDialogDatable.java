package com.menggp.abdcalendar.dialogs;

/*
     Вспомогательный интерфейс - для реализации взаиможействия между диалогом "EventOnDayDialogFragment" и вызывающими Activity
 */
public interface EventsOnDayDialogDatable {

    /*
        Метод отображет диалог с карточкой события
     */
    void getEventInfoDialogFromCalendarView(long id);


} // end_interface
