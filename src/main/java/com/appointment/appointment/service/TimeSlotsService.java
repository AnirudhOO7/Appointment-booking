package com.appointment.appointment.service;

import com.appointment.appointment.model.AppointmentGroup;
import org.springframework.stereotype.Service;
import com.appointment.appointment.model.TimeSlots;
import com.appointment.appointment.repository.TimeSlotsRepository;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

        List<TimeSlots> slots = new ArrayList<>();

        while(!slotStartTime.plusMinutes(slotDuration).isAfter(slotEndTime)){
            TimeSlots slot = new TimeSlots();
            slot.setAppointmentGroup(group);
            slot.setStartTime(slotStartTime);
            slot.setEndTime(slotStartTime.plusMinutes(slotDuration));
            slot.setAppDate(group.getAppDate());
            slot.setIsBooked(false);
            slot.setCurrentCapacity(0);
            slots.add(slot);
            slotStartTime = slotStartTime.plusMinutes(slotDuration);
            count++;
        }

            if (slots.size() >= 3) {
                for (TimeSlots slot : slots) {
                LocalTime time = slot.getStartTime();
                if ((time.isAfter(LocalTime.of(10, 15)) && time.isBefore(LocalTime.of(10, 45))) ||
                (time.isAfter(LocalTime.of(12, 15)) && time.isBefore(LocalTime.of(13, 15)))) {
                slot.setIsBreak(true);
                }
            }
            }

            for (TimeSlots slot : slots) {
                timeSlotsRepository.save(slot);
            }
        return count;
    }

    
}
