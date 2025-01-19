// 유무를 선택한 후 값 보내기
$(document).ready(function() {
    $('.survey-list li').click(function() {
        var alarm = $(this).data('alarm'); // 클릭한 항목의 알림 유무 가져오기

        // AJAX 요청을 통해 알림 설정을 서버로 전송
        $.ajax({
            url: '/saveSurveyData', // 서버에 요청할 URL
            method: 'POST',
            contentType: 'application/json', // JSON 형식으로 전송
            data: JSON.stringify({ alarm: alarm }), // 알림 값을 JSON으로 변환하여 전송
            success: function(response) {
//                alert('알림 설정이 저장되었습니다.'); // 성공 메시지
                window.location.href = '/classpage';
            },
            error: function() {
                alert('알림 설정 저장에 실패했습니다.'); // 오류 메시지
            }
        });
    });
});