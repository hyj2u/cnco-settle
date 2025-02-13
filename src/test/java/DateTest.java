import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {

    @Test
    public void setDate() {
        Date date = new Date(1728632677000L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:MM:ss");
        System.out.println(dateFormat.format(date));
    }
}
