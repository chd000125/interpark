// ConfirmFinalPage.js
import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

function ConfirmFinalPage() {
    const { key } = useParams(); // Redis 또는 부모로부터 전달받은 key
    const navigate = useNavigate();
    const [data, setData] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(`http://localhost:8787/reservation/confirm?key=${key}`);
                setData(response.data);
            } catch (error) {
                console.error("❌ 예매 정보 불러오기 실패:", error);
            }
        };
        if (key) fetchData();
    }, [key]);

    const handleConfirm = async () => {
        try {
            await axios.post("http://localhost:8787/reservation/save", { key });
            alert("✅ 예매가 확정되었습니다.");
            navigate(`/complete/${key}`);
        } catch (error) {
            console.error("❌ 예매 확정 실패:", error);
            alert("예매 확정 중 문제가 발생했습니다.");
        }
    };

    const handleCancel = async () => {
        try {
            await axios.delete(`http://localhost:8787/reservation/cancel?key=${key}`);
            alert("❌ 예매가 취소되었습니다.");
            navigate("/");
        } catch (error) {
            console.error("❌ 예매 취소 실패:", error);
            alert("예매 취소 중 문제가 발생했습니다.");
        }
    };

    if (!data) return <p>데이터를 불러오는 중...</p>;

    const { reservationDTO, rSpots } = data;

    return (
        <div>
            <h2>예매 최종 확인</h2>
            <ul>
                <li><strong>공연 제목:</strong> {reservationDTO.pTitle}</li>
                <li><strong>장소:</strong> {reservationDTO.pPlace}</li>
                <li><strong>날짜:</strong> {reservationDTO.pDate}</li>
                <li><strong>가격:</strong> {reservationDTO.pPrice}</li>
                <li><strong>선택한 좌석:</strong> {rSpots.join(", ")}</li>
                <li><strong>예매자:</strong> {reservationDTO.uName}</li>
                <li><strong>전화번호:</strong> {reservationDTO.rPhone}</li>
                <li><strong>이메일:</strong> {reservationDTO.rEmail}</li>
            </ul>

            <button className="confirm-btn" onClick={handleConfirm}>✅ 예매 확정</button>
            <button className="cancel-btn" onClick={handleCancel}>❌ 예매 취소</button>
        </div>
    );
}

export default ConfirmFinalPage;
