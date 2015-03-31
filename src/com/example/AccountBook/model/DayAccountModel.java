package com.example.AccountBook.model;

/**
 * Created by yoshida keisuke on 2015/03/17.
 */
public class DayAccountModel {
    private int price;
    private int type;
    private int year;
    private int month;
    private int day;
    private Long id;

    public DayAccountModel(Long id,int year,int month,int day,int price, int type) {
        this.price = price;
        this.type = type;
        this.year = year;
        this.month = month;
        this.day = day;
        this.id = id;
    }

    public DayAccountModel(int year,int month,int day,int price, int type) {
        this.id=null;
        this.price = price;
        this.type = type;
        this.year = year;
        this.month = month;
        this.day = day;
    }
    public DayAccountModel(int price, int type) {
        this.price = price;
        this.type = type;
    }



    public int getPrice(){
        return this.price;
    }

    public int getType(){
        return this.type;
    }

    public int getDay(){
        return this.day;
    }

    public int getMonth(){
        return this.month;
    }

    public int getYear(){
        return this.year;
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
