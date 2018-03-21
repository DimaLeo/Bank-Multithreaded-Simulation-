

import java.util.Timer;
import java.util.TimerTask;

public class Stopwatch {
	
	private long interval;
	private int gate_iD;

	public Stopwatch(long interval,int gate_iD) {
		super();
		this.interval = interval;
		this.gate_iD=gate_iD;
	
	
	}
	
	public void startCountDown() {
		
		int delay = 1000;
        int period = 1000;
    
        Timer time = new Timer();
    
        System.out.println(interval);
        time.scheduleAtFixedRate(new TimerTask() {

        public void run() {
            if (interval == 0) {
                System.out.println("work finished");
                time.cancel();
                time.purge();
            } else {
                System.out.println("time left to sleep for gate "+gate_iD +": "+timeCount());
                }
            }
        },delay,period);
		
    
	
	
	
}

private long timeCount() {          
    return --interval;
}

public long getInterval() {
	return interval;
}

public void setInterval(long interval) {
	this.interval=interval;
}




}
	
	
	


	
	  
	    
	  
	
	

	
	







