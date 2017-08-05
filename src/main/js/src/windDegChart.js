import Vue from 'vue'
import VueCharts from 'vue-chartjs'


/*
* Скорее всего это график не совсем достоверен.
* Не совсем понятно как построить розк ветром по имеющимся данным
*
*/

export default Vue.component('weed-deg-chart', {
  extends: VueCharts.Radar,
  computed: {
    labels() { return ['N','NNE', 'NE', 'ENE',  'E', 'ESE', 'SE', 'SSE',  'S',  'SSW','SW', 'WSW', 'W','WNW','NW' , 'NNW']},
    datasets(){
    return [{
          fill: true,
          label: 'Напрввление ветра',
          backgroundColor: 'rgba(212, 132, 28, 0.2)',
          borderColor: 'rgba(212, 132, 28, 1)',
          borderWidth: 1 ,
          data: this.$root.weedDegData
        }]
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