package com.example.ticket.controller;

import com.example.ticket.dto.KeyRequestDto;
import com.example.ticket.dto.ReservationDTO;
import com.example.ticket.dto.ReservationRequest;
import com.example.ticket.dto.Ticket;
import com.example.ticket.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ReservationController {

    private final ReservationService reservationService;

    // 서버 메모리에 임시 저장할 Map
    private final Map<String, ReservationRequest> selectStorage = new HashMap<>();

    /**
     * ✅ 테스트 및 좌석 선택을 위한 데이터 저장
     * 클라이언트는 UUID 키를 응답으로 받아 저장하고,
     * 이후 GET 요청에서 해당 키를 이용해 데이터를 조회
     */
    @PostMapping("/select")
    public ResponseEntity<String> selectPost(@RequestBody ReservationDTO reservationDTO) {
        String key = UUID.randomUUID().toString(); // 고유 키 생성
        ReservationRequest request = new ReservationRequest();
        request.setReservationDTO(reservationDTO);
        selectStorage.put(key, request);

        return ResponseEntity.ok(key); // 클라이언트는 이 키를 기억해야 함
    }

    /**
     * ✅ 저장된 좌석 선택 정보 조회
     * key 파라미터로 조회
     */
    @GetMapping("/select")
    public ResponseEntity<ReservationRequest> selectGet(@RequestParam String key) {
        ReservationRequest request = selectStorage.get(key);
        if (request == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(request);
    }

    /**
     * 좌석 선택 완료 → 예매 요청 데이터 전달
     */
    @PostMapping("/confirm")
    public ResponseEntity<ReservationRequest> selectPost(@RequestParam String key, @RequestParam List<String> rSpots) {
        ReservationRequest request = selectStorage.get(key);

        if (request == null) {
            return ResponseEntity.notFound().build();
        }
        request.setRSpots(rSpots);

        return ResponseEntity.ok(request);
    }

    /**
     * 예매 정보 확인 페이지
     */
    @GetMapping("/confirm")
    public ResponseEntity<ReservationRequest> confirmGet(@RequestParam String key) {
        ReservationRequest request = selectStorage.get(key);
        return ResponseEntity.ok(request);
    }

    /**
     * rPhone, rEmail 정보 업데이트
     */
    @PostMapping("/complete/info")
    public ResponseEntity<ReservationRequest> completePostInfo(@RequestBody KeyRequestDto request) {
        String key = request.getKey();
        ReservationRequest reservation = selectStorage.get(key);

        if (reservation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(reservation);
    }

    /** 예매 정보 확인 페이지 */
    @GetMapping("/complete")
    public ResponseEntity<ReservationRequest> getReservationInfo(@RequestParam String key) {
        ReservationRequest request = selectStorage.get(key);
        if (request == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(request);
    }

    /**
     * 좌석 개별 예약 → 티켓 생성
     */
    @PostMapping("/ticket")
    public ResponseEntity<List<Ticket>> completePost( @RequestParam String key) {
        ReservationRequest request = selectStorage.get(key);
        List<ReservationDTO> reservationList = reservationService.createFinalReservations(request);

        List<Ticket> tickets = reservationList.stream()
                .map(dto -> reservationService.getTicket(dto.getRId()))
                .filter(Objects::nonNull)
                .toList();

        return ResponseEntity.ok(tickets);
    }

    /** 예매 확정 (DB 저장 처리) */
    @PostMapping("/complete")
    public ResponseEntity<List<Ticket>> finalizeReservation(@RequestParam String key) {
        ReservationRequest request = selectStorage.get(key);
        if (request == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<ReservationDTO> finalReservations = reservationService.createFinalReservations(request);

        List<Ticket> tickets = finalReservations.stream()
                .map(dto -> reservationService.getTicket(dto.getRId()))
                .filter(Objects::nonNull)
                .toList();

        return ResponseEntity.ok(tickets);
    }
    /** 예매 취소 (임시 저장 데이터 삭제) */
    @DeleteMapping("/cancel")
    public ResponseEntity<String> cancelReservation(@RequestParam String key) {
        if (!selectStorage.containsKey(key)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("예매 정보가 없습니다.");
        }

        selectStorage.remove(key); // 예매 정보 삭제
        return ResponseEntity.ok("예매가 취소되었습니다.");
    }

    /**
     * 테스트용 티켓 데이터 반환
     */
    @GetMapping("/ticket")
    public ResponseEntity<List<Ticket>> getTickets(@RequestBody List<Ticket> tickets) {
        return ResponseEntity.ok(tickets);
    }
}
