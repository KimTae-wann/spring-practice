/**
 * 회원 페이지와 관련된 스크립트 작성
 */

$().ready(function () {
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

    // 이름과 이메일과 비밀번호를 제대로 입력했다 ==> 폼을 전송
    var email = $('#email').val();
    if (!email) {
      var emailErrorMessage = $('<div>');
      emailErrorMessage.addClass('validation-error');
      emailErrorMessage.text('이메일 형태가 아닙니다.');

      $('#email').after(emailErrorMessage);
    }

    var name = $('#name').val();
    if (!name) {
      var nameErrorMessage = $('<div>');
      nameErrorMessage.addClass('validation-error');
      nameErrorMessage.text('');

      $('#name').after(emailErrorMessage);
    }

    var passwordPattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/;
    var password = $('#password').val();
    if (!passwordPattern.test(password)) {
      var passwordErrorMessage = $('<div>');
      passwordErrorMessage('validation-error');
      nameErrorMessage.text(
        '비밀번호는 영소문자, 영대문자, 숫자, 최소 1개를 포함하여 8글자 이상으로 입력하세요.',
      );
      $('#password').after(passwordErrorMessage);
    }
    if (!password) {
      var passwordErrorMessage = $('<div>');
      passwordErrorMessage('validation-error');
      nameErrorMessage.text('비밀번호를 입력해주세요');
      $('#password').after(passwordErrorMessage);
    }
    // 제대로 입력하지 않았다 ==> 에러 메세지를 화면에 보여준다 폼 전송X
    if ($('.validation-error').length === 0) {
      // $(this).submit(); ==> jquery Event
      // 14번 라인(preventDefault)에서 전송 이벤트가 사라진 이유 때문에 동작되지 않는다.
      
      $(this).submit(); // ==> javascript Event
    }
  });
});
