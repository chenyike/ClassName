package library;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BookTest {
	Book book1;
	Book book2;

	@Before
	public void setUp() throws Exception {
		book1 = new Book("Double Star","Robert Heinlein");
		book2 = new Book("On the Road","Jack Kerouac");
	}
	
	@Test
	public void testBook(){
		assertTrue(book1 instanceof Book);
		assertTrue(book2 instanceof Book);
		//more tests
		Book newBook1 = new Book("lala","haha");		
		Book newBook2 = new Book("  ","haha");
		assertTrue(newBook1 instanceof Book);
		assertTrue(newBook2 instanceof Book);
	}

	@Test
	public void testGetTitle() {
		assertEquals(book1.getTitle(),"Double Star");
		assertEquals(book2.getTitle(),"On the Road");
		
		//more tests
		Book newBook1 = new Book("lala","haha");		
		assertEquals(newBook1.getTitle(),"lala");
		Book newBook2 = new Book("  ","haha");		
		assertEquals(newBook2.getTitle(),"  ");
	}

	@Test
	public void testGetAuthor() {
		assertEquals(book1.getAuthor(),"Robert Heinlein");
		assertEquals(book2.getAuthor(),"Jack Kerouac");
		
		//more tests
		Book newBook1 = new Book("lala","haha");		
		assertEquals(newBook1.getAuthor(),"haha");
		Book newBook2 = new Book("haha","  ");		
		assertEquals(newBook2.getAuthor(),"  ");
	}

	@Test
	public void testGetDueDate() {
		assertEquals(book1.getDueDate(),-1);
		assertEquals(book2.getDueDate(),-1);
		//make some changes to the dueDate and test again
		book1.checkOut(22);
		assertEquals(book1.getDueDate(),22);
		book1.checkOut(20150403);
		assertEquals(book1.getDueDate(),20150403);
		
		book2.checkOut(1111);
		assertEquals(book2.getDueDate(),1111);
		book2.checkOut(20150514);
		assertEquals(book2.getDueDate(),20150514);
	}

	@Test
	public void testCheckOut() {
		book1.checkOut(33);
		assertEquals(book1.getDueDate(),33);
		book1.checkOut(2015);
		assertEquals(book1.getDueDate(),2015);
		
		book2.checkOut(2222);
		assertEquals(book2.getDueDate(),2222);
		book2.checkOut(20150515);
		assertEquals(book2.getDueDate(),20150515);
	}

	@Test
	public void testCheckIn() {
		book1.checkOut(33);
		book1.checkIn();
		assertEquals(book1.getDueDate(),-1);
		book2.checkOut(2222);
		book2.checkIn();
		assertEquals(book2.getDueDate(),-1);
	}

	@Test
	public void testToString() {
		assertEquals(book1.toString(),"Double Star, by Robert Heinlein");
		assertEquals(book2.toString(),"On the Road, by Jack Kerouac");
		
		//more tests
		Book newBook1 = new Book("lala","haha");		
		assertEquals(newBook1.toString(),"lala, by haha");
		Book newBook2 = new Book("haha","James");		
		assertEquals(newBook2.toString(),"haha, by James");
	}

}
