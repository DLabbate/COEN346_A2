import java.util.concurrent.locks.ReentrantLock;

public class Process implements Runnable {
	
	public static int nextID = 1;
	int ID;
	double quantumTime = 1000;
	
	private double arrivalTime;
	private double serviceTime; 
	private double remainingTime; //Initialized to serviceTime
	private boolean hasCpu; 	//Pause or Resume
	private boolean isFinished;
	
	public boolean jobInProgress = false;
	
	
	public static final ReentrantLock mutex = new ReentrantLock(); //This mutex lock makes sure only one process has the CPU at a given time
	
	public static final ReentrantLock mutex_cpu = new ReentrantLock();
	
	Process() //Hardcoded values for testing
	{
		ID = nextID++;
		
		this.arrivalTime = 1000; // (ms)
		this.serviceTime = 5000;
		this.remainingTime = this.serviceTime;
		this.hasCpu = false;
		this.isFinished = false;
	}
	
	@Override
	public void run() {
		
		long enterTime = System.currentTimeMillis(); //Time at which we enter the run() function
		//mutex_cpu.lock();
		try
		{
			System.out.println("(Time, ms: " + Scheduler.getElapsedtime() + ") " + "Process #" + ID + " Started - calling run() ");
			
			
			while (!isFinished)
			{
				//System.out.println("Process #" + ID + " Main Loop" + "hasCpu: " + hasCpu);
				//System.out.println("Process #" + ID + "RUNNING");
			
				mutex.lock();
				jobInProgress = true;
				try
				{
					if (hasCpu)
					{
						System.out.println("(Time, ms: " + Scheduler.getElapsedtime() + ") " + "Process #" + ID + " Resumed -" + " Remaining Time: " + remainingTime);
						updateQuantumTime();
						decrementTime();
						System.out.println("(Time, ms: " + Scheduler.getElapsedtime() + ") " + "Process #" + ID + " Paused -" + " Remaining Time: " + remainingTime);
					}
				}
				finally
				{
					jobInProgress = false;
					mutex.unlock();
				}
				
			}
		}
		
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		finally
		{
			//mutex_cpu.unlock();
			System.out.println("(Time, ms: " + Scheduler.getElapsedtime() + ") " + "Process #" + ID + " Exiting run()");
			//lock.unlock();
		}
		
	}
	
	
	public synchronized void decrementTime() 
	{
		long EnterTime = System.currentTimeMillis();
		//remainingTime -= quantumTime;

		while ((System.currentTimeMillis() - EnterTime) <= quantumTime)
		{
			//System.out.println("Process # " + ID + "Running!");
			//System.out.println(System.currentTimeMillis());
		}
		
		remainingTime -= quantumTime;
		
		if (remainingTime < 0.1)
		{
			finish();
		}
		hasCpu = false; //After we decrement the time, the next process should have the CPU
		
		
	}

	private void finish() 
	{
		System.out.println("(Time, ms: " + Scheduler.getElapsedtime() + ") " + "Process #" + ID + " *FINISHED*");
		isFinished = true;
		hasCpu = false;
		
		
	}
	
	
	
	///////////////////////
	//GETTERS AND SETTERS//
	///////////////////////
	public double getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(double arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public double getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(double serviceTime) {
		this.serviceTime = serviceTime;
	}

	public double getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(double remainingTime) {
		this.remainingTime = remainingTime;
	}

	public boolean getHasCpu() {
		return hasCpu;
	}

	public void setHasCpu(boolean hasCpu) {
		this.hasCpu = hasCpu;
	}

	public boolean isFinished() {
		//return isFinished;
		if (remainingTime <= 0.1)
		{
			this.isFinished = true;
			return true;
		}
		else 
		{
			this.isFinished = false;
			return false;
		}
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
	
	public void updateQuantumTime()
	{ 
		double scale = 0.10;
		this.quantumTime = scale * remainingTime;
	}
}
