import Vue from 'vue'
import VueCharts from 'vue-chartjs'
import 'jquery';
import Tether from 'tether';
import 'bootstrap/dist/js/bootstrap'
import 'vue-resource'


Vue.component('line-chart', {
  extends: VueCharts.Line,
  mounted () {
    this.renderChart({
      labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
      datasets: [
        {
          fill: false,
          label: this.$root.message,
          backgroundColor: 'rgba(255, 99, 132, 0.2)',
          borderColor: 'rgba(255,99,132,1)',
          borderWidth: 1 ,
          data: [40, 39, 10, 40, 39, 80, 40]
        }
      ]
    }, {responsive: true, maintainAspectRatio: false})
  }
})

var vm = new Vue({
  el: '#app',
  data: {
    message: 'Hello'
  }
})
