import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

function ReservationInfo() {
    const { pId } = useParams();
    const navigate = useNavigate();

    const [reservationData, setReservationData] = useState(null);
    const [rPhone, setRPhone] = useState("");
    const [rEmail, setREmail] = useState("");
    const [error, setError] = useState("");

    // 백엔드에서 예약 정보 가져오기
    useEffect(() => {
        axios.get(`http://localhost:8787/reservation/${pId}`)
            .then(response => {
                console.log("📌 예약 데이터:", response.data);
                setReservationData(response.data);
            })
            .catch(error => {
                console.error("🚨 예약 정보 로드 실패:", error);
                setError("예약 정보를 불러오지 못했습니다.");
            });
    }, [pId]);

    const handleReserve = () => {
        if (!reservationData) {
            setError("예약 정보가 없습니다.");
            return;
        }

        const requestData = {
            tId: reservationData.tId,
            rSpot: reservationData.rSpot, // 좌석 번호
            rSpotStatus: "true",
            rTime: new Date().toISOString(), // 예매 시간 자동 입력
            rPhone: rPhone,
            rEmail: rEmail
        };

        console.log("📌 전송할 데이터:", requestData);

        axios.post(`http://localhost:8787/reserve/${pId}`, requestData, {
            headers: { "Content-Type": "application/json" }
        })
            .then(response => {
                console.log("✅ 예매 성공:", response.data);
                navigate(`/confirmation/${pId}`);
            })
            .catch(error => {
                console.error("🚨 예매 실패:", error.response ? error.response.data : error.message);
                setError("예매에 실패했습니다. 다시 시도해주세요.");
            });
    };

    return (
        <div>
            <h1>예매 정보 확인</h1>
            {error && <p style={{ color: "red" }}>{error}</p>}

            {reservationData ? (
                <div>
                    <h2>🎭 공연 정보</h2>
                    <p><strong>제목:</strong> {reservationData.pTitle}</p>
                    <p><strong>장소:</strong> {reservationData.pPlace}</p>
                    <p><strong>날짜:</strong> {reservationData.pDate}</p>
                    <p><strong>가격:</strong> {reservationData.pPrice.toLocaleString()}원</p>

                    <h2>🎟 좌석 정보</h2>
                    <p><strong>좌석 번호:</strong> {reservationData.rSpot}</p>

                    <h2>📞 예매자 정보 입력</h2>
                    <input
                        type="text"
                        placeholder="전화번호 입력"
                        value={rPhone}
                        onChange={(e) => setRPhone(e.target.value)}
                    />
                    <input
                        type="email"
                        placeholder="이메일 입력"
                        value={rEmail}
                        onChange={(e) => setREmail(e.target.value)}
                    />
                    <button onClick={handleReserve}>예매 완료</button>
                </div>
            ) : (
                <p>예약 정보를 불러오는 중...</p>
            )}
        </div>
    );
}

export default ReservationInfo;
