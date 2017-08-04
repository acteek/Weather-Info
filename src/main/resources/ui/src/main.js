import Vue from 'vue'
import 'jquery';
import Tether from 'tether';
import 'bootstrap/dist/js/bootstrap'
import VueResource from 'vue-resource';
import moment from "moment";
import VueMomentJS from "vue-momentjs";
import './temperatureChart.js'
import './humidityChart.js'
import './WindSpeedChart.js'
//TODO  тут нужно порефакторить !

Vue.use(VueResource);
Vue.use(VueMomentJS, moment);

var df = 'YYYY-MM-DDTHH:mm'
var currentDate = moment().format(df);
var tmpLabels = []
var tmpTempData = []
var humTempData = []
var weedSpeedTempData = []


var vm = new Vue({
  el: '#app',
  data: function() { return {
    searchCity: 'москва', //TODO Убрать значение после отладки
    dateMin: currentDate,
    dateMax: this.$moment(currentDate).add(5,'days').format(df),
    dateFrom: currentDate,
    dateTo: this.$moment(currentDate).add(3,'days').format(df),
    labels: [],
    tempData: [],
    humData: [],
    weedSpeedData: []
  }},
  methods: {
    getMetrics: function() {
     this.labels = []
     this.tempData = []
     this.humData = []
     this.weedSpeedData = []
     tmpLabels = []
     tmpTempData = []
     humTempData = []
     weedSpeedTempData = []
  	 this.$http.get('metrics?city='+this.searchCity+'&date-from='+this.dateFrom+'&date-to='+this.dateTo)
  	    .then(response => {
//  	       console.log(response) //TODO Убрать значение после отладки
           response.body.forEach ( metric => {
             for (var key in metric) {
//               console.log("KEY" + key + " => " + metric[key].temp) //TODO Убрать значение после отладки
                tmpLabels.push(key)
                tmpTempData.push(metric[key].temp)
                humTempData.push(metric[key].humidity)
                weedSpeedTempData.push(metric[key].wind_speed)
             }
           })
        })
      this.labels = tmpLabels
      this.tempData = tmpTempData
      this.humData = humTempData
      this.weedSpeedData = weedSpeedTempData

  	}
  }
})
