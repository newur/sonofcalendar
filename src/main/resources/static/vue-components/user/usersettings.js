Vue.component("usersettings", {
    template: `<div>
                    <div class="settingsbox">
                        <div class="settingsboxtitle">
                            <span>Kontoinformationen</span>
                        </div>
                        <div class="settingsoption">
                            <span class="settingslabel">
                                Benutzername
                            </span>
                            <span class="settingsvalue">
                                <input class="settingsinput" v-model="settings.username"  disabled>
                            </span>
                        </div>
                        <div class="settingsoption">
                            <span class="settingslabel">
                                Vorname
                            </span>
                            <span class="settingsvalue">
                                <input class="settingsinput" v-model="settings.firstname">
                            </span>
                        </div>
                        <div class="settingsoption">
                            <span class="settingslabel">
                                Nachname
                            </span>
                            <span class="settingsvalue">
                                <input class="settingsinput" v-model="settings.lastname">
                            </span>
                        </div>
                        <div class="settingsoption">
                            <span class="settingslabel">
                                Geburtstag
                            </span>
                            <span class="settingsvalue">
                                <input type="date" class="settingsinput" v-model="settings.birthday">
                            </span>
                        </div>
                        <div class="settingsoption">
                            <span class="settingslabel">
                                Email
                            </span>
                            <span class="settingsvalue">
                                <input class="settingsinput" v-model="settings.email">
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