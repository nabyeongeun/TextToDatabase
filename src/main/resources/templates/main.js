//$(document).ready(function() {
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
//});


function file_attach() {

    let forms = $('file_form');
    let formData = new FormData(forms[0]);

    let files = $('#file_input');

    for(i in files) {

        let file = files.get(i).files;

        formData.append("file_input", file);
//        formData.append("file_type", $('#file_type').val());

        $.ajax({
            url: "/uploadFile.do",
            contentType: false,
            data: formData,
            type: 'POST',
            async: false,
            success: (response) => {

                console.log(response);

            }, error () => {

            }

        });
    }


    let formData = new FormData(form);
}