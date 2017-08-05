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
    weedSpeedData: []
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
//  	       console.log(response) //TODO Убрать значение после отладки
           response.body.forEach ( metric => {
             for (var key in metric) {
//               console.log("KEY" + key + " => " + metric[key].temp) //TODO Убрать значение после отладки
                tmp.labels.push(key)
                tmp.tempData.push(metric[key].temp)
                tmp.humData.push(metric[key].humidity)
                tmp.weedSpeedData.push(metric[key].wind_speed)
                tmp.tempMinData.push(metric[key].temp_min)
                tmp.tempMaxData.push(metric[key].temp_max)
             }
           })
        })
      this.labels = tmp.labels
      this.tempData = tmp.tempData
      this.humData = tmp.humData
      this.weedSpeedData = tmp.weedSpeedData
      this.tempMinData = tmp.tempMinData
      this.tempMaxData = tmp.tempMaxData
  	},

  	clearMetricData: function() {
  	  this.labels = []
      this.tempData = []
      this.humData = []
      this.weedSpeedData = []
      this.tempMaxData = []
      this.tempMinData = []
  	}
  },
  created: function() {
    //Первичная отрисовка
  	this.searchCity = 'Санкт-Петербург'
  	this.getMetrics()
  	},
})
