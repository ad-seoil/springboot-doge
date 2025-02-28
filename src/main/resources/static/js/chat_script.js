// chat_script.js

document.addEventListener('DOMContentLoaded', () => {  // DOMContentLoaded 이벤트 리스너 등록 (페이지 로딩 완료 후 실행)
    const messageInput = document.getElementById('messageInput');  // 메시지 입력 창 엘리먼트 선택
    const sendButton = document.getElementById('sendButton');  // 전송 버튼 엘리먼트 선택
    const chatMessages = document.getElementById('chatMessages');  // 채팅 메시지 표시 영역 엘리먼트 선택

    sendButton.addEventListener('click', sendMessage);  // 전송 버튼 클릭 이벤트 리스너 등록 (sendMessage 함수 호출)

    messageInput.addEventListener('keydown', (event) => {  // 메시지 입력 창 keydown 이벤트 리스너 등록 (Enter 키 감지)
        if (event.key === 'Enter') {  // Enter 키가 눌렸을 때
            sendMessage();  // sendMessage 함수 호출
            event.preventDefault(); // Enter 키 기본 동작 (줄바꿈) 방지
        }
    });

    /**
     * @typedef {Object} ApiResponse
     * @property {string} openAiResponse - AI 응답 메시지
     */

    function sendMessage() {  // 메시지 전송 함수 정의
        const message = messageInput.value.trim();  // 입력 창에서 메시지 값 가져오기 (trim() 으로 공백 제거)
        if (!message) return;  // 메시지가 비어있으면 함수 종료

        displayMessage(message, 'user-message');  // 사용자 메시지를 채팅창 UI 에 표시

        messageInput.value = '';  // 입력 창 초기화

        // 백엔드 API 호출
        fetch('/api/chat', {// fetch() 함수의 두 번째 인자로 옵션 객체 전달 (POST 요청 설정)
            method: 'POST', // HTTP 요청 방식: POST 방식 지정
            headers: {  // 요청 헤더 설정
                'Content-Type': 'application/json'  // 요청 본문의 데이터 형식을 JSON 으로 명시
            },
            body: JSON.stringify({message: message})    // 요청 본문 (Body) 설정: JSON 형식으로 메시지 데이터 전달
        })
            .then(response => {
                console.log("서버 응답 상태 코드: ", response.status);
                return response.json();
            }) // JSON 응답 파싱
            .then(data => { // 응답 데이터 처리'
                /** @type {ApiResponse} */
                const apiData = data;
                console.log("API 응답 데이터 (data 객체): ", data);
                console.log("data.openAiResponse: ", apiData.openAiResponse);
                displayMessage(apiData.openAiResponse, 'ai-message'); // AI 챗봇 응답을 채팅창 UI 에 표시
            })
            .catch(error => {
                console.error("API 호출 중 오류 발생: ", error);
            });
    }

    function displayMessage(message, messageType) {  // 채팅 메시지 UI 에 표시하는 함수 정의
        const messageElement = document.createElement('div');  // 새로운 div 엘리먼트 생성 (메시지 컨테이너)
        messageElement.classList.add('message', messageType);  // message, messageType 클래스 추가 (CSS 스타일 적용)
        messageElement.textContent = message;  // 메시지 내용 설정
        chatMessages.appendChild(messageElement);  // 채팅 메시지 표시 영역에 메시지 엘리먼트 추가

        chatMessages.scrollTop = chatMessages.scrollHeight;  // 스크롤바를 항상 최하단으로 이동 (최신 메시지 보이도록)
    }
});