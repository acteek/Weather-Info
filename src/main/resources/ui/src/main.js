import Vue from 'vue'
import VueCharts from 'vue-chartjs'
import 'jquery';
import Tether from 'tether';
import 'bootstrap/dist/js/bootstrap'
import VueResource from 'vue-resource';

Vue.use(VueResource);


Vue.component('temp-chart', {
  extends: VueCharts.Line,
  computed: {
    labels() { return this.$root.labels},
    datasets(){
    return [{
          fill: false,
          label: 'Температура °С',
          backgroundColor: 'rgba(255, 99, 132, 0.2)',
          borderColor: 'rgba(255,99,132,1)',
          borderWidth: 1 ,
          data: this.$root.tempData
        }]
    }
  },

  watch: {
    datasets: function (newData) {
//      console.log('Change Data Set')
      let chart = this._chart
      chart.data.datasets = newData
      chart.data.labels = this.labels,
      chart.update()
    },
    labels: function (newData) {
//      console.log('Change Data Set')
      let chart = this._chart
      chart.data.datasets = this.datasets
      chart.data.labels = newData,
      chart.update()
    }
  },

  methods: {
    render: function() {
         this.renderChart({
               labels: this.labels,
               datasets: this.datasets
          }, {responsive: true, maintainAspectRatio: false})
 }},

  mounted () {
     this.render()
  }
})






var vm = new Vue({
  el: '#app',
  data: function() { return {
    searchCity: 'москва', //TODO Убрать значение после отладки
    dateFrom: '2017-08-01T12:00', //TODO ограничения по времени
    dateTo: '2017-08-01T14:00',
    labels: [],
    tempData: []
  }},
  methods: {
    getMetrics: function() {
     this.labels = []
     this.tempData = []
     var tmpLabels = []
     var tmpTempData = []
  	 this.$http.get('metrics?city='+this.searchCity+'&date-from='+this.dateFrom+'&date-to='+this.dateTo)
  	    .then(response => {
//  	       console.log(response) //TODO Убрать значение после отладки
           response.body.metrics.forEach ( metric => {
             for (var key in metric) {
//               console.log("KEY" + key + " => " + metric[key].temp) //TODO Убрать значение после отладки
                tmpLabels.push(key)
                tmpTempData.push(metric[key].temp)
             }
           })
        })
      this.labels = tmpLabels
      this.tempData = tmpTempData
  	}
  }
})
