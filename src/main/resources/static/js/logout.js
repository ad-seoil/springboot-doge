document.addEventListener('DOMContentLoaded', () => {
    const logoutMenu = document.getElementById('logoutMenu');
    if (logoutMenu) {
        logoutMenu.addEventListener('click', async () => {
            try {
                const response = await fetch('http://localhost:8083/logout', {
                    method: 'POST',
                    credentials: 'include', // 세션 정보 포함
                });
                if (response.ok) {
                    alert('로그아웃 되었습니다.');
                    window.location.href = "/main"; // "src/main/resources/static/" 내에 있는 main.html 파일
                } else {
                    alert('로그아웃 실패. 다시 시도해주세요.');
                }
            } catch (error) {
                console.error('로그아웃 요청 중 오류:', error);
            }
        });
    } else {
        console.error("logoutMenu 요소를 찾을 수 없습니다.");
    }
});
