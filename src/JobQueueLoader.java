import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class JobQueueLoader {
	private String path;
	
	public static void main(String[] args) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(
					"/Users/osama/Desktop/cpumemoryio.txt"));
			String line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public JobQueueLoader(String path) {
		this.path = path;
	}
	
//	public String[] loadProcesses() {
//		BufferedReader reader;
//		try {
//			reader = new BufferedReader(new FileReader(
//					"/Users/osama/Desktop/cpumemoryio.txt"));
//			String line = reader.readLine();
//			while (line != null) {
//				System.out.println(line);
//				// read next line
//				line = reader.readLine();
//			}
//			reader.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return new String[0];
//	}
}
