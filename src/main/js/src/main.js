import Vue from 'vue'
import 'jquery';
import Tether from 'tether';
import 'bootstrap/dist/js/bootstrap'
import VueResource from 'vue-resource';
import moment from 'moment';
import VueMomentJS from 'vue-momentjs';
import './temperatureChart.js'
import './humidityChart.js'
import './windSpeedChart.js'
import './windDegChart.js'
import './Statistic.js'
import './StatisticTempChart.js'
import {tmp, clearTempData} from './tempStorage.js'


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
    tempMaxData: [],
    tempMinData: [],
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
//TODO Можно отрефачить для использования в статистике
   getMetrics: function() {
     this.clearMetricData()
     clearTempData()
  	 this.$http.get('metrics?city='+this.searchCity+'&date-from='+this.dateFrom+'&date-to='+this.dateTo)
  	    .then(response => {
           response.body.forEach ( metric => {
             for (var key in metric) {
                tmp.labels.push(key)
                tmp.tempData.push(metric[key].temp)
                tmp.humData.push(metric[key].humidity)
                tmp.weedSpeedData.push(metric[key].wind_speed)
                tmp.tempMinData.push(metric[key].temp_min)
                tmp.tempMaxData.push(metric[key].temp_max)
                tmp.weedDegData.push(metric[key].wind_deg)
             }
           })
        })
      this.labels = tmp.labels
      this.tempData = tmp.tempData
      this.humData = tmp.humData
      this.weedSpeedData = tmp.weedSpeedData
      this.tempMinData = tmp.tempMinData
      this.tempMaxData = tmp.tempMaxData
      this.weedDegData = tmp.weedDegData
  	},

  	clearMetricData: function() {
  	  this.labels = []
      this.tempData = []
      this.humData = []
      this.weedSpeedData = []
      this.tempMaxData = []
      this.tempMinData = []
      this.weedDegData = []
  	}
  },
  created: function() {
    //Первичная отрисовка
  	this.searchCity = 'Санкт-Петербург'
  	this.getMetrics()
  	},
})
