'use strict';

angular.module('app').controller('MenuCtrl', function ($scope, $rootScope, $uibModal, $log, CONFIG, $templateCache) {
    var contexto = this;
    
   contexto.imagePath = CONFIG;     
   
    /*Exibe ou esconde o menu Administrativo*/
   $scope.$on('perfilRoot', function( event, data ){
        contexto.perfil = data;
   });  

   contexto.open = function () {

    var modalInstance = $uibModal.open({
      animation: true,
      templateUrl: 'app/public/content/views/viewTrocarSenha.html',   
      controller: 'TrocarSenhaCtrl as ctrl', 
      size: 'sm',
      resolve: {
        userLogado: function () {
          return $rootScope.userDados;
        }
      }
    });   
  }; 

  contexto.sair = function () {
    $rootScope.userDados = { };    
  };
    
}).directive('mainMenu', function () {
    return {
        restrict: 'AE',  
        scope: true,
        templateUrl: 'app/public/content/directives/directiveMenu.html',
         
    };
});
