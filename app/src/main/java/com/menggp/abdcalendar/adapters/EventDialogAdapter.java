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
import com.menggp.abdcalendar.datamodel.DateHandler;
import com.menggp.abdcalendar.datamodel.Event;
import com.menggp.abdcalendar.datamodel.EventType;

import java.util.List;

/*
    Адаптер списка событий - для диалога отображения событий для выбранной на календере даты, в режиме календаря
        - оптимизирован с ViewHolder
        - разметка: list_item_event_calendar
 */
public class EventDialogAdapter extends ArrayAdapter<Event> {

    private LayoutInflater inflater;
    private int layout;
    private List<Event> events;

    public EventDialogAdapter(@NonNull Context context, int resource, List<Event> events) {
        super(context, resource, events);
        this.events = events;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    } // end_constructor

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Оптимизация с помошью ViewHolder
        EventDialogAdapter.ViewHolder viewHolder;
        if (convertView==null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new EventDialogAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (EventDialogAdapter.ViewHolder) convertView.getTag();

        Event event = events.get( position );

        // Создаем объект resource - для использования при конвертации
        Resources res = convertView.getResources();


        // Изображение события на разметке
        viewHolder.eventImgOnList.setImageResource( event.getEventImg() );

        // Имя события на разметке
        viewHolder.eventNameOnList.setText( event.getEventName() );

        // Количество оставшихся дней до события
        int deltaDays = DateHandler.timeLeftToEvent( event.getEventDate() );
        String dayLeftSrt = "";
        if (deltaDays==-1)  dayLeftSrt = res.getString(R.string.today);
        else dayLeftSrt = res.getQuantityString(R.plurals.days, deltaDays, deltaDays);
        viewHolder.eventLeftTimeOnList.setText( dayLeftSrt );

        // Тип события
        viewHolder.eventTypeOnList.setText(EventType.convertToHumanNotation(res, event.getEventType()));

        // Иконка типа события
        switch (event.getEventType()){
            case BIRTHDAY: viewHolder.eventTypeIconOnList.setImageResource(R.drawable.i1_1_b); break;
            case ANNIVERSARY: viewHolder.eventTypeIconOnList.setImageResource(R.drawable.i2_1_a); break;
            case MEMODATE: viewHolder.eventTypeIconOnList.setImageResource(R.drawable.i4_1_m); break;
            case HOLIDAY: viewHolder.eventTypeIconOnList.setImageResource(R.drawable.i3_1_h); break;
            case OTHER: viewHolder.eventTypeIconOnList.setImageResource(R.drawable.i4_1_m); break;
        }

        // Количество лет событию и подпись
        if ( event.getEventSinceYear()==0 ) {
            viewHolder.eventPastYearsOnList.setText("--");
            viewHolder.eventSinceYearPrefixOnList.setText("");
            viewHolder.eventSinceYearOnList.setText("");
        } else {
            int pastYears = DateHandler.timePastYear( event.getEventSinceYear() );
            viewHolder.eventPastYearsOnList.setText( String.valueOf(pastYears) );
            // Год начала события
            viewHolder.eventSinceYearPrefixOnList.setText(R.string.lower_since_with_space);
            viewHolder.eventSinceYearOnList.setText( String.valueOf(event.getEventSinceYear()));
        }

        return convertView;
    } // end_method

    /*
        Приватный класс описывающий ViewHolder
    */
    private class ViewHolder {
        // Элементы разметки
        final ImageView eventImgOnList;
        final ImageView eventTypeIconOnList;
        final TextView eventNameOnList;
        final TextView eventLeftTimeOnList;
        final TextView eventTypeOnList;
        final TextView eventPastYearsOnList;
        final TextView eventSinceYearPrefixOnList;
        final TextView eventSinceYearOnList;
        ViewHolder(View view) {
            eventImgOnList = (ImageView) view.findViewById(R.id.event_img_on_list);
            eventNameOnList = (TextView) view.findViewById(R.id.event_name_on_list);
            eventLeftTimeOnList = (TextView) view.findViewById(R.id.event_left_time_on_list);
            eventTypeOnList = (TextView) view.findViewById(R.id.event_type_on_list);
            eventPastYearsOnList= (TextView) view.findViewById(R.id.event_past_years_on_list);
            eventSinceYearPrefixOnList = (TextView) view.findViewById(R.id.event_since_year_prefix_on_list);
            eventSinceYearOnList = (TextView) view.findViewById(R.id.event_since_year_on_list);
            eventTypeIconOnList = (ImageView) view.findViewById(R.id.event_type_icon_on_list);
        } // end_constructor
    } // end_private_class

} // end_class
