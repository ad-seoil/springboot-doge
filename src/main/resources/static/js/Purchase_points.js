// DOMContentLoaded 이벤트를 사용하여 HTML 로드 후 실행
document.addEventListener('DOMContentLoaded', () => {
    // Coins 이미지 클릭 시 이동
    const coinsImage = document.getElementById('coinsImage');
    if (coinsImage) {
        coinsImage.addEventListener('click', () => {
            window.location.href = '/purchase-points'; // "src/main/resources/static/" 내에 있는purchase_points.html 파일로이동
        });
    }

    // Gems 이미지 클릭 시 이동
    const gemsImage = document.getElementById('gemsImage');
    if (gemsImage) {
        gemsImage.addEventListener('click', () => {
            window.location.href = '/purchase-points'; // "src/main/resources/static/" 내에 있는purchase_points.html 파일로이동
        });
    }
});



const userData = {
    name: "", // 회원가입 시 입력된 이름
    level: "", // 학습레벨
    completedQuest: false // 퀘스트 완료 여부
};

// DOM 요소에 데이터 삽입
function updateUserUI() {
    document.getElementById("user-name").textContent = `유저 이름: ${userData.name}`;
    document.getElementById("user-level").textContent = `학습 레벨: ${userData.level}`;
    document.getElementById("user-progress").textContent = `오늘의 학습: ${userData.completedQuest ? "완료" : "미완료"}`;
}
// 랭킹 테이블 업데이트
function updateRankingTable() {
    const rankingBody = document.getElementById("ranking-body");
    rankingBody.innerHTML = ""; // 기존 내용을 초기화

    rankingData
        .sort((a, b) => b.level - a.level) // 레벨 순으로 정렬
        .forEach((user, index) => {
            const row = document.createElement("tr");
            row.innerHTML = `
                        <td>${index + 1}</td>
                        <td>${user.name}</td>
                        <td>${user.level}</td>
                        <td>${user.completedQuest ? "완료" : "미완료"}</td>
                    `;
            rankingBody.appendChild(row);
        });
}

// 문제 페이지에서 호출될 함수
function nextQuestion() {
    // 마지막 문제를 풀면 퀘스트 완료 처리
    if (!userData.completedQuest) {
        userData.level += 1; // 레벨 증가
        userData.completedQuest = true; // 퀘스트 완료 설정
        document.getElementById("completion-message").style.display = "block"; // 완료 메시지 표시
        updateUserUI();
        // 현재 유저 데이터를 랭킹에 반영
        const currentUser = rankingData.find(user => user.name === userData.name);
        if (currentUser) {
            currentUser.level = userData.level;
            currentUser.completedQuest = userData.completedQuest;
        }
        updateRankingTable();
    } else {
        alert("퀘스트는 이미 완료되었습니다.");
    }
}

// 페이지 로드 시 초기 데이터 표시
updateUserUI();
updateRankingTable();