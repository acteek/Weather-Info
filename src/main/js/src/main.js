import Vue from 'vue'
import 'jquery'
import Popper from 'popper.js'
import Tether from 'tether'
import 'bootstrap/dist/js/bootstrap'
import VueResource from 'vue-resource'
import moment from 'moment'
import VueMomentJS from 'vue-momentjs'
import './temperatureChart.js'
import './humidityChart.js'
import './windSpeedChart.js'
import './windDegChart.js'
import {tmp, clearTempData} from './tempStorage.js'

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
    tempData: [],
    humData: [],
    weedSpeedData: [],
    weedDegData: []
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
     clearTempData()
  	 this.$http.get('metrics?city='+this.searchCity+'&date-from='+this.dateFrom+'&date-to='+this.dateTo)
  	    .then(response => {
           response.body.forEach ( metric => {
              var label = moment.unix(metric.time).format('MMM Do, HH:mm')
                tmp.labels.push(label)
                tmp.tempData.push(metric.temp)
                tmp.humData.push(metric.humidity)
                tmp.weedSpeedData.push(metric.windSpeed)
                tmp.weedDegData.push(metric.windDirection)
           })
        })
      this.labels = tmp.labels
      this.tempData = tmp.tempData
      this.humData = tmp.humData
      this.weedSpeedData = tmp.weedSpeedData
      this.weedDegData = tmp.weedDegData
  	},

  	clearMetricData: function() {
  	  this.labels = []
      this.tempData = []
      this.humData = []
      this.weedSpeedData = []
      this.weedDegData = []
  	}
  },
  created: function() {
    //Первичная отрисовка
  	this.searchCity = 'Санкт-Петербург'
  	this.getMetrics()
  	},
})
