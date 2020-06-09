package com.menggp.abdcalendar.datamodel;

/*
    Класс описывает настройки фитрации по типу события
 */
public class EventTypeFilter {

    private boolean birthdayOn;
    private boolean anniversaryOn;
    private boolean memodateOn;
    private boolean holidayOn;
    private boolean otherOn;

    public EventTypeFilter(boolean birthdayOn, boolean anniversaryOn, boolean memodateOn, boolean holidayOn, boolean otherOn) {
        this.birthdayOn = birthdayOn;
        this.anniversaryOn = anniversaryOn;
        this.memodateOn = memodateOn;
        this.holidayOn = holidayOn;
        this.otherOn = otherOn;
    } // end_constructor

    // --- getters and setters
    public boolean isBirthdayOn() {
        return birthdayOn;
    }

    public void setBirthdayOn(boolean birthdayOn) {
        this.birthdayOn = birthdayOn;
    }

    public boolean isAnniversaryOn() {
        return anniversaryOn;
    }

    public void setAnniversaryOn(boolean anniversaryOn) {
        this.anniversaryOn = anniversaryOn;
    }

    public boolean isMemodateOn() {
        return memodateOn;
    }

    public void setMemodateOn(boolean memodateOn) {
        this.memodateOn = memodateOn;
    }

    public boolean isHolidayOn() {
        return holidayOn;
    }

    public void setHolidayOn(boolean holidayOn) {
        this.holidayOn = holidayOn;
    }

    public boolean isOtherOn() {
        return otherOn;
    }

    public void setOtherOn(boolean otherOn) {
        this.otherOn = otherOn;
    }
    // --- end_getters_setters

} // end_class
