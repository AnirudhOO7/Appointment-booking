package com.appointment.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.appointment.appointment.model.Booking;
import com.appointment.appointment.model.TimeSlots;

import java.util.Optional;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByTimeSlot(TimeSlots timeslot); //this is to view all the bookings done by the students for a particular time slots.

    List<Booking> findByUsername(String username); //this is to see all the bookings done by a single student.

    Optional<Booking> findByUsernameAndTimeSlot(String username, TimeSlots timeSlots);
    
}
