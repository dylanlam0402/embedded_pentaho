$(document).ready(function () {
    var list = $('.butDelete');
    for (var i = 0; i < list.length; i++) {
        var item = list[i];
        item.addEventListener('click', deteleFile)
    }
});
function deteleFile() {
    var value = this.getAttribute('data');
    var tokenval = $("#txtCSRF").val();
    var uploadFileDTO = {"id": value, "token": tokenval};
    $.ajax({
        type: 'DELETE',
        url: '/api/file/delete',
        contentType: 'application/json',
        dataType: 'json',
        async: true,
        data: JSON.stringify(uploadFileDTO),
        success: function (result) {
            if (result) {
                alert("delete")
                location.reload();
            }
        }
    });
}

