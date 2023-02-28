$(document).ready(function() {
    $('#file_input').change(file_attach);
    $('#date_format').val('YYYY/MM/DD HH24:MI:SS');

    $.ajax({
        url: "/initPage.do",
        success : (obj) => {
            if(obj.status == "SUCCESS")
                $("#db_info").html("connection : " + obj.url + "<br>username : " + obj.username + "<br>database product : " + obj.productName);
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

                var newFile = "<tr id='" + response.file_id + "'>" +
//                    "<td><input type='checkbox'></td>" +
                    "<td>" + response.file_name + "</td>" +
                    "<td>" + response.file_size + "</td>" +
                    "<td>" + response.row_count + "</td>" +
                    "<td>" + response.table_data_count + "</td>" +
                    "<td><button onclick=deleteRow('" + response.file_id + "')> C </button> </td></tr>";
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

function start() {

//    let truncate_yn = $('#truncate_yn').is(':checked');

    let tableList = ""

    for(let i in fileList) {

        let fileName = fileList[i].file_name.substr(0, fileList[i].file_name.indexOf("."))

        if(i == 0)
            tableList = fileName
        else
            tableList += "," + fileName
    }

    if(tableList == "") {
        alert("list is empty")
        return
    }

    $.ajax({
        url: "/preInsert.do",
        type: "GET",
        dataType: "json",
        data: {"tableList" : tableList},
        success : (obj) => {
            if(obj.status == "SUCCESS")
                alert("good")
            else
                alert(obj.description);
        }
    });

}