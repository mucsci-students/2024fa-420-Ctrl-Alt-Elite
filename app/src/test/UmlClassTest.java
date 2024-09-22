package gradletest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UmlClassTest {

    private UmlClass umlClass;

    @BeforeEach
    public void setUp() {
        umlClass = new UmlClass("ClassA");
    }

    @Test
    public void testGetName() {
        assertEquals("ClassA", umlClass.getName());
    }

    @Test
    public void testSetName() {
        umlClass.setName("ClassB");
        assertEquals("ClassB", umlClass.getName());
    }

    @Test
    public void testAddAttribute() {
        assertTrue(umlClass.addAttribute("Attribute1"));  // Successfully added
        assertFalse(umlClass.addAttribute("Attribute1"));  // Already exists
    }

    @Test
    public void testDeleteAttribute() {
        umlClass.addAttribute("Attribute1");
        assertTrue(umlClass.deleteAttribute("Attribute1"));  // Successfully deleted
        assertFalse(umlClass.deleteAttribute("Attribute1"));  // Already deleted
    }

    @Test
    public void testRenameAttribute() {
        umlClass.addAttribute("Attribute1");
        assertTrue(umlClass.renameAttribute("Attribute1", "Attribute2"));  // Successfully renamed
        assertFalse(umlClass.renameAttribute("Attribute1", "Attribute3"));  // Old name doesn't exist
        assertFalse(umlClass.renameAttribute("Attribute2", "Attribute2"));  // New name already exists
    }

    @Test
    public void testToString() {
        umlClass.addAttribute("Attribute1");
        assertEquals("Class: ClassA\nAttributes: [Attribute1]", umlClass.toString().trim());
    }
}
