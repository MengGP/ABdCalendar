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
    Диалог подтверждения полной очистки даннных
 */
public class DelAllDataDialogFragment extends DialogFragment {

    DelAllDataDialogDatable delAllDataDialogDatable;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        delAllDataDialogDatable = (DelAllDataDialogDatable) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Слушатель действия - flushAction
        DialogInterface.OnClickListener hardResetAction = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), R.string.toast_del_all_data, Toast.LENGTH_SHORT).show();
                delAllDataDialogDatable.hardResetData();
            }
        }; // end_listener

        // Создаем конструктор диалога и возвращаем построенный с его помощью диалог
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        return builder
                .setTitle( R.string.dialog_del_all_data_title)                      // заголовок
                .setIcon( R.drawable.delete )                                       // иконка в заголовке
                .setMessage( R.string.dialog_del_all_data )                         // текст сообщения окна
                .setNeutralButton(R.string.dialog_del_action, hardResetAction)    // удаление данных
                .setPositiveButton(R.string.dialog_cancel_action, null)     // отмена
                .create();

    } // end_method


} // end_class
