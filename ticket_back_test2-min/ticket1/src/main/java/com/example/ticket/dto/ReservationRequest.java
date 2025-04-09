package com.example.ticket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {

    @JsonProperty("reservationDTO")
    private ReservationDTO reservationDTO;
    @JsonProperty("rSpots")
    private List<String> rSpots;
}