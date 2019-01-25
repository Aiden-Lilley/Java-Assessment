
public class GameLoop {
	public final static String GAME_NAME = "Infinite Abyss";
	private boolean playing = true;
	private LevelTile level;
	private PlayerNavPos playerNavigation;
	private Player player;
	private int playerStartingHealth = 10;
	private int playerScore = 0;
	private boolean gamePlaying = false;
	public GameLoop(PlayerNavPos starPos) {
		playerNavigation = starPos;
	}
	public void play() {
		while(playing) {
		
			switch(playerNavigation) {
			case START:
				ConsoleOutput.formatStartupMessage(GAME_NAME);
				playerNavigation = PlayerNavPos.MENU;
				break;
			case MENU:
				if(player == null)
					System.out.println(ConsoleOutput.formatMenuPrompt());
				else if(player.getName().isEmpty()) {
					System.out.println(ConsoleOutput.formatMenuPrompt());
				}else
					System.out.println(ConsoleOutput.formatMenuPrompt(player.getName()));
				String choice = Input.getInput();
				playerNavigation = pickMenuItem(choice);
				break;
			case NAME:
				pickName();
				playerNavigation = PlayerNavPos.MENU;
				break;
			case GAME:
				if(player == null) {
					player = new Player();
				}
				if(!gamePlaying) {
					if(player.getName() == null) {
						pickName();
					}else {
						ConsoleOutput.formatMessage("Before we Continue are you happy with the name:" + player.getName());
						nameOk();
					}
					if(level == null) {
						initGame();
					}
					gamePlaying = true;
					
				}
				String dirInput = Input.getInput();
				System.out.println("movement registered");
				movePlayer(Direction.parseDirection(dirInput));
				System.out.println(level.formatChunk());
				
				break;
			case EXIT:
				playing = false;
				break;
			}		
		
		}
	}
	
	public void movePlayer(Direction dir) {
		switch(dir) {
		case NORTH:
			level.moveNorth();
			break;
		case SOUTH:
			level.moveSouth();
			break;
		case EAST:
			level.moveEast();
			break;
		case WEST: 
			level.moveWest();
			break;
		}
	}
	
	public void initGame() {
		level= new LevelTile();
		level.initMap();
		player.initPlayer(player.getName(), playerStartingHealth, playerScore);
	}
	
	private void nameOk() {
		String choice = Input.getInput();
		if (choice == "no") {
			pickName();
		}
	}
	
	private PlayerNavPos pickMenuItem(String choice) {
		for(PlayerNavPos navPos : PlayerNavPos.values()) {
			if(navPos.getChooser().equalsIgnoreCase(choice)) {
				return navPos;
			}
		}
		return PlayerNavPos.MENU;
	}
	
	private void pickName() {
		boolean pickedName = false;
		System.out.println(ConsoleOutput.formatMessage("Hi there, please enter the name you are known by!"));
		String name = Input.getInput();
		while(!pickedName) {
			if(player == null) {
				player = new Player();
			}
			if(!player.setName(name)) {
				System.out.println(ConsoleOutput.formatMessage("Sorry but that is not a valid name! please pick again."));
				pickedName = false;
			}else {
				pickedName = true;
			}
		}
	}

}

enum PlayerNavPos{
	START("","YOU SHOULDNT EVER SEE THIS"),
	MENU("Menu", "to go to the menu"),
	NAME("Choose Name", "to choose what to be known by"),
	GAME("Play", "to play!"),
	EXIT("Exit", "to quit!")
	;
	private String chooseString;
	private String description;
	private PlayerNavPos(String chooseString,String description) {
		this.chooseString = chooseString;
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getChooser() {
		return chooseString;
	}
	
	public boolean isValid() {
		if(chooseString == "")
			return false;
		return true;
	}
}
