package com.example.demo;


import com.example.ticket.dto.ReservationDTO;
import com.example.ticket.repository.ReservationRepository;
import com.example.ticket.service.ReservationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = com.example.ticket.TicketApplication.class)
@DisplayName("예약 서비스 단위 테스트")
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("선택된 좌석을 기반으로 예약 생성 테스트")
    void selectSeatsTest() {
        // given
        ReservationDTO dto = ReservationDTO.builder()
                .uId(1L)
                .uName("홍길동")
                .pId(100L)
                .pTitle("뮤지컬 하데스타운")
                .pPlace("예술의전당")
                .pDate("2025-04-20T00:00:00")
                .pPrice("55000")
                .pAllSpot(50)
                .build();

        List<String> rSpots = List.of("A1", "A2", "B1");




    }
}
