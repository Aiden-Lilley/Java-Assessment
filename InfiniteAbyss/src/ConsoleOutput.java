
public class ConsoleOutput {
	
	public static String formatMenuPrompt() {
		return formatMenuPrompt("");
	}
	
	public static String formatMenuPrompt(String playerName) {
		StringBuilder sb = new StringBuilder();
		if(playerName != "") {
			sb.append("Greetings " + playerName 
					+ "\nplease type one of the following options:\n"
					);
		}
		else {
			sb.append("Greetings there! \n"  
					+ " please type one of the following options:\n"
					);
		}
		sb = menuOptions(sb);
		return sb.toString();
	}
	
	public static String formatStartupMessage(String gameName) {
		return "Welcome to " + gameName;
	}
	
	public static StringBuilder menuOptions(StringBuilder sb) {
		for(PlayerNavPos navPos : PlayerNavPos.values()) {
			if(navPos.isValid())
				sb.append("|" + navPos.getChooser() + "|" + navPos.getDescription() + "|\n");
		}
		return sb;
	}
	
	public static String formatMessage(String message) {
		return "\n\n" + message;
	}

}
