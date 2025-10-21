package com.appointment.appointment.model;


import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "appointment_groups")
public class AppointmentGroup {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    private String groupType; 
    private String appName; 
    private LocalDate appDate; 
    private LocalTime startTime; 
    private LocalTime endTime; 
    private Integer slotDuration; 
    private Integer maxCapacity=5;  
    private Integer TotalSlots;

    @OneToMany(mappedBy = "appointmentGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TimeSlots> timeSlots;
}
