package fa.ctrl.alt.elite;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a UML class with a name and a set of attributes.
 */
public class UmlClass {
    private String name;
    private Set<String> attributes;

    public UmlClass(String name) {
        this.name = name;
        this.attributes = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean addAttribute(String attribute) {
        return attributes.add(attribute);
    }

    public boolean deleteAttribute(String attribute) {
        return attributes.remove(attribute);
    }

    public boolean renameAttribute(String oldName, String newName) {
        if (attributes.contains(oldName) && !attributes.contains(newName)) {
            attributes.remove(oldName);
            attributes.add(newName);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Class: " + name + "\nAttributes: " + attributes;
    }
}