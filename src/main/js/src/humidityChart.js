import Vue from 'vue'
import VueCharts from 'vue-chartjs'


export default Vue.component('humidity-chart', {
  extends: VueCharts.Line,
  computed: {
    labels() { return this.$root.labels},
    datasets(){
    return [{
          fill: true,
          label: 'Влажность воздуха %',
          backgroundColor: 'rgba(99, 255, 132, 0.2)',
          borderColor: 'rgba(99, 255, 132, 1)',
          borderWidth: 1 ,
          data: this.$root.humData
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