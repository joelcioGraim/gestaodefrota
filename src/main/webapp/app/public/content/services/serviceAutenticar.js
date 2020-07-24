'use strict';
angular.module('app')
    .factory('serviceAutenticar', function ($rootScope, $http, $location, serviceYdn) {
    	var dados = {};
    	var _autenticaUser = function(){
	   /* Verifica se o usuário está logado, se não tiver o redicionará apara a tela de login */
	    serviceYdn.db.get('usuario', 'usuario').always(function(record) {
	      if( record != undefined && (record.idUsuario !=  $rootScope.userDados.idUsuario || 
	      	record.data != new Date().toString('dd-MM-yyyy'))){
	        /*Apaga o usuário antigo*/
	        //serviceYdn.db.clear('usuario');
	        //$location.path("/")  
	        dados = null;
	        return dados;             
	      }else{
	      	 dados.usuario = record;  
	      	return dados;
	      }
	    });       
	};

    return {
    autenticaUser: _autenticaUser,
    dados: dados    
    };

});