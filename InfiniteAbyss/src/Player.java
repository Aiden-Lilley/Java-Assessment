import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import Utils.Tuple;

public class Player {
	private final static int MAX_HEALTH = 100;
	private String name;
	private int health;
	private int score;
	private List<TileType> history = new ArrayList<>();
	private int turnCount = 0;
	private int overHealedReductionRate = 1;
	private Tuple<Integer,Integer> coords = new Tuple<>(0,0);
	private List<StatusEffect> statusEffects = new ArrayList<>();
	private static Random rand = new Random();
	private Tuple<Integer,Integer> curPoisonDmgRange = new Tuple<>(); 
	
	public String getName() {
		return name;
	}
	
	public void initPlayer(String name,int health,int score) {
		
	}
	
	public boolean setName(String name) {
		if(name == "" || name == null)
			return false;
		if(Pattern.matches("[a-zA-z]+", name)) {
			this.name = name.trim();
			return true;
		}
		return false;
	}
	public int damage(int dmgPoints) {
		if((health - dmgPoints) < 0) {
			health -= dmgPoints;
			return 0;
		}
		health -= dmgPoints;
		return health;
	} 
	
	public void heal(int healthPoints) {
		health += healthPoints;
	}
	
	public void overHealed() {
		health -= overHealedReductionRate;
	}
	
	public int checkHealth() {
		if(statusEffects.isEmpty())
			return health;
		
			for(StatusEffect effect : statusEffects) {
				switch (effect){
					case OVERHEALED:{
						overHealed();
						break;
					}				
					case POISONED: {
						poison(this);
						break;
				}
			}
		}
			
		return health;
	}
	
	public static void poison(Player player) {
		player.damage(rand.nextInt(player.curPoisonDmgRange.getValueTwo()));
	}

}

enum StatusEffect{
	OVERHEALED("You are overhealed!", "Will lose 1 healthPoint a turn!"),
	POISONED("you are poisened!", "Will take 2-5 damage a turn");
	private String playerMessage;
	private String statusEffect;
	private StatusEffect(String playerMessage,String statusEffect) {
		this.playerMessage = playerMessage;
		this.statusEffect = statusEffect;
	}
	
	public String getPlayerMessage() {
		return playerMessage;
	}
	
	public String getStatusEffect() {
		return statusEffect;
	}
	
}
