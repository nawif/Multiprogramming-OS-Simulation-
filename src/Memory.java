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
		readyQueue.addAll(unsortedProcesses);
	}
	
}
