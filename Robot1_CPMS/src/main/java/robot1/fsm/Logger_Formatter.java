package robot1.fsm;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class Logger_Formatter  extends Formatter {

    @Override
    public String format(LogRecord record) {
        return new Date(record.getMillis())+"  "+record.getMessage()+"\n"+"\n";
    }
}



/*
record.getThreadID()+"::"+record.getSourceClassName()+"::"
+record.getSourceMethodName()+"::"
+new Date(record.getMillis())+"::"
+*/