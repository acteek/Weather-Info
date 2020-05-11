import Vue from 'vue'
import 'jquery'
import Popper from 'popper.js'
import Tether from 'tether'
import 'bootstrap/dist/js/bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import VueResource from 'vue-resource'
import moment from 'moment'
import VueMomentJS from 'vue-momentjs'
import './metricsChart.js'

window.Popper = Popper
Vue.use( VueResource, VueMomentJS, moment);

var vm = new Vue({
  el: '#app',
  data: function() { return {
    targetCity: '',
    cities: ['Berlin', 'Saint Petersburg', 'Moscow', 'Penza', 'Barcelona'],
    period: 3,
    labels: [],
    tempGraph: {
      label: 'Temperature °С',
      data:[],
      color: {
        background: 'rgba(255, 99, 132, 0.2)',
        border: 'rgba(255, 60, 132, 1)',
      }
    },
    humGraph: {
      label: 'Air humidity %',
      data:[],
      color: {
        background: 'rgba(99, 255, 132, 0.2)',
        border: 'rgba(99, 255, 132, 1)',
      }
    },
    windGraph: {
      label: 'Wind speed m/s',
      data:[],
      color: {
        background: 'rgba(99, 132, 255, 0.2)',
        border: 'rgba(99, 123, 255, 1)',
      }
    }
  }},
  computed: {
    from() {
     return moment().unix()
    },
    to(){
    return moment().add(this.period,'days').unix()
    }
  },
  methods: {
   updateMetrics: function() {
     this.clearMetricData()
  	 this.$http.get('metrics?city='+ this.targetCity+'&from='+this.from+'&to='+this.to)
  	    .then(response => {
           response.body.forEach ( metric => {
              var label = moment.unix(metric.time).format('MMM Do, HH:mm')
              this.labels.push(label)
              this.tempGraph.data.push(metric.temp)
              this.humGraph.data.push(metric.humidity)
              this.windGraph.data.push(metric.windSpeed)
           })
        })
  	},

  	clearMetricData: function() {
  	  this.labels = []
      this.tempGraph.data = []
      this.humGraph.data = []
      this.windGraph.data = []
  	}
  },
   watch: {
     targetCity: 'updateMetrics',
     period: 'updateMetrics',

   },
   created: function(){
     this.targetCity = this.cities[1]
     this.updateMetrics()

   },
})
