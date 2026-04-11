// fetch를 이용한 json 전송 방식
$().ready(function () {
  // 로그인한 유저의 이메일을 가져온다. 로그인을 안하면 없지.
  var loginEmail = $('.member-info').data('email');

  // 댓글을 여기저기서 불러올것이기 때문에 변수를 만든다
  var refreshReplies = function () {
    // 게시글 아이디
    var articleId = $('.view').data('article-id');

    // fetch 뒤에 객체 안적어 주면 GET, 적어주면 POST
    fetch('/api/replies/' + articleId)
      .then(function (response) {
        return response.json();
      })
      .then(function (json) {
        //console.log(json);
        var count = json.count;
        $('.replies-count').children('.count').text(count);

        var replies = json.result;
        for (var i = 0; i < replies.length; i++) {
          var reply = replies[i];
          var replyTemplate = $('.reply-item-template').html();
          replyTemplate = replyTemplate
            .replace('#replyId#', reply.id)
            .replace('#name#', reply.membersVO.name)
            .replace('#email#', reply.email)
            .replace('#recommendCount#', reply.recommendCnt)
            .replace('#createDate#', reply.crtDt)
            .replace('#modifyDate#', reply.mdfyDt)
            .replace('#content#', reply.reply);

          var replyDom = $(replyTemplate);
          //041O 로그인 한 회원이 작성한 댓글 인 경우
          //     로그인을 안한 회원인 경우
          //      추천하기 - 노출X
          if (!loginEmail || loginEmail === reply.email) {
            replyDom.find('.links-recommend').remove();
          }
          // 0410 로그인 한 회원이 작성하지 않은 댓글인 경우
          //      수정, 삭제 - 노출X
          if (!loginEmail || loginEmail !== reply.email) {
            // 로그인 안했을 때도 없어야 되지않나?
            replyDom.find('.links-update').remove();
            replyDom.find('.links-delete').remove();
          }
          // TODO 첨부파일이 있을 경우 첨부파일 목록 보여주기
          if (!reply.fileGroupId) {
            replyDom.find('.reply-attach-files').remove();
          } else {
            // reply.files를 반복하면서 a 태그를 ".reply-attach-files" 추가
            // 일단 담을 컨테이너 만들고 존재한다면 지워

            var fileContainer = replyDom
              .find('.reply-attach-files')
              .data('files', JSON.stringify(reply.files)); // 객체를 text 형태로 넣어준다.

            if (reply.reply && reply.files.length > 0) {
              for (var j = 0; j < reply.files.length; j++) {
                var file = reply.files[j];

                var fileSize = file.fileLength;
                var capaType = 'byte';
                if (fileSize > 1024) {
                  capaType = 'kb';
                  fileSize = Math.ceil(fileSize / 1024);
                }
                if (fileSize > 1024) {
                  // mb
                  capaType = 'mb';
                  fileSize = Math.ceil(fileSize / 1024);
                }

                var fileLink = $(
                  '<a href="/file/' +
                    file.fileGroupId +
                    '/' +
                    file.fileNum +
                    '"></a>',
                );
                fileLink.text(
                  file.displayName + ' (' + fileSize + ' ' + capaType + ')',
                );
                fileLink.css({ display: 'block', 'margin-bottom': '5px' });

                fileContainer.append(fileLink);
              }
            } else {
              // fileGroupId는 있는데 실제 파일 목록이 비어있는 경우
              fileContainer.remove();
            }
          }
          // 0410 수정날짜가 없을 때는 수정날짜 노출 X
          if (!reply.mdfyDt) {
            replyDom.find('.modify-date').remove();
          }
          // TODO 댓글 추천, 수정, 삭제
          // TODO 댓글 추천
          //      추천하기를 클릭하면, 추천수가 증가한다.
          replyDom.find('.links-recommend').on('click', function () {
            // 추천하려는 아이디 가져와
            var replyId = $(this).closest('.reply-item').data('reply-id');
            // 그놈의 추천수도 가져와
            var replySpan = $(this);

            // API 호출해
            // API 호출 (EndPoint ==> /api/replies/recommnd/{댓글아이디})
            fetch('/api/replies/recommend/' + replyId)
              .then(function (response) {
                // console.log(response.json()); // Promise 객체 반환 --> 비동기라 안끝나니깐
                // 호출 결과 ==> {replyId : "RP-20260410-000001": recommendCount: 0}
                return response.json();
              })
              .then(function (json) {
                // ".recommend-count" 텍스트를 추천 결과 값으로 변경
                var updatedCnt = json.recommendCnt + ' 추천';
                replySpan
                  .closest('.reply-item')
                  .find('.recommend-count')
                  .text(updatedCnt);
              });
          });

          // TODO 댓글 수정.
          //      수정을 클릭하면, 댓글을 수정할 수 있는 폼이 완성된다
          replyDom.find('.links-update').on('click', function () {
            $('.update-form').remove();

            var replyAttachFiles = $(this)
              .closest('.reply-item')
              .find('.reply-attach-files')
              .data('files');

            if (replyAttachFiles) {
              replyAttachFiles = JSON.parse(replyAttachFiles); // JSON을 객체로 바꿔준다. 그럼 파일 목록이 되겠지?
            }

            var content = $(this)
              .closest('.reply-item')
              .find('.content')
              .text(); // 댓글의 내용을 textarea에 넣어주려고

            var updateTemplate = $('.reply-item-update-template').html();
            var updateFormDom = $(updateTemplate);
            updateFormDom.find('textarea').val(content);

            // 취소 클릭
            updateFormDom.find('.update-cancel').on('click', function () {
              $('.update-form').remove();
            });

            // 저장 클릭
            updateFormDom.find('.update-save').on('click', function () {
              console.log('저장버튼을 클릭하였습니다.');

              // 수정을 위해 필요한 데이터

              // 1. 수정하려는 댓글의 아이디
              var replyId = $(this).closest('.reply-item').data('reply-id');
              // 2. 수정하려는 댓글의 내용
              var updateContent = $(this)
                .closest('.update-form')
                .find('textarea')
                .val();
              // 3. 삭제하려는 파일의 fileNum
              var deleteFilesNum = $(this)
                .closest('.update-form')
                .find('.update-file-list')
                .find("input[type='checkbox']:checked");
              // 4. 추가하려는 파일 (여러 개)
              var newAttachFiles = $(this)
                .closest('.update-form')
                .find('.reply-update-attach-file')[0].files;

              // console.log(replyId);
              // console.log(updateContent);
              // console.log(deleteFilesNum);
              // console.log(newAttachFiles);

              var formData = new FormData();
              formData.append('content', updateContent);
              // 삭제할 파일이 있으면 formData에 추가한다.
              deleteFilesNum.each(function () {
                formData.append('delFileNum', $(this).val());
              });
              // 추가하려는 파일이 있으면 formData에 추가한다.
              // 배열이기 때문에 배열로 반복
              for (var j = 0; j < newAttachFiles.length; j++) {
                formData.append('newAttachFile', newAttachFiles[j]);
              }

              fetch('/api/replies/' + replyId, {
                method: 'post',
                body: formData,
              })
                .then(function (response) {
                  return response.json();
                })
                .then(function (json) {
                  $('.replies').html('');
                  refreshReplies();
                });
            });

            if (replyAttachFiles) {
              var replyItemsTemplate = $('.reply-item-update-files').html();
              for (var j = 0; j < replyAttachFiles.length; j++) {
                var replyItemFile = replyAttachFiles[j];

                var fileTemplate = replyItemsTemplate
                  .replaceAll('#fileGroupId#', replyItemFile.fileGroupId)
                  .replaceAll('#fileNum#', replyItemFile.fileNum)
                  .replaceAll('#fileDisplayName#', replyItemFile.displayName);

                updateFormDom.find('.update-file-list').append($(fileTemplate));
              }
            }

            $(this)
              .closest('.reply-item')
              .find('.content')
              .after(updateFormDom);
          });

          // TODO 댓글 삭제
          //      삭제를 클릭하면, "삭제하시겠습니까?"를 물어보고 "확인"을 클릭했을 때 삭제되고
          //      이후 댓글 목록을 새로 고친다.
          replyDom.find('.links-delete').on('click', function () {
            //    결과의 replyId 댓글을 목록에서 제거한다. (댓글 다시 불러오기 X)
            //    "취소"를 클릭하면 아무 일도 일어나지 않는다.
            // "삭제하시겠습니까?" 를 사용자에게 물어본다
            if (confirm('삭제하시겠습니까?')) {
              var replyId = $(this).closest('.reply-item').data('reply-id');
              var replySpan = $(this);

              //    "확인" 을 클릭하면 삭제 API를 호출한다. (EndPoint ==> /api/replies/delete/{댓글아이디})
              fetch('/api/replies/delete/' + replyId)
                .then(function (result) {
                  return result.json();
                })
                .then(function (json) {
                  replySpan.closest('.reply-item').remove();
                });
              //    호출 결과 ==> { replyId: "RP-20260410-000001"}
            }
          });

          // --------------
          replyDom.css({ 'margin-left': (reply.level - 1) * 2 + 'rem' });
          replyDom.find('.links-write').on('click', function () {
            var replyId = $(this).closest('.reply-item').data('reply-id');
            console.log('Click! - ' + replyId);

            $('.reply-form').children('.parent-reply-id').val(replyId);
            $('.reply-content').focus();
          });
          $('.replies').append(replyDom);

          //$('.replies').append(replyTemplate);
          // console.log(replyTemplate.html());
        }
      });
  };
  refreshReplies();

  $('.reply-save').on('click', function () {
    var replyContent = $('.reply-content').val();
    var articleId = $(this).data('article-id');
    var parentReplyId = $('.parent-reply-id').val();
    var files = $('.reply-attach-file')[0]; // input 태그 뽑아낸다.
    //console.log(files.files); // 파일 리스트 전체
    //console.log(files.files[0]); // 첫 번재 파일 하나

    var formData = new FormData();
    formData.append('reply', replyContent);
    formData.append('articleId', articleId);
    formData.append('parentReplyId', parentReplyId);
    if (files.files.length > 0) {
      // 한개 이상을 선택했다면
      for (var i = 0; i < files.files.length; i++) {
        formData.append('attachFile', files.files[i]);
      }
    }

    fetch('/api/replies-with-file', {
      method: 'post',
      body: formData,
    })
      .then(function (response) {
        return response.json();
      })
      .then(function (json) {
        //console.log(json);

        // 댓글 등록하기 후 처리
        $('.reply-form').children('.parent-reply-id').val('');
        $('.reply-content').val('');
        $('.replies').html('');
        refreshReplies();
      });
  });
});
