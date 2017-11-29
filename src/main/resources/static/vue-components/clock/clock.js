Vue.component("clock", {
    template: `<div class="clock-wrapper">
                    <component :is="settings.clockType"></component>
               </div>`,
    props: ['settings']
});

Vue.component("digital", {
	template: `<div>
                   <p class="time">{{ time }}</p>
                   <p class="date">{{ date }}</p>
               </div>`,
	data: function() {
		return {
			date: '',
			time: ''
		}
	},
	created: function() {
        this.updateTime();
        this.timer = setInterval(this.updateTime, 300000)
    },
    methods: {

        updateTime: function() {
            var cd = new Date();
            this.time = this.zeroPadding(cd.getHours(), 2) + ':' + this.zeroPadding(cd.getMinutes(), 2);
            this.date = week[cd.getDay()] + ", " + this.zeroPadding(cd.getDate(), 2) + '.' + this.zeroPadding(cd.getMonth()+1, 2) + '.' + this.zeroPadding(cd.getFullYear(), 4);
        },
        zeroPadding: function(num, digit){
            var zero = '';
            for(var i = 0; i < digit; i++) {
                zero += '0';
            }
            return (zero + num).slice(-digit);
        }

    }
});

var week = ['Sonntag', 'Montag', 'Dienstag', 'Mittwoch', 'Donnerstag', 'Freitag', 'Samstag'];

Vue.component("analog", {
	template: `<canvas id="clock" width="200" height="200">
               </canvas>`,
    data: function() {
    		return {
    			clock: ''
    		}
    },
    created: function() {
            this.clock = new AnalogClock("clock");
            this.timer = setInterval(this.updateTime, 1000)
    },
    methods: {
        updateTime: function() {
            this.clock.draw();
        }
    }
});

function AnalogClock(clockId) {
  this.clockId = clockId;
  this.radius  = 0;

  // hour offset
  this.hourOffset = 0;

  // clock body
  this.bodyShadowColor   = "rgba(0,0,0,0.5)";
  this.bodyShadowOffsetX = 0.03;
  this.bodyShadowOffsetY = 0.03;
  this.bodyShadowBlur    = 0.06;

  // body dial
  this.dialColor         = 'rgb(60,60,60)';

  // clock hands
  this.handShadowColor   = 'rgba(0,0,0,0.3)';
  this.handShadowOffsetX = 0.03;
  this.handShadowOffsetY = 0.03;
  this.handShadowBlur    = 0.04;

	// clock colors
  this.hourHandColor     = 'rgb(0,0,0)';
  this.minuteHandColor   = 'rgb(0,0,0)';
  this.secondHandColor   = 'rgb(200,0,0)';

  // clock boss
  this.bossShadowColor   = "rgba(0,0,0,0.2)";
  this.bossShadowOffsetX = 0.02;
  this.bossShadowOffsetY = 0.02;
  this.bossShadowBlur    = 0.03;
};

AnalogClock.prototype.draw = function() {
  var clock = document.getElementById(this.clockId);
  if (clock) {
    var context = clock.getContext('2d');
    if (context) {
      this.radius = 0.75 * (Math.min(clock.width, clock.height) / 2);

      // clear canvas and set new origin
      context.clearRect(0, 0, clock.width, clock.height);
      context.save();
      context.setTransform(1, 0, 0, 1, 0, 0);
      context.translate(clock.width / 2, clock.height / 2);

      context.save();
      this.fillCircle(context, "rgb(255,255,255)", 0, 0, 1);
      context.restore();


      // draw dial
      for (var i = 0; i < 60; i++) {
        context.save();
        context.rotate(i * Math.PI / 30);
        if ((i % 15) == 0) {
          this.strokeLine(context, this.dialColor, 0.0, -1.0, 0.0, -0.70, 0.08);
        } else if ((i % 5) == 0) {
          this.strokeLine(context, this.dialColor, 0.0, -1.0, 0.0, -0.76, 0.08);
        } else {
          this.strokeLine(context, this.dialColor, 0.0, -1.0, 0.0, -0.92, 0.036);
        }
        context.restore();
      }

      // get current time
      var time    = new Date();
      var millis  = time.getMilliseconds() / 1000.0;
      var seconds = time.getSeconds();
      var minutes = time.getMinutes();
      var hours   = time.getHours() + this.hourOffset;

      // draw hour hand
      context.save();
      context.rotate(hours * Math.PI / 6 + minutes * Math.PI / 360);
      this.setShadow(context, this.handShadowColor, this.handShadowOffsetX, this.handShadowOffsetY, this.handShadowBlur);
      this.fillPolygon(context, this.hourHandColor, 0.0, -0.6,  0.065, -0.53, 0.065, 0.19, -0.065, 0.19, -0.065, -0.53);
      context.restore();

      // draw minute hand
      context.save();
      context.rotate(minutes * Math.PI / 30);
      this.setShadow(context, this.handShadowColor, this.handShadowOffsetX, this.handShadowOffsetY, this.handShadowBlur);
      this.fillPolygon(context, this.minuteHandColor, 0.0, -0.93,  0.045, -0.885, 0.045, 0.23, -0.045, 0.23, -0.045, -0.885);
      context.restore();

      // draw second hand
      context.save();
      context.rotate(seconds * Math.PI / 30);
      this.setShadow(context, this.handShadowColor, this.handShadowOffsetX, this.handShadowOffsetY, this.handShadowBlur);
      this.fillPolygon(context, this.secondHandColor, -0.006, -0.92, 0.006, -0.92, 0.028, 0.23, -0.028, 0.23);
      context.restore();
    }
  }
};

AnalogClock.prototype.setShadow = function(context, color, offsetX, offsetY, blur) {
  if (color) {
  	context.shadowColor   = color;
  	context.shadowOffsetX = this.radius * offsetX;
  	context.shadowOffsetY = this.radius * offsetY;
  	context.shadowBlur    = this.radius * blur;
  }
};

AnalogClock.prototype.fillCircle = function(context, color, x, y, radius) {
  if (color) {
    context.beginPath();
    context.fillStyle = color;
    context.arc(x * this.radius, y * this.radius, radius * this.radius, 0, 2 * Math.PI, true);
    context.fill();
  }
};

AnalogClock.prototype.strokeLine = function(context, color, x1, y1, x2, y2, width) {
  if (color) {
	  context.beginPath();
	  context.strokeStyle = color;
	  context.moveTo(x1 * this.radius, y1 * this.radius);
	  context.lineTo(x2 * this.radius, y2 * this.radius);
	  context.lineWidth = width * this.radius;
	  context.stroke();
  }
};

AnalogClock.prototype.fillPolygon = function(context, color, x1, y1, x2, y2, x3, y3, x4, y4, x5, y5) {
  if (color) {
	  context.beginPath();
	  context.fillStyle = color;
	  context.moveTo(x1 * this.radius, y1 * this.radius);
	  context.lineTo(x2 * this.radius, y2 * this.radius);
	  context.lineTo(x3 * this.radius, y3 * this.radius);
	  context.lineTo(x4 * this.radius, y4 * this.radius);
	  if ((x5 != undefined) && (y5 != undefined)) {
	    context.lineTo(x5 * this.radius, y5 * this.radius);
	  }
	  context.lineTo(x1 * this.radius, y1 * this.radius);
	  context.fill();
  }
};


