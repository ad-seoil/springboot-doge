let currentQuestionIndex = 0; // 현재 문제 인덱스
let totalQuestions = 0; // 총 문제 수
let selectedAnswer = null; // 선택된 답변 버튼
let selectedAnswerValue = null;

// 페이지 로드 시 초기화
document.addEventListener("DOMContentLoaded", () => {
  // 총 문제 수 설정
  totalQuestions = parseInt(document.getElementById("totalQuestions").value); // 총 문제 수 설정
  // 현재 문제 인덱스 설정
  currentQuestionIndex = parseInt(
    document.getElementById("currentQuestionIndex").value
  );
  // 초기 진행도 바 업데이트
  updateProgressBar();
});

// 진행도 바 업데이트 함수
function updateProgressBar() {
  const progressBar = document.getElementById("progressBar");
  const questionTracker = document.getElementById("questionTracker");

  const progressPercentage =
    ((currentQuestionIndex + 1) / totalQuestions) * 100;
  progressBar.style.width = progressPercentage + "%"; // 진행도 바의 너비 설정
  questionTracker.innerText = `${currentQuestionIndex + 1} / ${totalQuestions}`; // 진행 상태 표시
}

// 문제 선택 처리
function selectAnswer(choiceId, event, val) {
  event.preventDefault(); // 기본 동작 방지

  // 모든 선택지를 초기화
  const allChoices = document.querySelectorAll(".choice-button");
  allChoices.forEach((choice) => {
    choice.classList.remove("selected");
  });

  // 선택한 버튼에 클래스 추가
  selectedAnswer = document.getElementById(choiceId);
  selectedAnswer.classList.add("selected");

  // 정답지의 int값을 저장
  selectedAnswerValue = val;
  console.log(selectedAnswerValue);
}

function nextQuestion(event) {
  event.preventDefault();

  if (!selectedAnswer || !selectedAnswerValue) {
    alert("답변을 선택해주세요!");
    return;
  }

  console.log(selectedAnswer); // selectedAnswer 값 출력
  console.log(selectedAnswerValue); // 선택지의 int값 출력

  // 정답 인덱스 가져오기
  const correctAnswerIndex = parseInt(
    document.getElementById("correctAnswerIndex").value
  );
  const chosenButton =
    document.getElementById(`choice${selectedAnswerValue}_type_tts`) ||
    document.getElementById(`choice${selectedAnswerValue}_type_other`);
  const correctButton =
    document.getElementById(`choice${correctAnswerIndex}_type_tts`) ||
    document.getElementById(`choice${correctAnswerIndex}_type_other`);

  // Ajax 요청을 보내는 코드 추가 (폼 제출 없이 서버로 데이터 전송)
  // 서버에 선택된 답변을 전달하고, 다음 문제로 넘어가도록 요청
  fetch("/submitAnswer", {
    method: "POST", // 데이터를 보내는 방식
    headers: {
      "Content-Type": "application/x-www-form-urlencoded", // 데이터 형식은 x-www-form-urlencoded
    },
    body: new URLSearchParams({
      selectedAnswer: selectedAnswerValue, // selectedAnswerValue(int) 값을 전달
    }),
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      return response.text(); // 응답 텍스트를 반환
    })
    .then((data) => {
      // 성공적으로 응답을 받았을 때 처리 (data는 다음 문제 페이지의 URL)
      // 버튼 색상 변경(클래스를 추가해 css에서 색 변경처리)
      if (selectedAnswerValue == correctAnswerIndex) {
        // 선택한 값이 정답인 경우
        chosenButton.classList.add("correct"); // 정답 버튼에 'correct' 클래스를 추가하여 녹색으로 표시.
      } else {
        // 선택한 답변이 오답인 경우
        chosenButton.classList.add("incorrect"); // 오답 버튼에 'incorrect' 클래스를 추가하여 빨간색으로 표시,
        correctButton.classList.add("correct"); // 정답 버튼에 'correct' 클래스를 추가하여 녹색으로 표시.
      }
      // 0.5초 대기 후 버튼 색상 초기화 및 페이지 이동
      setTimeout(() => {
        console.log("0.5초 대기 완료. 버튼 색상 초기화 및 다음 문제로 이동"); // 콘솔에 로그 메시지를 출력해.

        // 버튼 색상 초기화
        chosenButton.classList.remove("correct", "incorrect");
        correctButton.classList.remove("correct", "incorrect");
        selectedAnswer.classList.remove("selected"); // 선택된 답변 표시 제거

        currentQuestionIndex++;
        if (currentQuestionIndex < totalQuestions) {
          updateProgressBar();
        }
        // 다음 문제 페이지로 이동
        // window.location.href = data; // `data` 변수에 저장된 URL로 페이지를 이동
        if (currentQuestionIndex >= totalQuestions) {
          window.location.href = '/completion';
        } else {
          window.location.reload(); // 또는 document.location.reload();
        }
      }, 500);
    })
    .catch((error) => {
      // 오류 처리
      console.error("답변 제출 실패:", error);
    });
}
