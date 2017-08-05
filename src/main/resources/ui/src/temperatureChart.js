import Vue from 'vue'
import VueCharts from 'vue-chartjs'


export default Vue.component('temp-chart', {
  extends: VueCharts.Line,
  computed: {
    labels() { return this.$root.labels},
    datasets(){
    return [
        {
          fill: true,
          label: 'Температура °С',
          backgroundColor: 'rgba(255, 99, 132, 0.2)',
          borderColor: 'rgba(255, 60, 132, 1)',
          borderWidth: 1 ,
          data: this.$root.tempData
        },
        {
          fill: true,
          label: 'Min отклонение',
          backgroundColor: 'rgba(41, 194, 168, 0.1)',
          borderColor: 'rgba(41, 194, 168, 1)',
          borderWidth: 1 ,
          data: this.$root.tempMinData
        },
        {
          fill: true,
          label: 'Max отклонение',
          backgroundColor: 'rgba(222, 222, 49, 0.1)',
          borderColor: 'rgba(222, 222, 49, 1)',
          borderWidth: 1 ,
          data: this.$root.tempMaxData
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