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
        assertEquals("start size should be 0", 0, list.size());
        assertTrue("List should be empty ", list.isEmpty());
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

        assertEquals(" should return correct item at index 0", "Item 1", list.get(0));
        assertEquals(" should return correct item at index 1", "Item 2", list.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetWithInvalidIndex() {
        list.get(0);
    }

    @Test
    public void testRemove() {
        list.add("Item 1");
        list.add("Item 2");
        list.add("Item 3");

        String removed = list.remove(1);

        assertEquals("remove should return the removed item", "Item 2", removed);
        assertEquals("size should decrease after removal", 2, list.size());
        assertEquals("items should shift after removal", "Item 3", list.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveWithInvalidIndex() {
        list.remove(0);
    }

    @Test
    public void testClear() {
        list.add("Item 1");
        list.add("Item 2");

        while (!list.isEmpty()) {
            list.remove(0);
        }

        assertEquals("size should be 0 after clearing manually", 0, list.size());
        assertTrue("list should be empty after clearing manually", list.isEmpty());
    }


    @Test
    public void testCapacityIncrease() {
        for (int i = 0; i < 20; i++) {
            list.add("Item " + i);
        }

        assertEquals("all items should be added despite capacity changes", 20, list.size());

        for (int i = 0; i < 20; i++) {
            assertEquals("Item " + i, list.get(i));
        }
    }
}