import Vue from 'vue'
import 'jquery'
import Popper from 'popper.js'
import Tether from 'tether'
import 'bootstrap/dist/js/bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import VueResource from 'vue-resource'
import moment from 'moment'
import VueMomentJS from 'vue-momentjs'
import './metricsChart.js'

window.Popper = Popper

Vue.use(VueResource);
Vue.use(VueMomentJS, moment);

const df = 'YYYY-MM-DDTHH:mm'
var currentDate = moment().format(df) ;


var vm = new Vue({
  el: '#app',
  data: function() { return {
    searchCity: '',
    dateFrom: currentDate,
    dateTo: this.$moment(currentDate).add(3,'days').format(df),
    labels: [],
    tempGraph: {
      label: 'Температура °С',
      data:[],
      color: {
        background: 'rgba(255, 99, 132, 0.2)',
        border: 'rgba(255, 60, 132, 1)',
      }
    },
    humGraph: {
      label: 'Влажность воздуха %',
      data:[],
      color: {
        background: 'rgba(99, 255, 132, 0.2)',
        border: 'rgba(99, 255, 132, 1)',
      }
    },
    windGraph: {
      label: 'Скорость ветра m/s',
      data:[],
      color: {
        background: 'rgba(99, 132, 255, 0.2)',
        border: 'rgba(99, 123, 255, 1)',
      }
    }
  }},
  computed: {

    dateMin() {
     return currentDate
    },
    dateMax() {
     return this.$moment(currentDate).add(5,'days').format(df)
    }
  },
  methods: {
   getMetrics: function() {
     this.clearMetricData()
  	 this.$http.get('metrics?city='+this.searchCity+'&date-from='+this.dateFrom+'&date-to='+this.dateTo)
  	    .then(response => {
           response.body.forEach ( metric => {
              var label = moment.unix(metric.time).format('MMM Do, HH:mm')
              this.labels.push(label)
              this.tempGraph.data.push(metric.temp)
              this.humGraph.data.push(metric.humidity)
              this.windGraph.data.push(metric.windSpeed)
           })
        })
  	},

  	clearMetricData: function() {
  	  this.labels = []
      this.tempGraph.data = []
      this.humGraph.data = []
      this.windGraph.data = []
  	}
  },
  created: function() {
    //Первичная отрисовка
  	this.searchCity = 'Санкт-Петербург'
  	this.getMetrics()
  	},
})
