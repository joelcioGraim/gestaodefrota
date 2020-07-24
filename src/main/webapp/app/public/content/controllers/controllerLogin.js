'use strict';

angular.module('app')

.controller('LoginCtrl', function ($scope, $rootScope, $http, $timeout, $location, toaster, CONFIG, serviceYdn ) {

  var contexto = this;
 
  contexto.exibeErro = false;

  contexto.imagePath = CONFIG;

  contexto.userLogado = {};

  $scope.$emit('menu', false); //Exibe o menu após o login

  contexto.usuario = {
    token: ''
  }

/*---------------------------------------- Funções e condições para logar --------------------------------------*/
  contexto.logar = function (user, form) {
  contexto.usuario = angular.copy(user);
  //contexto.formUsuario = form;

    if (contexto.usuario.login != "" &&
      contexto.usuario.senha != "") {
      var usuario = angular.toJson({usuario : contexto.usuario});
      $http.post( CONFIG.SERVIDOR + 'usuario/autenticar', usuario).success(function(data){
        if(data.autenticacaoDto != undefined){                 
                 $scope.$emit('menu', true); //Exibe o menu após o login      

                 /*variavel global*/                
                 $rootScope.userDados.idUsuario = data.autenticacaoDto.num;
                 $rootScope.userDados.nome = data.autenticacaoDto.nome;  
                 $rootScope.userDados.perfil = data.autenticacaoDto.perfil.idPerfil;                
                 $rootScope.userDados.token = data.autenticacaoDto.token;                 
                 
                 /* Dados do IndexDB */       
                 contexto.userLogado.token = data.autenticacaoDto.token;          
                 contexto.userLogado.nome = data.autenticacaoDto.nome;
                 contexto.userLogado.email = data.autenticacaoDto.email;                 
                 contexto.userLogado.perfil = data.autenticacaoDto.perfil;
                 contexto.userLogado.data = new Date().toString('dd-MM-yyyy');

                 /*Cria o BD*/
                 //serviceYdn.db.get('usuario', 'usuario').always(function(record) {
                 // if( record != undefined && (record.token != contexto.userLogado.token || record.data != contexto.userLogado.data)){
                    /*Apaga o usuário antigo*/
                 //   serviceYdn.db.clear('usuario');
                    /*Grava no BD o nome e token do usuário*/
                  //  serviceYdn.db.put('usuario',contexto.userLogado, 'usuario');                  
                 // }else{
                    /*Grava no BD o nome e token do usuário*/
                 //   serviceYdn.db.put('usuario',contexto.userLogado, 'usuario');
                 // }            

                //});
                 
                 $location.path('/home');

              }else{
                contexto.exibeErro = true;
                angular.copy({}, contexto.usuario);              
                form.$setPristine();  

                 $timeout(function () {
                      contexto.exibeErro = false;                      
                  },3000);            
              }
          

      }).error(function(data){
          toaster.pop('warning', 'Atenção', 'Servidor não respondeu.', 4000); //exibe um alerta na tela

      });  

    };

  };

});


