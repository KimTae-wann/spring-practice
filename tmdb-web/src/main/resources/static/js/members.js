/**
 * 
 */
$().ready(function () {
    
    $('#email').on('keyup', function() {
        var emailValue = $(this).val();
        console.log(emailValue);
        
        var emailPattern = /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/;
        
        // 이메일을 입력을 했을 때
        if (emailPattern.test(emailValue)) {
            // 비동기로 중복 여부를 검사해 온다.
            fetch("/regist/check/duplicate/" + emailValue)
            // 비동기 결과를 이용해서 메세지를 노출하거나 숨긴다.
            .then(function(fetchResult) {
                return fetchResult.json();
            })
            .then(function(json) {
                var duplicateResult = $("#email")
                    .closest(".input-div")
                    .children(".validation-error");
                    
                if (duplicateResult.length === 0) { // 에러가 없다면
                    duplicateResult = $("#email")
                    .closest(".input-div")
                    .children(".validation-ok");
                }
                
                if (duplicateResult.length === 0) { // ok 도 없다면
                    duplicateResult = $('<div>')
                    $("#email").after(duplicateResult);
                }
                
                if (!json.duplicate) { // 중복 X
                    duplicateResult.removeClass("validation-error")
                    duplicateResult.addClass('validation-ok');
                    duplicateResult.text(json.email + '은 사용 가능합니다.');
                } else { // 중복O
                    duplicateResult.removeClass("validation-ok")
                    duplicateResult.addClass('validation-error');
                    duplicateResult.text(json.email + '은 이미 사용중입니다.');
                }
            });
        } else {
            $(this)
                .closest(".input-div")
                .children(".validation-ok", ".validation-error")
                .remove();
        }
    })

    $("#confirm-password, #password").on("keyup", function () {
        var confirmPasswordValue = $("#confirm-password").val();
        var passwordValue = $("#password").val();

        $("#password, #confirm-password")
          .closest(".input-div")
          .children(".validation-error")
          .remove();

        if (confirmPasswordValue !== passwordValue) {
            var passwordErrorMessage = $("<div>");
            passwordErrorMessage.addClass("validation-error");
            passwordErrorMessage.text("비밀번호가 일치하지 않습니다.");

            var confirmPasswordErrorMessage = $("<div>");
            confirmPasswordErrorMessage.addClass("validation-error");
            confirmPasswordErrorMessage.text("비밀번호가 일치하지 않습니다.");

            $("#password").after(passwordErrorMessage);
            $("#confirm-password").after(confirmPasswordErrorMessage);
        }
    });
         
    $('#show-password').on('change', function() {
        var checked =$(this).prop("checked");
        if (checked) {
            $('#password').attr("type", "text");
        } else {
            $('#password').attr("type", "password");
        }
    });
});