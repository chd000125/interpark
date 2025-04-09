package com.example.ticket.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Ticket {
    private Long tId;          // 티켓 번호
    private String uName;      // 유저 이름
    private String rSpot;      // 좌석 번호
    private LocalDateTime pDate; // 공연 날짜+시간
    private String pTitle;     // 공연 제목
    private String pPlace;     // 공연 장소
    private Long uId;
}

