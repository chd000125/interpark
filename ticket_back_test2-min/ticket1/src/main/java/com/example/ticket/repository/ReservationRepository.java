package com.example.ticket.repository;

import com.example.ticket.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.pId = :pId AND r.uId = :uId")
    List<Reservation> findByPIdAndUId(@Param("pId") Long pId, @Param("uId") Long uId);
}
