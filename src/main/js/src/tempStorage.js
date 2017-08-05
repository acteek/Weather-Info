/**
* Темповое хранилище нужно для что бы корректно отрисовывать графики.
* Так как при итеративном заполеннии основной коллекции график падает с ошибкой.
*
**/

export var tmp = {
   labels: [],
   tempData: [],
   tempMinData: [],
   tempMaxData: [],
   humData: [],
   weedSpeedData: [],
   weedDegData: []
}

export function clearTempData() {
   for (var key in tmp) {
     tmp[key] = []
   }
}