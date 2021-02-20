package tp01PCCBPckg;

import java.util.Random;

public class Consumer extends Thread
{
    //Private class fields
    private int meanH,stdevH,meanC,stdevC;
    private String consName;
    private Random sleepTime;
    private Buffer buffer;
    private CONSUMER_STATES consState;

    //Constructor
    public Consumer(Buffer buffer, int i)
    {
        this.buffer = buffer;
        
        meanH = 110;
        stdevH = 15;
        meanC = 70;
        stdevC = 15;
        
        sleepTime = new Random();
        
        consName = "Consumer #" + i;
        
        setConsumerState(CONSUMER_STATES.AVAILABLE);
    }

    //Public methods
    public void setConsumerState(CONSUMER_STATES cs) { consState = cs; }
    
    public String getConsumerName() { return consName; }
    
    public CONSUMER_STATES getConsumerState() { return consState; }
    
    @Override
    public void run()
    {
        while(!buffer.getEnded())
        {
            try
            {
                System.out.println("I will consume, I am " + getConsumerName());
                
                buffer.consumeProduct(this,(long)sleepTime.nextGaussian()*stdevC + meanC); //Picking time.
                
                Thread.sleep((long)sleepTime.nextGaussian()*stdevH + meanH);   //Consuming time.
            }
            catch(InterruptedException e) { e.printStackTrace(); }
        }
    }
}