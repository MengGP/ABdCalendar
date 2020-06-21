package com.menggp.abdcalendar.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.menggp.abdcalendar.MainActivity;
import com.menggp.abdcalendar.R;

import java.util.Calendar;

/*
    Диалог выбора отображаемого месяца на виде календаря
 */
public class MonthChoiceDialogFragment extends DialogFragment {

    private MonthAndYearChoiceDialogDatable monthAndYearChoiceDialogDatable;

    int currYear;
    int currMonth;

    // связываем интерфейс TypeFilterDialogDatable с контекстом Activity (из которой вызывается диалог)
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        monthAndYearChoiceDialogDatable = (MonthAndYearChoiceDialogDatable) context;
    } // end_method

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Получаем переданные в диалог данные
        Bundle args = getArguments();
        currYear = args.getInt(MainActivity.CURR_YEAR_VIEW);
        currMonth = args.getInt(MainActivity.CURR_MONTH_ON_VIEW);

        // Получаем разметк и ее элементы
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate( R.layout.dialog_month_choice, null );
        RelativeLayout setNowDate = view.findViewById(R.id.dialog_set_now_date);
        TextView month01 = view.findViewById(R.id.month_choice_january);
        TextView month02 = view.findViewById(R.id.month_choice_february);
        TextView month03 = view.findViewById(R.id.month_choice_march);
        TextView month04 = view.findViewById(R.id.month_choice_april);
        TextView month05 = view.findViewById(R.id.month_choice_may);
        TextView month06 = view.findViewById(R.id.month_choice_june);
        TextView month07 = view.findViewById(R.id.month_choice_july);
        TextView month08 = view.findViewById(R.id.month_choice_august);
        TextView month09 = view.findViewById(R.id.month_choice_september);
        TextView month10 = view.findViewById(R.id.month_choice_october);
        TextView month11 = view.findViewById(R.id.month_choice_november);
        TextView month12 = view.findViewById(R.id.month_choice_december);

        // Для текущего месяца - меняем на разметке фон и цвет текста
        Resources res = getResources();
        switch (currMonth) {
            case 0:
                month01.setBackgroundColor( res.getColor(R.color.gray_dark) );
                month01.setTextColor( res.getColor(R.color.black ));
                break;
            case 1:
                month02.setBackgroundColor( res.getColor(R.color.gray_dark) );
                month02.setTextColor( res.getColor(R.color.black ));
                break;
            case 2:
                month03.setBackgroundColor( res.getColor(R.color.gray_dark) );
                month03.setTextColor( res.getColor(R.color.black ));
                break;
            case 3:
                month04.setBackgroundColor( res.getColor(R.color.gray_dark) );
                month04.setTextColor( res.getColor(R.color.black ));
                break;
            case 4:
                month05.setBackgroundColor( res.getColor(R.color.gray_dark) );
                month05.setTextColor( res.getColor(R.color.black ));
                break;
            case 5:
                month06.setBackgroundColor( res.getColor(R.color.gray_dark) );
                month06.setTextColor( res.getColor(R.color.black ));
                break;
            case 6:
                month07.setBackgroundColor( res.getColor(R.color.gray_dark) );
                month07.setTextColor( res.getColor(R.color.black ));
                break;
            case 7:
                month08.setBackgroundColor( res.getColor(R.color.gray_dark) );
                month08.setTextColor( res.getColor(R.color.black ));
                break;
            case 8:
                month09.setBackgroundColor( res.getColor(R.color.gray_dark) );
                month09.setTextColor( res.getColor(R.color.black ));
                break;
            case 9:
                month10.setBackgroundColor( res.getColor(R.color.gray_dark) );
                month10.setTextColor( res.getColor(R.color.black ));
                break;
            case 10:
                month11.setBackgroundColor( res.getColor(R.color.gray_dark) );
                month11.setTextColor( res.getColor(R.color.black ));
                break;
            case 11:
                month12.setBackgroundColor( res.getColor(R.color.gray_dark) );
                month12.setTextColor( res.getColor(R.color.black ));
                break;
        }

        // Слушатель установки текущего месяца
        setNowDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthAndYearChoiceDialogDatable.selectMonthAndYearOnCalendarView( Calendar.getInstance() );
                dismiss();
            }
        });

        // Слушатели выбора месяца
        month01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currCal = Calendar.getInstance();
                currCal.set(currYear, 0, 1); // текущий год
                monthAndYearChoiceDialogDatable.selectMonthAndYearOnCalendarView(currCal);
                dismiss();
            }
        });

        month02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currCal = Calendar.getInstance();
                currCal.set(currYear, 1, 1); // текущий год
                monthAndYearChoiceDialogDatable.selectMonthAndYearOnCalendarView(currCal);
                dismiss();
            }
        });

        month03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currCal = Calendar.getInstance();
                currCal.set(currYear, 2, 1); // текущий год
                monthAndYearChoiceDialogDatable.selectMonthAndYearOnCalendarView(currCal);
                dismiss();
            }
        });

        month04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currCal = Calendar.getInstance();
                currCal.set(currYear, 3, 1); // текущий год
                monthAndYearChoiceDialogDatable.selectMonthAndYearOnCalendarView(currCal);
                dismiss();
            }
        });

        month05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currCal = Calendar.getInstance();
                currCal.set(currYear, 4, 1); // текущий год
                monthAndYearChoiceDialogDatable.selectMonthAndYearOnCalendarView(currCal);
                dismiss();
            }
        });

        month06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currCal = Calendar.getInstance();
                currCal.set(currYear, 5, 1); // текущий год
                monthAndYearChoiceDialogDatable.selectMonthAndYearOnCalendarView(currCal);
                dismiss();
            }
        });

        month07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currCal = Calendar.getInstance();
                currCal.set(currYear, 6, 1); // текущий год
                monthAndYearChoiceDialogDatable.selectMonthAndYearOnCalendarView(currCal);
                dismiss();
            }
        });

        month08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currCal = Calendar.getInstance();
                currCal.set(currYear, 7, 1); // текущий год
                monthAndYearChoiceDialogDatable.selectMonthAndYearOnCalendarView(currCal);
                dismiss();
            }
        });

        month09.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currCal = Calendar.getInstance();
                currCal.set(currYear, 8, 1); // текущий год
                monthAndYearChoiceDialogDatable.selectMonthAndYearOnCalendarView(currCal);
                dismiss();
            }
        });

        month10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currCal = Calendar.getInstance();
                currCal.set(currYear, 9, 1); // текущий год
                monthAndYearChoiceDialogDatable.selectMonthAndYearOnCalendarView(currCal);
                dismiss();
            }
        });

        month11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currCal = Calendar.getInstance();
                currCal.set(currYear, 10, 1); // текущий год
                monthAndYearChoiceDialogDatable.selectMonthAndYearOnCalendarView(currCal);
                dismiss();
            }
        });

        month12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currCal = Calendar.getInstance();
                currCal.set(currYear, 11, 1); // текущий год
                monthAndYearChoiceDialogDatable.selectMonthAndYearOnCalendarView(currCal);
                dismiss();
            }
        });

        /*
        // Слушательль для блока "Снять все"
        RelativeLayout deselectAllBlock = (RelativeLayout)view.findViewById(R.id.dialog_deselect_all_types);
        View.OnClickListener onDeselectAllClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeBirthdayBox.setChecked( false );
                typeAnniversaryBox.setChecked( false );
                typeMemodateBox.setChecked( false );
                typeHolydayBox.setChecked( false );
                typeOtherBox.setChecked( false );
            }
        };
        deselectAllBlock.setOnClickListener( onDeselectAllClickListener );

        // Слушательль для блока "Выделить все"
        RelativeLayout selectAllBlock = (RelativeLayout)view.findViewById(R.id.dialog_select_all_types);
        View.OnClickListener onSelectAllClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeBirthdayBox.setChecked( true );
                typeAnniversaryBox.setChecked( true );
                typeMemodateBox.setChecked( true );
                typeHolydayBox.setChecked( true );
                typeOtherBox.setChecked( true );
            }
        };
        selectAllBlock.setOnClickListener( onSelectAllClickListener );
        */



        // Создаем конструктор диалога и возвращаем построенный с его помощью диалог
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        return builder
                .setTitle( R.string.dialog_month_choice_title )                // заголовок
                .setIcon( R.drawable.month_choice )                            // иконка в заголовке
                .setView( view )                                            // разметка
                .setPositiveButton(R.string.dialog_cancel_action, null)   // да
                .create();

    } // end_method
} // end_class























