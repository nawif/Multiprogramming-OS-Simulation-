import java.util.List;

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
	private List<Pair<taskValue,Integer>> tasks;
	

	
public int getpId() {
		return pId;
	}



	public String getpName() {
		return pName;
	}



	public long getTimeLoadedIntoCPU() {
		return timeLoadedIntoCPU;
	}



	public int getNumOfTimeInCpu() {
		return numOfTimeInCpu;
	}



	public long getTotTimeSpentInCPU() {
		return totTimeSpentInCPU;
	}



	public int getNumOfTimePerCPU() {
		return numOfTimePerCPU;
	}



	public long getTotTimeSpentPerIO() {
		return totTimeSpentPerIO;
	}



	public int getNumOfTimeInWating() {
		return numOfTimeInWating;
	}



	public long getExecutionTime() {
		return executionTime;
	}



	public processFinalStatus getfState() {
		return fState;
	}



public void setpId(int pId) {
		this.pId = pId;
	}



	public void setpName(String pName) {
		this.pName = pName;
	}



	public void setTimeLoadedIntoCPU(long timeLoadedIntoCPU) {
		this.timeLoadedIntoCPU = timeLoadedIntoCPU;
	}



	public void setNumOfTimeInCpu(int numOfTimeInCpu) {
		this.numOfTimeInCpu = numOfTimeInCpu;
	}



	public void setTotTimeSpentInCPU(long totTimeSpentInCPU) {
		this.totTimeSpentInCPU = totTimeSpentInCPU;
	}



	public void setNumOfTimePerCPU(int numOfTimePerCPU) {
		this.numOfTimePerCPU = numOfTimePerCPU;
	}



	public void setTotTimeSpentPerIO(long totTimeSpentPerIO) {
		this.totTimeSpentPerIO = totTimeSpentPerIO;
	}



	public void setNumOfTimeInWating(int numOfTimeInWating) {
		this.numOfTimeInWating = numOfTimeInWating;
	}



	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}



	public void setfState(processFinalStatus fState) {
		this.fState = fState;
	}



public Process(int pId, String pName, long timeLoadedIntoCPU, int numOfTimeInCpu, long totTimeSpentInCPU,
			int numOfTimePerCPU, long totTimeSpentPerIO, int numOfTimeInWating, long executionTime,
			processFinalStatus fState, int CPUBurstTime, int memoryRequired) {
		
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
		this.CPUBurstTime=CPUBurstTime;
		this.memoryRequired=memoryRequired;
	}



	public int getCPUBurstTime() {
	return CPUBurstTime;
}



public void setCPUBurstTime(int cPUBurstTime) {
	CPUBurstTime = cPUBurstTime;
}



	public int getMemoryRequired() {
	return memoryRequired;
}



public void setMemoryRequired(int memoryRequired) {
	this.memoryRequired = memoryRequired;
}



	enum processFinalStatus{
		KILLED, TERMINATED;
		
	}
	enum taskValue{
		CPU,IO,MEMORY;
	}
}