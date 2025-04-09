import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

function Home() {
    const [data, setData] = useState([]);

    useEffect(() => {
        axios.get("http://localhost:3001/reservations")
            .then(response => setData(response.data))
            .catch(error => console.error("데이터 로드 실패:", error));
    }, []);

    return (
        <div>
            <h1>예매 가능한 공연(테스트용)</h1>
            {data.map(item => (
                <div key={item.pId}>
                    <p>공연명: {item.pTitle}</p>
                    <p>장소: {item.pPlace}</p>
                    <Link to={`/select/${item.pId}`}>예매하기</Link>
                </div>
            ))}
        </div>
    );
}

export default Home;
