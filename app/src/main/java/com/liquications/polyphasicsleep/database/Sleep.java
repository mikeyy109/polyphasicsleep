package com.liquications.polyphasicsleep.database;

public class Sleep {

    private int id;
    private int num;
    private String time;

    public Sleep(){}

    public Sleep(int num, String time) {
        super();
        this.num = num;
        this.time = time;
    }

    //getters & setters

    @Override
    public String toString() {
        return "Sleep [id=" + id + ", num=" + num + ", time=" + time
                + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void add(Sleep sleep) {
    }
}
