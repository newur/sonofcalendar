window.onload = function () {

    $.getJSON( "/restGetUser", function( data ) {

        var vm = new Vue({
            el: "#main",
            data: {
                tab: 'usersettings',
                tabname: 'Kontoeinstellungen',
                settings: data
            },
            methods:{
                save: function(changedSettings){
                   var self = this;
                   $.ajax({
                       type: 'POST',
                       url: "/restSaveUser",
                       data: JSON.stringify(changedSettings),
                       success: function(data) {
                           self.settings=data;
                       },
                       contentType: "application/json",
                       dataType: 'json'
                   });
                },
                setTab: function(tab, tabname){
                    this.tab=tab;
                    this.tabname=tabname;
                }
            }
        });
    });

}