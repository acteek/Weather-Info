import Vue from 'vue'
import VueCharts from 'vue-chartjs'
import {tmp, clearTempData} from './tempStorage.js'

export default Vue.component('stat-temp-chart', {
  extends: VueCharts.Bar,
  data: function(){
  	return {
  	 city: 'Москва',
  	 cityMetrics: []
  	}
  },

  computed: {
    labels() { return this.$root.labels},
    datasets(){
    return [
        {
          fill: true,
          label: 'Температура '+ this.$root.searchCity ,
          backgroundColor: 'rgba(135, 50, 117, 0.2)',
          borderColor: 'rgba(135, 50, 117, 1)',
          borderWidth: 1 ,
          data: this.$root.tempData
        },
        {
          fill: true,
          label: 'Температура '+ this.city ,
          backgroundColor: 'rgba(25, 138, 128, 0.1)',
          borderColor: 'rgba(25, 138, 128, 1)',
          borderWidth: 1 ,
          data: this.cityMetrics
        }
     ]
    }
  },

  watch: {
    datasets: function (newData) {
      let chart = this._chart
      chart.data.datasets = newData
      chart.data.labels = this.labels,
      chart.update()
    },
    labels: function (newData) {
      this.getMetrics()
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
    },

    getMetrics: function() {
      tmp.statTempData = []
   	 this.$http.get('metrics?city='+this.city+'&date-from='+this.$root.dateFrom+'&date-to='+this.$root.dateTo)
   	    .then(response => {
            response.body.forEach ( metric => {
              for (var key in metric) {
                 tmp.statTempData.push(metric[key].temp)
              }
            })
         })
       this.cityMetrics = tmp.statTempData
   	},

 },

  mounted () {
     this.getMetrics()
     this.render()
  }
})