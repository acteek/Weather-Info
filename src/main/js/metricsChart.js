import Vue from 'vue'
import VueCharts from 'vue-chartjs'


export default Vue.component('metrics-chart', {
  extends: VueCharts.Line,
  props: ['labels', 'graph' ],
  computed: {
    datasets(){
    return [
        {
          fill: true,
          label: this.graph.label,
          backgroundColor: this.graph.color.background ,
          borderColor: this.graph.color.border,
          borderWidth: 1 ,
          data: this.graph.data
        },
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