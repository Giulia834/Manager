

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a manager system for class Game
 *
 * @author Juan Santana
 * @author Giulia Mendes
 */
public class GameManager {
	List<Game> gameList = new ArrayList<Game>();
	List<Tag> selectedTags = new ArrayList<Tag>();
	boolean tagFilter = false;
	/**
     * Constructs a new Game Manager object and loads the game list.
     */
	public GameManager() {
		loadGames();
		resetSelectedTags();
	}
	
	/**
	 * add game to the game list
	 * @param game Game object
	 */
	public void addGame(Game game) {
		gameList.add(game);
	}
	/**

    * Attempt to remove the specified game from the game list. If successful, return true; otherwise, return false.
    * @param gameName A string representing the name of the game to be removed
    * @return True if the game was successfully removed from the game list, otherwise false.
    * @throws java.lang.IllegalArgumentException If the provided argument is incompatible with the expected pattern.
    */
	public boolean deleteGame(String gameName) {
		for (Game game : gameList) {
			if(game.getName().equals(gameName)) {
				gameList.remove(game);
				return true;
			}
		}
		return false;
}
	/**
	 * Saves the game list into a serialized file
	 * 
	 */
    public void saveGames() {
        try (FileOutputStream fileOut = new FileOutputStream("../data/gameManager.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(getGameList());
            System.out.println("Serialized data is saved in gameTags.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
	
	/**
	 * returns the game list
	 * @return gameList A list of object Game
	 */
	public List<Game> getGameList() {
		List<Game> gameListAux = new ArrayList<Game>(gameList);
		if(tagFilter) {			
			for(Game game: gameList) {
				boolean selectGame = false;
				for(Tag tag: selectedTags) {
					for(Tag gameTag: game.getTags()) {
						if(gameTag.getTagName().equals(tag.getTagName())) {
							selectGame = true;
							break;
						}
					}
					if(selectGame)
						break;
				}
				if(!selectGame)
					gameListAux.remove(game);
			}
		}
		return gameListAux;
	}
	 
	/**
	 * loads the game list from a serialized file
	 */
	private void resetSelectedTags() {
		for(Game game: gameList)
			for(Tag tag: game.getTags())
				if(!selectedTags.contains(tag))
					selectedTags.add(tag);
	}
	private void loadGames() {
		  try (FileInputStream fileIn = new FileInputStream("../data/gameManager.ser");
		        ObjectInputStream in = new ObjectInputStream(fileIn)) {
		        gameList = (List<Game>) in.readObject();
		        } catch (IOException i) {
		            i.printStackTrace();
		        } catch (ClassNotFoundException c) {
		            System.out.println("Tag class not found");
		            c.printStackTrace();
		        }
	}
	public void sort(int criterea) {
		quickSort(0, gameList.size(), criterea);
	}
	private void quickSort(int begin, int end, int criterea) {
	    if (begin < end) {
	        int partitionIndex = partition(begin, end, criterea);

	        quickSort(begin, partitionIndex-1, criterea);
	        quickSort(partitionIndex+1, end, criterea);
	    }
	}
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
	public void filterByTag(List<Tag> selectedTags, boolean filter) {
		this.selectedTags = selectedTags;
		tagFilter = filter;
	}
}

