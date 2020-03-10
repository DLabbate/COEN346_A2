import java.util.List;
import java.util.ArrayList;
import java.util.Timer;

public class Scheduler implements Runnable {
	
	public static long startTime = System.currentTimeMillis(); //CLOCK
	//public static long startTime = 0;
	
	//public static final long elapsedTime = 0;
	
	private List<Process> waitingProcesses; 
	private List<Process> readyProcesses; //sort by remaining service time
	private List<Thread> threads = new ArrayList<Thread>();
	
	private boolean signalNext = false;
	
	public static long getElapsedtime() {
		return System.currentTimeMillis() - startTime;
		//startTime += 1;
		//return startTime;
	}
	
	
	public List<Process> getWaitingProcesses() {
		return waitingProcesses;
	}
	public void setWaitingProcesses(List<Process> waitingProcesses) {
		this.waitingProcesses = waitingProcesses;
	}
	public List<Process> getReadyProcesses() {
		return readyProcesses;
	}
	public void setReadyProcesses(List<Process> readyProcesses) {
		this.readyProcesses = readyProcesses;
	}


	@Override
	public void run() {
		
		startTime = System.currentTimeMillis();  //Re-initialize start time
		while (!isCompleted())
		{
			
			//if (start)
			if (!waitingProcesses.isEmpty())
			{
				for (int i = 0 ; i <waitingProcesses.size(); i++)
				{
					Process currentProcess = waitingProcesses.get(i); 
					if (currentProcess.getArrivalTime() < Scheduler.getElapsedtime())
					{
						currentProcess.setHasCpu(true);
						Thread thread = new Thread(currentProcess);
						threads.add(thread);
						thread.start();
						
						//while (currentProcess.getHasCpu()) {System.out.println("Process #" + currentProcess.ID + " waiting 1");} //Busy wait	
						
						//Now we want to move from the waiting queue to the ready queue
						waitingProcesses.remove(i);
						readyProcesses.add(currentProcess);
						
					
						//thread.suspend();
						//System.out.println("(Time, ms: " + Scheduler.getElapsedtime() + ") " + "Process #" + currentProcess.ID + " Added to ready queue");
					}
				}
			}
			
			
			if (!readyProcesses.isEmpty())
			{
				for (int i=0; i < readyProcesses.size(); i++)
				{
					Process currentProcess = readyProcesses.get(i);
					currentProcess.setHasCpu(true);
					
					//threads.get(i).resume();
					//while (currentProcess.getHasCpu()) {System.out.println("Process #" + currentProcess.ID + " waiting 2");} //Busy wait	
					//threads.get(i).suspend();
					
					if (currentProcess.isFinished())
					{
						readyProcesses.remove(i);
						threads.remove(i);
					}
					
				}
			}
			
		}
	}
		
	
	private boolean isCompleted()
	{
		if (waitingProcesses.isEmpty() && readyProcesses.isEmpty())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	/*
	 * This process is responsible for finding a process with
	 * the MINIMUM remaining execution time
	 * 
	 * Note the function returns the INDEX, not the actual process itself
	 */
	public int findNextProcess()
	{
		int minimum_index = 0; //Index of the process with minimum remaining time 
		for (int i=0; i <readyProcesses.size(); i++)
		{
			double temp = readyProcesses.get(i).getRemainingTime();
			if (readyProcesses.get(minimum_index).getRemainingTime() > temp)
			{
				minimum_index = i;
			}
			
		}
		
		return minimum_index;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//public void run()
	//{
		
		
		/*
		 * 
		 * ready queue
		 * check shortest servicetime from ones that arent finished
		 * set cpu flag
		 * resume
		 * 
		 * o_assane@encs.concordia.ca
		 */
	//}
	
	


}
