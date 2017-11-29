Vue.component("photosettings", {
    template: `<div>
                    <div class="settingsbox">
                        <div class="settingsboxtitle">
                            <span>Anzeigeeinstellungen</span>
                        </div>
                        <div class="settingsoption">
                            <span class="settingslabel">
                                Bildintervall
                            </span>
                            <span class="settingsvalue">
                                <input type="range" min="1" max="300" v-model="settings.photoSettings.photoTransitionTime"> {{settings.photoSettings.photoTransitionTime}} Sekunden
                            </span>
                        </div>
                    </div>

                    <div class="settingsbox">
                       <div class="settingsboxtitle">
                           <span>Meine Fotoalben</span>
                       </div>
                       <div class="settingsoption">
                           <table id="selectedPhotoAlbumsTable" border="0" style="text-align:left">
                              <tbody>
                                  <tr v-for="photoAlbum in settings.photoSettings.photoAlbums">
                                      <td><img width="16" :src="typeimg(photoAlbum.type)"></img></td>
                                      <td><input v-model="photoAlbum.name" style="width: 100px;"/></td>
                                      <td><input v-model="photoAlbum.url" style="width: 400px;"/></td>
                                  </tr>
                              </tbody>
                          </table>
                          <button @click="setPhotoAlbumType('google')">Google-Album hinzufügen</button>
                       </div>
                       <div class="settingsoption" v-if="selectedPhotoAlbumType==='google'">
                            <div v-for="photoAlbum in availablePhotoAlbums">
                                 <button @click="addPhotoAlbum(photoAlbum)">Hinzufügen</button>
                                 <span>{{ photoAlbum.name }}</span>
                            </div>
                       </div>
                    </div>
                    <button class="savesettingsbtn" @click="save()">Speichern</button>
               </div>`,
    props: ['settings'],
    data: function(){
        return {
            availablePhotoAlbums: [],
            selectedPhotoAlbumType: ''
        }
    },
    methods:{
        setPhotoAlbumType: function(type){
          this.selectedPhotoAlbumType=type;
          this.loadAvailablePhotoAlbums();
        },
        loadAvailablePhotoAlbums: function(){
            var self = this;
            $.post("/restGetPhotoAlbums", {'type': self.selectedPhotoAlbumType}, function(data, textStatus) {
              self.availablePhotoAlbums = data;
            },"json");
        },
        addPhotoAlbum: function(photoAlbum){
          this.settings.photoSettings.photoAlbums.push(photoAlbum);
          this.availablePhotoAlbums = $.grep(this.availablePhotoAlbums, function(e){
                                        return e.url != photoAlbum.url;
                                     });
        },
        typeimg: function(type){
            return "/img/"+type+"_icon_128.png";
        },
        save: function(){
            this.$parent.$options.methods.save(this.settings);
        }
    }
});