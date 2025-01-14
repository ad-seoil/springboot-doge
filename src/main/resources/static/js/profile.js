document.addEventListener('DOMContentLoaded', function() {

    // 수정하기 버튼 클릭 시
    document.getElementById('editButton').addEventListener('click', function() {
        document.getElementById('nickname').removeAttribute('readonly');
        document.getElementById('email').removeAttribute('readonly');

        // 생일 입력 필드
        const birthDateInput = document.getElementById('birthDate');

        // 생일 값이 null일 경우에만 readonly 속성 제거
        if (birthDateInput.value === '') {
            birthDateInput.removeAttribute('readonly');
        }
        
        document.querySelector('button[type="submit"]').style.display = 'inline';
        // 비밀번호 필드 표시
        document.querySelector('#password').style.display = 'inline'; 
        
        this.style.display = 'none';
    });

    // 비밀번호 검증
    document.getElementById('submitButton').addEventListener('click', function(event) {
        const password1 = document.getElementById('password1').value;
        const password2 = document.getElementById('password2').value;
    
        console.log("password1:", password1); // 로그 추가
        console.log("password2:", password2); // 로그 추가
    
        // 비밀번호가 입력되었는지 확인
//        if (password1 === '' || password2 === '') {
//            alert('비밀번호를 입력해 주세요.');
//            event.preventDefault(); // 기본 제출 동작 방지
//            return;
//        }
    
        // 비밀번호가 일치하는지 확인
        if (password1 !== password2) {
            alert('비밀번호가 일치하지 않습니다. 다시 입력해 주세요.');
            event.preventDefault(); // 기본 제출 동작 방지
            return;
        }
    
        // 비밀번호가 일치하는 경우
        console.log("비밀번호가 일치합니다. 폼을 제출합니다."); // 로그 추가
        document.getElementById('profileForm').submit(); // 폼 제출
    });

// 계정 삭제
document.getElementById('deleteAccount').addEventListener('click', function(event) {
    event.preventDefault(); // 기본 동작 방지

    if (confirm("계정을 정말 삭제하시겠습니까? 이 작업은 되돌릴 수 없습니다.")) {
        fetch('/api/members/delete', {
            method: 'DELETE'
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('계정 삭제 실패');
            }
            return response.text();
        })
        .then(data => {
            alert(data); // 성공 메시지 표시
            window.location.href = '/login'; // 로그인 페이지로 리다이렉트
        })
        .catch(error => {
            console.error('Error:', error);
            alert('계정 삭제 중 오류가 발생했습니다.');
        });
    }
});

// 프로필 이미지 업로드
    // 이미지 업로드 버튼 클릭 시 파일 선택 대화 상자 열기
    document.getElementById('uploadButton').addEventListener('click', function() {
        document.getElementById('file').click();
    });
    
});

