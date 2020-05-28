package com.menggp.abdcalendar.datamodel;

/*
    Класс описывает данные табилцы "Events" БД
 */
public class Event {

    // Аттрибуты класса
    private long id;                        // '_id' - INTEGER not null             >>> ID в БД
    private String eventName;               // 'event_name' - TEXT not null         >>> Имя события
    private String eventDate;               // 'event_date' - TEXT not null         >>> дата события - месяц + день
    private EventType eventType;            // 'event_type' - TEXT not null         >>> тип события - enum из списка
    private int eventSinceYear;             // 'event_since_year' - INTEGER         >>> год начала/отсчета события
    private String eventComment;            // 'event_comment' - TEXT               >>> комментарий
    private int eventImg;                   // 'event_img' - INTEGER not null       >>> изображение для события
    private EventAlertType eventAlertType;  // 'event_alert_type' - TEXT not null   >>> тип напоминания для события - enum из списка

    // Конструктор с полным наборос аргументов
    public Event(long id, String eventName, String eventDate, EventType eventType, int eventSinceYear, String eventComment, int eventImg, EventAlertType eventAlertType) {
        this.id = id;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventType = eventType;
        this.eventSinceYear = eventSinceYear;
        this.eventComment = eventComment;
        this.eventImg = eventImg;
        this.eventAlertType = eventAlertType;
    } // end_constructor

    // Конструктор - без 'event_since_year'
    public Event(long id, String eventName, String eventDate, EventType eventType, String eventComment, int eventImg, EventAlertType eventAlertType) {
        this(id, eventName, eventDate, eventType, 0, eventComment, eventImg, eventAlertType);
    } // end_constructor

    // Конструктора - без 'event_comment'
    public Event(long id, String eventName, String eventDate, EventType eventType, int eventSinceYear, int eventImg, EventAlertType eventAlertType) {
        this(id, eventName, eventDate, eventType, eventSinceYear, null, eventImg, eventAlertType);
    } // end_constructor

    // Конструктор без 'event_since_year' и 'event_comment'
    public Event(long id, String eventName, String eventDate, EventType eventType, int eventImg, EventAlertType eventAlertType) {
        this(id, eventName, eventDate, eventType, 0, null, eventImg, eventAlertType);
    } // end_constructor

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public int getEventSinceYear() {
        return eventSinceYear;
    }

    public void setEventSinceYear(int eventSinceYear) {
        this.eventSinceYear = eventSinceYear;
    }

    public String getEventComment() {
        return eventComment;
    }

    public void setEventComment(String eventComment) {
        this.eventComment = eventComment;
    }

    public int getEventImg() {
        return eventImg;
    }

    public void setEventImg(int eventImg) {
        this.eventImg = eventImg;
    }

    public EventAlertType getEventAlertType() {
        return eventAlertType;
    }

    public void setEventAlertType(EventAlertType eventAlertType) {
        this.eventAlertType = eventAlertType;
    }
    // --- end_getters_setters

} // end_class
