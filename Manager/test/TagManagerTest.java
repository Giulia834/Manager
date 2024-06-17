import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the TagManager class.
 * 
 * @author Juan Santana
 * @author Giulia Mendes
 */
class TagManagerTest {

	private TagManager tagManager;

    @BeforeEach
    void setUp() {
        tagManager = new TagManager(new ArrayList<Tag>(),new HashMap<String, Tag>());
    }

    @Test
    void testAddTag() {
        assertTrue(tagManager.addTag("RPG", "Role-playing game"));
        assertFalse(tagManager.addTag("RPG", "Duplicate tag"));
    }

    @Test
    void testDeleteTag() {
        tagManager.addTag("RPG", "Role-playing game");
        assertTrue(tagManager.deleteTag("RPG"));
        assertFalse(tagManager.deleteTag("RPG"));
    }

    @Test
    void testGetTagList() {
        tagManager.addTag("RPG", "Role-playing game");
        assertEquals(1, tagManager.getTagList().size());
        assertEquals("RPG", tagManager.getTagList().get(0).getTagName());
    }

}
