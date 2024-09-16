
public class UmlAttribute {
	/** The name of the attribute. */
    private String name;
    
    /** The associated class of the attribute. */
    private MyUmlClass attrClass;
	
	/**
	 * Constructs an attribute with a unique name, as well as an associated class name.
	 * 
	 * @param name The name of the attribute.
	 * @param className The name of the associated class of the attribute.
	 */
	public UmlAttribute(String name, MyUmlClass attrClass) {
		this.name = name;
		this.attrClass = attrClass; // TODO might need to check to make sure this copy works
	}
	
	/*
	 * Returns the name of the attribute.
	 * 
	 * @return The name of the attribute.
	 */
	public String getName() {
		return this.name;
	}
	 
	/**
	 * Returns the name of the attribute's associated class.
	 * 
	 * @return The name of the attribute's associated class.
	 */
	public String getClassName() {
		return this.attrClass.getName();
	}
	
	/**
	 * Returns a string representation of an attribute.
	 * 
	 * @return A string representation of an attribute.
	 */
	public String toStirng() {
		return this.getName();
	}
}	
