/**
 * 
 */

$().ready(function () {
  
    $('#writeVO').on('submit', function (event) {
        // 이미 브라우저에 할당된 submit 콜백 이벤트를 제거한다.
        event.preventDefault();
        // form 내부에 존재하는 ".validation error" 엘리먼트를 모두 제거
        $(this).find('.validation-error').remove();
        
        // 제대로 입력하지 않은 경우 ==> 에러 메세지를 화면에 보여줌
        // title, synopsis, state, language
        var title = $('#title').val();
        if(!title) {
            var titleErrorMessage = $('<div>');
            titleErrorMessage.addClass('validation-error');
            titleErrorMessage.text('제목을 입력하세요 (브라우저)');
            
            $('#title').after(titleErrorMessage);
        }
        
        var synopsis = $('#synopsis').val();
        if(!synopsis) {
            var synopsisErrorMessage = $('<div>');
            synopsisErrorMessage.addClass('validation-error');
            synopsisErrorMessage.text('줄거리를 입력하세요 (브라우저)');
            
            $('#synopsis').after(synopsisErrorMessage);
        }
        
        var state = $('#state').val();
        if(!state) {
            var stateErrorMessage = $('<div>');
            stateErrorMessage.addClass('validation-error');
            stateErrorMessage.text('상태를 입력하세요 (브라우저)');
            
            $('#state').after(stateErrorMessage);
        }
        
        var language = $('#language').val();
        if(!language) {
            var languageErrorMessage = $('<div>');
            languageErrorMessage.addClass('validation-error');
            languageErrorMessage.text('언어를 입력하세요 (브라우저)');
            
            $('#language').after(languageErrorMessage);
        }        
        
        // 제대로 입력한 경우 ==> 폼 전송
        if ($('.validation-error').length === 0) {
            this.submit();
        }
        // TODO 무한루프에 빠짐
    });  
});