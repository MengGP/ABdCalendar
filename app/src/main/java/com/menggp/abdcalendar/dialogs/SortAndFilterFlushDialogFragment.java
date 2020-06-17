package com.menggp.abdcalendar.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.menggp.abdcalendar.R;

/*
    Диалог подтверждения сброса фильтров и сортировок
 */
public class SortAndFilterFlushDialogFragment extends DialogFragment {

    private SortAndFilterFlushDialogDatable sortAndFilterFlushDialogDatable;

    // связываем интерфейс SortAndFilterFlushDialogDatable с контекстом Activity (из которой вызывается диалог)
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sortAndFilterFlushDialogDatable = (SortAndFilterFlushDialogDatable) context;
    } // end_method

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Слушатель действия - yesAction
        DialogInterface.OnClickListener yesAction = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), R.string.toast_sort_and_filter_flush, Toast.LENGTH_SHORT).show();
                sortAndFilterFlushDialogDatable.flushSortAndFilter();
            }
        }; // end_listener


        // Создаем конструктор диалога и возвращаем построенный с его помощью диалог
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        return builder
                .setTitle( R.string.dialog_sort_and_filter_flush )                // заголовок
                .setIcon( R.drawable.clear )                                       // иконка в заголовке
                .setNegativeButton(R.string.dialog_no_action, null)     // отмена
                .setPositiveButton(R.string.dialog_yes_action, yesAction)        // да
                .create();

    } // end_method


} // end_class
