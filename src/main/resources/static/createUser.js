window.onload = function () {

    $("#createUserForm").submit(function(event) {
          event.preventDefault();
          var $form = $( this ), url = $form.attr( 'action' );
          $.ajax({
                type: 'POST',
                url: url,
                data: { username: $('#username').val(),
                        password: $('#password').val(),
                        email: $('#email').val() }
          }).done(function(data) {
            if(data==true){
                window.location.replace("http://localhost:8080/settings.html");
            }
            else {
                //TODO
                console.log("Something went wrong - User was not created");
            }
          });
    });

}
