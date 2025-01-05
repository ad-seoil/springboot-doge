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
    event.preventDefault(); // 기본 폼 제출 방지
    console.log("selectAnswer 함수 호출, 선택된 인덱스: " + answerIndex); // 함수 호출 확인

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
    console.log("색상 변경 후 0.5초 대기 완료"); // 0.5초 후 로그 출력

        setTimeout(() => {
            console.log("1초 대기 완료. 버튼 색상 초기화 및 다음 문제로 이동"); // 1초 후 로그 출력

            chosenButton.classList.remove('correct', 'incorrect');
            correctButton.classList.remove('correct', 'incorrect');

            currentQuestionIndex++;
            updateProgressBar();
            document.forms[0].submit();
        }, 1000);
    }, 500);
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

