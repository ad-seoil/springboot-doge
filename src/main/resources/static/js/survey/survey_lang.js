// 각 언어를 클릭했을 때 해당 값을 db로 보내기
$(document).ready(function() {
    $('.language-item').click(function() {
        var languageId = $(this).data('id'); // 클릭한 언어의 ID 가져오기
        $.ajax({
            url: '/saveLanguage', // 서버에 요청할 URL
            method: 'POST',
            data: {
                languageId: languageId
            },
            success: function(response) {
//                alert('언어 저장에 성공했습니다.'); // 성공 메시지
                // 언어가 저장되면 survey_purpose.html로 리다이렉트
                window.location.href = '/surveyLevel'; // 원하는 URL로 변경
            },
            error: function(xhr, status, error) {
                alert('언어 저장에 실패했습니다: ' + xhr.responseText); // 오류 메시지
            }
        });
    });
});