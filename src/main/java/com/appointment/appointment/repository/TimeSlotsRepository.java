package com.appointment.appointment.repository;
import com.appointment.appointment.model.TimeSlots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TimeSlotsRepository extends JpaRepository<TimeSlots, Long>{
}

    
