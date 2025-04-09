// import { BrowserRouter, Routes, Route } from "react-router-dom";
// import Home from "./pages/Home";
// import SelectSeat from "./pages/SelectSeat";
// import ConfirmBooking from "./pages/ConfirmBooking";
// import CompleteBooking from "./pages/CompleteBooking";
// import "./styles/global.css";
//
// function App() {
//     return (
//         <BrowserRouter>
//             <Routes>
//                 <Route path="/" element={<Home />} />
//                 <Route path="/select/:pId" element={<SelectSeat />} />
//                 <Route path="/confirm/:pId" element={<ConfirmBooking />} />
//                 <Route path="/complete/:pId" element={<CompleteBooking />} />
//             </Routes>
//         </BrowserRouter>
//     );
// }
//
// export default App;

// src/App.js
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import CompleteBooking from "./pages/CompleteBooking";

function App() {
    return (
        <Router>
            <Routes>
                {/* 예매 완료 정보 출력 페이지 */}
                <Route path="/complete/:key" element={<CompleteBooking />} />

                {/* 테스트용 기본 페이지 */}
                <Route
                    path="/"
                    element={
                        <div style={{ textAlign: "center", marginTop: "100px" }}>
                            <h2>CompleteBooking 테스트</h2>
                            <p>아래 버튼을 눌러 예매 완료 페이지로 이동</p>
                            <button
                                onClick={() => window.location.href = "/complete/sampleKey123"}
                                style={{
                                    padding: "12px 24px",
                                    fontSize: "16px",
                                    backgroundColor: "#007bff",
                                    color: "white",
                                    border: "none",
                                    borderRadius: "6px",
                                    cursor: "pointer"
                                }}
                            >
                                예매 완료 페이지로 이동
                            </button>
                        </div>
                    }
                />
            </Routes>
        </Router>
    );
}

export default App;
