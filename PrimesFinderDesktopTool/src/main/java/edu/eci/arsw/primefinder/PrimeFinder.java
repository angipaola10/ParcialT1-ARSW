package edu.eci.arsw.primefinder;

import edu.eci.arsw.math.MathUtilities;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrimeFinder extends Thread{
        
    private  BigInteger a;
    private  BigInteger b;
    private PrimesResultSet prs;
    private boolean pause;
    
        public PrimeFinder(BigInteger _a, BigInteger _b, PrimesResultSet prs){
            this.a = _a;
            this.b = _b;
            this.prs = prs;
            this.pause = false;
        }
    
        
	public void findPrimes(){
                MathUtilities mt=new MathUtilities();
                int itCount = 0;
                BigInteger i = a;
                while (i.compareTo(b)<=0){
                    synchronized(this){
                        while(pause){
                            try {
                                pause();
                                wait();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(PrimeFinder.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } 
                    }
                    itCount++;
                    if (mt.isPrime(i)){
                        prs.addPrime(i);
                    }

                    i=i.add(BigInteger.ONE);
                }
                
	}

    @Override
    public void run() {
        findPrimes();
    }
    
    public void pause(){
        pause = true;
    }
    
    public void resumee(){
        pause = false;
        synchronized(this){
            notifyAll();
        }
    }
    
}
