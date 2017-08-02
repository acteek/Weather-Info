
// const timeToUpdate = 10000

// const Conversations = Vue.extend({
// 	name: 'Conversations',
// 	data: function() {
// 		return {
// 			messages: []
// 		} 
// 	},
// 	methods: {
// 		deleteConv: function(accountId,cid,index){
// 			this.$http.post('/assignee/remove/account/'+accountId+'/cid/'+cid)
// 			this.messages.splice(index, 1)	
// 		},
// 		getAssignee: function(){
// 			this.$http.get('/assignee/response.json')
// 			.then(response => {
// 				this.messages = response.body
// 			});
// 			setTimeout(this.getAssignee, timeToUpdate)
// 		},
// 		setStyle: function(state){
// 			switch (state) {
// 				case 'Assigne': return 'success'
// 				break
// 				case 'ExpectAssigne': return 'info'
// 				break
// 				case 'ExpectConversation': return 'warning'
// 				break
// 				default: return 'danger'
// 			}
// 		}
// 	},
// 	created: function(){
// 		this.getAssignee()
// 	},
// 	computed: {
// 		filtermessages(){
// 			return this.messages.filter((ms)=>{
// 				return Object.values(ms).join('').toLowerCase().includes(this.$root.searchMessage.toLowerCase())	
// 			})
// 		}
// 	},
// 	template:
// 	'<div v-if="messages.length!=0">'+
// 	'<table class="table table-hover table-bordered table-responsive">'+
// 	'<thead ><tr class="active"><th>Cid</th><th>Аккаунт</th><th>Статус</th><th>Собеседник</th><th>Оператор</th><th></th></tr></thead>' +
// 	'<tbody v-for="(ms,index) in filtermessages">' +
// 	'<tr v-bind:class="setStyle(ms.state)" v-bind:id ="ms.conversationId"  >' +
// 	'<td>{{ms.conversationId}}</td><td>{{ms.accountId}}</td><td>{{ms.state}}</td><td>{{ms.discourserId}}</td><td>{{ms.employeeId}}</td>' +
// 	'<td><button v-on:click="deleteConv(ms.accountId,ms.conversationId,index)" type="button" class="btn btn-danger btn-sm" >Закрыть</button></td>' +
// 	'</tr>' +
// 	'</tbody>'+
// 	'</table>'+
// 	'</div>'+
// 	'<div v-else>'+
// 	'<div class="alert alert-danger"><strong>Ошибка!</strong> Нет активных обращений, пробую получить данные</div>'+
// 	'<div class="progress"><div class="progress-bar progress-bar-striped active"  style="width:100%"></div></div>'+
// 	'</div>'

// })

// const Overview = Vue.extend({
// 	name: 'Overview',
// 	data: function(){
// 		return {
// 			countAccounts: 0,
// 			countAssignee: 0
// 		}
// 	},
// 	methods: {
// 		getAssigneeCounts: function(){
// 			this.$http.get('/assignee/response.json')
// 			.then(response => {
// 				this.countAssignee = response.body.length
// 			})
// 			setTimeout(this.getAssigneeCounts, timeToUpdate)
// 		},
// 		getAccountsCounts: function(){
// 			this.$http.get('/account/response.json')
// 			.then(response => {
// 				this.countAccounts = response.body.length
// 			})
// 			setTimeout(this.getAccountsCounts, timeToUpdate)
// 		}
// 	},

// 	created: function(){
// 		this.getAssigneeCounts();
// 		this.getAccountsCounts()

// 	},
// 	template:
// 	'<div class="panel panel-default">'+
// 	'<div class="panel-heading">Статистика Core-App</div>'+
// 	'<div class="panel-body">'+
// 	'<div class="alert alert-info">Активных аккаунтов <strong>{{countAccounts}}</strong></div>'+
// 	'<div class="alert alert-info">Активных обращений <strong>{{countAssignee}}</strong></div>'+
// 	'</div>'+
// 	'</div>'
// })


/**
Docs

http://vue-chartjs.org/#/home?id=introduction
http://www.chartjs.org/docs/latest/
http://vue-charts.hchspersonal.tk/line

**/


Vue.component('line-chart', {
  extends: VueChartJs.Line,
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
    message: 'Hello World'
  }
})