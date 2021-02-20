package tp01PCCBPckg;

import java.util.Random;

public class Producer extends Thread
{
    //Private Fields
    private int mean,stdev;
    private Random sleepTime;
    private Buffer buffer;

    //Constructor
    public Producer(Buffer buffer)
    {
    	this.buffer = buffer;
    	
    	mean = 150;
    	stdev = 15;
    	
    	sleepTime = new Random();
    }

    //Public methods
    @Override
    public void run()
    {
        while(!buffer.getEnded())
        {
            try { Thread.sleep((long)sleepTime.nextGaussian()*stdev + mean); }
            catch(InterruptedException e) { e.printStackTrace(); }
            
            buffer.addProduct();
        }
    }
}
