package library;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CalendarTest {
	Calendar cal1;
	Calendar cal2;

	@Before
	public void setUp() throws Exception {
		cal1 = new Calendar();
		cal2 = new Calendar();	
	}

	@Test
	public void testCalendar() {
		//test the construction for the two calendars
		assertEquals(cal1.getDate(), 0, 0);
		assertEquals(cal2.getDate(), 0, 0);
		assertTrue(cal1 instanceof Calendar);
		assertTrue(cal2 instanceof Calendar);
	}

	@Test
	public void testGetDate() {
		assertEquals(cal1.getDate(), 0, 0);
		cal1.advance();
		assertEquals(cal1.getDate(), 1, 0);
		cal1.advance();
		cal1.advance();
		assertEquals(cal1.getDate(), 3, 0);
		
		//test again for the cal2
		assertEquals(cal2.getDate(), 0 , 0);
		cal2.advance();
		cal2.advance();
		assertEquals(cal2.getDate(), 2, 0);
		cal2.advance();
		cal2.advance();
		assertEquals(cal2.getDate(), 4, 0);
	}

	@Test
	public void testAdvance() {
		cal1.advance();
		assertEquals(cal1.getDate(), 1, 0);
		cal1.advance();
		cal1.advance();
		assertEquals(cal1.getDate(), 3, 0);
		cal1.advance();
		cal1.advance();
		cal1.advance();
		assertEquals(cal1.getDate(), 6, 0);
		//test for cal2
		cal2.advance();
		cal2.advance();
		assertEquals(cal2.getDate(), 2, 0);
		cal2.advance();
		cal2.advance();
		assertEquals(cal2.getDate(), 4, 0);
	}

}
