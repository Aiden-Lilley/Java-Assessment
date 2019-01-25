import Utils.Tuple;

public class main {
	
	public static void main(String[] args) {
		/*Tile tile = new Tile();
		tile.generateTile();
		System.out.println(tile);*/
		/*Chunk chunk = new Chunk();
		chunk.generateChunk();
		System.out.println(chunk);;*/
		/*Level level = new Level();
		level.initMap(4, 4);
		System.out.println(level);*/
		GameLoop game = new GameLoop(PlayerNavPos.START);
		game.play();


		
	}

}
