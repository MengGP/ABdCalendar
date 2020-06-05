package com.menggp.abdcalendar;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/*
    Класс дилого выбора даты события:
        - в виде 2х спиннеров - день / месяц
 */
public class EventDatePickerDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Получаем переданные в диалог данные
        int eventDay = getArguments().getInt("day");
        int eventMonth = getArguments().getInt("month");

        // Получаем разметку и элемнты с нее
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate( R.layout.dialog_del_confirmation, null);
        ImageView eventImgView = (ImageView)view.findViewById(R.id.event_img_on_del_dialog);
        TextView eventNameView = (TextView)view.findViewById(R.id.event_name_on_del_dialog);


        return super.onCreateDialog(savedInstanceState);
    } // end_method

} // end_class
