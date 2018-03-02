package minigames.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class Date {


    public static String getStringDate(){

        Locale locale = new Locale("pt","BR");
        java.util.Date d = new java.util.Date();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss",locale);




        return df.format(d);

    }

    public static Long getLongDate(){
        Locale locale = new Locale("pt","BR");
        java.util.Date d = new java.util.Date();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss",locale);

    return d.getTime();

    }


    public static String getLongToDateString(Long l){

        java.util.Date d = new java.util.Date(l);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        return df.format(d);

    }

}
