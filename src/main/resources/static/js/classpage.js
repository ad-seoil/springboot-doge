// 초기 유저 데이터 로드 (로컬 스토리지 활용)
const userData = JSON.parse(localStorage.getItem("userData")) || {
    name: "",
    level: "",
    completedQuest: false,
    freeTrialStartDate: null,
};

const ClassPage = {
    showProblemPage: function (url) {
        if (url) {
            console.log(`Navigating to: ${url}`); // 디버깅용 로그
            window.location.href = url; // HTML에서 받은 URL로 이동
        } else {
            console.error("URL이 제공되지 않았습니다.");
        }
    },

    // 퀘스트 페이지 이동
    navigateToQuest: function () {
        window.location.href = "/Quest-Progress";
    },

    // 포인트 구매 페이지 이동
    navigateToPurchasePage: function () {
        window.location.href = "/purchase-points";
    },

    // 프로필 페이지 이동
    navigateToProfile: function () {
        window.location.href = "/profile";
    },
    //랭킹 페이지 이동
    navigateToRankingPage: function () {
        location.href = '/User_Ranking';
    },

    // 7일 무료 사용 시작 함수
    activateFreeTrial: function () {
        if (!userData.freeTrialStartDate) {
            const today = new Date();
            userData.freeTrialStartDate = today.toISOString();
            alert("7일 무료 사용이 시작되었습니다! 광고 없이 이용 가능하며, 획득 재화가 2배로 증가합니다.");
            this.updateFreeTrialStatus();
            this.saveUserData();
        } else {
            const trialEndDate = new Date(userData.freeTrialStartDate);
            trialEndDate.setDate(trialEndDate.getDate() + 7);

            const today = new Date();
            if (today > trialEndDate) {
                alert("7일 무료 사용 기간이 종료되었습니다.");
            } else {
                alert("이미 무료 사용 중입니다.");
            }
        }
    },

    // 무료 사용 상태 저장 함수
    saveUserData: function () {
        localStorage.setItem("userData", JSON.stringify(userData));
    },

    // 무료 사용 상태 업데이트 함수
    updateFreeTrialStatus: function () {
        const statusElement = document.getElementById("free-trial-status");
        if (userData.freeTrialStartDate) {
            const trialEndDate = new Date(userData.freeTrialStartDate);
            trialEndDate.setDate(trialEndDate.getDate() + 7);

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
    },
};

// 전역 함수로 설정
window.ClassPage = ClassPage;

// 페이지 로드 시 무료 사용 상태 업데이트
document.addEventListener("DOMContentLoaded", () => {
    ClassPage.updateFreeTrialStatus();
});
