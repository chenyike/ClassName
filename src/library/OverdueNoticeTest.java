package library;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OverdueNoticeTest {
	private Patron dave;
	private Patron paula;
	private OverdueNotice daveNotice;
	private OverdueNotice paulaNotice;
	private Book book;	
	private Book book1;	
	private Book book2;	

	@Before
	public void setUp() throws Exception {
		dave = new Patron("Dave", null);
		paula = new Patron("Paula", null);
		daveNotice = new OverdueNotice(dave, 20150403);
		paulaNotice = new OverdueNotice(paula,20150515);
		book = new Book("Disappearing Nightly", "Laura Resnick");
		book1 = new Book("1984", "George Orwell");
        book2 = new Book("A Room With A View","E.M. Forster");
	}

	@Test
	public void testOverdueNotice() {
		assertEquals(daveNotice.getPatron().toString(), "Dave");
		assertEquals(daveNotice.getTodaysDate(), 20150403);
		
		assertEquals(paulaNotice.getPatron().toString(), "Paula");
		assertEquals(paulaNotice.getTodaysDate(), 20150515);
	}
	
	@Test
	public void testGetPatron(){
		assertEquals(daveNotice.getPatron().toString(), "Dave");
		assertEquals(daveNotice.getPatron().getName(), "Dave");
		
		assertEquals(paulaNotice.getPatron().toString(), "Paula");
		assertEquals(paulaNotice.getPatron().getName(), "Paula");
	}
	
	@Test
	public void testGetTodaysDate(){
		assertEquals(daveNotice.getTodaysDate(), 20150403);
		assertEquals(paulaNotice.getTodaysDate(), 20150515);
		//more tests
		OverdueNotice daveNotice2 = new OverdueNotice(dave, 2011);
		OverdueNotice paulaNotice2 = new OverdueNotice(paula,2022);	
		assertEquals(daveNotice2.getTodaysDate(), 2011);
		assertEquals(paulaNotice2.getTodaysDate(), 2022);
	}

	@Test
	public void testToString() {
		// test without overdue book
		book.checkOut(20150823);
		daveNotice.getPatron().take(book);
		assertEquals(daveNotice.toString(),"Disappearing Nightly will be due at: 20150823\nNo overdue books!");
		book1.checkOut(20150725);
		daveNotice.getPatron().take(book1);
		assertEquals(daveNotice.toString(),"Disappearing Nightly will be due at: 20150823\n1984 will be due at: 20150725\nNo overdue books!");
		//test with overdue books
		book2.checkOut(20150223);
		daveNotice.getPatron().take(book2);
		assertEquals(daveNotice.toString(),"Disappearing Nightly will be due at: 20150823\n1984 will be due at: 20150725\nA Room With A View will be due at: 20150223\nAttention!!! You have overdue books: A Room With A View ");
		
	}

}
