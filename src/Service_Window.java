import java.util.Random;

public class Service_Window implements Runnable{
	
	
	private Work_Day currentWorkDay;
	private Customer currentCustomer;
	private int window_no;
	
	
	private boolean busy=false; // a flag that shows weather the employee is working with a customer or not
	
	public Service_Window(int window_no,Work_Day currentWorkDay) {
		super();
		this.window_no = window_no;
		
		this.currentWorkDay=currentWorkDay;
	}
	
	public void run() {
		
		
	    //put the thread to sleep for some time so that people get in the bank, prevents the thread working when queue is empty
		long start,end,slept;
		long startWork,worked;
		long timeToSleep=0;
		boolean interrupted2=false;
		
		while(currentWorkDay.getQueue().isEmpty()) {
			
			start=System.currentTimeMillis(); // for debugging purposes might remove later get the start of the sleep
			
			try {
				Thread.currentThread();
				Thread.sleep(1000);
				
				end=System.currentTimeMillis(); // for debugging purposes might remove later get the end of the sleep 
				slept=end-start; // for debugging purposes , calculating how long has the thread slept 
				//System.out.println("Slept for "+slept/1000);
				
			}
			catch(InterruptedException e) {
				//work out how much more time to sleep for
	            end=System.currentTimeMillis();
	            slept=end-start;
	            timeToSleep-=slept;
	            interrupted2=true;
				
			}
			
			if(interrupted2){
		        //restore interruption before exit
		        Thread.currentThread().interrupt();
		    }
		}
		
		
	    // after every bank employee has drunk his coffee they start working
		
		//System.out.println("My Thread is Running . Window "+window_no);
		
		//if the que is not empty and the window is not busy
		while(!currentWorkDay.getQueue().isEmpty() && !busy) {
		    
			this.currentWorkDay.getWindowNumberLock().lock();
		    try {
		    	int x = currentWorkDay.getCurrent_ticket_no()+1;// call the customer with the next ticket number
				currentWorkDay.setCurrent_ticket_no(x);
				startWork=System.currentTimeMillis();
				busy=true; // makes the window busy
				
				currentCustomer = currentWorkDay.getQueue().poll(); // PriorityQueue.poll() gets the head of the queue and removes it from it
				
				//System.out.println("The customer "+currentCustomer.getCustomer_iD()+" was served by window "+window_no +" and had ticket number "+currentCustomer.getTicket().getTicket_Number()); //for debugging purposes might be removed later
				
				
				
		    }
		    finally {
		    	this.currentWorkDay.getWindowNumberLock().unlock();
		    }
			start=System.currentTimeMillis(); // for debugging purposes might remove later get the start of the sleep
				
			try {
				Random workTime = new Random();
				timeToSleep = (workTime.nextInt(60)+1)*1000; // random number from 1-60 seconds converted to milliseconds
				
				Thread.currentThread();
				Thread.sleep(timeToSleep); // puts the thread to sleep
				
				end=System.currentTimeMillis(); // for debugging purposes might remove later get the end of the sleep 
				
				slept=end-start; // for debugging purposes , calculating how long has the thread slept 
				
				
				
				currentWorkDay.getWaitingTimeLock().lock();
				try {
					currentCustomer.setWorkDone(true);
					currentCustomer.setServed(System.currentTimeMillis());
					
					
				}
				finally {
					currentWorkDay.getWaitingTimeLock().unlock();
				}
				
				//System.out.println("Window "+window_no+" slept for "+slept/1000);// for debugging purposes , might be removed later
				
			}
			catch(InterruptedException e) {
				//work out how much more time to sleep for
	            end=System.currentTimeMillis();
	            slept=end-start;
	            timeToSleep-=slept;
	            interrupted2=true;
				
			}
			
			if(interrupted2){
		        //restore interruption before exit
		        Thread.currentThread().interrupt();
		    }	
				
			busy=false;
		    
		    
		    
			
			
		
		
		}
			
			
		
		
		
	}

	public boolean isBusy() {
		return busy;
	}
	
	

	
	
	
	
	

}
