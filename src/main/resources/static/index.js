window.onload = function () {

    $.getJSON( "/restGetUser", function( data ) {
        var vm = new Vue({
          el: "#main",
          data: {
              settings: data
          },
          created: function() {
              this.timer = setInterval(this.fetchSettings, 60000);
          },
          methods: {
              fetchSettings: function() {
                var self = this;
                $.getJSON( "/restGetUser", function( data ) {
                    self.settings=data;
                });
              },
          }
        });
    });

}
