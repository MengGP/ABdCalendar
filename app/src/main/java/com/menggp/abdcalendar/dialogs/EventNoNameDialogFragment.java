package com.menggp.abdcalendar.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.menggp.abdcalendar.R;

/*
    Диалог - предупреждает, если не заполнено имя событи
 */
public class EventNoNameDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Создаем конструктор диалога и возвращаем построенный с его помощью диалог
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle( R.string.dialog_no_name_alert_title )                // заголовок
                .setIcon( R.drawable.about_program )                          // иконка в заголовке
                //.setView( view )                                                  // разметка
                .setPositiveButton(R.string.dialog_yes_action, null)     // YES
                .create();
    } // end_method

} // end_class
