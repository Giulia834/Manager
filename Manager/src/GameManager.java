

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * Represents a manager system for class Game
 *
 * @author Juan Santana
 * @author Giulia Mendes
 */
public class GameManager {
	GameList gameList = new GameList();
	List<Game> searchGameList = new ArrayList<Game>();
	List<Tag> selectedTags = new ArrayList<Tag>();
	boolean showShearchList = false;
	boolean tagFilter = false;
	/**
     * Constructs a new Game Manager object and loads the game list.
     */
	public GameManager() {
		loadGames();
		resetSelectedTags();
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
		List<Game> gameListAux;
		if(showShearchList)			
			gameListAux = new ArrayList<Game>(searchGameList);
		else
			gameListAux = new ArrayList<Game>(gameList.gameList);
		if(tagFilter) {		
			if(selectedTags.isEmpty())
				return gameListAux;
		
			for(Game game: gameList.gameList) {
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
		for(Game game: gameList.gameList)
			for(Tag tag: game.getTags())
				if(!selectedTags.contains(tag))
					selectedTags.add(tag);
	}
	private void loadGames() {
		  try (FileInputStream fileIn = new FileInputStream("../data/gameManager.ser");
		        ObjectInputStream in = new ObjectInputStream(fileIn)) {
		        gameList.gameList = (List<Game>) in.readObject();
		        } catch (IOException i) {
		            i.printStackTrace();
		        } catch (ClassNotFoundException c) {
		            System.out.println("Tag class not found");
		            c.printStackTrace();
		        }
	}

	public void filterByTag(List<Tag> selectedTags, boolean filter) {
		this.selectedTags = selectedTags;
		tagFilter = filter;
	}
	 void searchGame(String gameName) {
		 	if(gameName.equals("") || gameName == null) {
		 		searchGameList = new ArrayList<Game>(gameList.gameList);
		 		showShearchList = false;
		 		return;
		 	}
		 	searchGameList = new ArrayList<>();
		 	for(Game game: gameList.gameList)
		 		if(game.getName().contains(gameName))
		 			searchGameList.add(game);
		 	showShearchList = true;
	      
	 
	       
	 
	        
	    }
}