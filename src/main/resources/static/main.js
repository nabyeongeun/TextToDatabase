$(document).ready(function() {
    $('#file_input').change(file_attach);
//  $("#add-user-form").submit(function(event) {
//    event.preventDefault();
//    var name = $("#name").val();
//    var age = $("#age").val();
//    var city = $("#city").val();
//
//    $.ajax({
//      type: "POST",
//      url: "/addUser",
//      data: { name: name, age: age, city: city },
//      success: function(response) {
//        var newUser = '<tr><td>' + name + '</td><td>' + age + '</td><td>' + city + '</td></tr>';
//        $("table tbody").append(newUser);
//      }
//    });
//  });
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

                fileList.push(response);

                console.log(fileList);

                var newFile = '<tr><td>'
                    + '<input type="checkbox">' + '</td><td>'
                    + response.file_name + '</td><td>'
                    + response.file_size + '</td><td>'
                    + response.data_count + '</td><td>'
                    + response.upload_count + '</td><td>'
                    + "<button onclick=deleteRow('" + response.file_name + "')> C </button> </td></tr>";
                $("table tbody").append(newFile);
            },
            error: () => {
                alert("File upload failed");
            }
        });
    }
}

function deleteRow(fileName) {

    for(let i in fileList) {

        if(fileList[i].file_name == fileName) {
            fileList.splice(i, 1);
            $('#'+fileName).remove();
        }
    }

}