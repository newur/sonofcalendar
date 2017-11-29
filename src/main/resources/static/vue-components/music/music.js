Vue.component("musicplayer", {
    template: `<div class="musicplayer">
                    <div class="musicplayer-search">
                        <input v-model="searchValue">
                        <button @click="search">Suchen</button>
                    </div>
                    <div class="musicplayer-search-results">

                    </div>
                    <div class="musicplayer-mini">
                        <span class="musicplayercontrol" @click="start()">START</span>
                        <span class="musicplayercontrol" @click="stop()">PAUSE</span>
                        <span class="musicplayercontrol" @click="stop()">STOP</span>
                        <span class="musicplayercontrol" @click="start()">PREV</span>
                        <span class="musicplayercontrol" @click="start()">NEXT</span>
                    </div>
               </div>`,
    data: function(){
        return {
            mopidy: '',
            searchValue: ''
        }
    },
    created: function(){
        this.mopidy = new Mopidy({webSocketUrl: "ws://localhost:6680/mopidy/ws/"});
        this.mopidy.on(console.log.bind(console));
    },
    methods: {
        start: function(){
            var trackDesc = function (track) {
                return track.name + " by " + track.artists[0].name +
                    " from " + track.album.name;
            };
            var queueAndPlay = function (mopidy, playlistNum, trackNum) {
                playlistNum = playlistNum || 0;
                trackNum = trackNum || 0;
                mopidy.playlists.getPlaylists().then(function (playlists) {
                    var playlist = playlists[playlistNum];
                    console.log("Loading playlist:", playlist.name);
                    return mopidy.tracklist.add(playlist.tracks).then(function (tlTracks) {
                        return mopidy.playback.play(tlTracks[trackNum]).then(function () {
                            return mopidy.playback.getCurrentTrack().then(function (track) {
                                console.log("Now playing:", trackDesc(track));
                            });
                        });
                    });
                })
                .catch(console.error.bind(console))
                .done();
            };
            this.mopidy.on("state:online", queueAndPlay(this.mopidy));
        },
        stop: function(){
            var stop = function(mopidy){
                mopidy.playback.stop;
            };
            this.mopidy.on("state:online", stop(this.mopidy));
        },
        search: function(){
            var search = function(mopidy, searchValue){
                mopidy.library.search({'any': [searchValue]}).done(showSearchResults);
            };
            var showSearchResults = function(searchResult){
                var albums = (searchResult[1]);
            };
            this.mopidy.on("state:online", search(this.mopidy,this.searchValue));
        }
    }
});