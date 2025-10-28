package com.appointment.appointment.service;

import org.springframework.stereotype.Service;

import com.appointment.appointment.repository.BookingRepository;
import com.appointment.appointment.repository.TimeSlotsRepository;
import com.appointment.appointment.model.Booking;
import com.appointment.appointment.model.TimeSlots;
import java.util.Optional;
import java.time.LocalDate;
import java.util.List;


@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final TimeSlotsRepository timeSlotsRepository;

    public BookingService(BookingRepository bookingRepository, TimeSlotsRepository timeSlotsRepository){
        this.bookingRepository = bookingRepository;
        this.timeSlotsRepository = timeSlotsRepository;
    
    }

    public List<Booking> getBookingsByUsername(String username){
        return bookingRepository.findByUsername(username);
    }

    public String bookSlot(Long timeSlotId, String username ){
        TimeSlots timeSlot = timeSlotsRepository.findById(timeSlotId).orElse(null);
        if (timeSlot == null){
            return "Time Slot is not found";}

        if (timeSlot.getAppDate().isBefore(LocalDate.now())){
            return "Cannot book past date slots";}

        if (timeSlot.getCurrentCapacity() >= timeSlot.getAppointmentGroup().getMaxCapacity()){
            return "Time Slot is fully booked";}

        Optional<Booking> existingBooking = bookingRepository.findByUsernameAndTimeSlot(username, timeSlot);
        if (existingBooking.isPresent()){
            return "You have already booked this time slot";}

        Booking booking = new Booking();
        booking.setTimeSlot(timeSlot);
        booking.setUsername(username);
        bookingRepository.save(booking);
        timeSlot.setCurrentCapacity(timeSlot.getCurrentCapacity() + 1);
        timeSlotsRepository.save(timeSlot);
        return "Booking Successful";
    }
}
