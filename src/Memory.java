import java.util.*;


public class Memory {
	private final int MAX_SIZE=192;
	private int alocatedMemorySize;
	private Queue<Process> readyQueue;
	private Queue<Process> waitQueue;
	
	public Memory(List<Process> unsortedProcesses) {
		shortestJobFirst(unsortedProcesses);
	}

	private void shortestJobFirst(List<Process> unsortedProcesses) {
		unsortedProcesses.sort((p1, p2) -> {
			return p1.getCPUBurstTime() < p2.getCPUBurstTime() ? 1:-1;
		});
		addToReadyQueue(unsortedProcesses);
	}

	private void addToReadyQueue(List<Process> unsortedProcesses) {
		for (Process process : unsortedProcesses) {
			if(canAdd(process.getMemoryRequired())) {
				readyQueue.add(process);
				unsortedProcesses.remove(process);
			}
		}
	}

	private boolean canAdd(int jobSize) {
		return (MAX_SIZE*0.9 <= alocatedMemorySize + jobSize);
	}
	
}
