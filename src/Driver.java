import java.util.ArrayList;

public class Driver {

	public static void main(String[] args) {
		
		/*
		Process p1 = new Process();
		p1.setArrivalTime(1000);
		p1.setRemainingTime(1000);
		
		Process p2 = new Process();
		p2.setArrivalTime(20000);
		p2.setRemainingTime(3000);
		
		Process p3 = new Process();
		p3.setArrivalTime(3000);
		p3.setRemainingTime(1000);
		
		Thread t1 = new Thread(p1);
		Thread t2 = new Thread(p2);
		Thread t3 = new Thread(p3);
		*/
		
		FileHelper fileHelper = new FileHelper("src/input.txt");
		fileHelper.FillWaitingProcesses();
		
		
		Scheduler scheduler = new Scheduler();
		ArrayList<Process> waitingList = fileHelper.getwaitingProcesses();
		ArrayList<Process> readyList = new ArrayList<>();
		
		/*
		waitingList.add(p1);
		waitingList.add(p2);
		waitingList.add(p3);
		*/
		
	
		/*
		p1.setHasCpu(true);
		t1.start();
		p2.setHasCpu(true);
		t2.start();
		*/
		
		scheduler.setWaitingProcesses(waitingList);
		scheduler.setReadyProcesses(readyList);
		//System.out.println(scheduler.findNextProcess());
		//scheduler.run();
		Thread schedulerThread = new Thread(scheduler);
		schedulerThread.start();
		//t1.suspend();
		//long x = System.currentTimeMillis();
		//while()
		//System.out.println("HELLO");
		//t1.resume();
		//t1.start();
		
	}

}
