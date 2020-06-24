package com.menggp.abdcalendar.dialogs;

/*
     Вспомогательный интерфейс - для реализации взаиможействия между диалогом "EventOnDayDialogFragment" и вызывающими Activity
 */
public interface EventsOnDayDialogDatable {

    /*
        Метод переходит к диалогу с карточкой события
     */
    void getEventInfoDialogFromCalendarView(long id);

    /*
        Метод переходит к EventActivityInfo (активити с карточкой события)
     */
    void getEventActivityInfoFromCalendarView(long id);


} // end_interface
