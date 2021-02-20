package tp01PCCBPckg;

import java.io.IOException;

public class Main
{
    //Class constants
    public static final int NUM_PRODUCERS = 10;
    public static final int NUM_CONSUMERS = 6;
    public static final int LOG_TIMER = 2000;			//The time interval (in milliseconds) between log prints. FJC
    public static final int MAX_BUFFER = 25;			//Maximum buffer size.
    public static final int MAX_CONSUMINGS = 1000;		//After this quantity of consuming, the program ends. FJC

    //Class variables
    public static Buffer buffer;
    public static Producer[] producers;
    public static Consumer[] consumers;
    public static Log myLog;
    public static String logName;		//The name of the log file. FJC
    
    //Public methods
    public static void main(String[] args)
    {
    	buffer = new Buffer(MAX_BUFFER, MAX_CONSUMINGS);
        
    	producers = new Producer[NUM_PRODUCERS];
        
    	consumers = new Consumer[NUM_CONSUMERS];
    	
    	logName = "ReportLog.txt";

        for(int i=0; i<NUM_PRODUCERS; i++)
        {
            producers[i] = new Producer(buffer);
            producers[i].start();
        }
        
        for(int i=0; i<NUM_CONSUMERS; i++)
        {
        	consumers[i] = new Consumer(buffer,i);
        	consumers[i].start();
        }

        try
        {
        	myLog = new Log(logName);
        	myLog.setLog(LOG_TIMER,buffer,consumers);
        }
        catch(IOException | InterruptedException e) { e.printStackTrace(); }
    }
}