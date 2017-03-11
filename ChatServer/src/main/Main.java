package main;

public class Main {
	
	public static void main(String[] args) {
	
		if (args == null || args.length != 1){
			System.out.println("Port number is missing");
			System.exit(0);
		}
		
		
		int portNumber = Integer.parseInt(args[0]);
		Server main = new Server(portNumber);
		main.run();

	}

}	
