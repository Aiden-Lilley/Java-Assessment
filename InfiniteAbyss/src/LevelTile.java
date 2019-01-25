import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import Utils.Tuple;

public class LevelTile {
	public static final int STARTING_SIZE = 16;
	Tile[][] tiles = new Tile[STARTING_SIZE][STARTING_SIZE];
	private int preLoadAmount = 5;
	List<List<Tile>> tileTemp = new ArrayList<>();
	Tuple<Integer,Integer> playercoord = new Tuple<>(0,0);
	public void initMap(){
		for(int x = 0;x < STARTING_SIZE;x++) {
			for(int y = 0;y<STARTING_SIZE;y++) {
				tiles[x][y] = new Tile();
				tiles[x][y].generateTile();
			}
		}
	}
	
	public void addToMap(Tuple<Integer,Integer> currentLoc,int amount,Direction direction) {
		List<Tile[]> t= Arrays.asList(tiles);
		List<List<Tile>> tileList = new LinkedList();
		Tile[][] tTiles;
		List<Tile[]> tempList;
		List<Tile> xTile;
		for(Tile[] ts : t) {
			tileList.add(Arrays.asList(ts));
		}
		
		switch(direction) {
		case NORTH:
			for(int i = 0;i < amount;i++) {
					tileList.add(0,new ArrayList<Tile>());
					for(int x = 0; x < tiles[tiles.length-1].length;x++) {
						tileList.get(0).add(new Tile());
					}
					tileList.get(0).forEach(tile -> tile.generateTile());
				}
				tTiles = new Tile[tiles.length + amount][tiles[tiles.length-1].length];
				tiles = tTiles;
				tempList = new ArrayList<>();
				for(List<Tile> tileLists : tileList) {
					Tile[] tempArray = new Tile[tileLists.size()];
					tileLists.toArray(tempArray);
					tempList.add(tempArray);
				}
				tempList.toArray(tiles);
			break;
		case SOUTH:
				for(int i = 0;i < amount;i++) {
					tileList.add(new ArrayList<Tile>());
					for(int x = 0; x < tiles[tiles.length-1].length;x++) {
						tileList.get(tileList.size()-1).add(new Tile());
					}
					tileList.get((tiles.length - 1) + i).forEach(tile -> tile.generateTile());
				}
				tTiles = new Tile[tiles.length + amount][tiles[tiles.length-1].length];
				tiles = tTiles;
				tempList = new ArrayList<>();
				for(List<Tile> tileLists : tileList) {
					Tile[] tempArray = new Tile[tileLists.size()];
					tileLists.toArray(tempArray);
					tempList.add(tempArray);
				}
				tempList.toArray(tiles);
			break;
		case EAST:
			xTile = tileList.get(currentLoc.getValueOne()).stream().collect(Collectors.toList());
			for(int i = 0;i < amount;i++) {
				xTile.add(new Tile());
				xTile.get(xTile.size()-1).generateTile();
			}
			tTiles = new Tile[tiles.length][tiles[tiles.length-1].length + amount];
			tiles = tTiles;
			tempList = new ArrayList<>();
			tileList.set(currentLoc.getValueOne(), xTile);
			for(List<Tile> tileLists : tileList) {
				Tile[] tempArray = new Tile[tileLists.size()];
				tileLists.toArray(tempArray);
				tempList.add(tempArray);
			}
			tempList.toArray(tiles);
		break;
		case WEST:
			xTile = tileList.get(currentLoc.getValueOne()).stream().collect(Collectors.toList());
			for(int i = 0;i < amount;i++) {
				xTile.add(0,new Tile());
				xTile.get(0).generateTile();
			}
			tTiles = new Tile[tiles.length][tiles[tiles.length-1].length + amount];
			tiles = tTiles;
			tempList = new ArrayList<>();
			tileList.set(currentLoc.getValueOne(), xTile);
			for(List<Tile> tileLists : tileList) {
				Tile[] tempArray = new Tile[tileLists.size()];
				tileLists.toArray(tempArray);
				tempList.add(tempArray);
			}
			tempList.toArray(tiles);
		break;
		}
	}
	
	public void placePlayer(int x,int y) {
		playercoord.set(x, y);
	}
	
	public void moveSouth() {
		if((playercoord.getValueOne() + 1) >= tiles.length) {
			addToMap(playercoord, preLoadAmount, Direction.SOUTH);
		}
		playercoord.set(playercoord.getValueOne() + 1, playercoord.getValueTwo());
	}
	
	public void moveNorth() {
		if((playercoord.getValueOne() -1) < 0) {
			addToMap(playercoord, preLoadAmount, Direction.NORTH);
			playercoord.set((playercoord.getValueOne() + preLoadAmount) - 1, playercoord.getValueTwo());

		}
		playercoord.set(playercoord.getValueOne() - 1, playercoord.getValueTwo());

	}
	public void moveEast() {
		if((playercoord.getValueTwo() +1) >= tiles[playercoord.getValueOne()].length) {
			addToMap(playercoord, preLoadAmount, Direction.EAST);
		}
		playercoord.set(playercoord.getValueOne(), (playercoord.getValueTwo() + 1));
	}
	public void moveWest() {
		if((playercoord.getValueTwo() -1) < 0) {
			addToMap(playercoord, preLoadAmount, Direction.WEST);
			playercoord.set(playercoord.getValueOne(), (playercoord.getValueTwo() + preLoadAmount) - 1);

		}
		playercoord.set(playercoord.getValueOne(), playercoord.getValueTwo() - 1);
	}
	
	public String formatChunk() {
		StringBuilder sb = new StringBuilder();
		for(int x = 0;x <tiles.length;x++) {
			if(x>0)
			sb.append("\n");
			if(tiles[x] != null) {
				for(int y = 0;y<tiles[x].length;y++) {
					if(x == playercoord.getValueOne() && y == playercoord.getValueTwo())
						sb.append("[P] ");
					else if(tiles[x][y] != null) {
							sb.append("[" 
							+tiles[x][y].formatOutput()
							+ "] "
							);
					}
				}
			}

		}
		return sb.toString();
	}
	
	
}

enum Direction{
	NORTH("north"),
	SOUTH("South"),
	EAST("east"),
	WEST("west"),
	INVALID("");
	String chooseString;
	Direction(String chooseString) {
		this.chooseString = chooseString;
	}
	public static Direction parseDirection(String dir) {
		for(Direction direction : Direction.values()) {
			if(direction.chooseString.equalsIgnoreCase(dir))
				return direction;

		}
		return Direction.INVALID;
	}
}