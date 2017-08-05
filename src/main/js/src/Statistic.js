import Vue from 'vue'
import VueCharts from 'vue-chartjs'


export default Vue.component('statistic', {
  computed: {
    maxTemp(){return this.maxMetric(this.$root.tempData)},
    minTemp(){return this.minMetric(this.$root.tempData)},
    avgTemp(){return this.avgMetric(this.$root.tempData)},

    maxHum(){return this.maxMetric(this.$root.humData) },
    minHum(){return this.minMetric(this.$root.humData)},
    avgHum(){return this.avgMetric(this.$root.humData)},

    maxWeed(){return this.maxMetric(this.$root.weedSpeedData)},
    minWeed(){return this.minMetric(this.$root.weedSpeedData)},
    avgWeed(){return this.avgMetric(this.$root.weedSpeedData)},

  },
  methods: {
    maxMetric: function(arr) {
      if (arr.length ==0) {return 0 }
      else {
     return Math.max.apply(null, arr)
           }
     },
    minMetric: function(arr) {
      if (arr.length ==0) {return 0 }
      else {
     return Math.min.apply(null, arr)
      }
     },
    avgMetric: function(arr) {
     if (arr.length ==0) {return 0 }
     else {
       var sum = arr.reduce(function (a, b) { return a + b })
       var avg = (sum / arr.length).toFixed(2)
       return avg
     }
    }
  },

  template:
  '<div>'+
  	'<table class="table table-striped">'+
  	'<thead>' +
  	'<tr><th>Метрика</th><th>Минимум</th><th>Максимум</th><th>Среднее</th></tr>' +
  	'</thead>' +
  	'<tbody>' +
  	'<tr><td>Температура</td><td>{{minTemp}}</td><td>{{maxTemp}}</td><td>{{avgTemp}}</td></tr>' +
  	'<tr><td>Влажность воздуха</td><td>{{minHum}}</td><td>{{maxHum}}</td><td>{{avgHum}}</td></tr>' +
  	'<tr><td>Скорость ветра</td><td>{{minWeed}}</td><td>{{maxWeed}}</td><td>{{avgWeed}}</td></tr>' +
  	'</tbody>'+
  	'</table>'+
  '</div>'

})