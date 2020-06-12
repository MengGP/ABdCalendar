package com.menggp.abdcalendar.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.menggp.abdcalendar.R;

/*
    Диалог выбора типа сортировки
 */
public class SortDialogFragment extends DialogFragment {

    private SortDialogDatable sortDialogDatable;

    private static final String EV_SORT_TYPE = "ev_sort_type";

    RadioGroup sortTypeSwitchBox;
    RadioButton sortBoxType0;
    RadioButton sortBoxType1;
    RadioButton sortBoxType2;

    int sortType;

    // связываем интерфейс SortDialogDatable с контекстом Activity (из которой вызывается диалог)
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sortDialogDatable = (SortDialogDatable) context;
    } // end_method

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // получаем переданные в диалог данные
        Bundle args = getArguments();
        sortType = args.getInt(EV_SORT_TYPE);

        // Получаем разметку и элементы
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate( R.layout.dialog_sort, null);
        sortTypeSwitchBox = (RadioGroup) view.findViewById(R.id.sort_type_switch);
        sortBoxType0 = (RadioButton) view.findViewById(R.id.sort_type_0);
        sortBoxType1 = (RadioButton) view.findViewById(R.id.sort_type_1);
        sortBoxType2 = (RadioButton) view.findViewById(R.id.sort_type_2);

        // Начальные данные
        switch (sortType) {
            case 0: sortBoxType0.setChecked(true); break;
            case 1: sortBoxType1.setChecked(true); break;
            case 2: sortBoxType2.setChecked(true); break;
        }

        // Слушатель действия - flushAction
        DialogInterface.OnClickListener flushAction = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), R.string.toast_sort_default, Toast.LENGTH_SHORT).show();
                sortType = 0;
                sortDialogDatable.updSortType(sortType);
            }
        }; // end_listener

        // Слушатель действия - yesAction
        DialogInterface.OnClickListener yesAction = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), R.string.toast_sort_changed, Toast.LENGTH_SHORT).show();
                // считываем в аттрибут sortType выбранные вараинт
                switch (sortTypeSwitchBox.getCheckedRadioButtonId() ) {
                    case R.id.sort_type_0: sortType=0; break;
                    case R.id.sort_type_1: sortType=1; break;
                    case R.id.sort_type_2: sortType=2; break;
                }
                sortDialogDatable.updSortType(sortType);
            }
        }; // end_listener

        // Создаем конструктор диалога и возвращаем построенный с его помощью диалог
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        return builder
                .setTitle( R.string.dialog_sort )                // заголовок
                .setIcon( R.drawable.sort )                                       // иконка в заголовке
                .setView( view )                                                    // разметка
                .setNeutralButton(R.string.dialog_flush_action, flushAction)       // сбросить фильтр
                .setNegativeButton(R.string.dialog_cancel_action, null)     // отмена
                .setPositiveButton(R.string.dialog_yes_action, yesAction)        // да
                .create();

    } // end_class

} // end_class
