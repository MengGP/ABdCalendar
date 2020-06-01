package com.menggp.abdcalendar.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.menggp.abdcalendar.R;
import com.menggp.abdcalendar.datamodel.DateConverter;
import com.menggp.abdcalendar.datamodel.Event;
import com.menggp.abdcalendar.datamodel.EventAlertType;
import com.menggp.abdcalendar.datamodel.EventType;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/*
    Адапетер для списка объектов класа Event
        - оптимизирован с ViewHolder
        - разметка: list_item_event
 */
public class EventListAdapter extends ArrayAdapter<Event> {

    private LayoutInflater inflater;
    private int layout;
    private List<Event> events;

    public EventListAdapter(@NonNull Context context, int resource, List<Event> events) {
        super(context, resource, events);
        this.events = events;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    } // end_constructor

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,ViewGroup parent) {

        // Оптимизация с помошью ViewHolde-а
        ViewHolder viewHolder;
        if (convertView==null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Event event = events.get( position );

        // Создаем объект resource - для использования при конвертации
        Resources res = convertView.getResources();

        // Изображение события на разметке
        viewHolder.eventImgOnList.setImageResource( event.getEventImg() );
        // Имя события на разметке
        viewHolder.eventNameOnList.setText( event.getEventName() );
        // Дата события на разметке - в отформатированном виде
        viewHolder.eventDateOnList.setText(
                DateConverter.convertDbNotationToItemNotation( res, event.getEventDate() )
        );

        // Количество оставшихся дней до события
        int deltaDays = DateConverter.timeLeftToEvent( event.getEventDate() );
        String dayLeftSrt = "";
        if (deltaDays==-1)  dayLeftSrt = "Сегодня";
        else  dayLeftSrt = deltaDays+"дней";
        viewHolder.eventLeftTimeOnList.setText( dayLeftSrt );


        viewHolder.eventTypeOnList.setText(EventType.convertToString( event.getEventType() ));
        viewHolder.eventAlertTypeOnList.setText(EventAlertType.convertToString( event.getEventAlertType()) );
        viewHolder.eventPastYearsOnList.setText( "--" );
        viewHolder.eventYearsOnList.setText("years");
        viewHolder.eventSinceYearOnList.setText( String.valueOf(event.getEventSinceYear()));

        return convertView;
    } // end_method

    // Приватный класс описывающий ViewHolder
    private  class ViewHolder {
        // Элементы разметки
        final ImageView eventImgOnList;
        final TextView eventNameOnList, eventDateOnList, eventLeftTimeOnList, eventTypeOnList, eventAlertTypeOnList, eventPastYearsOnList, eventYearsOnList, eventSinceYearOnList;
        ViewHolder(View view) {
            eventImgOnList = (ImageView) view.findViewById(R.id.event_img_on_list);
            eventNameOnList = (TextView) view.findViewById(R.id.event_name_on_list);
            eventDateOnList = (TextView) view.findViewById(R.id.event_date_on_list);
            eventLeftTimeOnList = (TextView) view.findViewById(R.id.event_left_time_on_list);
            eventTypeOnList = (TextView) view.findViewById(R.id.event_type_on_list);
            eventAlertTypeOnList = (TextView) view.findViewById(R.id.event_alert_type_on_list);
            eventPastYearsOnList= (TextView) view.findViewById(R.id.event_past_years_on_list);
            eventYearsOnList = (TextView) view.findViewById(R.id.event_years_on_list);
            eventSinceYearOnList = (TextView) view.findViewById(R.id.event_since_year_on_list);
        } // end_constructor
    } // end_private_class

} // end_class




























