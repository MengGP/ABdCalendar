package com.menggp.abdcalendar.datamodel;

/*
    Класс описыает настройки фильтрации событий по месяцам
 */
public class EventMonthFilter {

    private boolean month01;    // январь
    private boolean month02;    // февраль
    private boolean month03;    // март
    private boolean month04;    // апрель
    private boolean month05;    // май
    private boolean month06;    // июнь
    private boolean month07;    // июль
    private boolean month08;    // август
    private boolean month09;    // сентябрь
    private boolean month10;    // октябрь
    private boolean month11;    // ноябрь
    private boolean month12;    // декабрь

    public EventMonthFilter(boolean month01,
                            boolean month02,
                            boolean month03,
                            boolean month04,
                            boolean month05,
                            boolean month06,
                            boolean month07,
                            boolean month08,
                            boolean month09,
                            boolean month10,
                            boolean month11,
                            boolean month12)
    {
        this.month01 = month01;
        this.month02 = month02;
        this.month03 = month03;
        this.month04 = month04;
        this.month05 = month05;
        this.month06 = month06;
        this.month07 = month07;
        this.month08 = month08;
        this.month09 = month09;
        this.month10 = month10;
        this.month11 = month11;
        this.month12 = month12;
    } // end_constructor

    // --- Getters and setters


    public boolean isMonth01() {
        return month01;
    }

    public void setMonth01(boolean month01) {
        this.month01 = month01;
    }

    public boolean isMonth02() {
        return month02;
    }

    public void setMonth02(boolean month02) {
        this.month02 = month02;
    }

    public boolean isMonth03() {
        return month03;
    }

    public void setMonth03(boolean month03) {
        this.month03 = month03;
    }

    public boolean isMonth04() {
        return month04;
    }

    public void setMonth04(boolean month04) {
        this.month04 = month04;
    }

    public boolean isMonth05() {
        return month05;
    }

    public void setMonth05(boolean month05) {
        this.month05 = month05;
    }

    public boolean isMonth06() {
        return month06;
    }

    public void setMonth06(boolean month06) {
        this.month06 = month06;
    }

    public boolean isMonth07() {
        return month07;
    }

    public void setMonth07(boolean month07) {
        this.month07 = month07;
    }

    public boolean isMonth08() {
        return month08;
    }

    public void setMonth08(boolean month08) {
        this.month08 = month08;
    }

    public boolean isMonth09() {
        return month09;
    }

    public void setMonth09(boolean month09) {
        this.month09 = month09;
    }

    public boolean isMonth10() {
        return month10;
    }

    public void setMonth10(boolean month10) {
        this.month10 = month10;
    }

    public boolean isMonth11() {
        return month11;
    }

    public void setMonth11(boolean month11) {
        this.month11 = month11;
    }

    public boolean isMonth12() {
        return month12;
    }

    public void setMonth12(boolean month12) {
        this.month12 = month12;
    }
    // --- end_getters_setters

    /*
       Метод проверяет, все ли типы событий выбраны, или какой-то тип отфильтрован
    */
    public boolean filterExist() {
        if ( !this.month01 ) return true;
        else if ( !this.month02) return true;
        else if ( !this.month03) return true;
        else if ( !this.month04) return true;
        else if ( !this.month05) return true;
        else if ( !this.month06) return true;
        else if ( !this.month07) return true;
        else if ( !this.month08) return true;
        else if ( !this.month09) return true;
        else if ( !this.month10) return true;
        else if ( !this.month11) return true;
        else if ( !this.month12) return true;

        return false;
    } // end_method

} // end_class
