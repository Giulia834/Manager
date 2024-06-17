/**
 * Package Manager contains classes related to managing games.
 */


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a game entity.
 * 
 * @author Juan Santana
 * @author Giulia Mendes
 */
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private List<Tag> tags;
    private LocalDate releaseDate;
    private LocalDateTime dateAdded;
    private boolean played;

    /**
     * Retrieves the name of the game.
     * @return The name of the game.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the game.
     * @param name The name of the game.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the list of tags associated with the game.
     * @return The list of tags associated with the game.
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * Retrieves the release date of the game.
     * @return The release date of the game.
     */
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the release date of the game.
     * @param releaseDate The release date of the game.
     */
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Retrieves the date when the game was added.
     * @return The date when the game was added.
     */
    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    /**
     * Constructs a new Game object with the specified parameters.
     * @param name The name of the game.
     * @param tags The list of tags associated with the game.
     * @param releaseDate The release date of the game.
     */
    public Game(String name, List<Tag> tags, LocalDate releaseDate, boolean played) {
        this.name = name;
        this.tags = tags;
        this.releaseDate = releaseDate;
        this.dateAdded = LocalDateTime.now();
        this.played = played;
    }
    /**
     * Returns if the game was played or not.
     * @return True if it was played. False otherwise.
     */
    public boolean getPlayed() {
    	return played;
    }
    /**
     * Compares the games according to the given criteria.
     * @param game
     * @param  criteria  criteria used to compare two games
     * @return
     */
    public boolean  compare(Game game, int  criteria) {
    	if( criteria == 0)
        	return this.name.compareToIgnoreCase(game.name) <= 0;
    	if( criteria == 1)
    		return this.name.compareToIgnoreCase(game.name) >= 0;
    	if( criteria == 2)
    		return this.releaseDate.compareTo(game.releaseDate)  >= 0;
    	if( criteria == 3)
    		return this.dateAdded.compareTo(game.dateAdded)  >= 0;
    	return false; 		
    }
    @Override
    public String toString() {
		return name;
		}
}
