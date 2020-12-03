package com.daviddryburgh.learningspring.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.daviddryburgh.learningspring.business.domain.RoomReservation;
import com.daviddryburgh.learningspring.business.service.ReservationService;

@Controller
@RequestMapping("/reservations")
public class RoomReservationWebController {

	private final ReservationService reservationService;

	@Autowired
	public RoomReservationWebController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}
	
	@GetMapping
	public String getReservations(@RequestParam(value="date", required = false)String dateString, Model model) {
		Date date = DateUtils.createDateFromDateString(dateString);
		List<RoomReservation> roomReservations = this.reservationService.getRoomReservationsForDate(date);
		model.addAttribute("roomReservations", roomReservations);
		return "reservations";
	}
	
	
}
