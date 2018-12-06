import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
		ToggleIO(io, RAM);
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
		
		long osTotalRunTime = System.currentTimeMillis() - osBootTime;
		generateStatistics(osTotalRunTime);
		}


	private static void generateStatistics(long osTotalRunTime) {
		StringBuilder stringBuilder = new StringBuilder();
//		stringBuilder.append("Process ID"+"\t");
//		stringBuilder.append("Program name"+"\t");
//		stringBuilder.append("When it was loaded into the ready queue"+"\t");
//		stringBuilder.append("Number of times it was in the CPU"+"\t");
//		stringBuilder.append("Total time spent in the CPU"+"\t");
//		stringBuilder.append("Number of times it performed an IO"+"\t");
//		stringBuilder.append("Total time spent in performing IO"+"\t");
//		stringBuilder.append("Number of times it was waiting for memory"+"\t");
//		stringBuilder.append("Time it terminated or was killed"+"\t");
//		stringBuilder.append("Its final state: Killed or Terminated"+"\t");
//		stringBuilder.append("\n");
		int cpuTime=0;
		for (Process process : finishedJobs) {
			stringBuilder.append(process.getpId()+"\t");
			stringBuilder.append(process.getpName()+"\t");
			stringBuilder.append(process.getTimeLoadedIntoReadyQueue()+"\t");
			stringBuilder.append(process.getInCpuCounter()+"\t");
			stringBuilder.append(process.getTotTimeSpentInCPU()+"\t");
			stringBuilder.append(process.getInIOCounter()+"\t");
			stringBuilder.append(process.getTotTimeSpentPerIO()+"\t");
			stringBuilder.append(process.getNumOfTimeInWating()+"\t");
			stringBuilder.append(process.getExecutionTime()+"\t");
			stringBuilder.append(process.getfState()+"\t");
			stringBuilder.append("\n");
			cpuTime+= process.getTotTimeSpentInCPU();
		}
		stringBuilder.append("CPU Utilization: %" +((double)(cpuTime)/(osTotalRunTime))*100 );
	      BufferedWriter bw = null;
	 	 File file = new File("output.txt");
	      FileWriter fw;
		try {
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			bw.write(stringBuilder.toString());
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  

		
//		System.out.println(stringBuilder.toString());
		
	}


	private static void ToggleIO(IO io, Memory RAM) {
				 io.runTask();
				 new Thread(new Runnable() {
					
					@Override
					public void run() {
						while(true) {
							Process p = io.checkForDeadlock();
							if(p == null)
								continue;
							else {
								io.IOwaitingQueue.remove(p);
								p.setfState(processFinalStatus.TERMINATED);
								p.setExecutionTime(System.currentTimeMillis());
								RAM.freeAlocatedMem(p);
								finishedJobs.add(p);
							}
								
						}
						
					}
				}).start();
	}


	private static void ToggleScheduler(CPU cpu, Memory RAM, IO io) {
							while(!RAM.readyQueue.isEmpty()) {
								Process p = RAM.getNextProcess();
								if(p == null)
									continue;
								Pair<String , Integer> nextTask = p.getNextTask();
								if(nextTask == null) {
									RAM.freeAlocatedMem(p);
									p.setfState(processFinalStatus.KILLED);
									p.setExecutionTime(System.currentTimeMillis());
									finishedJobs.add(p);
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
		
		if(p.validateAlocatedMem(task.getSecond())) { // data is correct
			RAM.addToReadyQueue(p);
		}else {
			p.setfState(processFinalStatus.TERMINATED);
			RAM.freeAlocatedMem(p);
			p.setExecutionTime(System.currentTimeMillis());
			finishedJobs.add(p);
		}
		
	}


	private static void sendTaskToIO(Process p, IO io) {
		p.increaseInIOCounter();		
		io.AddProcess(p);
	}


	private static void sendTaskToCpu(Pair<String, Integer> task, CPU cpu, Memory RAM, Process p) {
				p.deleteTask(task);
				p.setfState(processFinalStatus.RUNNING);
				p.increaseNumOfTimeInCpu();
				p.setTotTimeSpentInCPU(task.getSecond());
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
						p.setExecutionTime(System.currentTimeMillis());
						finishedJobs.add(p);
						iterator.remove();
					}
					else {
						RAM.addToReadyQueue(p);
						iterator.remove(); // this function call remove the element from collection at run time
					}
				}else
					p.increaseNumOfTimeInWating();
			}
			
			
		}








	private static Queue<Process> loadJobs() {
		JobQueueLoader jobQueueLoader = new JobQueueLoader();
		return jobQueueLoader.getProcesses();
		
	}
}
