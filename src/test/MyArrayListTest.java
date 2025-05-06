package test;

import utility.collection.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit tests for MyArrayList
 */
public class MyArrayListTest {
    private ArrayList<String> list;

    @Before
    public void setUp() {
        list = new ArrayList<>();
    }

    @Test
    public void testInitialSize() {
        assertEquals("Initial size should be 0", 0, list.size());
        assertTrue("List should be empty initially", list.isEmpty());
    }

    @Test
    public void testAdd() {
        list.add("Item 1");
        assertEquals("Size should be 1 after adding 1 item", 1, list.size());
        assertFalse("List should not be empty after adding an item", list.isEmpty());

        list.add("Item 2");
        assertEquals("Size should be 2 after adding 2 items", 2, list.size());
    }

    @Test
    public void testGet() {
        list.add("Item 1");
        list.add("Item 2");

        assertEquals("Get should return correct item at index 0", "Item 1", list.get(0));
        assertEquals("Get should return correct item at index 1", "Item 2", list.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetWithInvalidIndex() {
        list.get(0); // Should throw exception when list is empty
    }

    @Test
    public void testRemove() {
        list.add("Item 1");
        list.add("Item 2");
        list.add("Item 3");

        String removed = list.remove(1);

        assertEquals("Remove should return the removed item", "Item 2", removed);
        assertEquals("Size should decrease after removal", 2, list.size());
        assertEquals("Items should shift after removal", "Item 3", list.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveWithInvalidIndex() {
        list.remove(0); // Should throw exception when list is empty
    }

    @Test
    public void testClear() {
        list.add("Item 1");
        list.add("Item 2");

        // Manually clear the list since clear() doesn't exist
        while (!list.isEmpty()) {
            list.remove(0);
        }

        assertEquals("Size should be 0 after clearing manually", 0, list.size());
        assertTrue("List should be empty after clearing manually", list.isEmpty());
    }


    @Test
    public void testCapacityIncrease() {
        // Fill the list to test capacity increase
        for (int i = 0; i < 20; i++) {
            list.add("Item " + i);
        }

        assertEquals("All items should be added despite capacity changes", 20, list.size());

        // Check that all items are still accessible
        for (int i = 0; i < 20; i++) {
            assertEquals("Item " + i, list.get(i));
        }
    }
}