/**
 *
 */

$().ready(function () {
  // ".add-file"을 클릭하면
  // 새로운 파일 input과 버튼을
  // ".attach-files" 아래에 추가한다.
  $('.attach-files').on('click', '.add-file', function () {
    // 새로운 파일이 추가될 대 마다 기존의 "add-file"을 "del-file"로 변경하고
    // 텍스트는 "+" 에서 "-"로 변경한다
    $(this)
      .closest('.attach-files')
      .children('.add-file')
      .removeClass('add-file')
      .addClass('del-file')
      .text('-');

    var fileInput = $('<input />');
    fileInput.attr({
      type: 'file',
      name: 'attachFile',
    });

    var addButton = $('<button />');
    addButton
      .attr({
        type: 'button',
      })
      .addClass('add-file')
      .text('+');

    $('.attach-files').prepend(addButton).prepend(fileInput);

    $('.del-file').on('click', function () {
      $(this).prev().remove();
      $(this).remove();
    });
  });
  
  
  // 브라우저에서 입력값 검증하는 방법 2가지
  // 1. 폼 전송할 때 체크하는 방법
  // 2. 입력폼에 값을 입력을 할 때 체크하는 방법 (keyup 이벤트 활용)

  // 회원 가입 폼이 전송이 되기 전에 입력값을 제대로 작성했는지 체크

  // 폼이 전송이 될 때 이벤트를 처리
  $('#writeVO').on('submit', function (event) {
      // 이미 브라우저에 할당된 submit 콜백 이벤트를 제거한다.
      event.preventDefault();
      // form 내부에 존재하는 ".validation error" 엘리먼트를 모두 제거.
      $(this).find('.validation-error').remove();

      // 제목과 이메일을 제대로 입력했다 ==> 폼을 전송
      var subject = $('#subject').val();
      if (!subject) {
        var subjectErrorMessage = $('<div>');
        subjectErrorMessage.addClass('validation-error');
        subjectErrorMessage.text('');

        $('#subject').after(emailErrorMessage);
      }

      var email = $('#email').val();
      if (!email) {
        var emailErrorMessage = $('<div>');
        emailErrorMessage.addClass('validation-error');
        emailErrorMessage.text('이메일 형태가 아닙니다.');

        $('#email').after(emailErrorMessage);
      }
      // 제대로 입력하지 않았다 ==> 에러 메세지를 화면에 보여준다 폼 전송X
      if ($('.validation-error').length === 0) {
        // $(this).submit(); ==> jquery Event
        // (preventDefault)에서 전송 이벤트가 사라진 이유 때문에 동작되지 않는다.
        
        this.submit(); // ==> javascript Event
      }
    });
});
