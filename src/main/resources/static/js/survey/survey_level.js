// 각 레벨를 클릭했을 때 해당 값을 db로 보내기
$(document).ready(function() {
    $('.survey-list li').click(function() {
        var level = $(this).data('level'); // 클릭한 레벨 가져오기
        var languageId = $(this).data('language-id'); // 언어 ID 가져오기

        $.ajax({
            url: '/saveLevel',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ level: level, languageId: languageId }),
            success: function(response) {
//                alert('레벨이 저장되었습니다.');
                window.location.href = '/surveyDaily'; // 다음 페이지로 이동
            },
            error: function() {
                alert('레벨 저장에 실패했습니다.');
            }
        });
    });
});