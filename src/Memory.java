import java.util.*;

public class Memory {
	private final int MAX_SIZE = 100;
	public static int alocatedMemorySize;
	static Queue<Process> readyQueue = new PriorityQueue<>();

	public Memory(int osSize) {
		alocatedMemorySize = osSize;
	}
	public int getAlocatedMemorySize() {
		return alocatedMemorySize;
	}

	public void addToReadyQueue(Process p1) {
		p1.setfState(processFinalStatus.READY);
		System.out.println("Process id: " + p1.getpId());
		freeAlocatedMem(p1);
		Pair<String, Integer> nextMem = p1.getNextMemory();
		p1.setAlocatedMemory( nextMem.getSecond() );
		alocatedMemorySize += p1.getAlocatedMemory();
		p1.deleteTask(p1.getNextMemory());
		readyQueue.add(p1);
	}
	public void freeAlocatedMem(Process p) {
		if(p.getAlocatedMemory() > 0) {
			System.out.println("FREEING" +p.getAlocatedMemory());
			alocatedMemorySize-=p.getAlocatedMemory(); //already checked that it isn't negative value
		}
			
	}
	public void getCpus() {
		System.out.println("CPU \t RAM");
		for (Process process : readyQueue) {
			System.out.println(process.getNextBurst() + " \t " + process.getNextMemory().getSecond());
		}
	}

	public boolean canAdd(int jobSize) {
		return (MAX_SIZE * 0.9 >= alocatedMemorySize + jobSize);
	}

	public Process getNextProcess() {
		if (readyQueue != null && !readyQueue.isEmpty()) {
			if (readyQueue.size() > 0)
				System.out.println("readyQueue SIZE: " + readyQueue.size());
			return readyQueue.remove();
		}
		return null;
	}

	public void addProcess(Process p) {
		System.out.println("addProcess: " + p.getpId());
		p.setfState(processFinalStatus.READY);
		readyQueue.add(p);
	}

}
