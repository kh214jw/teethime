public class User_meal {
    private String date;
    private String kind;
    private String time;

    public User_meal(){}

    public String getDate(){
        return date;
    }
    public String getKind(){
        return kind;
    }
    public String getTime(){
        return time;
    }
    public User_meal(String date,String kind,String time){
        this.date = date;
        this.kind = kind;
        this.time = time;
    }
}
