import java.util.ArrayList;

public class Driver {

	public static void main(String[] args) {
			
		FileHelper fileHelper = new FileHelper("src/input.txt");
		fileHelper.FillWaitingProcesses();
		
		
		Scheduler scheduler = new Scheduler();
		ArrayList<Process> waitingList = fileHelper.getwaitingProcesses();
		ArrayList<Process> readyList = new ArrayList<>();
		
		scheduler.setWaitingProcesses(waitingList);
		scheduler.setReadyProcesses(readyList);
		
		Thread schedulerThread = new Thread(scheduler);
		schedulerThread.start();
		
	}

}
