$().ready(function() {
    $("#posterUrl").on("change", function() {
        console.log($(this)[0].files);
        
        var file = $(this)[0].files[0];
        
        if (file) {
            $("#fileName").text(file.name);
        } else {
            $("#fileName").text("선택된 파일 없음");
        }
    });
});