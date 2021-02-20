package tp01PCCBPckg;

import java.util.ArrayList;
import java.util.Random;

public class Buffer
{
    //Private class fields
    private int discarded,consumed,produced,maxSize,maxConsumings,randomNum;
	private ArrayList<FOODS> fridge;
    private Random random; //Random object will be used to generate random numbers. FJC

    //Constructor
    public Buffer(int maxSize, int maxConsumings)
    {
        this.maxSize = maxSize;
        this.maxConsumings = maxConsumings;
        
        fridge = new ArrayList<FOODS>();
        
        random = new Random();
    }

    //Public methods
    public synchronized int getCurrentSize() { return fridge.size(); }
    
    public int getDiscarded() { return discarded; }
    
    public int getConsumed() { return consumed; }
    
    public int getProduced() { return produced; }
    
    public boolean getEnded() { return consumed == maxConsumings; }
    
    public boolean isEmpty() { return fridge.size() == 0; }
    
    public boolean isFull() { return fridge.size() == maxSize; }

    public synchronized void addProduct()
    {
        produced++;
        
        System.out.println("Produced products: " + produced);
        
        if(!isFull())
        {
            System.out.println("Buffer has free space. Adding product...");
            
            fridge.add(pickRandomFood());
            
            notify();
        }
        else
        {
            System.out.println("Buffer is full. Discarding product...");
            
            discarded++;
            
            System.out.println("Discarded products: " + discarded);
        }
        
        System.out.println("Occupied buffer slots: " + fridge.size());
    }

    public synchronized void consumeProduct(Consumer cc, long pickingTime)
    {
        System.out.println("Accessing fridge. I am " + cc.getConsumerName());
        
        while(isEmpty())
        {
            System.out.println("Buffer is empty. Waiting... I am " + cc.getConsumerName());
            
            try { wait(200); } //Waiting still until notification.
            catch(InterruptedException e) { e.printStackTrace(); }
        }
        
        if(!getEnded())
        {
            try
            {
            	cc.setConsumerState(CONSUMER_STATES.CONSUMING);
                Thread.sleep(pickingTime); //Picking time.
            }
            catch(InterruptedException e) { e.printStackTrace(); }
            
            FOODS eaten = fridge.remove(0); //Eat.
            
            System.out.println("I've consumed: " + eaten);
            
            consumed++;
            
            System.out.println("Consumed products: " + consumed);
        }
        
        cc.setConsumerState(CONSUMER_STATES.AVAILABLE);
    }

    //Private methods
    private FOODS pickRandomFood()
    {
        FOODS food;
        
        randomNum = random.nextInt(FOODS.values().length); //Gets a random integer between 0 and the quantity of items in FOODS enum. FJC
        
        food = FOODS.values()[randomNum];
        
        return food;
    }
}