import java.util.*;

public class Process implements Comparable<Process> {

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
	private Queue<Pair<String,Integer>> tasks;
	private int alocatedMemory;
	
	
	
	public Process(int id, Queue<Pair<String,Integer>> tasks) {
		pId=id;
		this.tasks=tasks;
		setAlocatedMemory(0);
	}
	
	public Pair<String, Integer> getNextMemory() {
		for (Pair<String, Integer> pair : tasks) {
			if(pair.getFirst().equals("memory"))
				return pair;
		}
		return null;
	}
	
	public int getNextBurst() {
		for (Pair<String, Integer> pair : tasks) {
			if(pair.getFirst().equals("cpu"))
				return pair.getSecond();
		}
		return 0;
	}
	
	public void deleteTask(Pair<String, Integer> task) {
		tasks.remove(task);
	}
	
	public String getFeatures() {
		StringBuilder str = new StringBuilder();
		for (Pair<String, Integer> pair : tasks) {
//			str.append(pair.getFirst());
			str.append(pair.getSecond());
			str.append("\t");
		}
		return pId+"\t"+str.toString();
	}
	
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



	public int getAlocatedMemory() {
		return alocatedMemory;
	}

	public void setAlocatedMemory(int alocatedMemory) {
		this.alocatedMemory = alocatedMemory;
	}



	enum processFinalStatus{
		KILLED, TERMINATED, READY, WAITING, RUNNING;
		
	}



	@Override
	public int compareTo(Process o) {
		return getNextBurst() >= o.getNextBurst() ? 1:-1;

	}
}