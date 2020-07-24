'use strict';
angular
    .module('app', ['ngResource', 'ngRoute', 'ngAnimate', 'ngTouch', 'ui.bootstrap',
     'toaster', 'colorpicker.module', 'wysiwyg.module', 'focus-if', 'ui.calendar'

    ]).config(function ($routeProvider, $httpProvider, $resourceProvider, $locationProvider) {
    	$locationProvider.html5Mode(false);
        $resourceProvider.defaults.stripTrailingSlashes = true;
        $httpProvider.defaults.headers.post['Content-Type'] = 'application/json; charset=UTF-8';     
               
        $routeProvider
            .when('/', {
                templateUrl: 'app/public/content/views/viewLogin.html',                
            })
            .when('/home', {
                templateUrl: 'app/public/content/views/viewHome.html',                
            })
            .when('/cadastro-veiculo', {
                templateUrl: 'app/public/content/views/viewVeiculo.html',                
            })
            .when('/cadastro-solicitacao', {
                templateUrl: 'app/public/content/views/viewSolicitacao.html',
            })
            .when('/cadastro-abastecimento', {
                templateUrl: 'app/public/content/views/viewAbastecimento.html',
            })
            .when('/cadastro-manutencao', {
                templateUrl: 'app/public/content/views/viewManutencao.html',
            })
            .when('/cadastro-usuario', {
                templateUrl: 'app/public/content/views/viewUsuario.html',                 
            })
            .when('/cadastro-posto', {
                templateUrl: 'app/public/content/views/viewPosto.html',
            })
            .when('/cadastro-oficina', {
                templateUrl: 'app/public/content/views/viewOficina.html',
            })
            .otherwise({
                redirectTo: '/'
            });
          
    }).constant('CONFIG', {
        VERSAO: "1.1",
        //SERVIDOR: "http://localhost:8080/gestaodefrota/", //Desenvolvimento
        SERVIDOR: "http://184.107.94.164:26509/gestaodefrota/",  //Remoto Produção
        /*PATHINPORT: "/public"*/   //Desenvolvimento
        PATHINPORT: "app/public"  //Produção
    }).controller('MainCtrl', function ($scope, $rootScope) {
         
        var contexto = this;

        /*Variável global com os dados do usuário logado*/
        $rootScope.$root.userDados =  { };

        /*Exibe ou esconde o menu*/
        $scope.$on('menu', function( event, data ){
           contexto.exibeMenu = data;
        });

        /*Exibe ou esconde o menu Administrativo*/
       $scope.$on('perfil', function( event, data ){
         $scope.$broadcast('perfilRoot', data);           
       });

    });

/*Constante utilizado pelo ngResource*/
//var $SERVICES_CONTEXT = "http://localhost:8080/gestaodefrota/";
var $SERVICES_CONTEXT = "http://184.107.94.164:26509/gestaodefrota/";  //Remoto Produção

