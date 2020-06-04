package com.menggp.abdcalendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.menggp.abdcalendar.datamodel.Event;
import com.menggp.abdcalendar.repository.DatabaseAdapter;

/*
    Класс описывает диалог подтвержения удаления
 */
public class EventDelConfirmationDialogFragment extends DialogFragment {

    private EventDelConfirmationDialogDatable eventDelConfirmationDialogDatable;

    // связываем интерфейс EventDelConfirmationDialogDatable с контекстом Activity (из которой вызывается диалог)
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        eventDelConfirmationDialogDatable = (EventDelConfirmationDialogDatable) context;
    } // end_method

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Получаем переданный в диалог ID события
        long eventId = getArguments().getLong("id");

        // Получаем событие из БД
        DatabaseAdapter dbAdapter = new DatabaseAdapter( this.getContext() );
        final Event event = dbAdapter.getEvent( eventId );

        // Получаем разметку и элемнты с нее
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate( R.layout.dialog_del_confirmation, null);
        ImageView eventImgView = (ImageView)view.findViewById(R.id.event_img_on_del_dialog);
        TextView eventNameView = (TextView)view.findViewById(R.id.event_name_on_del_dialog);

        // Помещаем данные на разметку
        eventImgView.setImageResource( event.getEventImg() );
        eventNameView.setText( event.getEventName() );

        // Слушатель действия - deleteAction
        DialogInterface.OnClickListener delAction = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), R.string.toast_del_accepted, Toast.LENGTH_SHORT).show();
                if ( eventId != 0 ) eventDelConfirmationDialogDatable.delEvent( eventId );
            }
        }; // end_listener

        // Создаем конструктор диалога и возвращаем построенный с его помощью диалог
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        return builder
                .setTitle( R.string.dialog_del_confirmation_title )             // заголовок
                .setIcon( R.drawable.act_bar_trash_bin )                        // иконка в заголовке
                .setView( view )                                                // разметка
                .setNegativeButton(R.string.dialog_del_action, delAction)       // delete
                .setPositiveButton(R.string.dialog_cancel_action, null) // cancel
                .create();
    } // end_method

} // end_class



































