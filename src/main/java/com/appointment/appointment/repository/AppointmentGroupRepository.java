package com.appointment.appointment.repository;

import com.appointment.appointment.model.AppointmentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface AppointmentGroupRepository extends JpaRepository<AppointmentGroup, Long> {   
    
        @Query("SELECT DISTINCT g FROM AppointmentGroup g LEFT JOIN FETCH g.timeSlots")
        List<AppointmentGroup> findAllWithSlots();

        
}

