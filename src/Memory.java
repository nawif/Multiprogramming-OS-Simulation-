import java.util.*;


public class Memory {
	private final int MAX_SIZE=192;
	private int alocatedMemorySize;
	private Queue<Process> readyQueue = new PriorityQueue<>();
	
	public Memory(int osSize) {
		alocatedMemorySize=osSize;
	}


	public void addToReadyQueue(Process p1) {
		readyQueue.add(p1);
		p1.setAlocatedMemory(p1.getAlocatedMemory()+p1.getNextMemory().getSecond());
		alocatedMemorySize+=p1.getAlocatedMemory();
		p1.deleteTask(p1.getNextMemory() );
	}
	public void getCpus() {
		System.out.println("CPU \t RAM");
		for (Process process : readyQueue) {
			System.out.println(process.getNextBurst()+" \t "+process.getNextMemory().getSecond());
		}
	}

	public boolean canAdd(int jobSize) {
		return (MAX_SIZE*0.9 >= alocatedMemorySize + jobSize);
	}
	
}
