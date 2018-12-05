import java.util.Iterator;
import java.util.Queue;

public class OperatingSystem {	


	public static void main(String[] args) {
		Disk disk = new Disk();
		Memory RAM= new Memory(32);
		CPU cpu = new CPU();
		Queue<Process> jobs =loadJobs();
		disk.setJobQueue(jobs);
		System.out.println("MAIN");
		ToggleLongTerm(disk, RAM);
		System.out.println("MAIN");
		
		}


	private static void ToggleLongTerm(Disk disk, Memory RAM) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("Thread");
					longTermScheduler(disk, RAM);
					Thread.sleep(200);
					RAM.getCpus();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}).start();
	}


		private static void longTermScheduler(Disk disk, Memory RAM) {
			
			Queue<Process> jq = disk.getJobQueue();
			if (!jq.isEmpty())
				fillReadyQueue(RAM, jq);
			
			Queue<Process> wq = disk.getWaitingQueue();
			if(!wq.isEmpty())
				fillReadyQueue(RAM, wq);
		}


		private static void fillReadyQueue(Memory RAM, Queue<Process> jq) {
//			for (Process process : jq) {
//				System.out.println(process.getNextMemory());
//				if(RAM.canAdd(process.getNextMemory()) ) {
//					RAM.addToReadyQueue(process);
//					jq.remove(process);
//				}
//					
//			}
			
			Iterator<Process> iterator = jq.iterator();
			while(iterator.hasNext()){
				Process p = iterator.next();
//				System.out.println(p.getNextMemory());
				if(RAM.canAdd(p.getNextMemory().getSecond()) ) {
					if(p.getNextMemory().getSecond()<0)
						iterator.remove();
					else {
						RAM.addToReadyQueue(p);
						iterator.remove(); // this function call remove the element from collection at run time
					}
				}
			}
		}








	private static Queue<Process> loadJobs() {
		JobQueueLoader jobQueueLoader = new JobQueueLoader();
		return jobQueueLoader.getProcesses();
		
	}
}
