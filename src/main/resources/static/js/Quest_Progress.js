const userData = {
    freeTrialStartDate: null, // 무료 사용 시작 날짜
};

const QuestProgress = {
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

// 페이지 로드 시 로직
document.addEventListener("DOMContentLoaded", () => {
    console.log("Quest Progress 페이지 로드 완료.");

    // 퀘스트 완료 버튼 동작
    const completeQuestButton = document.getElementById("completeQuestButton");
    if (completeQuestButton) {
        console.log("completeQuestButton 버튼이 존재합니다."); // 디버깅용
        completeQuestButton.addEventListener("click", () => {
            alert("퀘스트를 완료했습니다!");
        });
    } else {
        console.log("completeQuestButton 버튼을 찾을 수 없습니다."); // 디버깅용
    }

    // 돌아가기 버튼 동작
    const backButton = document.getElementById("backButton");
    if (backButton) {
        console.log("backButton 버튼이 존재합니다."); // 디버깅용
        backButton.addEventListener("click", () => {
            window.location.href = "/classpage";
        });
    } else {
        console.log("backButton 버튼을 찾을 수 없습니다."); // 디버깅용
    }

    // 구매하러 가기 버튼 동작
    const goToPurchaseButton = document.getElementById("goToPurchasePageButton");
    if (goToPurchaseButton) {
        console.log("goToPurchasePageButton 버튼이 존재합니다."); // 디버깅용
        goToPurchaseButton.addEventListener("click", () => {
            window.location.href = "/purchase-points"; // Purchase_points 페이지로 이동
        });
    } else {
        console.log("goToPurchasePageButton 버튼을 찾을 수 없습니다."); // 디버깅용
    }

    // 7일 무료 사용 버튼 동작
    QuestProgress.updateFreeTrialStatus(); // 무료 사용 상태 업데이트
    const freeTrialButton = document.getElementById("free-trial-button");
    if (freeTrialButton) {
        console.log("freeTrialButton 버튼이 존재합니다."); // 디버깅용
        freeTrialButton.addEventListener("click", () => {
            QuestProgress.activateFreeTrial();
        });
    } else {
        console.log("freeTrialButton 버튼을 찾을 수 없습니다."); // 디버깅용
    }
});
