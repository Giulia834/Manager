import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameManagerTest {
	private GameManager gameManager;

    @BeforeEach
    void setUp() {
        gameManager = new GameManager(new ArrayList<>());
    }
    
	@Test
    void testAddGame() {
        Game game = new Game("The Witcher 3", new ArrayList<>(), LocalDate.of(2015, 5, 19), true);
        assertTrue(gameManager.gameList.addGame(game));
        assertFalse(gameManager.gameList.addGame(game)); // Duplicate game
    }

    @Test
    void testDeleteGame() {
        Game game = new Game("The Witcher 3", new ArrayList<>(), LocalDate.of(2015, 5, 19), true);
        gameManager.gameList.addGame(game);
        assertTrue(gameManager.gameList.deleteGame("The Witcher 3"));
        assertFalse(gameManager.gameList.deleteGame("The Witcher 3"));
    }
    
    @Test
    void testSearchGame() {
        Game game = new Game("The Witcher 3", new ArrayList<>(), LocalDate.of(2015, 5, 19), true);
        gameManager.gameList.addGame(game);
        gameManager.searchGame("The Witcher 3");
        assertEquals(game, gameManager.getGameList().get(0));
    }
    
    @Test
    void testGetGameList() {
    	Game game = new Game("The Witcher 3", new ArrayList<>(), LocalDate.of(2015, 5, 19), true);
    	gameManager.gameList.addGame(game);
    	assertEquals(1, gameManager.getGameList().size());
    	assertEquals("The Witcher 3", gameManager.getGameList().get(0).getName());
    	assertTrue(gameManager.getGameList().get(0).getPlayed());
    }
    
    @Test
    void testSort() {
    	Game game1 = new Game("The Witcher 3", new ArrayList<>(), LocalDate.of(2015, 5, 19), true);
    	Game game2 = new Game("Hades", new ArrayList<>(), LocalDate.of(2020, 9, 17), true);
    	Game game3 = new Game("Bloodborne", new ArrayList<>(), LocalDate.of(2015, 3, 24), true);
    	gameManager.gameList.addGame(game1);
    	gameManager.gameList.addGame(game2);
    	gameManager.gameList.addGame(game3);
    	assertEquals("The Witcher 3", gameManager.getGameList().get(0).getName());
    	assertEquals("Hades", gameManager.getGameList().get(1).getName());
    	assertEquals("Bloodborne", gameManager.getGameList().get(2).getName());
    	// Sorts in alphabetical order
    	gameManager.gameList.sort(0);
    	assertEquals("Bloodborne", gameManager.getGameList().get(0).getName());
    	// Sorts in inverse alphabetical order
    	gameManager.gameList.sort(1);
    	assertEquals("The Witcher 3", gameManager.getGameList().get(0).getName());
    	// Sorts by release date (The first is the most recent)
    	gameManager.gameList.sort(2);
    	assertEquals("Hades", gameManager.getGameList().get(0).getName());
    }

}

