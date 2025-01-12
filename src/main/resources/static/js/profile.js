document.addEventListener('DOMContentLoaded', function() {

    // 수정하기 버튼 클릭 시
    document.getElementById('editButton').addEventListener('click', function() {
        document.getElementById('nickname').removeAttribute('readonly');
        document.getElementById('email').removeAttribute('readonly');
        
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
        if (password1 === '' || password2 === '') {
            alert('비밀번호를 입력해 주세요.');
            event.preventDefault(); // 기본 제출 동작 방지
            return;
        }
    
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
    



    // 이미지 업로드 버튼 클릭 시 파일 선택 대화 상자 열기
    document.getElementById('uploadButton').addEventListener('click', function() {
        document.getElementById('set_profile').click();
    });

    // 파일 선택 시 이벤트 처리
    document.getElementById('set_profile').addEventListener('change', function(event) {
        const file = event.target.files[0]; // 선택된 파일
        if (!file) {
            console.error('파일이 선택되지 않았습니다.');
            return; // 파일이 선택되지 않은 경우 처리 중단
        }

        // sessionStorage에서 memberId 가져오기
        const memberId = sessionStorage.getItem('memberId'); // memberId가 저장된 세션 스토리지에서 가져오기
        if (!memberId) {
            console.error('memberId를 찾을 수 없습니다.');
            return; // memberId가 없는 경우 처리 중단
        }

        const formData = new FormData();
        formData.append('profileImg', file); // FormData에 파일 추가
        formData.append('memberId', memberId); // sessionStorage에서 가져온 memberId 추가

        // AJAX 요청
        fetch('/uploadProfileImage', {
            method: 'POST',
            body: formData
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('네트워크 응답이 정상적이지 않습니다.');
            }
            return response.json();
        })
        .then(data => {
            console.log('Success:', data);
            const imgElement = document.querySelector('.upload-img img:last-of-type');
            imgElement.src = URL.createObjectURL(file); // 선택한 이미지로 미리보기 업데이트
        })
        .catch((error) => {
            console.error('Error:', error);
        });
    });

});
