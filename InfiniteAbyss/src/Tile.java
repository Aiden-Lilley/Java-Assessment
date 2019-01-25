import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Tile {
	Random rand = new Random();
	private String tileName;
	private TileType type = TileType.DEFAULT;
	private int tileValue = 0;
	private String tileDescription;
	
	public void generateTile() {
		int[] cumulative = new int[TileType.values().length];
		int cumulativeTotal = 0;
		for(int i = 0;i < cumulative.length;i++) {
			cumulativeTotal+= TileType.values()[i].getSpawnChance();
			cumulative[i] = cumulativeTotal;
		}
		int choice = rand.nextInt(cumulativeTotal);
		TileType typeChoice = null;
		for(int i = 0;i < cumulative.length;i++) {
			if(i == 0) {
				if(choice <=
					cumulative[i]) {
					typeChoice = TileType.values()[i];
				break;
				}
			}
			else if(choice <= cumulative[i] && choice > cumulative[i-1]) {
				typeChoice = TileType.values()[i];
				break;
			}
		}
		tileName = typeChoice.getTileType();
		type = typeChoice;
		tileValue = typeChoice.getValue();
		tileDescription = typeChoice.getDescription();
	}
	
	public String toString() {
		return tileName + " : " + tileDescription + " : " + type.getSpawnChance() + " : " + type.getValue();
	}
	
	public String formatOutput() {
		return String.valueOf(type.getSymbol());
	}
	
	public String getType() {
		return type.name().substring(0, 1);
	}

}

enum TileType{
	DEFAULT("greyBoring",0,90,"this is a description",' '),
	ENEMYENCOUNTER("enemy",10,2,"this is a description",'*'),
	HEALINGFOUNTAIN("healing",20,3,"this is a description",'+'),
	SHRINE("shrine",0,2,"A SHRINE",'$')
	;
	private String typeName;
	private int tileValue;
	private int spawnChance;
	private String description;
	private char symbol;
	TileType(String typeName,int value,int spawnChance, String description,char symbol) {
		this.typeName = typeName;
		this.tileValue = value;
		this.spawnChance = spawnChance;
		this.description = description;
		this.symbol = symbol;
	}
	
	public char getSymbol() {
		return symbol;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public int getSpawnChance() {
		return spawnChance;
	}
	
	public int getValue() {
		return tileValue;
	}
	
	public String getTileType() {
		return this.typeName;
	}
}
