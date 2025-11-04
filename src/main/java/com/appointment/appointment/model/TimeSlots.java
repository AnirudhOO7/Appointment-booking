package com.appointment.appointment.model;


import java.time.LocalTime;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;


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

    @OneToMany(mappedBy = "timeSlot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;

    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate appDate;
    private Boolean isBooked = false;
    private Integer currentCapacity;
    @Column(name = "is_break")
    private boolean isBreak = false;
    
    public boolean getIsBreak() {
        return isBreak;
    }

    public void setIsBreak(boolean isBreak) {
        this.isBreak = isBreak;
    }
}