'use strict';

angular.module('app').controller('RodapeCtrl', function ($scope, $rootScope, CONFIG) {
    var contexto = this;
    
    contexto.imagePath = CONFIG;  
    contexto.versaoPath = CONFIG;  
   
    
}).directive('mainRodape', function () {
    return {
        restrict: 'AE',  
        scope: true,
        templateUrl: 'app/public/content/directives/directiveRodape.html',
         
    };
});


