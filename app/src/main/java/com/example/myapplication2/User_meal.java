package com.example.myapplication2;

public class User_meal {
    public int num;
    private String date;
    private String kind;
    private String time;

    public User_meal(String date,String kind, String time){
        this.date = date;
        this.kind = kind;
        this.time = time;

    }

    public int getNum(){
        return num;
    }
    public String getDate(){
        return date;
    }
    public void setDate(){
        this.date = date;
    }
    public String getKind(){
        return kind;
    }
    public void setKind(){
        this.kind = kind;
    }
    public String getTime(){
        return time;
    }
    public void setTime(){
        this.time = time;
    }

    public String toString(){
        return "-" + date+ " kind : "+ kind + "time : "+ time;
    }
    public User_meal(int num, String date,String kind,String time){
        this.num = num;
        this.date = date;
        this.kind = kind;
        this.time = time;
    }
    public String toUserString(){
        return "- kind : "+kind+ " time : "+time;
    }
}
