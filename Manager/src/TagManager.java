
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Represents a manager system for class Tag
 *
 * @author Juan Santana
 * @author Giulia Mendes
 */
public class TagManager {
	
	public List<Tag> tagList;
	Map<String, Tag> tagDict;
	
	/**
     * Constructs a new Tag Manager object and loads the tag list.
     */
	public TagManager() {
		tagList = new ArrayList<Tag>();
		tagDict = new HashMap<String, Tag>();
		loadTags();
	}
	/**
	 * Constructs a new Tag Manager object without loading the tag list.
	 * @param tagList
	 * @param tagDict
	 */
	public TagManager(List<Tag> tagList, Map<String, Tag> tagDict) {
		this.tagList = tagList;
		this.tagDict = tagDict;
	}
	
	/**
	 * Creates and add a new tag to the tag list
	 * @param name Name of the new tag
	 * @param description Short description of the new tag
	 */
	public boolean addTag(String name, String description) {
		for(Tag tag: tagList)
			if(name.equals(tag.getTagName()))
					return false;
		Tag t = new Tag(name, description);
		tagList.add(t);	
		tagDict.put(name, t);
		return true;
	}
	/**
	 * Attempt to remove the specified tag from the tag list. If successful, return true; otherwise, return false.
	 * @param tagName Name of the tag to delete
	 * @return True if the tag was successfully removed from the tag list, otherwise false.
	 */ 
	public boolean deleteTag(String tagName) {
		for (Tag tag : tagList) {
			if(tag.getTagName().equals(tagName)) {
				tagList.remove(tag);
				tagDict.remove(tagName);
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
		try {
			String path = "../data/";
		    String filePath = path + "gameTags.ser";
		            
		   // Create the directory if it doesn't exist
		   File directory = new File(path);
		   if (!directory.exists()) 
		             directory.mkdirs(); // Create the directory and any necessary parent directories
		       
		     FileOutputStream fileOut = new FileOutputStream(filePath);
		     ObjectOutputStream out = new ObjectOutputStream(fileOut); 
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
	@SuppressWarnings("unchecked")
	private void loadTags() {
	    try (FileInputStream fileIn = new FileInputStream("../data/gameTags.ser");
	        ObjectInputStream in = new ObjectInputStream(fileIn)) {
	    	
	        this.tagList = (List<Tag>) in.readObject();
	        
	        for(Tag tag: tagList) 
	        	tagDict.put(tag.getTagName(), tag);
	       
	       
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
