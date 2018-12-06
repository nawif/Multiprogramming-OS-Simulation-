import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

public class OperatingSystem {	
	static ArrayList<Process> finishedJobs = new ArrayList<>(); 

	public static void main(String[] args) throws InterruptedException {
		long osBootTime = System.currentTimeMillis();
		Disk disk = new Disk();
		Memory RAM= new Memory(32);
		CPU cpu = new CPU();
		IO io = new IO();
		Queue<Process> jobs =loadJobs();
		disk.setJobQueue(jobs);
		ToggleLongTerm(disk, RAM);
		Thread.sleep(1000);
		ToggleIO(io);
		ToggleScheduler(cpu, RAM, io);
		Thread.sleep(4000);
		System.out.println("readyQueue----------------------------------------");
		for (Process process : RAM.readyQueue) {
			System.out.println(process.getFeatures());
		}
		System.out.println("finishedJobs----------------------------------------");
		for (Process process : finishedJobs) {
			System.out.println(process.getFeatures());
		}
		System.out.println("WaitingQueue----------------------------------------");
		for (Process process : disk.getWaitingQueue()) {
			System.out.println(process.getFeatures());
		}
		System.out.println("JobQueue----------------------------------------");
		for (Process process : disk.getJobQueue()) {
			System.out.println(process.getFeatures());
		}
		System.out.println(RAM.getAlocatedMemorySize());
		}


	private static void ToggleIO(IO io) {
				 io.runTask();
	}


	private static void ToggleScheduler(CPU cpu, Memory RAM, IO io) {
							while(!RAM.readyQueue.isEmpty()) {
								Process p = RAM.getNextProcess();
								if(p == null)
									continue;
								Pair<String , Integer> nextTask = p.getNextTask();
								if(nextTask == null) {
									RAM.freeAlocatedMem(p);
									System.out.println("TASK: "+ p.getpId() +" is FINSHIED");
									continue;

								}
								switch (nextTask.getFirst()) {
								case "cpu":
									System.out.println("CPU 2 :" + "\t"+ nextTask.getSecond());
									sendTaskToCpu(nextTask, cpu, RAM, p);
									break;
								case "io":
									sendTaskToIO(p, io);
									io.runTask();
									break;
								case "memory":
									sendTaskToMemory(nextTask,p ,RAM);
									break;
								default:
									System.out.println("aaaaaaaa");
								}
								
							}			
	}


	private static void sendTaskToMemory(Pair<String, Integer> task, Process p, Memory RAM) {
//				boolean tag=p.setAlocatedMemory(task.getSecond());
//				System.out.println(tag);
//				if(tag)  {
//					RAM.addToReadyQueue(p);
//					p.deleteTask(task);
//					System.out.println("RAM" + "\t"+ task.getSecond());
//				}
//					
//				else {
//					
//					p.setfState(processFinalStatus.TERMINATED);
//					RAM.freeAlocatedMem(p);
//					finishedJobs.add(p);
//					System.out.println("terminated process: "+p.getpId() + "\t alocatedMem: " + p.getAlocatedMemory());
//					
//				}
		
		if(p.validateAlocatedMem(task.getSecond())) { // data is correct
			RAM.addToReadyQueue(p);
		}else {
			p.setfState(processFinalStatus.TERMINATED);
			RAM.freeAlocatedMem(p);
			finishedJobs.add(p);
		}
		
	}


	private static void sendTaskToIO(Process p, IO io) {
				io.AddProcess(p);
	}


	private static void sendTaskToCpu(Pair<String, Integer> task, CPU cpu, Memory RAM, Process p) {
				p.deleteTask(task);
				cpu.RunTask(task.getSecond());
				RAM.addProcess(p);
				System.out.println("CPU 1 :" + "\t"+ task.getSecond() + task.getFirst());
		
	}


	private static void ToggleLongTerm(Disk disk, Memory RAM) {
		new Thread(new Runnable() {
			@Override
			public void run() {
					while(true) {
						try {
						longTermScheduler(disk, RAM);
						Thread.sleep(200);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
			}
		}).start();
	}


		private static void longTermScheduler(Disk disk, Memory RAM) {
			
			Queue<Process> jq = disk.getJobQueue();
			if (!jq.isEmpty())
				fillReadyQueue(RAM, jq);
			
			Queue<Process> wq = disk.getWaitingQueue();
			if(!wq.isEmpty()) {
				fillReadyQueue(RAM, wq);
			}
		}


		private static void fillReadyQueue(Memory RAM, Queue<Process> jq) {
			Iterator<Process> iterator = jq.iterator();
			while(iterator.hasNext()){
				Process p = iterator.next();
				if(RAM.canAdd(p.getNextMemory().getSecond() ) ) {
					if(p.getNextMemory().getSecond()<0) {
						p.setfState(processFinalStatus.TERMINATED);
						finishedJobs.add(p);
						iterator.remove();
					}
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
