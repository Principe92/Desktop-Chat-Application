package main;

public class Main {

	public static void main(String[] args) {
		if (args == null || args.length != 2){
			System.out.println("Port number is missing");
			System.exit(0);
		}
		
		
		int portNumber = Integer.parseInt(args[1]);
		String server = args[0]; 
		
		Client main = new Client(server, portNumber);
		main.run();

	}

}
