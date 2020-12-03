package com.daviddryburgh.learningspring.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.daviddryburgh.learningspring.business.domain.RoomReservation;
import com.daviddryburgh.learningspring.business.service.ReservationService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(RoomReservationWebController.class)
public class RoomReservationWebControllerTest {

	@MockBean
	private ReservationService reservationService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void getReservation() throws Exception{
		String dateString = "2020-01-01";
		Date date = DateUtils.createDateFromDateString(dateString);
		List<RoomReservation> roomReservations = new ArrayList<>();
		RoomReservation roomReservation = new RoomReservation();
		roomReservation.setFirstName("JUnit");
		roomReservation.setLastName("Unit");
		roomReservation.setDate(date);
		roomReservation.setGuestId(1);
		roomReservation.setRoomId(100);
		roomReservation.setRoomName("JUnit Room");
		roomReservation.setRoomNumber("J1");
		roomReservations.add(roomReservation);
		
		given(reservationService.getRoomReservationsForDate(date)).willReturn(roomReservations);
		
		this.mockMvc.perform(get("/reservations?date=2020-01-01"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Unit, JUnit")));
		
	}

}
