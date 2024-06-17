
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class GameListTest {

    private GameList gameList;

    @BeforeEach
    public void setUp() {
        gameList = new GameList(new ArrayList<>());
        
        // Cria amostras de dados para os testes
        List<Tag> game1Tags = new ArrayList<>();
        game1Tags.add(new Tag("Action", null));
        Game game1 = new Game("Game 1", game1Tags,  LocalDate.of(2021, 1, 1), false);
        
        List<Tag> game2Tags = new ArrayList<>();
        game2Tags.add(new Tag("Strategy", null));
        Game game2 = new Game("Game 2", game2Tags, LocalDate.of(2020, 1, 1), false);

        gameList.addGame(game1);
        gameList.addGame(game2);
    }

    @Test
    public void testAddGame() {
        List<Tag> game3Tags = new ArrayList<>();
        game3Tags.add(new Tag("Adventure", null));
        Game game3 = new Game("Game 3", game3Tags, null, false);

        assertTrue(gameList.addGame(game3));
        assertEquals(3, gameList.getSize());

        //try to add a game with the same name
        assertFalse(gameList.addGame(new Game("Game 3", game3Tags, null, false)));
    }

    @Test
    public void testDeleteGame() {
        assertTrue(gameList.deleteGame("Game 1"));
        assertEquals(1, gameList.getSize());

        // Try to remove a game not in the list
        assertFalse(gameList.deleteGame("Nonexistent Game"));
    }

    @Test
    public void testGetSize() {
        assertEquals(2, gameList.getSize());
    }

    @Test
    public void testGet() {
        Game game = gameList.get(0);
        assertEquals("Game 1", game.getName());
    }

    @Test
    public void testSortAlphabetic() {
        gameList.sort(0);
        assertEquals("Game 1", gameList.get(0).getName());
        assertEquals("Game 2", gameList.get(1).getName());
    }

    @Test
    public void testSortInverseAlphabetic() {
        gameList.sort(1);
        assertEquals("Game 2", gameList.get(0).getName());
        assertEquals("Game 1", gameList.get(1).getName());
    }

    @Test
    public void testSortReleaseDate() {
    	gameList.sort(2);
        assertEquals("Game 2", gameList.get(1).getName());
        assertEquals("Game 1", gameList.get(0).getName());
    }

    @Test
    public void testSortDateAdded() {
    	gameList.sort(3);
        assertEquals("Game 2", gameList.get(1).getName());
        assertEquals("Game 1", gameList.get(0).getName());
    }

    @Test
    public void testReset() {
        gameList.reset();
        assertEquals(0, gameList.getSize());
    }
}
