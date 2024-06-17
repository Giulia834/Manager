import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameManagerTest {
	private GameManager gameManager;

    @BeforeEach
    void setUp() {
        gameManager = new GameManager();
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

}

