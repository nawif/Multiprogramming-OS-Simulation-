
public class CPU {
	public void RunProcess(Process p){
		try {
			Thread.sleep(p.getExecutionTime());
//			p.ge
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
