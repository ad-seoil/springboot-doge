// 각 언어를 클릭했을 때 해당 값을 db로 보내기
$(document).ready(function() {
    $('.survey-list li').click(function() {
        var level = $(this).text().trim(); // 클릭한 레벨 텍스트 가져오기
        $.ajax({
            url: '/saveLevel', // 서버에 요청할 URL (레벨 저장 URL)
            method: 'POST',
            contentType: 'application/json', // JSON 형식으로 전송
            data: JSON.stringify({ level: level }), // 레벨을 JSON으로 변환하여 전송
            success: function(response) {
                 alert('레벨 저장에 성공했습니다.')
            },
            error: function() {
                alert('레벨 저장에 실패했습니다.'); // 오류 메시지
            }
        });
    });
});