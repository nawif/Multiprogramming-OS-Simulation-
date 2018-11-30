
public class Process {

	private int pId; //Process ID
	private String pName; //Program name
	private long timeLoadedIntoCPU; // When it was loaded into the ready queue.
	private int numOfTimeInCpu; //Number of times it was in the CPU
	private long totTimeSpentInCPU; //Total time spent in the CPU
	private int numOfTimePerCPU; //Number of times it performed an IO. 
	private long totTimeSpentPerIO; //Total time spent in performing IO
	private int numOfTimeInWating; //Number of times it was waiting for memory 
	private long executionTime; //Time it terminated or was killed
	private processFinalStatus fState; //Its final state Killed or Terminated
	

	
public Process(int pId, String pName, long timeLoadedIntoCPU, int numOfTimeInCpu, long totTimeSpentInCPU,
			int numOfTimePerCPU, long totTimeSpentPerIO, int numOfTimeInWating, long executionTime,
			processFinalStatus fState) {
		
		this.pId = pId;
		this.pName = pName;
		this.timeLoadedIntoCPU = timeLoadedIntoCPU;
		this.numOfTimeInCpu = numOfTimeInCpu;
		this.totTimeSpentInCPU = totTimeSpentInCPU;
		this.numOfTimePerCPU = numOfTimePerCPU;
		this.totTimeSpentPerIO = totTimeSpentPerIO;
		this.numOfTimeInWating = numOfTimeInWating;
		this.executionTime = executionTime;
		this.fState = fState;
	}



	enum processFinalStatus{
		KILLED, TERMINATED;
		
	}
}