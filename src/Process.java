import java.util.*;

public class Process implements Comparable<Process> {

	private int pId; //Process ID
	private String pName; //Program name
	private long timeLoadedIntoReadyQueue; // When it was loaded into the ready queue.
	private int inCpuCounter; //Number of times it was in the CPU
	private long totTimeSpentInCPU; //Total time spent in the CPU
	private int inIOCounter; //Number of times it performed an IO. 
	private long totTimeSpentInIO; //Total time spent in performing IO
	private int numOfTimeInWating; //Number of times it was waiting for memory 
	private long executionTime; //Time it terminated or was killed
	private processFinalStatus fState; //Its final state Killed or Terminated
	private Queue<Pair<String,Integer>> tasks;
	private int alocatedMemory;
	
	
	
	public Process(int id, Queue<Pair<String,Integer>> tasks) {
		pId=id;
		this.tasks=tasks;
		setAlocatedMemory(0);
		fState=processFinalStatus.WAITING;
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
	
	public Pair<String, Integer> getNextTask(){
		return tasks.peek();
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
		return pId+" - "+fState+"\t"+str.toString();
	}
	
	public int getpId() {
		return pId;
	}
	
	
	
	public String getpName() {
		return pName;
	}


	
	public long getTimeLoadedIntoReadyQueue() {
		return timeLoadedIntoReadyQueue;
	}



	public int getInCpuCounter() {
		return inCpuCounter;
	}



	public long getTotTimeSpentInCPU() {
		return totTimeSpentInCPU;
	}



	public int getInIOCounter() {
		return inIOCounter;
	}



	public long getTotTimeSpentPerIO() {
		return totTimeSpentInIO;
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



	public void setTimeLoadedIntoReadyQueue(long timeLoadedIntoCPU) {
		this.timeLoadedIntoReadyQueue = timeLoadedIntoCPU;
	}



	public void increaseNumOfTimeInCpu() {
		this.inCpuCounter++;
	}



	public void setTotTimeSpentInCPU(long totTimeSpentInCPU) {
		this.totTimeSpentInCPU += totTimeSpentInCPU;
	}



	public void increaseInIOCounter() {
		this.inIOCounter++;
	}



	public void setTotTimeSpentInIO(long totTimeSpentPerIO) {
		this.totTimeSpentInIO += totTimeSpentPerIO;
	}



	public void increaseNumOfTimeInWating() {
		this.numOfTimeInWating++;
	}



	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}



	public void setfState(processFinalStatus fState) {
		if(fState == processFinalStatus.READY) {
			if( timeLoadedIntoReadyQueue == 0 )
				timeLoadedIntoReadyQueue=System.currentTimeMillis();
		}
		this.fState = fState;
	}



	public int getAlocatedMemory() {
		return alocatedMemory;
	}

	public boolean setAlocatedMemory(int alocatedMemory) {
		if(this.alocatedMemory + alocatedMemory < 0) {
			System.out.println("Process id: "+this.pId +" alocated: "+ (this.alocatedMemory + alocatedMemory));
			return false;
		}
		this.alocatedMemory += alocatedMemory;
		return true;
	}
	
	public boolean validateAlocatedMem(int memoryToBeAdded) {
		return (alocatedMemory + memoryToBeAdded >= 0);
	}






	@Override
	public int compareTo(Process o) {
		return getNextBurst() >= o.getNextBurst() ? 1:-1;

	}
}