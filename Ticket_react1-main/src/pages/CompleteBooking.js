import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

function ConfirmReservation() {
    const { pId } = useParams();
    const navigate = useNavigate();

    const [performanceInfo, setPerformanceInfo] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    // Redis에서 공연 정보 조회
    useEffect(() => {
        axios.get(`http://localhost:8080/cache/performance/${pId}`) // Redis 기반 API
            .then((res) => {
                setPerformanceInfo(res.data);
                setLoading(false);
            })
            .catch((err) => {
                console.error("❌ Redis 조회 실패:", err);
                setError("공연 정보를 불러오지 못했습니다.");
                setLoading(false);
            });
    }, [pId]);

    // 예매 확정 → JPA로 DB에 저장
    const handleConfirm = () => {
        axios.post(`http://localhost:8080/reservation/confirm`, performanceInfo)
            .then((res) => {
                alert("예매가 확정되었습니다!");
                navigate(`/reservation/complete/${pId}`);
            })
            .catch((err) => {
                console.error("❌ 예매 확정 실패:", err);
                alert("예매 확정에 실패했습니다.");
            });
    };

    // 예매 취소 → Redis 캐시에서 삭제 또는 무효화
    const handleCancel = () => {
        axios.delete(`http://localhost:8080/reservation/cancel/${pId}`)
            .then((res) => {
                alert("예매가 취소되었습니다.");
                navigate(`/`);
            })
            .catch((err) => {
                console.error("❌ 예매 취소 실패:", err);
                alert("예매 취소에 실패했습니다.");
            });
    };

    if (loading) return <p>공연 정보를 불러오는 중...</p>;
    if (error) return <p style={{ color: "red" }}>{error}</p>;

    return (
        <div>
            <h1>🎟 예매 확인</h1>

            <h2>🎭 공연 정보 (Redis)</h2>
            <p><strong>제목:</strong> {performanceInfo.title}</p>
            <p><strong>장소:</strong> {performanceInfo.place}</p>
            <p><strong>날짜:</strong> {performanceInfo.date}</p>
            <p><strong>가격:</strong> {performanceInfo.price.toLocaleString()}원</p>
            <p><strong>좌석:</strong> {performanceInfo.spot}</p>

            <div style={{ marginTop: "20px" }}>
                <button onClick={handleConfirm}>예매 확정</button>
                <button onClick={handleCancel} style={{ marginLeft: "10px" }}>예매 취소</button>
            </div>
        </div>
    );
}

export default ConfirmReservation;
