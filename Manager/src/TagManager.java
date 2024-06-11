
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * Represents a manager system for class Tag
 *
 * @author Juan Santana
 * @author Giulia Mendes
 */
public class TagManager {
	
	public List<Tag> tagList;
	
	/**
     * Constructs a new Tag Manager object and loads the tag list.
     */
	public TagManager() {
		tagList = new ArrayList<Tag>();
		loadTags();
	}
	
	/**
	 * Creates and add a new tag to the tag list
	 * @param name Name of the new tag
	 * @param description Short description of the new tag
	 */
	public void addTag(String name, String description) {
		Tag t = new Tag(name, description);
		tagList.add(t);
	}
	/**
	 * Attempt to remove the specified game from the game list. If successful, return true; otherwise, return false.
	 * @param tagName Name of the tag to delete
	 * @return True if the tag was successfully removed from the tag list, otherwise false.
	 */ 
	public boolean deleteTag(String tagName) {
		for (Tag tag : tagList) {
			if(tag.getTagName().equals(tagName)) {
				tagList.remove(tag);
				return true;
			}
		}
		return false;
}
	/**
	 * Saves the tag list into a serialized file
	 * 
	 */
	public void saveTags() {
		try (FileOutputStream fileOut = new FileOutputStream("gameTags.ser");
	     ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
	     out.writeObject(getTagList());
	     System.out.println("Serialized data is saved in gameTags.ser");
	     out.close();
		}catch (IOException i) {
		 i.printStackTrace();
		}
		
	}
	/**
	 * returns the tag list
	 * @return tagList A list of tags
	 */
	public List<Tag> getTagList() {
		return tagList;
	}
	/**
	 * loads the tag list from a serialized file
	 */
	private void loadTags() {
	    try (FileInputStream fileIn = new FileInputStream("gameTags.ser");
	        ObjectInputStream in = new ObjectInputStream(fileIn)) {
	    	
	        this.tagList = (List<Tag>) in.readObject();
	       
	       
	        in.close();
	    } catch (FileNotFoundException fnf) {
	        System.out.println("The file gameTags.ser was not found.");
	        fnf.printStackTrace();
	    } catch (IOException i) {
	        System.out.println("Error reading the file gameTags.ser.");
	        i.printStackTrace();
	    } catch (ClassNotFoundException c) {
	        System.out.println("Tag class not found.");
	        c.printStackTrace();
	    } catch (ClassCastException cc) {
	        System.out.println("Error casting the read object to List<Tag>.");
	        cc.printStackTrace();
	    }
	}
}
