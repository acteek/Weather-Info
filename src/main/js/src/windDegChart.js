import Vue from 'vue'
import VueCharts from 'vue-chartjs'



export default Vue.component('weed-deg-chart', {
  extends: VueCharts.Radar,
  data: function(){ return {
    directions: ['N', 'NNE', 'NE', 'ENE', 'E', 'ESE', 'SE', 'SSE', 'S', 'SSW', 'SW', 'WSW', 'W','WNW', 'NW', 'NNW']
  }},
  computed: {
    dataValues() { },
    labels() { return this.directions},
    datasets(){
    return [{
          fill: true,
          label: 'Направление ветра',
          backgroundColor: 'rgba(212, 132, 28, 0.2)',
          borderColor: 'rgba(212, 132, 28, 1)',
          borderWidth: 1 ,
          data: this.values()
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
    },
    counts: function(direction) {
      return this.$root.weedDegData.reduce((acc, elem) => {
     	if(elem === direction){return acc +1}
     	else {return acc}
     	},0)
     },
    values: function(){
     return this.directions.map((dr)=>{
       return this.counts(dr)
       })
    }
 },

  mounted () {
     this.render()
  }
})