import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Represents the game list.
 * 
 * @author Juan Santana
 * @author Giulia Mendes
 */
public class GameList {
	protected List<Game>gameList;
 
    /**
	 * add game to the game list
	 * @param game Game object
	 */
	public GameList() {}
	public GameList(List<Game> gameList) {
		this.gameList = gameList;
	}
	public boolean addGame(Game game) {
		for (Game gameInList: gameList) {
			if (gameInList.getName().equals(game.getName()))
				return false;
		}
		gameList.add(game);
		return true;
	}
	/**

    * Attempt to remove the specified game from the game list. If successful, return true; otherwise, return false.
    * @param gameName A string representing the name of the game to be removed
    * @return True if the game was successfully removed from the game list, otherwise false.
    * @throws java.lang.IllegalArgumentException If the provided argument is incompatible with the expected pattern.
    */
	public boolean deleteGame(String gameName) {
		for (Game game: gameList) {
			if(game.getName().equals(gameName)) {
				gameList.remove(game);
				return true;
			}
		}
		return false;
	}
 
	/**
	 * Retrieves the size of the list.
	 * @return the size of the list
	 */
    public int getSize() {
        return gameList.size();
    }
 
	/**
	 * Retrieves the game at the given index.
	 * @param index
	 * @return the game at the given index.
	 */
	public Game get(int index) {
		return gameList.get(index);
		
	}
	
	/**
	 * Sorts the game list according to a criteria.
	 * @param criteria
	 */
	public void sort(int criterea) {
		quickSort(0, gameList.size()-1, criterea);
	}
	
	/**
	 * Sort the elements of game list using a specific  criteria
	 * @param begin First indice of array
	 * @param end Last indice of array
	 * @param criteria Represents the sort criteria: 0 - alphabetic order
	 * 												 1 - inverse alphabetic order
	 * 												 2 - release date
	 * 												 3 - date added
	 */
	private void quickSort(int begin, int end, int  criteria) {
	    if (begin < end) {
	        int partitionIndex = partition(begin, end,  criteria);

	        quickSort(begin, partitionIndex-1,  criteria);
	        quickSort(partitionIndex+1, end,  criteria);
	    }
	}
	
	/**
	 * 
	 * @param begin First indice of partition
	 * @param end Last indice of partition
	 * @param  criteria represents the sort criteria:
	 * @return
	 */
	private int partition(int begin, int end, int criterea) {
	    Game pivot = gameList.get(end);
	    int i = (begin-1);

	    for (int j = begin; j < end; j++) {
	        if (gameList.get(j).compare(pivot, criterea)) {
	            i++;

	            
	            Collections.swap(gameList, i, j);
	        }
	    }

	    Collections.swap(gameList, i + 1, end);

	    return i+1;
	}
	void reset() {
		gameList = new ArrayList<Game>();
	}
	
    
}