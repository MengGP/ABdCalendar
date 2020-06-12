package com.menggp.abdcalendar.dialogs;

/*
    Вспомогательный интерфейс - для реализации взаиможействия между диалогом "EventChangeConfirmationDialogFragment" и вызывающими Activity
 */
public interface EventChangeConfirmationDialogDatable {

    // Метод - закрытие редактора события без сохраения изменений
    void noSaveEventChage();

    // Метод - закрытие редактора события c сохраениенм изменений
    void saveEventChage();

} // end_interface
