'use strict';

angular.module('app').controller('HomeCtrl', function ($scope, $rootScope, $location, $compile, toaster, $filter,
  $uibModal, serviceSolicitacao ) {
    
    /*Verifica se o o usuário está logado*/
    // if($rootScope.userDados.token == null){        
    //     $location.path("/");
    //     toaster.pop('info', 'Atenção', 'Favor entrar no sistema.', 5000); //exibe um alerta na tela
    // }

    var contexto = this;

    
    $scope.$emit('menu', true); //Exibe o menu após o login

    contexto.usuarios = [];

    contexto.usuarioLogado =  $rootScope.userDados.nome;  

    $scope.$emit('perfil', $rootScope.userDados.perfil); 

/*---------------------------------------- Dados Cadastrados --------------------------------------*/
  /*  Solicitações
    contexto.solicitacao = new serviceSolicitacao();
    contexto.solicitacoes = [];
     function buscarTodasSolicitacoes() {
     
          serviceSolicitacao.query(function (solicitacoes) {
          contexto.solicitacoes = solicitacoes; 
          console.log("Solicitaçoes: "+contexto.solicitacoes);
          },
          function (erro) {
              toaster.pop('warning', 'Atenção', 'Não foi possível carregar as solicitações cadastrados.', 4000); //exibe um alerta na tela
          });
        };

    buscarTodasSolicitacoes();    


---------------------------------------- Funções Calendario --------------------------------------
    var date = new Date();
    var d = date.getDate();
    var m = date.getMonth();
    var y = date.getFullYear();


     event source that contains custom events on the scope 
    contexto.events = [
      // { title: 'All Day Event', start: new Date(y, m, 1) },
      { title: '{{contexto.solicitacoes.solicitante}}', start: new Date(y, m, d - 1), end: new Date(y, m, d - 2) }
      // { title: 'Birthday Party', start: new Date(y, m, d + 1, 19, 0), end: new Date(y, m, d + 1, 22, 30), allDay: true }
      
    ];

     Modal com detalhes do evento clicado 
    contexto.alertOnEventClick = function( date, jsEvent, view){
        contexto.open(date);
    };

     Render Tooltip 
    contexto.eventRender = function (event, element, view) {
        element.attr({
            'tooltip': event.title,
            'tooltip-append-to-body': true
        });
        $compile(element)(contexto);
    };

     config object 
    contexto.uiConfig = {
        calendar: {
            height: 450,
            editable: true,
            header: {
                left: 'month basicWeek basicDay',
                center: 'title',
                right: 'today prev,next'
            },
            eventRender: contexto.eventRender,
            eventClick: contexto.alertOnEventClick
        }   
    };
     carrega o array de eventos
    contexto.eventSources = [contexto.events];

   
----------------------------------------- Modal Evento ---------------------------------------
  contexto.open = function (date) {

    var modalInstance = $uibModal.open({
      animation: true,
      templateUrl: 'app/public/content/views/viewEventoCalendar.html',   
      controller: 'EventoCalendarCtrl as ctrl', 
      size: 'lg',
      resolve: {
        dadoEventoCalendar: function () {
          return date;
        }
      }
    });   
  }; 

  contexto.sair = function () {
        
  };*/

/*---------------------------------------- Paginação Grid --------------------------------------*/
    contexto.quantidade = 5;
    contexto.pageSize = contexto.quantidade;
    contexto.currentPage = 1;

  contexto.setItemsPerPage = function(num) {
    contexto.pageSize = num;
    contexto.currentPage = 1; //reseta para o primeiro
  };
});
