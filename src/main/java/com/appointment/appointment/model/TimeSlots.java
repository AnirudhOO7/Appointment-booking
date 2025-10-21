package com.appointment.appointment.model;


import java.time.LocalTime;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "time_slots")
public class TimeSlots {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appointment_group_id", nullable = false)
    private AppointmentGroup appointmentGroup;

    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate appDate;
    private Boolean isBooked = false;
    private Integer currentCapacity;
    
}
