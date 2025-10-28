package com.appointment.appointment.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    @ManyToOne //As we will have many bookings for on time slot like in case of group.
    @JoinColumn(name = "timeslot_id", nullable = false)
    private TimeSlots timeSlot;
    private LocalDateTime bookingTime;

    
}
