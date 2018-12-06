import java.util.LinkedList;
import java.util.Queue;

public class IO {
	Queue<Process> IOwaitingQueue = new LinkedList<>();
	public void AddProcess(Process p){
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
				Thread.sleep(task.getSecond());
				System.out.println("IO \t" + task.getSecond() + task.getFirst() );
				current.deleteTask(task);
				System.out.println("BEFORE: "+Memory.readyQueue.size());
				Memory.readyQueue.add(current);
				System.out.println("AFTER: "+Memory.readyQueue.size());
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
