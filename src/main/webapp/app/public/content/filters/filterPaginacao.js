'use strict';

angular.module('app').filter('filterPaginacao', function (){
	return function(data, start){
		return data.slice(start);
	}

});


