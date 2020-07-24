'use strict';

angular.module('app').controller('PostoCtrl', function ($scope, $rootScope, $filter, toaster, $location, servicePosto ) {

  /*Verifica se o o usuário está logado*/
    if($rootScope.userDados.token == null){        
        $location.path("/");
        toaster.pop('info', 'Atenção', 'Favor entrar no sistema.', 5000); //exibe um alerta na tela
    }

    var contexto = this;

    $scope.$emit('menu', true); //Exibe o menu após o login

/*---------------------------------------- Funções Rest --------------------------------------*/
    /*Cadastro e Edição */

    contexto.posto = new servicePosto();
    contexto.postos = [];   

    /*Traz todos os cadastrados*/
    function buscarTodos() {
        servicePosto.query(
            function (postos) {
              contexto.postos = postos;
            },

            function (erro) {
                toaster.pop('warning', 'Atenção', 'Não foi possível carregar os postos cadastrados.', 4000); //exibe um alerta na tela
            }
        );
    }

        buscarTodos();//chamada da função ao carregar a pagina

     /*Limpa os campos do formulário*/
    contexto.limparCampos = function (form, posto){    
     angular.copy({}, posto);     

      if(form != undefined ){ 
        form.$setPristine();
      }

      buscarTodos();
    }

        /*Salvar e atualizar */
        contexto.salvar = function () {
            contexto.posto.idPosto > 0 ? atualizar() : novo();
        };

        /*Cadastrar */
        function novo() {
            contexto.posto.$save().then(function () {
                contexto.posto = new servicePosto();                
                contexto.limparCampos();
                toaster.pop('success', 'Atenção', 'Cadastro realizado com sucesso.', 4000); //exibe um alerta na tela
            }).catch( function (erro) {
                toaster.pop('error', 'Atenção', 'Erro ao cadastrar o posto.', 4000); //exibe um alerta na tela
            });

        };

        /*Atualizar */
        function atualizar() {
            contexto.posto.dataRegistro = $filter('dateRest')(contexto.posto.dataRegistro); //moment(contexto.posto.dataRegistro).format("YYYY-MM-DDTHH:mm:ss-0300")
            contexto.posto.$update({params: contexto.posto.idPosto}, 
                function () {
                    contexto.posto = new servicePosto();                    
                    contexto.limparCampos();
                    toaster.pop('success', 'Atenção', 'Cadastro atualizado com sucesso.', 4000); //exibe um alerta na tela
                },
                function (erro) {
                    toaster.pop('error', 'Atenção', 'Erro ao atualizar o posto.', 4000); //exibe um alerta na tela
                }
            );

        };

        /*Edita um usuário*/
        contexto.edit = function (posto) {
            contexto.posto = posto;           
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

