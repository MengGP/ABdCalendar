package com.menggp.abdcalendar.dialogs;

import com.menggp.abdcalendar.datamodel.Event;

/*
    Вспомогательный интерфейс - для реализации взаиможействия между диалогом "delConfirmationDialogFragment" и вызывающими Activity
 */
public interface EventDelConfirmationDialogDatable {

    // Метод - удаления сообытия
    void delEvent(long eventId);

} // end_interface
