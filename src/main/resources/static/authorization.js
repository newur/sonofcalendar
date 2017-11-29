$.get( "/restAuthorize", function( data ) {
    if (data!="success"){
        window.stop();
        window.location.replace(data);
    }
});