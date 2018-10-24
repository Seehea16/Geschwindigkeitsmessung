package Geschwindigkeitsmessung;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Measurement implements Serializable{
    public static final DateTimeFormatter DTFDATE = DateTimeFormatter.
            ofPattern("dd.MM.yyyy");
    public static final DateTimeFormatter DTFTIME = DateTimeFormatter.
            ofPattern("HH:mm");
    private LocalDate date;
    private LocalTime time;
    private String kennz;
    private int geschw;
    private int erl;
    private int ueber;

    public Measurement(LocalDate date, LocalTime time, String kennz, int geschw, int erl) {
        this.date = date;
        this.time = time;
        this.kennz = kennz;
        this.geschw = geschw;
        this.erl = erl;
        this.ueber = ueber - erl;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getKennz() {
        return kennz;
    }

    public int getGeschw() {
        return geschw;
    }

    public int getErl() {
        return erl;
    }

    public int getUeber() {
        return ueber;
    }
    
    public String getDateAsString() {
        return this.date.format(DTFDATE);
    }
    
    public String getTimeAsString() {
        return this.time.format(DTFTIME);
    }
}
