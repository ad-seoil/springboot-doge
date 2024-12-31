let currentQuestionIndex = 0; // 현재 문제 인덱스
let totalQuestions = 0; // 총 문제 수

function updateProgressBar() {
    const progressBar = document.getElementById('progressBar');
    const questionTracker = document.getElementById('questionTracker');
    const progressPercentage = ((currentQuestionIndex + 1) / totalQuestions) * 100;
    progressBar.style.width = progressPercentage + '%'; // 진행도 바의 너비 설정
    questionTracker.innerText = `${currentQuestionIndex + 1} / ${totalQuestions}`; // 진행 상태 표시
}

function selectAnswer(answerIndex) {
    const correctAnswerIndex = ${question.answer}; // 정답 인덱스
    const chosenButton = document.getElementById(`choice${answerIndex}`);
    const correctButton = document.getElementById(`choice${correctAnswerIndex}`);

    if (answerIndex === correctAnswerIndex) {
        chosenButton.classList.add('correct');
    } else {
        chosenButton.classList.add('incorrect');
        correctButton.classList.add('correct'); // 정답 버튼 색상 변경
    }

    setTimeout(() => {
        currentQuestionIndex++;
        nextQuestion(); // 다음 문제로 이동
    }, 1000); // 1초 후 다음 문제로 이동
}

function nextQuestion() {
    if (currentQuestionIndex >= totalQuestions) {
        window.location.href = '/completion'; // 완료 페이지로 리다이렉트
    } else {
        // 현재 문제 인덱스에 따라 서버에서 다음 문제를 가져오는 요청
        fetch(`/question?index=${currentQuestionIndex}`)
            .then(response => response.json())
            .then(data => {
                document.getElementById('questionText').innerText = data.question;
                document.getElementById('choice1').innerText = data.ex1;
                document.getElementById('choice2').innerText = data.ex2;
                document.getElementById('choice3').innerText = data.ex3;
                updateProgressBar(); // 진행도 바 업데이트
            });
    }
}

// 페이지 로드 시 초기화
document.addEventListener('DOMContentLoaded', () => {
    totalQuestions = ${totalQuestions}; // 총 문제 수 설정
    updateProgressBar(); // 초기 진행도 바 업데이트
});
