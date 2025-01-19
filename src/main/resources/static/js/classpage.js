// 초기 유저 데이터 로드 (로컬 스토리지 활용)
const userData = JSON.parse(localStorage.getItem("userData")) || {
    name: "",
    level: "",
    completedQuest: false,
    freeTrialStartDate: null,
};

const ClassPage = {
    navigateToQuestionPage: function (url) {
        window.location.href = url; // 문제 페이지로 이동
    },

    navigateToPurchasePage: function () {
        console.log("Navigating to purchase page...");
        window.location.href = "/purchase-points";
    },
    navigateToProfile: function () {
        console.log("Navigating to profile page...");
        window.location.href = "/profile";
    },

    trackCompletedStars: function () {
        const completedStars = parseInt(localStorage.getItem("completedStars") || "0");
        localStorage.setItem("completedStars", completedStars + 1); // 별 클릭 수 증가
    },

    showCompletedStars: function () {
        const completedStars = parseInt(localStorage.getItem("completedStars") || "0");
        alert(`총 ${completedStars}개의 문제를 해결하였습니다.`);
    },

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

    saveUserData: function () {
        localStorage.setItem("userData", JSON.stringify(userData));
    },

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

// 퀘스트 완료 버튼 이벤트 추가
document.addEventListener("DOMContentLoaded", () => {
    ClassPage.updateFreeTrialStatus();

    const completeQuestButton = document.getElementById("completeQuestButton");
    completeQuestButton.addEventListener("click", () => {
        const taskMessage = document.getElementById("taskMessage");
        taskMessage.innerHTML = "오늘의 목표를 달성하였습니다!";
    });
});
