package com.appointment.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.appointment.appointment.model.AppointmentGroup;
import com.appointment.appointment.repository.AppointmentGroupRepository;
import com.appointment.appointment.service.TimeSlotsService;
import com.appointment.appointment.service.AppointmentGroupService;

@ExtendWith(MockitoExtension.class)
public class AppointmentGroupServiceTest {
    
    @Mock
    private AppointmentGroupRepository appointmentGroupRepository;

    @Mock
    private TimeSlotsService timeSlotsService;

    @InjectMocks
    private AppointmentGroupService appointmentGroupService;


    //I am creating a dummy appointment group for testing purpose
    private AppointmentGroup dummyGroup(){
        AppointmentGroup group = new AppointmentGroup();
        group.setId(1L);
        group.setAppName("Dummy Group");
        group.setTimeSlots(new ArrayList<>());
        return group;
    }

    @Test
    void testSaveAppointmentGroup(){
        AppointmentGroup group  = dummyGroup();

        when(timeSlotsService.generateTimeSlots(group)).thenReturn(3);
        
        appointmentGroupService.saveAppointmentGroup(group);

        assertEquals(3, group.getTotalSlots());
    }

    @Test
    void testGetGroupById(){
        AppointmentGroup group = dummyGroup();

        when(appointmentGroupRepository.findById(1L)).thenReturn(Optional.of(group));

        AppointmentGroup result = appointmentGroupService.getGroupById(1L);
        assertNotNull(result);
        assertEquals("Dummy Group", group.getAppName());

    }

    @Test
    void testGetAllGroups(){
        List<AppointmentGroup> list = List.of(dummyGroup());
        when(appointmentGroupRepository.findAllWithSlots()).thenReturn(list);

        List<AppointmentGroup> result = appointmentGroupService.getALLGroups();
        assertEquals(1, result.size());
    }
}
