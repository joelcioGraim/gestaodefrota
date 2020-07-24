'use strict';

angular.module('app').controller('EventoCalendarCtrl', function ($scope, $rootScope, $uibModalInstance, toaster, CONFIG,
 dadoEventoCalendar) {

	/*Verifica se o o usuário está logado*/
    // if($rootScope.userDados.token == null){        
    //     $location.path("/");
    //     toaster.pop('info', 'Atenção', 'Favor entrar no sistema.', 5000); //exibe um alerta na tela
    // }

	var contexto = this;

  contexto.titulo = dadoEventoCalendar.title;
  contexto.eventos = dadoEventoCalendar.source.events; 

  contexto.sair = function () {
 	 $uibModalInstance.dismiss('cancel');
	};  
/*---------------------------------------- Paginação Grid --------------------------------------*/
    contexto.quantidade = 5;
    contexto.pageSize = contexto.quantidade;
    contexto.currentPage = 1;

  contexto.setItemsPerPage = function(num) {
    contexto.pageSize = num;
    contexto.currentPage = 1; //reseta para o primeiro
  };  	
});