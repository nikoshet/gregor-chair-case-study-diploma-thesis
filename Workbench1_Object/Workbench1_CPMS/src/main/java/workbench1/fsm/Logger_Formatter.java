package workbench1.fsm;



import java.util.logging.LogRecord;
import java.util.Date;
import java.util.logging.Formatter;

public class Logger_Formatter extends Formatter {
    public String format(LogRecord record) {
        return new Date(record.getMillis())+"  "+record.getMessage()+"\n"+"\n";
    }
}
