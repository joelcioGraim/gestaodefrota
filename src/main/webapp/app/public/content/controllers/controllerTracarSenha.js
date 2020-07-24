'use strict';

angular.module('app').controller('TrocarSenhaCtrl', function ($scope, $rootScope, $uibModalInstance, toaster, CONFIG, $http,
	userLogado, serviceUsuario) {

	/*Verifica se o o usuário está logado*/
    if($rootScope.userDados.token == null){        
        $location.path("/");
        toaster.pop('info', 'Atenção', 'Favor entrar no sistema.', 5000); //exibe um alerta na tela
    }

	var contexto = this;

	contexto.dadosTroca = {};

    contexto.cancelar = function () {
   	 $uibModalInstance.dismiss('cancel');
  	};

  	contexto.usuario = {};     	

  	contexto.atualizar = function (usuario) {

  		 if(usuario != null){
  			contexto.usuario.token = userLogado.token;
  		 	contexto.usuario.idUsuario = userLogado.idUsuario;

	  		var usuario = angular.toJson({usuario : contexto.usuario});

	      	$http.post( CONFIG.SERVIDOR + 'usuario/trocarSenha', usuario).success(function(data){

	      		$uibModalInstance.close();
  				toaster.pop(data.validationMessage.id, 'Atenção!' , data.validationMessage.message, 4000); //exibe um alerta na tela
  			
  			}).error(function(data){

          		toaster.pop('error', 'Atenção', 'Servidor não respodeu.', 4000); //exibe um alerta na tela

      		});  
  		}
   	 
  	};
});