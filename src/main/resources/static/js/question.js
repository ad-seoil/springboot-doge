let currentQuestionIndex = 0; // 현재 문제 인덱스
let totalQuestions = 0; // 총 문제 수

// 페이지 로드 시 초기화
document.addEventListener('DOMContentLoaded', () => {
    totalQuestions = parseInt(document.getElementById('totalQuestions').value); // 총 문제 수 설정
    currentQuestionIndex = parseInt(document.getElementById('currentQuestionIndex').value);
    updateProgressBar(); // 초기 진행도 바 업데이트
});


function updateProgressBar() {
    const progressBar = document.getElementById('progressBar');
    const questionTracker = document.getElementById('questionTracker');

    const progressPercentage = ((currentQuestionIndex + 1) / totalQuestions) * 100;
    progressBar.style.width = progressPercentage + '%'; // 진행도 바의 너비 설정
    questionTracker.innerText = `${currentQuestionIndex + 1} / ${totalQuestions}`; // 진행 상태 표시
}

function selectAnswer(answerIndex) {
    const correctAnswerIndex = parseInt(document.getElementById('correctAnswerIndex').value); // 정답 인덱스
    const chosenButton = document.getElementById(`choice${answerIndex}`);
    const correctButton = document.getElementById(`choice${correctAnswerIndex}`);

    if (answerIndex === correctAnswerIndex) {
        chosenButton.classList.add('correct');  // 정답 버튼 색상 변경
    } else {
        chosenButton.classList.add('incorrect'); // 오답 버튼 색상 변경
        correctButton.classList.add('correct');
    }

    setTimeout(() => {
        currentQuestionIndex++;
        // 버튼 색상 초기화
        const buttons = document.querySelectorAll('.choice-button');
        buttons.forEach(button => {
            button.classList.remove('correct', 'incorrect');
        });
        document.forms[0].submit();
    }, 1300); // 1.3초 후 다음 문제로 이동
}

//function nextQuestion() {
//    // 버튼 색상 초기화
//    const buttons = document.querySelectorAll('.choice-button');
//    buttons.forEach(button => {
//        button.classList.remove('correct', 'incorrect');
//    });
//
//    if (currentQuestionIndex >= totalQuestions) {
//        window.location.href = '/completion'; // 완료 페이지로 리다이렉트
//    } else {
//        document.forms[0].submit(); // 현재 폼 제출하여 다음 문제 로드
//    }
//}

