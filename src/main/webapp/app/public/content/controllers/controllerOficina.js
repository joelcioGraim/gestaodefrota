'use strict';

angular.module('app').controller('OficinaCtrl', function ($scope, $rootScope, $filter, toaster, $location, serviceOficina ) {

  /*Verifica se o o usuário está logado*/
    if($rootScope.userDados.token == null){        
        $location.path("/");
        toaster.pop('info', 'Atenção', 'Favor entrar no sistema.', 5000); //exibe um alerta na tela
    }

    var contexto = this;

    $scope.$emit('menu', true); //Exibe o menu após o login

/*---------------------------------------- Funções Rest --------------------------------------*/
    /*Cadastro e Edição */

    contexto.oficina = new serviceOficina();
    contexto.oficinas = [];   

    /*Traz todos os cadastrados*/
    function buscarTodos() {
        serviceOficina.query(
            function (oficinas) {
              contexto.oficinas = oficinas;
            },

            function (erro) {
                toaster.pop('warning', 'Atenção', 'Não foi possível carregar as oficinas cadastradas.', 4000); //exibe um alerta na tela
            }
        );
    }

    buscarTodos();//chamada da função ao carregar a pagina

     /*Limpa os campos do formulário*/
    contexto.limparCampos = function (form, oficina){    
     angular.copy({}, oficina);     

      if(form != undefined ){ 
        form.$setPristine();
      }

      buscarTodos();
    }

        /*Salvar e atualizar */
        contexto.salvar = function () {
            contexto.oficina.idOficina > 0 ? atualizar() : novo();
        };

        /*Cadastrar */
        function novo() {
            contexto.oficina.$save().then(function () {
                contexto.oficina = new serviceOficina();                
                contexto.limparCampos();
                toaster.pop('success', 'Atenção', 'Cadastro realizado com sucesso.', 4000); //exibe um alerta na tela
            }).catch( function (erro) {
                toaster.pop('error', 'Atenção', 'Erro ao cadastrar a oficina.', 4000); //exibe um alerta na tela
            });

        };

        /*Atualizar */
        function atualizar() {
            contexto.oficina.dataRegistro = $filter('dateRest')(contexto.oficina.dataRegistro); //moment(contexto.posto.dataRegistro).format("YYYY-MM-DDTHH:mm:ss-0300")
            contexto.oficina.$update({params: contexto.oficina.idOficina}, 
                function () {
                    contexto.oficina = new serviceOficina();                    
                    contexto.limparCampos();
                    toaster.pop('success', 'Atenção', 'Cadastro atualizado com sucesso.', 4000); //exibe um alerta na tela
                },
                function (erro) {
                    toaster.pop('error', 'Atenção', 'Erro ao atualizar a oficina.', 4000); //exibe um alerta na tela
                }
            );

        };

        /*Edita um usuário*/
        contexto.edit = function (oficina) {
            contexto.oficina = oficina;           
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

