package com.example.AccountBook.module;

/**
 * Created by yoshida keisuke on 2015/03/18.
 */
public class MyCalendar {

    private static final int DAYS_LIST[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final String DAY_OF_WEEK_JP[]= {"日","月","火","水","木","金","土"};
    private static final String DAY_OF_WEEK_EN[] = {"Sun","Mon","Tue","Wed","Thr","Fri","Sat"};
    /**
     * @param year  西暦
     * @param month 月
     * @return days 日数
     */
    public int getDays(int year, int month) {
        int days = 0;

        if (month == 2) {
            days = DAYS_LIST[month - 1];
            if (isLeapYear(year)) {
                days++;
            }
        } else if (1 <= month && month <= 12) {
            days = DAYS_LIST[month - 1];
        }

        return days;
    }


    /**
     * 西暦、月、日から曜日を返す
     * @param year 西暦
     * @param month 月
     * @param day 日
     * @return 曜日の番号
     */
    public int getDayOfWeek(int year, int month, int day) {
        if (month == 1) {
            year = year - 1;
        } else if (month == 2) {
            year = year - 1;
            month = 14;
        }

        int dayOfWeek = (5 * year / 4 - year / 100 + (26 * month + 16) / 10 + day) % 7;
        return dayOfWeek;
    }

    public String getDayOfWeekWithJP(int year,int month,int day){
        int dayNum = getDayOfWeek(year,month,day);
        return DAY_OF_WEEK_JP[dayNum];
    }

    public String getDayOfWeekWithEn(int year,int month,int day){
        int dayNum = getDayOfWeek(year,month,day);
        return DAY_OF_WEEK_EN[dayNum];
    }

    private boolean isLeapYear(int year) {
        if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
            return true;
        }
        return false;
    }


}
