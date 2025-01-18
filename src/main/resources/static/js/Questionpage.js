let clickedOptions = [];
let remainingTime =360;

function markCorrect(option) {
    clickedOptions.push(option.innerText);

    if (clickedOptions.length === 3) {
        const correctSequence = ['rest', 'holiday', 'vacant'];
        if (JSON.stringify(clickedOptions) === JSON.stringify(correctSequence)) {
            option.classList.add('correct');
            alert('정답입니다!');
            addTimeAutomatically();
            enableNextButton();
            applyCorrectStyle(); // 정답 시 스타일 변경
            clickedOptions = []; // 다음 문제를 위해 초기화
        } else {
            alert('정답이 아닙니다. 다시 시도하세요.');
            clickedOptions = []; // 다시 시도하도록 초기화
        }
    }
}

function addTimeAutomatically() {
    remainingTime += 10; // 정답 시 10초 추가
    document.getElementById('time-display').innerText = `남은 시간: ${remainingTime}초`;
}

function enableNextButton() {
    const nextButton = document.querySelector('.next-button');
    nextButton.classList.add('active');
    nextButton.disabled = false;
}

function updateTime() {
    const timeDisplay = document.getElementById('time-display');
    if (remainingTime > 0) {
        remainingTime--;
        timeDisplay.innerText = `남은 시간: ${remainingTime}초`;
    } else {
        alert('시간 초과!');
        clearInterval(timerInterval);
    }
}

// 정답 시 동적으로 스타일 추가
function applyCorrectStyle() {
    const questionContainer = document.querySelector('.question-container');
    questionContainer.classList.add('correct-style'); // 동적으로 클래스 추가
    console.log('정답 스타일이 적용되었습니다.');
}

// 오답 시 스타일 리셋
function resetIncorrectStyle() {
    const questionContainer = document.querySelector('.question-container');
    questionContainer.classList.remove('correct-style'); // 클래스 제거
    console.log('오답 스타일이 초기화되었습니다.');
}

// 페이지 로드 시 초기 설정
document.addEventListener("DOMContentLoaded", () => {
    // 페이지 구분용 클래스 추가
    document.body.classList.add("page-question");
    console.log('페이지 초기화 완료: "page-question" 클래스가 추가되었습니다.');

    // 타이머 시작
    const timerInterval = setInterval(updateTime, 1000);
});
