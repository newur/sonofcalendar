Vue.component("feed", {
    template: `<div id="feed">
                    <span class="feedTitle">{{feedItem.title}}</span></br>
                    <span class="feedText">{{feedItem.description}}</span>
               </div>`,
    props: ['settings'],
    data: function(){
        return {
           feedItem: '',
           feedItems: [],
           unshownFeedItems: []
        }
    },
    mounted: function(){
        this.getFeedItems();
        this.timerGetFeedItems = setInterval(self.getFeedItems,600000);
    },
    methods: {
        getFeedItems: function(){
            var self = this;
            $.getJSON( "https://api.rss2json.com/v1/api.json?rss_url=" + this.settings.rssUrl, function( data ) {
                if(data.items){
                    self.feedItems = data.items;
                    self.unshownFeedItems = self.feedItems.slice();
                    self.showNext();
                    setTimeout(self.showNext, self.settings.rssTransitionTime*1000);
                }
            });
        },
        showNext: function() {
            if (this.unshownFeedItems.length == 0) {
                this.unshownFeedItems = this.feedItems.slice();
            }
            this.feedItem = this.unshownFeedItems[0];
            this.unshownFeedItems.splice(0,1);
            setTimeout(this.showNext, this.settings.rssTransitionTime*1000);
        },
    }
});




