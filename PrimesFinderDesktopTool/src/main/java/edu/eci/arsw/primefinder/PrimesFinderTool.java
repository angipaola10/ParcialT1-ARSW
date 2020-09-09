package edu.eci.arsw.primefinder;

import edu.eci.arsw.mouseutils.MouseMovementMonitor;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class PrimesFinderTool implements Runnable{
    
        private final int NTHREADS = 4; 
        private ConcurrentLinkedDeque<PrimeFinder> threads;
        private int maxPrim;
        PrimesResultSet prs;
        
        public PrimesFinderTool(){
            threads = new ConcurrentLinkedDeque<>();
            maxPrim=1000;
        }
        
        @Override
        public void run() {
            prs = new PrimesResultSet("john");
                BigInteger d = new BigInteger(maxPrim/NTHREADS+"");

                for (int i=0; i<NTHREADS; i++){

                    BigInteger a = new BigInteger(i+"").multiply(d);
                    a = a.add(new BigInteger("1"));
                    BigInteger b =  a.add(d);
                    b = a.subtract(new BigInteger("1"));
                    if (i == NTHREADS-1){
                        b = new BigInteger(maxPrim+"");
                    }
                    threads.addLast(new PrimeFinder(a,b,prs));
                }
                for (PrimeFinder pf: threads){
                    pf.start();
                }
                for (PrimeFinder pf: threads){
                    try {
                        pf.join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PrimesFinderTool.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                System.out.println("Prime numbers found:");
                System.out.println(prs.getPrimes());
            
            /*while(task_not_finished){
                try {
                    //check every 10ms if the idle status (10 seconds without mouse
                    //activity) was reached. 
                    Thread.sleep(10);
                    if (MouseMovementMonitor.getInstance().getTimeSinceLastMouseMovement()>10000){
                        System.out.println("Idle CPU ");
                    }
                    else{
                        System.out.println("User working again!");
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(PrimesFinderTool.class.getName()).log(Level.SEVERE, null, ex);
                }
            }*/
        }
        
	public static void main(String[] args) {
            Thread primesFinderTool = new Thread(new PrimesFinderTool());
            primesFinderTool.start();
        }	
}


