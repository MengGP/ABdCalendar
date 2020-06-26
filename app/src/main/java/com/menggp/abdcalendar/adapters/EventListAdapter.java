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
import com.menggp.abdcalendar.datamodel.EventAlertType;
import com.menggp.abdcalendar.datamodel.EventMonthFilter;
import com.menggp.abdcalendar.datamodel.EventType;
import com.menggp.abdcalendar.datamodel.EventTypeFilter;
import com.menggp.abdcalendar.repository.DatabaseAdapter;

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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

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
                DateHandler.convertDbToHumanNotation( res, event.getEventDate() )
        );

        // Количество оставшихся дней до события
        int deltaDays = DateHandler.timeLeftToEvent( event.getEventDate() );
        String dayLeftSrt = "";
        if (deltaDays==-1)  dayLeftSrt = res.getString(R.string.today);
        else dayLeftSrt = res.getQuantityString(R.plurals.days, deltaDays, deltaDays);
        viewHolder.eventLeftTimeOnList.setText( dayLeftSrt );

        // Тип события
        viewHolder.eventTypeOnList.setText(EventType.convertToHumanNotation(res, event.getEventType()));

        // Тип напоминания
        viewHolder.eventAlertTypeOnList.setText(EventAlertType.convertToHumanNotation(res, event.getEventAlertType()) );

        // Количество лет событию и подпись
        if ( event.getEventSinceYear()==0 ) {
            viewHolder.eventPastYearsOnList.setText("--");
            viewHolder.eventYearsOnList.setText("");
            viewHolder.eventSinceYearPrefixOnList.setText("");
            viewHolder.eventSinceYearOnList.setText("");
        } else {
            int pastYears = DateHandler.timePastYear( event.getEventSinceYear() );
            viewHolder.eventPastYearsOnList.setText( String.valueOf(pastYears) );
            viewHolder.eventYearsOnList.setText( res.getQuantityText(R.plurals.years, pastYears) );
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
        final TextView eventNameOnList;
        final TextView eventDateOnList;
        final TextView eventLeftTimeOnList;
        final TextView eventTypeOnList;
        final TextView eventAlertTypeOnList;
        final TextView eventPastYearsOnList;
        final TextView eventYearsOnList;
        final TextView eventSinceYearPrefixOnList;
        final TextView eventSinceYearOnList;
        ViewHolder(View view) {
            eventImgOnList = (ImageView) view.findViewById(R.id.event_img_on_list);
            eventNameOnList = (TextView) view.findViewById(R.id.event_name_on_list);
            eventDateOnList = (TextView) view.findViewById(R.id.event_date_on_list);
            eventLeftTimeOnList = (TextView) view.findViewById(R.id.event_left_time_on_list);
            eventTypeOnList = (TextView) view.findViewById(R.id.event_type_on_list);
            eventAlertTypeOnList = (TextView) view.findViewById(R.id.event_alert_type_on_list);
            eventPastYearsOnList= (TextView) view.findViewById(R.id.event_past_years_on_list);
            eventYearsOnList = (TextView) view.findViewById(R.id.event_years_on_list);
            eventSinceYearPrefixOnList = (TextView) view.findViewById(R.id.event_since_year_prefix_on_list);
            eventSinceYearOnList = (TextView) view.findViewById(R.id.event_since_year_on_list);
        } // end_constructor
    } // end_private_class

    /*
        Метод обновляет данные адаптера при задании фитрации и сортировки
            типы сортировки для аргумента sortType:
                0 - по умолчанию - от текущей даты
                1 - от начала года
                2 - по имени по возрастанию
     */
    public void updAdapterData(String nameStrFilter, EventTypeFilter typeFilter, EventMonthFilter monthFilter, int sortType) {
        // Создаем связь с БД через DatabaseAdapter
        DatabaseAdapter dbAdapter = new DatabaseAdapter(this.getContext());

        // Очищаем данные адаптера
        this.clear();

        // Читаем в данные в адаптер с заданными параметрами фильтрации и сортировки
        if (nameStrFilter.isEmpty() ) this.addAll( dbAdapter.getEvents(typeFilter, monthFilter, sortType) );
        else this.addAll( dbAdapter.getEvents(nameStrFilter, typeFilter, monthFilter, sortType) );

        // Актуализируем выводимые адаптером данные
        this.notifyDataSetChanged();
    } // end_method

} // end_class




























