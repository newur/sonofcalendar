Vue.component("calendarsettings", {
    template: `<div>
                   <div class="settingsbox">
                        <div class="settingsboxtitle">
                            <span>Anzeigeeinstellungen</span>
                        </div>
                        <div class="settingsoption">
                            <span class="settingslabel">
                                Layout
                            </span>
                            <span class="settingsvalue">
                                <select style="height: 14px;" v-model="settings.calendarSettings.calendarType">
                                    <option disabled value="">Bitte auswählen</option>
                                    <option value="days">Übersicht</option>
                                    <option value="events">Terminliste</option>
                                </select>
                            </span>
                        </div>
                        <div class="settingsoption">
                            <span class="settingslabel">
                                Angezeigte Tage
                             </span>
                            <span class="settingsvalue">
                                <input class="settingsinput" v-model="settings.calendarSettings.maxDays">
                            </span>
                        </div>
                   </div>

                   <div class="settingsbox">
                       <div class="settingsboxtitle">
                           <span>Meine Kalender</span>
                       </div>
                       <div class="settingsoption">
                           <table id="selectedCalendarsTable" border="0" style="text-align:left">
                              <tbody>
                                  <tr v-for="calendar in settings.calendarSettings.calendars">
                                      <td><img width="16" :src="typeimg(calendar.type)"></img></td>
                                      <td><input v-model="calendar.name" style="width: 100px;"/></td>
                                      <td><input v-model="calendar.url" style="width: 400px;"/></td>
                                      <td><color-picker-dropdown v-model="calendar.color" :initial="calendar.color"></color-picker-dropdown></td>
                                      <td><button @click="removeCalendar(calendar)">Entfernen</button></td>
                                  </tr>
                              </tbody>
                          </table>
                          <button @click="setCalendarType('google')">Google-Kalender hinzufügen</button>
                          <button @click="setCalendarType('icalendar')">Kalender per iCal-Url hinzufügen</button>
                       </div>
                       <div class="settingsoption" v-if="selectedCalendarType==='icalendar'">
                             <tbody>
                                 <tr>
                                     <td><img width="16" :src="typeimg(selectedCalendarType)"></img></td>
                                     <td><input v-model="newICal.name" style="width: 100px;"/></td>
                                     <td><input v-model="newICal.url" style="width: 400px;"/></td>
                                     <td><color-picker-dropdown v-model="newICal.color" :initial="newICal.color"></color-picker-dropdown></td>
                                     <td><button @click="addICalendar()">Hinzufügen</button></td>
                                 </tr>
                             </tbody>
                       </div>
                       <div class="settingsoption" v-if="selectedCalendarType==='google'">
                            <div v-for="calendar in availableCalendars">
                                 <button @click="addCalendar(calendar)">Hinzufügen</button>
                                 <span>{{ calendar.name }}</span>
                            </div>
                       </div>
                   </div>

                   <button class="savesettingsbtn" @click="save()">Speichern</button>
               </div>`,
    props: ['settings'],
    data: function(){
        return {
            newICal: {
                url: '',
                type: 'icalendar',
                name: '',
                color: '#000000'
            },
            availableCalendars: [],
            selectedCalendarType: ''
        }
    },
    methods:{
        setCalendarType: function(type){
          this.selectedCalendarType=type;
          if(type!=='icalendar'){
            this.loadAvailableCalendars();
          }
          else{
            this.newICal.id='';
            this.newICal.name='';
            this.newICal.description='';
          }
        },
        addCalendar: function(calendar){
          this.settings.calendarSettings.calendars.push(calendar);
          this.availableCalendars = $.grep(this.availableCalendars, function(e){
                                        return e.id != calendar.id;
                                     });
        },
        addICalendar: function(){
           this.settings.calendarSettings.calendars.push(this.newICal);
        },
        removeCalendar: function(calendar){
          this.settings.calendarSettings.calendars =
                        $.grep(this.settings.calendarSettings.calendars, function(e){
                            return e.url != calendar.url;
                        });
          this.availableCalendars.push(calendar);
        },
        loadAvailableCalendars: function(){
            var self = this;
            $.post("/restGetAvailableCalendarsOfType", {'type': self.selectedCalendarType}, function(data, textStatus) {
              self.availableCalendars = data;
            },"json");
        },
        typeimg: function(type){
            return "/img/"+type+"_icon_128.png";
        },
        save: function(){
            this.$parent.$options.methods.save(this.settings);
        },
        hideAddCalendarWizard: function(){
            this.overlay='hidden';
            this.selectedCalendarType = '';
        }
    }
});