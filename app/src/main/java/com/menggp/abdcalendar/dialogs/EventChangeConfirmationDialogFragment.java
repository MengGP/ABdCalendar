package com.menggp.abdcalendar.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.menggp.abdcalendar.R;

/*
    Диалог подтвержения сохранения внесенных изменений
        - вызывается при нажатии кнопки "back" на странице режактирования события, если были внесены измениния
 */
public class EventChangeConfirmationDialogFragment extends DialogFragment {

    private EventChangeConfirmationDialogDatable eventChangeConfirmationDialogDatable;

    // связываем интерфейс EventChangeConfirmationDialogDatable с контекстом Activity (из которой вызывается диалог)
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        eventChangeConfirmationDialogDatable = (EventChangeConfirmationDialogDatable) context;
    } // end_method

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Слушатель действия - NO
        DialogInterface.OnClickListener noAction = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), R.string.toast_event_change_no_saved, Toast.LENGTH_SHORT).show();
                eventChangeConfirmationDialogDatable.noSaveEventChage();
            }
        }; // end_listener

        // Слушатель действия - YES
        DialogInterface.OnClickListener yesAction = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eventChangeConfirmationDialogDatable.saveEventChage();
            }
        }; // end_listener

        // Создаем конструктор диалога и возвращаем построенный с его помощью диалог
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle( R.string.dialog_save_confirmation_title )                // заголовок
                .setIcon( R.drawable.act_bar_save_floppy )                          // иконка в заголовке
                //.setView( view )                                                  // разметка
                .setNeutralButton(R.string.dialog_cancel_action, null)      // CANCEL
                .setNegativeButton(R.string.dialog_no_action, noAction)         // NO
                .setPositiveButton(R.string.dialog_yes_action, yesAction)     // YES
                .create();

    } // end_method

} // end_class
