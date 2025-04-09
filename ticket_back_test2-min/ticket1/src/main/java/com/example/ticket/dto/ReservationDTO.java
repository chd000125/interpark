package com.example.ticket.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationDTO {

    @JsonProperty("uName")
    private String uName;

    @JsonProperty("uId")
    private Long uId;

    @JsonProperty("pId")
    private Long pId;

    @JsonProperty("pTitle")
    private String pTitle;

    @JsonProperty("pPlace")
    private String pPlace;

    @JsonProperty("pDate")
    private String pDate;

    @JsonProperty("pPrice")
    private String pPrice;

    @JsonProperty("pAllSpot")
    private Integer pAllSpot;

    @JsonProperty("rId")
    private Long rId;

    @JsonProperty("rSpot")
    private String rSpot;

    @JsonProperty("rSpotStatus")
    private String rSpotStatus;

    @JsonProperty("rPhone")
    private String rPhone;

    @JsonProperty("rEmail")
    private String rEmail;

    @JsonProperty("rTime")
    private LocalDateTime rTime;

}
