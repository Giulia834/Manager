package Manager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a manager system for class Game
 *
 * @author Juan Santana
 * @author Giulia Mendes
 */
public class GameManager {
	List<Game> gameList = new ArrayList<Game>();
	/**
     * Constructs a new Game Manager object and loads the game list.
     */
	public GameManager() {
		loadGames();
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
        try (FileOutputStream fileOut = new FileOutputStream("gameManager.ser");
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
		return gameList;
	}
	 
	/**
	 * loads the game list from a serialized file
	 */
	private void loadGames() {
		  try (FileInputStream fileIn = new FileInputStream("gameManager.ser");
		        ObjectInputStream in = new ObjectInputStream(fileIn)) {
		        gameList = (List<Game>) in.readObject();
		        } catch (IOException i) {
		            i.printStackTrace();
		        } catch (ClassNotFoundException c) {
		            System.out.println("Tag class not found");
		            c.printStackTrace();
		        }
	}
}

