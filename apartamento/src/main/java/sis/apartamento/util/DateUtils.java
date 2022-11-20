package sis.apartamento.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public DateUtils(){
    }

    public static Date calculaDataMaisUmMes(Date data) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.MONTH, +1);
        data = calendar.getTime();
        return data;
    }
}
