# Manager
 
Name: Giulia Pereira Mendes			NUSP: 13687582 <br>
Name: Juan Santana 				NUSP: 11954311 <br>
 

## Requirements
1. **System Features:** Implement the following features in the game list system: <br>
- **GameList:** add, delete and sort games in the game list. <br>
- **GameManager:** save and load game files, filter and search games in the game list. <br>
- **TagManager:** add and delete tags in the tag list. Save and load tag files. <br>
2. **GUI Design:** Design a graphical user interface (GUI) using Swing that allows the user to interact with the system and perform the required tasks. The GUI should be intuitive, easy to navigate and visually appealing. <br>
3. **Object-Oriented Design:** Implement the system using object-oriented programming principles. Create classes for games, tags and other relevant entities. Utilize inheritance and polymorphism to create a flexible and extensible system design. <br>
4. **Input/Output:** Implement file I/O to save and load games and tags data from a local database or file. Ensure data integrity and consistency during read/write operations. <br>
5. **Control Structures and Data Structures:** Implement system logic using control structures (loops, conditionals) and appropriate data structures (arrays, lists, maps) to manage game list and tag list. <br>
6. **Exception Handling:** Implement proper exception handling to catch and handle potential errors or invalid user input during system operation. <br>
7. **Documentation:** Include comments and JavaDocs. <br>


## Project Description
Our project is a comprehensive game management system designed to streamline the organization and categorization of games. The core features include the ability to add games and associate them with various tags for easy sorting and retrieval. Here’s a detailed overview of the project: <br>
### Project Description: Game Management System
#### **Objective**                                                                                                            
The main objective of this project is to develop a user-friendly application that allows users to manage their game collection efficiently. Users can add new games, assign tags to these games, and easily search for and filter games based on their tags. <br>

#### **Key Features**
1. **Add Games:** <br>
- Users can input details about their games, such as the title, genre, release date, and developer. <br>
- Each game entry can be enhanced with additional information like a brief description, cover image, and personal ratings. <br>
2. **Tag Management:** <br>
- Users can create and manage tags that categorize games into various groups, such as "Action," "RPG," "Multiplayer," or custom tags like "Favorites." <br>
- Tags can be added or deleted as needed, providing flexibility in how games are categorized. <br>
3. **Game-Tag Association:** <br>
- Users can assign multiple tags to each game, allowing for versatile categorization and improved searchability. <br>
4. **Search and Filter:** <br>
- The system includes powerful search and filter capabilities, enabling users to quickly find games based on their titles or associated tags. <br>
5. **User Interface:** <br>
- The GUI is designed to be intuitive and visually appealing, with clear navigation and organized layouts. <br>
- The interface for adding or editing games and tags is straightforward, minimizing the learning curve for new users. <br>
	 
## Comments About the Code
The src folder contains all the class files required to build the project. The files names are very intuitive, as an example we have the GameTable class file that handles the game table in the GUI. <br>
The JUnit test files used for testing some of the classes are located in the test folder. <br>
More comments explaining the code are found in javadoc. <br>


## Test Plan
Tests were conducted for the GameList, GameManager, and TagManager classes using the JUnit framework. The tests are written below: <br>

```
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
```

```
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
```

```
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
```


## Test Results
Each of the tests succeeded without failures, showing the correctness of the examined classes. <br>


## Build Procedures
The following instructions will guide you to compile and run the code using a Makefile. <br>
- Linux: <br>

Open the terminal in the Manager folder and run the commands in order: <br>
```
make
make run
```
The command make compiles the files and the command make run runs the class file with the main method. If the command make fails, run this command before to clean the bin folder: <br>
```
rm -rf bin
```
Obs.: The command make clean won’t work in Linux.  <br>
Be sure to have jdk installed, if you don’t, run this command before the previous ones: <br>
```
sudo apt install default-jdk
```

- Windows: <br>

First you have to install GNU Make to run the Makefile. <br>
Install the Chocolatey for Windows following the instructions [here](chocolatey.org/install). After installing the Chocolatey, open the terminal (PowerShell or CMD) and run the command: <br>
```
choco install make
```
Now open the terminal in the Manager folder and run the commands in order: <br>
```
make
make run
```
If the command make fails, run this command before to clean the bin folder: <br>
```
rmdir /s /q bin
```
or
```
make clean
```
If you don’t have jdk installed, you can install it [here](https://www.oracle.com/br/java/technologies/downloads/#jdk22-windows) <br>

## Problems 
Using Git for the first time was quite a handful; we experienced numerous issues while merging and cloning the project. Updating Swing components, such as modifying the checkboxes interface after adding or deleting a tag from the tag list, proved to be particularly challenging. <br>

## Comments
We intend to further enhance the game management system by introducing the ability to edit existing games and tags. This addition will provide users with even more flexibility and control over their game collections. <br>
