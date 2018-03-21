import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Work_Day{
	
	
	private long helper1;
	private long helper2;
	private static long averageWaitingTime=0;
	private static int ticketCounter=0;
	private static int currentCustomerCount=0;
	private static int current_ticket_no=0; // the employee serves the customer with the current ticket number
	private int number_of_gates; // number of gates that customers come through * number of threads creating customers
	private int limit_of_waiting_customers; // self explanatory 
	private int limit_of_employees; // limit of employees working * number of threads that serve customers 
	
	private ReentrantLock CustomerCountlock = new ReentrantLock();
	private ReentrantLock ticketPrinterLock = new ReentrantLock();
	private ReentrantLock WindowNumberLock = new ReentrantLock();
	private ReentrantLock waitingTimeLock = new ReentrantLock();
	private ArrayList<Long> allWaiting = new ArrayList<Long>();
	private ArrayList<Service_Window> availableWindows = new ArrayList<Service_Window>();	
	


	private PriorityQueue<Customer> queue = new PriorityQueue<Customer>();
	
	
	
	public Work_Day(int number_of_gates, int limit_of_waiting_customers, int limit_of_employees) {
		super();
		this.number_of_gates = number_of_gates;
		this.limit_of_waiting_customers = limit_of_waiting_customers;
		this.limit_of_employees = limit_of_employees;
		
		
        for(int i=0;i<this.limit_of_employees;i++) {
			Service_Window window =new Service_Window(i,this);
			new Thread(window).start();
			availableWindows.add(window);
		}

		for(int i=0;i<this.number_of_gates;i++) {
			
			new Thread(new Gate(i,this)).start();
			
		}
		
		
		
	}
    
	public boolean windowAvailable(){
        int counter=0;
		for(Service_Window s: availableWindows){
			if(s.isBusy()){
				counter++;
			}
			
		}
		if(counter==availableWindows.size()){
			return false;
		}
		else{
			return true;
		}
	}
	
	 public Ticket TicketPrinter() {
		   
		  ticketCounter++;
		  
		  
		  
			   
			   
				
					   if(allWaiting.size()!=0){
						   long x=0;
						   for(Long l: allWaiting){
							   x=x+l;
						   }
			               x=x/allWaiting.size();
							
							
							averageWaitingTime= (averageWaitingTime+x)/1000;
						   
							return new Ticket(ticketCounter,averageWaitingTime);
					   }
					   return new Ticket(ticketCounter,averageWaitingTime);
					   
				  
				 
					
			
			   
			
	 }
	 
	 public void logPrinter() {
		 
	 }
			 
		   
		   
	   
	
	
    //Getters & Setters
	 
	public int getCurrentCustomerCount() {
		return currentCustomerCount;
	}

	public void setCurrentCustomerCount(int currentCustomerCount) {
		this.currentCustomerCount = currentCustomerCount;
	}


	public int getLimit_of_waiting_customers() {
		return limit_of_waiting_customers;
	}

	public ReentrantLock getCustomerCountlock() {
		return CustomerCountlock;
	}


	public void setCustomerCountlock(ReentrantLock CustomerCountlock) {
		this.CustomerCountlock = CustomerCountlock;
	}


    public ReentrantLock getTicketPrinterLock() {
	    return ticketPrinterLock;
    }

    public void setTicketPrinterLock(ReentrantLock ticketPrinterLock) {
	    this.ticketPrinterLock = ticketPrinterLock;
    }


	public PriorityQueue<Customer> getQueue() {
		return queue;
	}


	public ReentrantLock getWindowNumberLock() {
		return WindowNumberLock;
	}


	public void setWindowNumberLock(ReentrantLock windowNumberLock) {
		WindowNumberLock = windowNumberLock;
	}


	public int getCurrent_ticket_no() {
		return current_ticket_no;
	}


	public void setCurrent_ticket_no(int current_ticket_no) {
		Work_Day.current_ticket_no = current_ticket_no;
	}


	public long getAverageWaitingTime() {
		return averageWaitingTime;
	}


	public void setAverageWaitingTime(long averageWaitingTime) {
		Work_Day.averageWaitingTime = averageWaitingTime;
	}


	public ReentrantLock getWaitingTimeLock() {
		return waitingTimeLock;
	}


	public int getLimit_of_employees() {
		return limit_of_employees;
	}

	public ArrayList<Long> getAllWaiting() {
		return allWaiting;
	}

    
	
	
	


	
	
	
    
    
   
	
	
	
	
	
	
	
	

	
	
	
	
}
