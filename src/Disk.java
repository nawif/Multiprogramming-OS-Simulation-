import java.util.LinkedList;
import java.util.Queue;

public class Disk {
	private Queue<Process> jobQueue;
	private Queue<Process> waitingQueue;
	
	public Disk(Queue<Process> jobQueue, Queue<Process> waitingQueue) {
		this.jobQueue = jobQueue;
		this.waitingQueue = waitingQueue;
	}
	public Disk() {
		this.jobQueue = null;
		this.waitingQueue = new LinkedList<>();
	}
	public Queue<Process> getJobQueue() {
		return jobQueue;
	}
	public void setJobQueue(Queue<Process> jobQueue) {
		this.jobQueue = jobQueue;
	}
	public Queue<Process> getWaitingQueue() {
		return waitingQueue;
	}
	public void setWaitingQueue(Queue<Process> waitingQueue) {
		this.waitingQueue = waitingQueue;
	}
	
}
