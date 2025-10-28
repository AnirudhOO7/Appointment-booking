package com.appointment.appointment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.appointment.appointment.model.AppointmentGroup;
import com.appointment.appointment.model.TimeSlots;
import com.appointment.appointment.repository.AppointmentGroupRepository;

@Service
public class AppointmentGroupService {

    private final AppointmentGroupRepository appointmentGroupRepository;
    private final TimeSlotsService timeSlotsService;

    public AppointmentGroupService(AppointmentGroupRepository appointmentGroupRepository, TimeSlotsService timeSlotsService){
        this.appointmentGroupRepository = appointmentGroupRepository;
        this.timeSlotsService = timeSlotsService;
    }

    public void saveAppointmentGroup(AppointmentGroup appointmentGroup){
        appointmentGroupRepository.save(appointmentGroup);
        int count = timeSlotsService.generateTimeSlots(appointmentGroup);
        appointmentGroup.setTotalSlots(count);
        appointmentGroupRepository.save(appointmentGroup);
    }



    public List<AppointmentGroup> getALLGroups(){
        return appointmentGroupRepository.findAllWithSlots();
    }

    public AppointmentGroup getGroupById(Long id){
        return appointmentGroupRepository.findById(id).orElse(null);
    }

    public void deleteGroupById(Long id) {
        AppointmentGroup group = appointmentGroupRepository.findById(id).orElse(null);
        if(group!=null){
        appointmentGroupRepository.delete(group);
    }
}
}
    
