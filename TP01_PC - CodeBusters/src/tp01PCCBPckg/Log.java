package tp01PCCBPckg;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class Log
{
    //Private class fields
    private File f;
    private FileHandler FH;
    private Logger logger;

    //Constructor
    public Log(String fileName) throws SecurityException, IOException
    {
        f = new File(fileName);
        
        if(!f.exists()) { f.createNewFile(); }
        
        FH = new FileHandler(fileName,true);
        
        SimpleFormatter formatter = new SimpleFormatter();
        
        FH.setFormatter(formatter);
    }

    //Public methods
    public void setLog(int timer, Buffer buffer, Consumer[] consumers) throws InterruptedException
    {
        logger = Logger.getLogger("ReportTest");
        
        logger.addHandler(FH);
        logger.setLevel(Level.INFO);

        //Write every 'timer' milliseconds.
        while(!buffer.getEnded())
        {
            Thread.sleep(timer);
            
            logger.info("BUFFER CURRENT SIZE: " + buffer.getCurrentSize());
            
            for(Consumer cc : consumers) logger.info("Consumer Thread State: " + cc.getConsumerName() + ": " + cc.getConsumerState());
            
            System.out.println("");
        }
        
        //Message shown before ending program.
        logger.info("------------------------------------------------------------------------------");
        logger.info("END OF PROGRAM: ");
        logger.info("Consumed: " + buffer.getConsumed());
        logger.info("Produced: " + buffer.getProduced());
        logger.info("Discarded: " + buffer.getDiscarded());
        logger.info("Products in buffer: " + buffer.getCurrentSize());
        logger.info("------------------------------------------------------------------------------");
        
        System.out.println("");
    }
}