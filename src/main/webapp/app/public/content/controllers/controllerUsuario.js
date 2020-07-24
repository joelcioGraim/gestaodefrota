'use strict';

angular.module('app').controller('UsuarioCtrl', function ($scope, $rootScope, $filter, toaster, $location, serviceUsuario,
  servicePerfil, serviceYdn) {

  /*Verifica se o o usuário está logado*/
    /*if($rootScope.userDados.token == null){        
        $location.path("/");
        toaster.pop('info', 'Atenção', 'Favor entrar no sistema.', 5000); //exibe um alerta na tela
    }*/
  
    var contexto = this;
    contexto.itensNaPagina = [];
    $scope.$emit('menu', true); //Exibe o menu após o login

/*---------------------------------------- Funções Combobox --------------------------------------*/
    /*Combobox perfil de usuário*/
    contexto.perfil = new servicePerfil();
    contexto.perfis = [];
     function buscarTodosPerfis() {
          servicePerfil.query(function (perfis) {
          contexto.perfis = perfis;
          },
          function (erro) {
              toaster.pop('warning', 'Atenção', 'Não foi possível carregar os tipos de perfil.', 4000); //exibe um alerta na tela
          });
        };

    buscarTodosPerfis();

/*---------------------------------------- Funções Rest --------------------------------------*/
    contexto.exibeSenha = true;
    contexto.usuario = new serviceUsuario();
    contexto.usuarios = [];   

    /*Limpa os campos do formulário*/
    contexto.limparCampos = function (form, usuario){
    
     angular.copy({}, usuario);
     contexto.confirmaSenha = '';     
      if(form != undefined ){ 
        form.$setPristine();
      }    
      contexto.exibeSenha = true; 
      buscarTodos()
    };

    /*Traz todos os ativos*/
    function buscarTodos() {
        serviceUsuario.query(
            function (usuarios) {
              contexto.usuarios = usuarios;
              contexto.itensNa(usuarios); // Indica os objetos da paginação inicial
            },

            function (erro) {
            	//Trata usuário sem token
              if(erro.status == 401){
            	  toaster.pop(erro.data.validationMessage.id, 'Atenção', erro.data.validationMessage.message, 4000); //exibe um alerta na tela
            	  $location.path("/");
              }else{
            	  toaster.pop('warning', 'Atenção', 'Não foi possível carregar o usuário.', 4000); //exibe um alerta na tela
              }
            }
        );
    };

    buscarTodos();//chamada da função ao carregar a pagina

    /*Salvar e atualizar*/
    contexto.salvar = function () {
        contexto.usuario.idUsuario > 0 ? atualizar() : novo();
    };

    /*Cadastrar*/
    function novo() {

        console.log(contexto.usuario);

        contexto.usuario.$save().then(function () {
            contexto.usuario = new serviceUsuario();              
            contexto.limparCampos();    
             toaster.pop('success', 'Atenção', 'Cadastro realizado com sucesso.', 4000); //exibe um alerta na tela       
        }).catch( function (erro) {
           toaster.pop('error', 'Atenção', 'Erro ao cadastrar o usuário.', 4000); //exibe um alerta na tela
        });

    };

    /*Atualizar*/
    function atualizar() {

        contexto.usuario.$update({params: contexto.usuario.idUsuario},
            function () {
                contexto.usuario = new serviceUsuario();                   
                contexto.limparCampos();
                toaster.pop('success', 'Atenção', 'Cadastro atualizado com sucesso.', 4000); //exibe um alerta na tela
            },

            function (erro) {
              toaster.pop('error', 'Atenção', 'Erro ao atualizar o usuário.', 4000); //exibe um alerta na tela
            }
        );

    };

    /*Editar*/
    contexto.edit = function (usuario) {
        contexto.usuario = usuario;
        contexto.confirmaSenha = usuario.senha;
        contexto.exibeSenha = false;
    };
    /*---------------------------------------- Paginação Grid --------------------------------------*/
    
    contexto.quantidade = 2;
    contexto.pageSize = contexto.quantidade;
    contexto.currentPage = 1;

  contexto.setItemsPerPage = function(num) {
    contexto.pageSize = num;
    contexto.currentPage = 1; //reseta para o primeiro
  };  
  
  /**
  * Função responsável em trazer todos os objetos da referida paginação.
  * Ao mudar de paginação os objetos mudam!
  * Isso serve para remoção ou edição de todos de uma paginação, comparando-se com o objeto original em um laço for.
  * OBS: Ao pegar o usuários a primeira vez está função e chamada!
  **/
  
  contexto.itensNa = function(valor){
	  var testar = {};
	  //console.log("Todo Objeto: "+valor);
	  if(contexto.currentPage != 1){
		  console.log("Objeto d pg: "+$filter('filterPaginacao')(valor, (contexto.currentPage - 1) * contexto.pageSize));
		  testar = $filter('filterPaginacao')(valor, ((contexto.currentPage - 1) * contexto.pageSize)-contexto.pageSize);
	  }else{
		  console.log("Objeto d pg: "+$filter('limitTo')(valor, contexto.pageSize));  
		  testar = $filter('limitTo')(valor, contexto.pageSize);
//		  for(x in $filter('limitTo')(valor, contexto.pageSize)){
//			  console.log("Array: "+x);
//		  };  
	  }
	  
	  console.log("Array: "+testar);
  };
  
  
});

