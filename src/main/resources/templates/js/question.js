 let currentQuestionIndex = 0;  // 현재 문제 index
    let totalQuestions = 0;     // 총 문제 갯수

    async function loadNextQuestion() { // 다음 문제 불러오기 함수
        const response = await fetch(`/api/question/${currentQuestionIndex}`);
        const questionData = await response.json();

        document.getElementById('questionText').innerText = questionData.question;
//        document.getElementById('questionImage').src = questionData.image_file || '';
        document.getElementById('answerInput').value = ''; // 이전 입력값 초기화

        currentQuestionIndex++;
        // 진행도 바 증가 로직
        document.getElementById('questionTracker').innerText = `${currentQuestionIndex} / ${totalQuestions}`; // n/x 형식으로 표시
        const progress = (currentQuestionIndex / totalQuestions) * 100;
        document.getElementById('progressBar').style.width = progress + '%';

        // 마지막 문제의 버튼을 '다음 문제' 에서 '완료' 로 바꾸는 로직
        if (currentQuestionIndex >= totalQuestions) {
            document.getElementById('nextButton').innerText = '완료';
            document.getElementById('nextButton').onclick = () => window.location.href = '/completion';
        }
    }

    async function submitAnswer() { // 답 제출 함수
        const answer = document.getElementById('answerInput').value;

        await fetch('/api/submit-answer', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ answer: answer }),
        });
        loadNextQuestion();
    }

    document.getElementById('nextButton').onclick = submitAnswer;

    // 문제 갯수 선택하지 않았을 때 기본값 설정
    window.onload = () => {
        totalQuestions = parseInt(new URLSearchParams(window.location.search).get('count')) || 5;
        loadNextQuestion();
    };