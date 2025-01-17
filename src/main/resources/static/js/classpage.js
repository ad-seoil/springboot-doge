function navigateToLearningPage() {
    // 이동할 페이지 URL을 설정
    window.location.href = "/selectQuestions";  // "src/main/resources/static/" 내에 있는 Questionpage.html 파일

}
// 초기 유저 데이터
const userData = {
    name: "", //유저데이터를 받아와서보일수있게
    level:"",
    completedQuest: false,
    freeTrialStartDate: null, // 무료 사용 시작 날짜
};

// 7일 무료 사용 시작 함수
function activateFreeTrial() {
    if (!userData.freeTrialStartDate) {
        const today = new Date();
        userData.freeTrialStartDate = today.toISOString(); // ISO 문자열로 저장
        alert("7일 무료 사용이 시작되었습니다! 광고 없이 이용 가능하며, 획득 재화가 2배로 증가합니다.");
        updateFreeTrialStatus();
    } else {
        const trialEndDate = new Date(userData.freeTrialStartDate);
        trialEndDate.setDate(trialEndDate.getDate() + 7); // 7일 후 날짜 계산

        const today = new Date();
        if (today > trialEndDate) {
            alert("7일 무료 사용 기간이 종료되었습니다.");
        } else {
            alert("이미 무료 사용 중입니다.");
        }
    }
}

// 무료 사용 상태 업데이트 함수
function updateFreeTrialStatus() {
    const statusElement = document.getElementById("free-trial-status");
    if (userData.freeTrialStartDate) {
        const trialEndDate = new Date(userData.freeTrialStartDate);
        trialEndDate.setDate(trialEndDate.getDate() + 7); // 7일 후 날짜 계산

        const today = new Date();
        if (today > trialEndDate) {
            statusElement.textContent = "7일 무료 사용이 종료되었습니다.";
            document.getElementById("free-trial-button").disabled = true;
        } else {
            statusElement.textContent = `무료 사용 중 (종료일: ${trialEndDate.toLocaleDateString()})`;
        }
    } else {
        statusElement.textContent = "7일 무료 사용이 활성화되지 않았습니다.";
    }
}

// 페이지 로드 시 무료 사용 상태 업데이트
document.addEventListener("DOMContentLoaded", updateFreeTrialStatus);