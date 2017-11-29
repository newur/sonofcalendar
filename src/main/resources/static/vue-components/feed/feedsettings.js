Vue.component("feedsettings", {
    template: `<div>
                    <div class="settingsbox">
                        <div class="settingsboxtitle">
                            <span>Einstellungen RSS Feed</span>
                        </div>
                        <div class="settingsoption">
                            <span class="settingslabel">
                                RSS Feed
                            </span>
                            <span class="settingsvalue">
                                <input class="settingsinput" v-model="settings.rssSettings.rssUrl">
                            </span>
                        </div>
                        <div class="settingsoption">
                            <span class="settingslabel">
                                Anzeigedauer pro Feed
                            </span>
                            <span class="settingsvalue">
                                <input type="range" min="1" max="300" v-model="settings.rssSettings.rssTransitionTime"> {{settings.rssSettings.rssTransitionTime}} Sekunden
                            </span>
                        </div>
                    </div>
                    <button class="savesettingsbtn" @click="save()">Speichern</button>
               </div>`,
    props: ['settings'],
    methods:{
       save: function(){
           this.$parent.$options.methods.save(this.settings);
       }
    }
});




