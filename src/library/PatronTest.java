package library;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PatronTest {
	 private Patron dave;
	 private Patron paula;
	 private Book book;	

	@Before
	public void setUp() throws Exception {
		dave = new Patron("Dave", null);
        paula = new Patron("Paula", null);
        book = new Book("Disappearing Nightly", "Laura Resnick");
	}

	@Test
	public void testPatron() {
		assertTrue(dave instanceof Patron);
		assertTrue(paula instanceof Patron);
		//more tests
		Patron james = new Patron("James",null);
		Patron jian = new Patron("Jian Li",null);
		assertTrue(james instanceof Patron);
		assertTrue(jian instanceof Patron);
		
	}

	@Test
	public void testGetName() {
		assertEquals("Dave", dave.getName());
        assertEquals("Paula", paula.getName());
      //more tests
      	Patron james = new Patron("James",null);
      	Patron jian = new Patron("Jian Li",null);
      	assertEquals("James", james.getName());
        assertEquals("Jian Li", jian.getName());
	}

	@Test
	public void testTake() {
		paula.take(book);
        assertTrue(paula.getBooks().contains(book));
        assertFalse(dave.getBooks().contains(book));
        //adding more books
        Book book1 = new Book("1984", "George Orwell");
        Book book2 = new Book("A Room With A View","E.M. Forster");
        paula.take(book1);
        paula.take(book2);
        assertTrue(paula.getBooks().contains(book1));
        assertTrue(paula.getBooks().contains(book2));
        assertEquals(paula.getBooks().size(), 3);
        //test there are at most 3 books in the list!
        Book book3 = new Book("All the King's Men","Robert Penn Warren");
        paula.take(book3);
        assertFalse(paula.getBooks().contains(book3));
        assertEquals(paula.getBooks().size(), 3);
	}

	@Test
	public void testGiveBack() {
		paula.take(book);
        assertTrue(paula.getBooks().contains(book));
        paula.giveBack(book);
        assertFalse(paula.getBooks().contains(book));
        
        dave.take(book);
        assertTrue(dave.getBooks().contains(book));
        dave.giveBack(book);
        assertFalse(dave.getBooks().contains(book));
	}

	@Test
	public void testGetBooks() {
		paula.take(book);
        assertTrue(paula.getBooks().contains(book));
        assertEquals(paula.getBooks().size(), 1);
        //more tests
		dave.take(book);
        assertTrue(dave.getBooks().contains(book));
        assertEquals(dave.getBooks().size(), 1);
	}

	@Test
	public void testToString() {
		assertEquals(dave.toString(),"Dave");
        assertEquals(paula.toString(),"Paula");
        //more tests
        Patron james = new Patron("James",null);
		Patron jian = new Patron("Jian Li",null);
		assertEquals(james.toString(),"James");
		assertEquals(jian.toString(),"Jian Li");
	}

}
