import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the GameManager class.
 * 
 * @author Juan Santana
 * @author Giulia Mendes
 */
class GameManagerTest {

    private GameManager gameManager;
    private List<Game> sampleGames;
    private List<Tag> sampleTags;

    @BeforeEach
    public void setUp() {
        gameManager = new GameManager();
        sampleGames = new ArrayList<>();
        sampleTags = new ArrayList<>();
        
        // Starts without any game
        gameManager.gameList.reset();
        
        // creating data test
        Tag actionTag = new Tag("Action", null);
        Tag strategyTag = new Tag("Strategy", null);
        
        List<Tag> game1TagList = new ArrayList<>();
        game1TagList.add(actionTag);
        Game game1 = new Game("Game 1", game1TagList, null, false);

        List<Tag> game2TagList = new ArrayList<>();
        game2TagList.add(strategyTag);
        Game game2 = new Game("Game 2", game2TagList, null, true);

        sampleGames.add(game1);
        sampleGames.add(game2);

        sampleTags.add(actionTag);
        sampleTags.add(strategyTag);

        for(Game game: sampleGames)
            gameManager.gameList.gameList.add(game);
    }


    @Test
    public void testFilterByTag() {
        List<Tag> tagsToFilter = new ArrayList<>();
        tagsToFilter.add(new Tag("Action", null));
        gameManager.filterByTag(tagsToFilter, true);

        List<Game> filteredGames = gameManager.getGameList();
        assertEquals(1, filteredGames.size());
        assertEquals("Game 1", filteredGames.get(0).getName());
    }

    @Test
    public void testFilterByPlayed() {
        gameManager.filterByPlayed("Played");
        List<Game> filteredGames = gameManager.getGameList();
        assertEquals(1, filteredGames.size());
        assertEquals("Game 2", filteredGames.get(0).getName());
        gameManager.filterByPlayed("Not Played");
        filteredGames = gameManager.getGameList();
        assertEquals(1, filteredGames.size());
        assertEquals("Game 1", filteredGames.get(0).getName());
    }

    @Test
    public void testSearchGame() {
        gameManager.searchGame("Game 1");
        List<Game> searchResults = gameManager.getGameList();
        System.out.println(searchResults);
        assertEquals(1, searchResults.size());
        assertEquals("Game 1", searchResults.get(0).getName());

        gameManager.searchGame("Nonexistent");
        searchResults = gameManager.getGameList();
        assertEquals(0, searchResults.size());
    }
}
