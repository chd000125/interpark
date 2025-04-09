package com.example.ticket.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "r_id")
    private Long rId;        // 예매 ID

    @Column(name = "u_name", nullable = false, length = 255)
    private String uName;    // 유저 이름

    @Column(name = "u_id", nullable = false, length = 255)
    private Long uId;      // 유저 ID

    @Column(name = "r_spot", length = 255)
    private String rSpot;    // 좌석 번호

    @Column(name = "r_spot_status", length = 255)
    private String rSpotStatus; // 좌석 상태

    @Column(name = "r_phone", length = 255)
    private String rPhone;   // 예매자의 전화번호

    @Column(name = "r_email", length = 255)
    private String rEmail;   // 예매자의 이메일

    @CreationTimestamp
    @Column(name = "r_time")
    private LocalDateTime rTime; // 예매 시간 (현재 시간 자동 저장)

    @Column(name = "p_date")
    private LocalDateTime pDate; // 공연 날짜 및 시간

    @Column(name = "p_title", nullable = false, length = 255)
    private String pTitle;   // 공연 제목

    @Column(name = "p_place", nullable = false, length = 255)
    private String pPlace;   // 공연 장소

    @Column(name = "p_price", nullable = false)
    private int pPrice;      // 좌석 가격

    @Column(name = "p_all_spot", nullable = false)
    private int pAllSpot;    // 전체 좌석

    @Column(name = "p_id", nullable = false, length = 255)
    private Long pId;      // 공연 ID
}
