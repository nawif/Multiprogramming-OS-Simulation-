import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class IO {
	Queue<Process> IOwaitingQueue = new LinkedList<>();
	public void AddProcess(Process p){
		p.setfState(processFinalStatus.WAITING);
		IOwaitingQueue.add(p);
	}
	
	public void runTask() { //todo threading
		while(!IOwaitingQueue.isEmpty()) {
			Process current =IOwaitingQueue.poll();
			System.out.print("");
			if(current == null)
				continue;

			Pair<String ,Integer> task = current.getNextTask();
			try {
				if(task.getSecond() < 0 || !task.getFirst().equals("io"))
					continue;
				current.setfState(processFinalStatus.RUNNING);
				current.setTotTimeSpentInIO(task.getSecond());
				Thread.sleep(task.getSecond());
				System.out.println("IO \t" + task.getSecond() + task.getFirst() );
				current.deleteTask(task);
				System.out.println("BEFORE: "+Memory.readyQueue.size());
				Memory.readyQueue.add(current);
				current.setfState(processFinalStatus.READY);
				System.out.println("AFTER: "+Memory.readyQueue.size());
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Process checkForDeadlock() {
		int totalMemoryInQueue = 0;
		Iterator<Process> iterator = IOwaitingQueue.iterator();
		while(iterator.hasNext()){
			try {
				Process process = iterator.next();
				totalMemoryInQueue+= process.getAlocatedMemory();
			}catch(Exception e){
				
			}

		}
		
		if(Memory.alocatedMemorySize == totalMemoryInQueue) { // DEADLOCK
			return findLargestIO();
		}
		return null;
	}

	private Process findLargestIO() {
		Process p = IOwaitingQueue.peek();
		for (Process process : IOwaitingQueue) {
			Pair<String ,Integer> task1 = process.getNextTask();
			Pair<String ,Integer> task2 = p.getNextTask();
			if(task1.getSecond() > task2.getSecond())
				p=process;
		}
		return p;
	}

}
