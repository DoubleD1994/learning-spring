package com.daviddryburgh.learningspring.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daviddryburgh.learningspring.business.domain.RoomReservation;
import com.daviddryburgh.learningspring.business.service.ReservationService;

@RestController
@RequestMapping("api/reservations")
public class RoomReservationWebServiceController {

	private final ReservationService reservationService;
	
	@Autowired
	public RoomReservationWebServiceController(ReservationService reservationService) {
		super();
		this.reservationService = reservationService;
	}
	
	@GetMapping
	public List<RoomReservation> getRoomReservations(@RequestParam(name="date", required=false)String dateString){
		Date date = DateUtils.createDateFromDateString(dateString);
		return this.reservationService.getRoomReservationsForDate(date);
	}
	
	
}
