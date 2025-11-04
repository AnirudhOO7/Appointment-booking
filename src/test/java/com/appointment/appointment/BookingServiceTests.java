package com.appointment.appointment;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.appointment.appointment.model.User;
import com.appointment.appointment.model.AppointmentGroup;
import com.appointment.appointment.model.TimeSlots;
import com.appointment.appointment.repository.BookingRepository;
import com.appointment.appointment.repository.TimeSlotsRepository;
import com.appointment.appointment.service.AuthService;
import com.appointment.appointment.service.BookingService;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTests {

    @Mock
    private AuthService authService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private TimeSlotsRepository timeSlotsRepository;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void testBookSlot_successful_Individual() {
        User user = new User("test1","1234","Student","Group_1");
        
        AppointmentGroup group = new AppointmentGroup();
        group.setGroupType("Individual");

        TimeSlots slot = new TimeSlots();
        slot.setId(1L);
        slot.setAppDate(LocalDate.now().plusDays(1));
        slot.setCurrentCapacity(0);
        slot.setAppointmentGroup(group);

        when(timeSlotsRepository.findById(1l)).thenReturn(Optional.of(slot));

        String result = bookingService.bookSlot(1L, user);
        assertEquals("Booking Successful", result);
        assertEquals(1, slot.getCurrentCapacity());
    }

    @Test
    void testBookSlot_successful_Group() {
        User user = new User("test2","5678","Student","Group_1");
        
        AppointmentGroup group = new AppointmentGroup();
        group.setGroupType("Group");

        TimeSlots slot = new TimeSlots();
        slot.setId(2L);
        slot.setAppDate(LocalDate.now().plusDays(1));
        slot.setCurrentCapacity(0);
        slot.setAppointmentGroup(group);

        when(authService.getUsersByGroup("Group_1"))
            .thenReturn(List.of(user,new User("test3","1357","Student","Group_1")));

        when(timeSlotsRepository.findById(2L)).thenReturn(Optional.of(slot));

        String result = bookingService.bookSlot(2L, user);
        assertEquals("Booking Successful for entire group (2 students)", result);
        assertEquals(2, slot.getCurrentCapacity());
    }
}
