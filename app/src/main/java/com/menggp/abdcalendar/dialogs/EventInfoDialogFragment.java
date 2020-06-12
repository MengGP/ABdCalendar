package com.menggp.abdcalendar.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.menggp.abdcalendar.R;
import com.menggp.abdcalendar.datamodel.DateHandler;
import com.menggp.abdcalendar.datamodel.Event;
import com.menggp.abdcalendar.datamodel.EventAlertType;
import com.menggp.abdcalendar.datamodel.EventType;
import com.menggp.abdcalendar.repository.DatabaseAdapter;

/*
    Диалог с информацией о событии
 */
public class EventInfoDialogFragment extends DialogFragment {

    private EventInfoDialogDatable eventInfoDialogDatable;

    long eventId;

    // связываем интерфейс TypeFilterDialogDatable с контекстом Activity (из которой вызывается диалог)
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        eventInfoDialogDatable = (EventInfoDialogDatable) context;
    } // end_method

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Получаем переданный в диалог ID события
        eventId = getArguments().getLong("id");

        // Получаем событие из БД
        DatabaseAdapter dbAdapter = new DatabaseAdapter( this.getContext() );
        Event event = dbAdapter.getEvent( eventId );

        // создаем объект Resources для получения достпа к некоторым данным приложения
        Resources res = getResources();

        // Получем разметку и элементы разметки
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate( R.layout.dialog_event_info , null );
        TextView eventDateView = (TextView)view.findViewById(R.id.event_date_on_dialog_info);
        TextView eventSinceYearView = (TextView)view.findViewById(R.id.event_since_year_on_dialog_info);
        TextView eventTypeView = (TextView)view.findViewById(R.id.event_type_on_dialog_info);
        TextView eventAlertTypeView = (TextView)view.findViewById(R.id.event_alert_type_on_dialog_info);
        TextView eventCommentView = (TextView)view.findViewById(R.id.event_comment_on_dialog_info);

        // Заполняем данные на разметке
        // дата события
        eventDateView.setText(DateHandler.convertDbToHumanNotation(res, event.getEventDate()));
        // возраст события
        String eventAges="";
        if ( event.getEventSinceYear()==0 ) eventAges = "--";
        else {
            int pastYears = DateHandler.timePastYear( event.getEventSinceYear() );
            eventAges += pastYears+" ";
            eventAges += res.getQuantityText(R.plurals.years, pastYears) + " c ";
            eventAges += event.getEventSinceYear();
        }
        eventSinceYearView.setText( eventAges );
        // Тип события
        eventTypeView.setText(EventType.convertToHumanNotation(res, event.getEventType()));
        // Оповещение
        eventAlertTypeView.setText(EventAlertType.convertToHumanNotation(res, event.getEventAlertType()));
        // Комментарий
        eventCommentView.setText( event.getEventComment() );

        // Слушатель действия - delAction
        DialogInterface.OnClickListener delAction = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eventInfoDialogDatable.getDelConfirmationDialog(eventId);
            }
        }; // end_listener

        /*
        // Слушатель действия - yesAction
        DialogInterface.OnClickListener yesAction = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), R.string.toast_type_filter_changed, Toast.LENGTH_SHORT).show();
                // Устанавиваем типы событий в соответствии с выбором и передаем объект EventTypeFilter в активити
                typeFilter = new EventTypeFilter(
                        typeBirthdayBox.isChecked(),
                        typeAnniversaryBox.isChecked(),
                        typeMemodateBox.isChecked(),
                        typeHolydayBox.isChecked(),
                        typeOtherBox.isChecked()
                );

                typeFilterDialogDatable.updTypeFilter(typeFilter);
            }
        }; // end_listener

         */


        // Создаем конструктор диалога и возвращаем построенный с его помощью диалог
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        return builder
                .setTitle( event.getEventName() )                // заголовок
                .setIcon( event.getEventImg() )                                       // иконка в заголовке
                .setView( view )                                                    // разметка
                .setNeutralButton(R.string.dialog_del_action, delAction)       // сбросить фильтр
                // .setNegativeButton(R.string.dialog_back_action, null)     // отмена
                .setPositiveButton(R.string.dialog_back_action, null)        // да
                .create();


    } // end_method
} // end_class
