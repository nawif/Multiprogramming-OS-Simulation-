import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractQueue;
import java.util.LinkedList;
import java.util.Queue;


public class JobQueueLoader {
	public  Queue<Process> getProcesses(){
		return loadProcesses();
	}
	private static Queue<Process> loadProcesses() {
		BufferedReader reader;
		Queue<Process> jobs=new LinkedList<>();
		try {
			reader = new BufferedReader(new FileReader(
					System.getProperty("user.dir")+"/cpumemoryio.txt"));
			String tags = reader.readLine();
			String line = reader.readLine(); //to get rid of the features naming
			while (line != null) {
				jobs.add(getFeatures(tags.split("\t"),line));
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jobs;
	}

	private static Process getFeatures(String[] tags ,String line) {
		String[] everyTask = line.split("\t");
		Process p =null;
		try {
			Queue<Pair<String,Integer>> tasks = new LinkedList<>();
			int id=Integer.parseInt(everyTask[0]);
			for (int i = 1; i < everyTask.length; i++) {
				Pair<String,Integer> task = new Pair<String, Integer>(tags[i],Integer.parseInt(everyTask[i]));
				tasks.add(task);
			}
			p = new Process(id, tasks);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return p;
	}
}
