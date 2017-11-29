Vue.component("photos", {
    template: `<div class="bgvue">
                    <img id="bottom"/>
                    <img id="top"/>
               </div>`,
    props: ['settings'],
    data: function(){
        return {
            topImgActive: '',
            images: [],
            unshownImages: []
        }
    },
    mounted: function(){
        var self = this;
        $.getJSON( "/restGetPhotoUrls", function( data ) {
            self.images=data;
            self.unshownImages = self.images.slice();
            self.showFirst();
            setTimeout(self.showNext, self.settings.photoTransitionTime*1000);
            self.timerReloadUrls = setInterval(self.getPhotoUrls,300000)
        });
    },
    methods: {
        getPhotoUrls: function(){
            var self = this;
            $.getJSON( "/restGetPhotoUrls", function( data ) {
                self.images=data;
                self.unshownImages = self.images.slice();
            });
        },
        showFirst: function() {
            var self = this;
            $( "#top" ).fadeOut( 1, function() {
                topImgActive = false;
                document.getElementById('bottom').src = self.nextPicture();
                document.getElementById('top').src = self.nextPicture();
            });
        },
        showNext: function() {
            var self = this;
            setTimeout(self.showNext, self.settings.photoTransitionTime*1000);
            if(!topImgActive){
                $( "#top" ).fadeIn( 1000, function() {
                    document.getElementById('bottom').src = self.nextPicture();
                });
                topImgActive = true;
            }
            else {
                $( "#top" ).fadeOut( 1000, function() {
                    document.getElementById('top').src = self.nextPicture();
                });
                topImgActive = false;
            }
        },
        nextPicture: function(){
            if (this.unshownImages.length == 0) {
                this.unshownImages = this.images.slice();
            }
            var randomInt = Math.floor(Math.random() * this.unshownImages.length);
            nextPictureUrl = this.unshownImages[randomInt];
            this.unshownImages.splice(randomInt,1);
            return nextPictureUrl+"=s0";
        }
    }
});