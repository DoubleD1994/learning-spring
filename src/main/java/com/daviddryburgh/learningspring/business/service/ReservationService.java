package com.daviddryburgh.learningspring.business.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daviddryburgh.learningspring.business.domain.RoomReservation;
import com.daviddryburgh.learningspring.data.entity.Guest;
import com.daviddryburgh.learningspring.data.entity.Reservation;
import com.daviddryburgh.learningspring.data.entity.Room;
import com.daviddryburgh.learningspring.data.repository.GuestRepository;
import com.daviddryburgh.learningspring.data.repository.ReservationRepository;
import com.daviddryburgh.learningspring.data.repository.RoomRepository;

@Service
public class ReservationService {
	
	private final RoomRepository roomRepository;
	private final GuestRepository guestRepository;
	private final ReservationRepository reservationRepository;
	
	@Autowired
	public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository,
			ReservationRepository reservationRepository) {
		super();
		this.roomRepository = roomRepository;
		this.guestRepository = guestRepository;
		this.reservationRepository = reservationRepository;
	}

	public List<RoomReservation> getRoomReservationsForDate(Date date){
		Iterable<Room> rooms = this.roomRepository.findAll();
		Map<Long, RoomReservation> roomReservationMap = new HashMap<Long, RoomReservation>();
		rooms.forEach(room -> {
			RoomReservation roomReservation = new RoomReservation();
			roomReservation.setRoomId(room.getRoomId());
			roomReservation.setRoomName(room.getRoomName());
			roomReservation.setRoomNumber(room.getRoomNumber());
			roomReservationMap.put(room.getRoomId(), roomReservation);
		});
		Iterable<Reservation> reservations = this.reservationRepository.findReservationByReservationDate(new java.sql.Date(date.getTime()));
		reservations.forEach(reservation -> {
			RoomReservation roomReservation = roomReservationMap.get(reservation.getRoomId());
			roomReservation.setDate(date);
			Guest guest = this.guestRepository.findById(reservation.getGuestId()).get();
			roomReservation.setFirstName(guest.getFirstName());
			roomReservation.setLastName(guest.getLastName());
			roomReservation.setGuestId(guest.getGuestId());
		});
		List<RoomReservation> roomReservations = new ArrayList<>();
		for(Long id: roomReservationMap.keySet()) {
			roomReservations.add(roomReservationMap.get(id));
		}
		roomReservations.sort(new Comparator<RoomReservation>() {
			@Override
			public int compare(RoomReservation o1, RoomReservation o2) {
				if(o1.getRoomName() == o2.getRoomName()) {
					return o1.getRoomNumber().compareTo(o2.getRoomNumber());
				}
				return o1.getRoomName().compareTo(o2.getRoomName());
			}
		});
		return roomReservations;
	}
	
	public List<Guest> getHotelGuests() {
		Iterable<Guest> guests = this.guestRepository.findAll();
		List<Guest> guestList = new ArrayList<>();
		guests.forEach(guest ->{guestList.add(guest);});
		guestList.sort(new Comparator<Guest>() {
			@Override
			public int compare(Guest o1, Guest o2) {
				if(o1.getLastName() == o2.getLastName()) {
					return o1.getFirstName().compareTo(o2.getFirstName());
				}
				return o1.getLastName().compareTo(o2.getLastName());
			}
		});
		return guestList;
	}
}
