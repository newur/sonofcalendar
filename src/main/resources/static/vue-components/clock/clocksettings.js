Vue.component("clocksettings", {
    template: `<div>
                    <div class="settingsbox">
                        <div class="settingsboxtitle">
                            <span>Anzeigemodus Datum/Uhrzeit</span>
                        </div>
                        <div class="settingsoption">
                            <span class="settingslabel">
                                Uhrdarstellung
                            </span>
                            <span class="settingsvalue">
                                <select style="height: 25px; padding: 0px;" v-model="settings.clockSettings.clockType">
                                    <option disabled value="">Bitte auswählen</option>
                                    <option value="analog">Bahnhofsuhr</option>
                                    <option value="digital">Digitaluhr</option>
                                </select>
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