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

    // Конструктор без аргументов - по умолчанию все аттрибуты = false
    public EventTypeFilter() {
        this(false, false, false, false, false);
    } // end_constructor

    // Конструктор - с задаем всех аргументов
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

    /*
        Метод проверяет, все ли типы событий выбраны, или какой-то тип отфильтрован
     */
    public boolean filterExist() {
        if ( !this.birthdayOn ) return true;
        else if ( !this.anniversaryOn) return true;
        else if ( !this.memodateOn) return true;
        else if ( !this.holidayOn) return true;
        else if ( !this.otherOn) return true;

        return false;
    } // end_method

    /*
        Метод устанвливает все фильтры в FALSE
     */
    public void setAllFalse() {
        this.birthdayOn = false;
        this.anniversaryOn = false;
        this.memodateOn = false;
        this.holidayOn = false;
        this.otherOn = false;
    } // end_method

    /*
        Метод устанвливает все фильтры в TRUE
     */
    public void setAllTrue() {
        this.birthdayOn = true;
        this.anniversaryOn = true;
        this.memodateOn = true;
        this.holidayOn = true;
        this.otherOn = true;
    } // end_method

    /*
        Метод устанавливает занчаение TRUE - для укзанного типа события
     */
    public void setTypeTrue(EventType type) {
        // установка типа для текущего события
        switch ( type ) {
            case BIRTHDAY: this.setBirthdayOn(true); break;
            case ANNIVERSARY: this.setAnniversaryOn(true); break;
            case MEMODATE: this.setMemodateOn(true); break;
            case HOLIDAY: this.setHolidayOn(true); break;
            case OTHER: this.setOtherOn(true); break;
        }
    } // end_method

} // end_class
