package com.appointment.appointment.service;

import com.appointment.appointment.model.AppointmentGroup;
import org.springframework.stereotype.Service;
import com.appointment.appointment.model.TimeSlots;
import com.appointment.appointment.repository.TimeSlotsRepository;
import java.time.LocalTime;

@Service
public class TimeSlotsService {

    private final TimeSlotsRepository timeSlotsRepository;


    public TimeSlotsService(TimeSlotsRepository timeSlotsRepository){
        this.timeSlotsRepository = timeSlotsRepository;
    }


    //create time slots and update count
    public int generateTimeSlots(AppointmentGroup group){
        int count = 0;
        LocalTime slotStartTime = group.getStartTime();
        LocalTime slotEndTime = group.getEndTime();
        Integer slotDuration = group.getSlotDuration();


        while(!slotStartTime.plusMinutes(slotDuration).isAfter(slotEndTime)){
            TimeSlots slot = new TimeSlots();
            slot.setAppointmentGroup(group);
            slot.setStartTime(slotStartTime);
            slot.setEndTime(slotStartTime.plusMinutes(slotDuration));
            slot.setAppDate(group.getAppDate());
            slot.setIsBooked(false);
            slot.setCurrentCapacity(0);
            timeSlotsRepository.save(slot);
            slotStartTime = slotStartTime.plusMinutes(slotDuration);
            count++;
        }
        return count;
    }

    
}
