import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structures.AVL;
import structures.Node;

public class AVLTest {

    private AVL<Integer> avl;

    @BeforeEach
    void setUp() {
        avl = new AVL<>();
    }

    // --------------- TESTS PARA EL MÉTODO ADD -------------------

    @Test
    void testAddSingleElement() {
        avl.add(10);
        assertEquals(10, avl.search(10).getValue());
        assertEquals(1, avl.search(10).getHeight());
    }

    @Test
    void testAddMultipleElements() {
        avl.add(30);
        avl.add(20);
        avl.add(40);
        assertEquals(20, avl.search(20).getValue());
        assertEquals(40, avl.search(40).getValue());
        assertEquals(2, avl.search(30).getHeight());
    }

    @Test
    void testAddWithBalancing() {
        avl.add(30);
        avl.add(20);
        avl.add(10);
        assertEquals(20, avl.search(20).getValue());
        assertEquals(10, avl.search(10).getValue());
        assertEquals(30, avl.search(30).getValue());
        assertEquals(2, avl.search(20).getHeight());
    }

    // --------------- TESTS PARA EL MÉTODO DELETE ----------------

    @Test
    void testDeleteLeafNode() {
        avl.add(50);
        avl.add(30);
        avl.add(70);
        avl.delete(30);
        assertNull(avl.search(30));
        assertEquals(50, avl.search(50).getValue());
    }

    @Test
    void testDeleteNodeWithOneChild() {
        avl.add(50);
        avl.add(30);
        avl.add(70);
        avl.add(80);
        avl.delete(70);
        assertNull(avl.search(70));
        assertEquals(80, avl.search(80).getValue());
    }

    @Test
    void testDeleteNodeWithTwoChildren() {
        avl.add(50);
        avl.add(30);
        avl.add(70);
        avl.add(60);
        avl.add(80);
        avl.delete(70);
        assertNull(avl.search(70));
        assertEquals(80, avl.search(80).getValue());
        assertEquals(60, avl.search(60).getValue());
    }

    // --------------- TESTS PARA EL MÉTODO SEARCH ----------------

    @Test
    void testSearchExistingElement() {
        avl.add(15);
        avl.add(25);
        avl.add(35);
        Node<Integer> result = avl.search(25);
        assertNotNull(result);
        assertEquals(25, result.getValue());
    }

    @Test
    void testSearchNonExistingElement() {
        avl.add(15);
        avl.add(25);
        avl.add(35);
        Node<Integer> result = avl.search(100);
        assertNull(result);
    }

    @Test
    void testSearchInEmptyTree() {
        Node<Integer> result = avl.search(10);
        assertNull(result);
    }

    // --------------- TESTS PARA EL MÉTODO INORDER ----------------

    @Test
    void testInOrderWithElements() {
        avl.add(30);
        avl.add(10);
        avl.add(20);
        String result = avl.inOrder();
        assertEquals(" 10 20 30 ", result);
    }

    @Test
    void testInOrderEmptyTree() {
        String result = avl.inOrder();
        assertEquals("Empty Tree", result);
    }

    @Test
    void testInOrderAfterDeletion() {
        avl.add(30);
        avl.add(10);
        avl.add(20);
        avl.add(40);
        avl.delete(10);
        String result = avl.inOrder();
        assertEquals(" 20 30 40 ", result);
    }
}
