import java.util.HashSet;
import java.util.Set;

/**
 * Represents all attributes for a class in a UML editor.
 */
public class UmlClassAttributes {
	//always need to check if the class exists and if the name of attr is unique
	
	private Set<UmlAttribute> attributes;
	private MyUmlClass attrClass;
	
	public UmlClassAttributes (MyUmlClass attrClass) {
		this.attributes = new HashSet<>();
		this.attrClass = attrClass;
	}
	
	// TODO
	/**
	 * Add requires a class name and a unique name; 
	 * should fail if name is invalid or duplicated 
	 * or if class name does not exist 
	 * @return
	 */
	public boolean addAttribute (UmlAttribute attr) {
		
		return false;
	}
	
	//TODO
	/**
	 * Delete requires a class name and a unique name; 
	 * should fail if name is invalid 
	 * or if class name does not exist 
	 * @return
	 */
	public boolean deleteAttribute () {
		return false;
	}
	
	//TODO
	/**
	 * Rename requires a class name and a unique name; 
	 * should fail if name is invalid or duplicated 
	 * or if class name does not exist
	 * @return
	 */
	public boolean renameAttribute () {
		return false;
	}
	
	/**
	 * Creates a string representation of the set of attributes.
	 * 
	 * @return A string representation of the set of attributes.
	 */
	public String toString() {
		return attributes.toString(); //TODO need to test, should only print out names
	}
}