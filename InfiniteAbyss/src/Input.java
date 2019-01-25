import java.util.Scanner;

public class Input {
	
	private static Scanner input = new Scanner(System.in);
	
	public static String getInput() {
		String outPut = input.nextLine();
		while(outPut.isEmpty()) {
			outPut = input.nextLine();
		}
		return outPut;
	}

}
