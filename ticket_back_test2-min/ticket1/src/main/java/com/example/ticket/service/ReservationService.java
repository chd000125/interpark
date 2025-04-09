package com.example.ticket.service;

import com.example.ticket.dto.ReservationDTO;
import com.example.ticket.dto.ReservationRequest;
import com.example.ticket.dto.Ticket;
import com.example.ticket.entity.Reservation;
import com.example.ticket.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;


    // 최종 단계 엔티티 생성
    public List<ReservationDTO> createFinalReservations(ReservationRequest request) {
        ReservationDTO dto = request.getReservationDTO();
        List<String> rSpots = request.getRSpots();

        List<ReservationDTO> reservations = new ArrayList<>();

        for (String rSpot : rSpots) {
            Reservation reservation = Reservation.builder()
                    .rSpot(rSpot)
                    .uId(dto.getUId())
                    .uName(dto.getUName())
                    .pId(dto.getPId())
                    .pTitle(dto.getPTitle())
                    .pPlace(dto.getPPlace())
                    .pDate(LocalDateTime.parse(dto.getPDate()))
                    .pPrice(Integer.parseInt(dto.getPPrice()))
                    .pAllSpot(dto.getPAllSpot())
                    .rPhone(dto.getRPhone())
                    .rEmail(dto.getREmail())
                    .rSpotStatus("nonAvailable")
                    .rTime(LocalDateTime.now())
                    .build();

            Reservation saved = reservationRepository.save(reservation);
            reservations.add(toDTO(saved));
        }

        return reservations;
    }




    // 예매 dto 정보 업데이트
    public ReservationRequest confirmReservations(ReservationRequest request, String rEmail, String rPhone) {
        // 이메일과 전화번호를 DTO에 반영
        ReservationDTO updatedDTO = request.getReservationDTO();
        updatedDTO.setREmail(rEmail);
        updatedDTO.setRPhone(rPhone);

        // 그대로 리턴 (좌석 목록과 함께)
        return new ReservationRequest(updatedDTO, request.getRSpots());
    }





    // 단일 예매 조회
    public ReservationDTO getReservation(Long rId) {
        return reservationRepository.findById(rId)
                .map(this::toDTO)
                .orElse(null);
    }

    // 사용자 예매 조회
    public List<ReservationDTO> getUserReservations(Long pId, Long uId) {
        List<Reservation> reservations = reservationRepository.findByPIdAndUId(pId, uId);
        if (reservations.isEmpty()) return Collections.emptyList();

        List<ReservationDTO> result = new ArrayList<>();
        for (Reservation r : reservations) {
            result.add(toDTO(r));
        }
        return result;
    }

    // 티켓 조회
    public Ticket getTicket(Long rId) {
        return reservationRepository.findById(rId)
                .map(res -> Ticket.builder()
                        .tId(res.getRId())
                        .rSpot(res.getRSpot())
                        .uName(res.getUName())
                        .pTitle(res.getPTitle())
                        .pPlace(res.getPPlace())
                        .pDate(res.getPDate())
                        .uId(res.getUId())
                        .build())
                .orElse(null);
    }

    /* DTO 변환 */
    private ReservationDTO toDTO(Reservation res) {
        return ReservationDTO.builder()
                .rId(res.getRId())
                .uName(res.getUName())
                .uId(res.getUId())
                .rSpot(res.getRSpot())
                .rSpotStatus(res.getRSpotStatus())
                .rPhone(res.getRPhone())
                .rEmail(res.getREmail())
                .rTime(res.getRTime())
                .pDate(String.valueOf(res.getPDate()))
                .pTitle(res.getPTitle())
                .pPlace(res.getPPlace())
                .pPrice(String.valueOf(res.getPPrice()))
                .pAllSpot(res.getPAllSpot())
                .pId(res.getPId())
                .build();
    }
}
