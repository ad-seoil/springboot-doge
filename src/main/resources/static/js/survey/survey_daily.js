// 각 시간을 클릭했을 때 해당 값을 db로 보내기
$(document).ready(function() {
    $('.survey-list li').click(function() {
        var goal = $(this).data('goal'); // 클릭한 목표 가져오기

        // AJAX 요청을 통해 목표를 서버로 전송
        $.ajax({
            url: '/saveDaily', // 서버에 요청할 URL
            method: 'POST',
            contentType: 'application/json', // JSON 형식으로 전송
            data: JSON.stringify({ goal: goal }), // 목표를 JSON으로 변환하여 전송
            success: function(response) {
//                alert('목표 저장에 성공했습니다.'); // 성공 메시지
                window.location.href = '/surveyAlarm';
            },
            error: function() {
                alert('목표 저장에 실패했습니다.'); // 오류 메시지
            }
        });
    });
});