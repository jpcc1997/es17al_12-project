package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class RoomReserveMethodTest {
	Room room;

	@Before
	public void setUp() {
		Hotel hotel = new Hotel("XPTO123", "Lisboa");
		this.room = new Room(hotel, "01", Type.SINGLE);
	}

	@Test
	public void success() {
		LocalDate arrival = new LocalDate(2016, 12, 19);
		LocalDate departure = new LocalDate(2016, 12, 24);
		Booking booking = this.room.reserve(Type.SINGLE, arrival, departure);

		Assert.assertTrue(booking.getReference().length() > 0);
		Assert.assertEquals(arrival, booking.getArrival());
		Assert.assertEquals(departure, booking.getDeparture());
	}
	
	@Test
	public void nullArrival(){
		LocalDate departure = new LocalDate(2016, 12, 24);
		try{
			this.room.reserve(Type.DOUBLE, null, departure);
			Assert.fail();
			
		} catch(HotelException e){
			Assert.assertEquals(0, this.room.getNumBookings());
		}
	}
	
	@Test 
	public void nullDeparture(){
		LocalDate arrival = new LocalDate(2016, 12, 24);
		try {
			this.room.reserve(Type.DOUBLE,arrival, null);
			Assert.fail();
		} catch(HotelException e){
			Assert.assertEquals(0, this.room.getNumBookings());
		}
	}
	
	@Test 
	public void nullType(){
		LocalDate arrival = new LocalDate(2016, 12, 19);
		LocalDate departure = new LocalDate(2016, 12, 24);
		try{
			this.room.reserve(null,arrival, departure);
			Assert.fail();
		} catch(HotelException e){
			Assert.assertEquals(0, this.room.getNumBookings());
		}
	}
	
	
	@Test 
	public void unexistingType(){
		LocalDate arrival = new LocalDate(2016, 12, 19);
		LocalDate departure = new LocalDate(2016, 12, 24);
		try{
			this.room.reserve(Type.DOUBLE, arrival, departure);
			Assert.fail();
			
		}catch (HotelException e){
			Assert.assertEquals(0, this.room.getNumBookings());
		}
		
	}
	
	@Test
	public void reserveOccupiedRoom(){
		LocalDate arrival = new LocalDate(2016, 12, 19);
		LocalDate departure = new LocalDate(2016, 12, 24);
		this.room.reserve(Type.SINGLE, arrival, departure);
		
		LocalDate arrival1 = new LocalDate(2016, 12, 20);
		try{
			this.room.reserve(Type.SINGLE, arrival1, departure);
			Assert.fail();
			
		}catch (HotelException h){
			Assert.assertEquals(1, this.room.getNumBookings());
		}
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}
	
}
