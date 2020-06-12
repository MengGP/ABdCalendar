package com.menggp.abdcalendar.dialogs;

/*
    Вспомогательный интерфейс - для реализации взаиможействия между диалогом "EventInfoDialogFragment" и вызывающими Activity
 */
public interface EventInfoDialogDatable {

    // метод вызывает диалог подтверждения удаления события
    void getDelConfirmationDialog(long id);

} // end_interface
