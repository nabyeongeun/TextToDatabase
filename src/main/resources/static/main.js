$(document).ready(function() {
    $('#file_input').change(file_attach);

    $.ajax({
        url: "/initPage.do",
        success : (obj) => {
            if(obj.status == "SUCCESS")
                $("#db_info").html("connection info : " + obj.url + "<br>database product : " + obj.productName);
            else
                alert("Page Loading Error");
        }, error : () => {
            alert("Page Loading Error");
        }
    });
});

let fileList = [];

function file_attach() {

    let files = $('#file_input').get(0).files;

    for (let i = 0; i < files.length; i++) {
        let formData = new FormData();
        formData.append("file", files[i]);
        $.ajax({
            url: "/uploadFile.do",
            contentType: false,
            processData: false,
            data: formData,
            type: 'POST',
            success: (response) => {

                if(response?.status != 'SUCCESS') {
                    alert(response.statusDescription);
                    return;
                }

                fileList.push(response);

//                console.log(fileList);

                var newFile = '<tr id="' + response.file_id + '"><td>'
                    + '<input type="checkbox">' + '</td><td>'
                    + response.file_name + '</td><td>'
                    + response.file_size + '</td><td>'
                    + response.row_count + '</td><td>'
                    + response.upload_count + '</td><td>'
                    + "<button onclick=deleteRow('" + response.file_id + "')> C </button> </td></tr>";
                $("table tbody").append(newFile);
            }, error: () => {
                alert("Invalid File Input");
            }
        });
    }
}

function deleteRow(id) {

    for(let i in fileList) {

        if(fileList[i].file_id == id) {
            fileList.splice(i, 1);
//            console.log('$("#'+id+'").remove()');
            $('#'+id).remove();
        }
    }

}