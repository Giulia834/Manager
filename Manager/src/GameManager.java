import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;



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
	int playedFilter = 0;
	/**
     * Constructs a new Game Manager object and loads the game list.
     */
	public GameManager() {
		loadGames();
		resetSelectedTags();
	}
	public GameManager(List<Game> gameList) {
		this.gameList.gameList = gameList;
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
			if(!selectedTags.isEmpty()) {
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
		}
		if(playedFilter != 0) {
			if(playedFilter==1) {
				for(Game game: gameList.gameList)
					if(!game.getPlayed()) 
						gameListAux.remove(game);
			}
			else {
				for(Game game: gameList.gameList)
					if(game.getPlayed()) 
						gameListAux.remove(game);
			}
		}
		return gameListAux;
	}
	
	/**
	 * Reset selected tags list
	 */
	private void resetSelectedTags() {
		for(Game game: gameList.gameList)
			for(Tag tag: game.getTags())
				if(!selectedTags.contains(tag))
					selectedTags.add(tag);
	}
	
	/**
	 * loads the game list from a serialized file
	 */
	@SuppressWarnings("unchecked")
	protected void loadGames() {
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
	
	/**
	 * Filters the games by the selected tag.
	 * 
	 * @param selectedTags
	 * @param filter
	 */
	public void filterByTag(List<Tag> selectedTags, boolean filter) {
		this.selectedTags = selectedTags;
		tagFilter = filter;
	}
	
	/**
	 * Filters the games by played or not played. By default it is set to All.
	 * 
	 * @param p String that indicates the selected filter.
	 */
	public void filterByPlayed(String p) {
		if(p.equals("All"))
			playedFilter = 0;
		else if(p.equals("Played"))
			playedFilter = 1;
		else
			playedFilter = -1;
	}
	
	/**
	 * Shows the games searched in the table
	 * 
	 * @param gameName
	 */
	public void searchGame(String gameName) {
		 if(gameName.equals("") || gameName == null) {
		 	searchGameList = new ArrayList<Game>(gameList.gameList);
		 	showShearchList = false;
		 	return;
		 }
		 searchGameList = new ArrayList<>();
		 for(Game game: gameList.gameList)
		 	if(game.getName().toLowerCase().contains(gameName.toLowerCase()))
		 		searchGameList.add(game);
		 showShearchList = true;   
	    }
}