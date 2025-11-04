package com.appointment.appointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appointment.appointment.repository.BookingRepository;
import com.appointment.appointment.repository.TimeSlotsRepository;
import com.appointment.appointment.model.Booking;
import com.appointment.appointment.model.TimeSlots;

import com.appointment.appointment.model.User;

import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class BookingService {

    private final AuthService authService;
    private final BookingRepository bookingRepository;
    private final TimeSlotsRepository timeSlotsRepository;

    public BookingService(AuthService authService,BookingRepository bookingRepository, TimeSlotsRepository timeSlotsRepository){
        this.bookingRepository = bookingRepository;
        this.timeSlotsRepository = timeSlotsRepository;
        this.authService = authService;
    }



    public List<Booking> getBookingsByUsername(String username){
        return bookingRepository.findByUsername(username);
    }



    public String bookSlot(Long timeSlotId, User user ){
        TimeSlots timeSlot = timeSlotsRepository.findById(timeSlotId).orElse(null);
        if (timeSlot == null){
            return "Time Slot is not found";}

        if (timeSlot.getAppDate().isBefore(LocalDate.now())){
            return "Cannot book past date slots";}


        if(timeSlot.getIsBreak()) {
            return "This slot is a break and cannot be booked";
        }
        String appointmentType = timeSlot.getAppointmentGroup().getGroupType();

        if(appointmentType.equals("Group") && user.getGroup() !=null){

            List<User> groupMembers = authService.getUsersByGroup(user.getGroup());

            for(User member : groupMembers){
                List<Booking> bookings = bookingRepository.findByUsername(member.getUsername());
                for(Booking b: bookings){
                    if (b.getTimeSlot().getAppDate().isEqual(timeSlot.getAppDate()))
                        return "Your group has already booked this time slot";
                }
            }
        
        for (User member: groupMembers){
            Booking booking = new Booking();
            booking.setTimeSlot(timeSlot);
            booking.setUsername(member.getUsername());
            booking.setBookingTime(LocalDateTime.now());
            bookingRepository.save(booking);
        }

        timeSlot.setCurrentCapacity(timeSlot.getCurrentCapacity() + groupMembers.size());
        timeSlotsRepository.save(timeSlot);

        return "Booking Successful for entire group (" + groupMembers.size() + " students)";
    }

        else{
        Optional<Booking> existingBooking = bookingRepository.findByUsernameAndTimeSlot(user.getUsername(), timeSlot);
        if (existingBooking.isPresent()){
            return "You have already booked this time slot";}

        Booking booking = new Booking();
        booking.setTimeSlot(timeSlot);
        booking.setUsername(user.getUsername());
        booking.setBookingTime(LocalDateTime.now());
        bookingRepository.save(booking);

        timeSlot.setCurrentCapacity(timeSlot.getCurrentCapacity() + 1);
        timeSlotsRepository.save(timeSlot);

        return "Booking Successful";
    }
}

}
