
public class Customer implements Runnable,Comparable<Customer>{
	
	private Work_Day currentWorkDay;
	private boolean workDone = false;
	private long startedWaitng,served;
	private String customer_iD; // might be needed for debugging later
	private Ticket ticket ; // ticket the customer obtained from the ticket printer after entering the bank.
	
	
	
	public Customer(String customer_iD,Work_Day currentWorkDay) {
		super();
		this.customer_iD = customer_iD;
		this.currentWorkDay = currentWorkDay;
	}


	public String getCustomer_iD() {
		return customer_iD;
	}


	@Override
	public void run() {
		
		long start,end,slept;
		
		long timeToSleep=0;
		boolean interrupted = false;
		
		this.currentWorkDay.getTicketPrinterLock().lock();//enables the lock so no other customer can get a ticket at the same time
		try {
			ticket = currentWorkDay.TicketPrinter(); // customer gets a ticket from the printer
			
			//System.out.println("Customer "+customer_iD+" has the ticket No"+ticket.getTicket_Number()+"and the estimated waiting time is "+currentWorkDay.getAverageWaitingTime());//for debugging purposes might be removed later
			currentWorkDay.getQueue().add(this);//adds the customer that gets the number to a waiting queue
			startedWaitng=System.currentTimeMillis();
		}
		finally {
			this.currentWorkDay.getTicketPrinterLock().unlock(); //releases lock so other customers can get tickets;
		}
		
		//Makes the customer wait for as long as there are no available windows
		while(!currentWorkDay.windowAvailable()) {
			start=System.currentTimeMillis();
			try {
				Thread.currentThread();
				Thread.sleep(1000);//will calculate time to sleep later
				end=System.currentTimeMillis();
	            slept=end-start;
	            //System.out.println("The customer has waited "+slept/1000+"seconds");
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
		while(!workDone){
			start=System.currentTimeMillis();
			try {
				Thread.currentThread();
				Thread.sleep(1000);//will calculate time to sleep later
				end=System.currentTimeMillis();
	            slept=end-start;
	            //System.out.println("The customer has waited "+slept/1000+"seconds");
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
		
		currentWorkDay.getAllWaiting().add(served-startedWaitng);
		
		
		
		
		
		
		
	}


	


	@Override
	public int compareTo(Customer other) {
		return Integer.compare(this.ticket.getTicket_Number(), other.ticket.getTicket_Number());
	}

    public void setTicket(Ticket ticket) {
    	this.ticket=ticket;
    }
    public Ticket getTicket() {
    	return ticket;
    }


	public void setWorkDone(boolean workDone) {
		this.workDone = workDone;
	}


	public void setServed(long served) {
		this.served = served;
	}
     
    
    

	
	
    
	
	
	
	

}
