package uk.co.willanthony.quotationapp;

public class Quote {
    private long ID;
    private String title;
    private String date;
    private String time;

    public Quote(){

    }

    public Quote(String title, String date, String time) {
        this.title = title;
        this.date = date;
        this.time = time;
    }

    public Quote(long ID, String title, String date, String time) {
        this.ID = ID;
        this.title = title;
        this.date = date;
        this.time = time;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
