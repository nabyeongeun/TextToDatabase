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


function file_attach() {
    let formData = new FormData();
    let files = $('#file_input').get(0).files;
    for (let i = 0; i < files.length; i++) {
        formData.append("file", files[i]);
        $.ajax({
            url: "/uploadFile.do",
            contentType: false,
            processData: false,
            data: formData,
            type: 'POST',
            success: function(response) {
                console.log(response);
            },
            error: function() {
                console.error("File upload failed");
            }
        });
    }
}

//function file_attach() {
//    let formData = new FormData();
//    let files = $('#file_input').get(0).files;
//    files.forEach(function(file) {
//        formData.append("file_input", file);
//        let data = formData;
//        $.ajax({
//            url: "/uploadFile.do",
//            contentType: false,
//            processData: false,
//            data: data,
//            type: 'POST',
//            success: function(response) {
//                console.log(response);
//            },
//            error: function() {
//                console.error("File upload failed");
//            }
//        });
//    });
//}
