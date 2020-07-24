'use strict';

angular.module('app').controller('SaidaDateCtrl', function ($scope, $rootScope, $compile, $filter, CONFIG) {
  
 var data = this;

        $scope.model = [];       

        $('.input-group.saida.date').datepicker({
            todayBtn: "linked",
            language: "pt-BR",
            format: "dd/mm/yyyy",
            orientation: "top auto",
            autoclose: true,          
           
        }).on('changeDate', function (e) {
                    
            if (typeof e.date != 'undefined') {
                $scope.model[$scope.value] = e.date;           
            }
            else {
                $scope.model[$scope.value] = undefined;
            }

            /*Verifica para onde enviar o valor*/             
            $scope.$emit('dataSaida', e); //Envia a data selecionada
               
        });
        $('.input-group.saida.date').datepicker('setDate', new Date());
         
        //Recebe a data selecionado para ser exibida no formulário
        $scope.$on('SelectedDataSaida', function (event, date) {                
            $('.input-group.saida.date').datepicker('update', date);             
        });

         //Recebe a data selecionado para ser exibida no formulário
        $scope.$on('RefreshdDataSaida', function (event) {                
            $('.input-group.retorno.date').datepicker('setDate', new Date());          
        });
   
    
}).directive('saidaDate', function () {
    return {
        restrict: 'A',  
        scope: {
                model: "=model",
                value: '=value'
                },     
        templateUrl: 'app/public/content/directives/DatesFields/directiveDateSaida.html',
         
    };
});
