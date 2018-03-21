
import java.util.Random;


public class Gate implements Runnable{
	
	
	private Work_Day currentWorkDay;// reference to current work day , so its data can be used
	
	private int gate_iD;
	private final int maxCustomersPerGate=15;// max number of customers each gate can generate at a time

	public Gate(int gate_iD,Work_Day currentWorkDay) {
		super();
		this.currentWorkDay=currentWorkDay;
		this.gate_iD = gate_iD;
	}
	
	
	
	public void run() {
		
		long start,end,slept;
		long timeToSleep=0;
		int customersAtGate;
		boolean interrupted=false; // checks if the thread was interrupted , initially false
		
		
		//System.out.println("My Thread is Running . Gate "+gate_iD); // for debugging purposes might remove later
		//System.out.println(Thread.currentThread().getState()); // for debugging purposes might remove later
		
		
		while(currentWorkDay.getQueue().size()<currentWorkDay.getLimit_of_waiting_customers()){
			
			start=System.currentTimeMillis(); // for debugging purposes might remove later get the start of the sleep 
			try {
				
				Random randomTime = new Random(); //create a new random item
				timeToSleep = (randomTime.nextInt(50) + 1)*1000; // getting a random number from 1-50 (seconds) and converting to milliseconds .The thread gets put to sleep for that time.
				//System.out.println("Thread "+gate_iD+" will sleep for "+timeToSleep/1000 +" seconds");  // for debugging purposes might remove later
				
				Thread.currentThread();
				Thread.sleep(timeToSleep);
				
				end=System.currentTimeMillis(); // for debugging purposes might remove later get the end of the sleep 
				slept=end-start; // for debugging purposes , calculating how long has the thread slept 
				//System.out.println("The thread "+gate_iD+" slept for "+slept/1000+" seconds"); //for debugging purposes might be removed later
				
				
				this.currentWorkDay.getCustomerCountlock().lock();
				try {
					Random randomCustomers = new Random();
					customersAtGate= randomCustomers.nextInt(maxCustomersPerGate)+1; // random number of customers at the gate
					int maxTries=0;
					while(currentWorkDay.getLimit_of_waiting_customers()-currentWorkDay.getQueue().size()<customersAtGate && maxTries<10 ) {
						
						if(maxTries<9) {
						   customersAtGate= randomCustomers.nextInt(maxCustomersPerGate)+1; // random number of customers at the gate
						   maxTries++;//if the random number exceeds the max capacity of the bank it will have 9 tries to get the remaining number to fill the bank from random numbers
						   
						}
						else {
							customersAtGate=currentWorkDay.getLimit_of_waiting_customers()-currentWorkDay.getQueue().size(); // if it fails the number of the remaining customers to fill the bank will be given automatically 
							break;
							
						}
							
						
					}
					

							   
					for(int i=0;i<customersAtGate;i++) {
						
						
						    //creates as many customers as the random number from above has specified 
							//System.out.println("Customer "+currentWorkDay.getCurrentCustomerCount()+" from gate "+gate_iD);
							
							new Thread(new Customer("id"+currentWorkDay.getCurrentCustomerCount(),currentWorkDay)).start();
							
							int x= currentWorkDay.getCurrentCustomerCount()+1; //raises the current number of customers in the bank by one each time a customer is created
							currentWorkDay.setCurrentCustomerCount(x); 
							
							
						
						
						
					}
				 
				}	
				finally {	
					this.currentWorkDay.getCustomerCountlock().unlock(); // unlocks the lock and lets other threads use it
				}
				
				while(currentWorkDay.getQueue().size()==currentWorkDay.getLimit_of_waiting_customers()) {  // puts the thread to sleep if the bank is full , no more customers can come in
					
					boolean interrupted2=false;
					start=System.currentTimeMillis(); // for debugging purposes might remove later get the start of the sleep
					
					try {
						Thread.currentThread();
						Thread.sleep(1000);
						
						end=System.currentTimeMillis(); // for debugging purposes might remove later get the end of the sleep 
						slept=end-start; // for debugging purposes , calculating how long has the thread slept 
						//System.out.println("The thread "+gate_iD+" slept for "+slept/1000+" seconds"); //for debugging purposes might be removed later
						
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
				
					
					
			}
			
			catch(InterruptedException e) {
				
				//work out how much more time to sleep for
	            end=System.currentTimeMillis();
	            slept=end-start;
	            timeToSleep-=slept;
	            interrupted=true;
				
			}
			
			if(interrupted){
		        //restore interruption before exit
		        Thread.currentThread().interrupt();
		    }
			
			
			
			
			
			
		}
		
		
	}
	
	
	
}
	