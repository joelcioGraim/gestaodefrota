'use strict';

angular.module('app').controller('AbastecimentoCtrl', function ($scope, $rootScope, $filter, toaster, $location, serviceAbastecimento,
  serviceUsuario, serviceVeiculo, servicePosto ) {

  /*Verifica se o o usuário está logado*/
    if($rootScope.userDados.token == null){        
        $location.path("/");
        toaster.pop('info', 'Atenção', 'Favor entrar no sistema.', 5000); //exibe um alerta na tela
    }
    
    var contexto = this;

    $scope.$emit('menu', true); //Exibe o menu após o login

/*---------------------------------------- Variáveis ------------------------------------------------*/    
    contexto.abastecimento = new serviceAbastecimento();
    contexto.abastecimentos = [];   
    contexto.endereco = '';
/*---------------------------------------- Funções Combobox --------------------------------------*/
    /*Combobox de veículos cadastrados*/
    contexto.veiculo = new serviceVeiculo();
    contexto.veiculos = [];
     function buscarTodosVeiculos() {
     
          serviceVeiculo.query(function (veiculos) {
          contexto.veiculos = veiculos; 
          },
          function (erro) {
              toaster.pop('warning', 'Atenção', 'Não foi possível carregar os veiculos cadastrados.', 4000); //exibe um alerta na tela
          });
        };

    buscarTodosVeiculos();    

      /*Combobox de usuário cadastrados*/
    contexto.usuario = new serviceUsuario();   
    contexto.usuarios = []; 
     function buscarUsuario() {
     
          serviceUsuario.query(function (usuarios) {
            contexto.usuarios = usuarios;
            for (var x in usuarios) {
              if(usuarios[x].idUsuario == $rootScope.userDados.idUsuario){
                contexto.abastecimento.usuario = usuarios[x];
              }
            }
          },
          function (erro) {
              toaster.pop('warning', 'Atenção', 'Não foi possível carregar o usuario cadastrado.', 4000); //exibe um alerta na tela
          });
        };

    buscarUsuario();

     /*Combobox de usuário cadastrados*/
    contexto.posto = new servicePosto();   
    contexto.postos = []; 
     function buscarPosto() {
     
          servicePosto.query(function (postos) {
            contexto.postos = postos;           
          },
          function (erro) {
              toaster.pop('warning', 'Atenção', 'Não foi possível carregar os postos cadastrados.', 4000); //exibe um alerta na tela
          });
        };

    buscarPosto();

/*---------------------------------------- Funções Rest e Regras--------------------------------------*/
/*Cadastro e Edição */
    /*Traz todos  os cadastrados ativos*/
    function buscarTodos() {
        serviceAbastecimento.query(
            function (abastecimentos) {
              contexto.abastecimentos = abastecimentos;
            },

            function (erro) {
              toaster.pop('warning', 'Atenção', 'Não foi possível carregar os abastecimentos cadastrados.', 4000); //exibe um alerta na tela
            }
        );
    }

        buscarTodos();//chamada da função ao carregar a pagina

    /*Limpa os campos do formulário*/
    contexto.limparCampos = function (form, abastecimento){
     angular.copy({}, abastecimento);  
     contexto.endereco = '';

      if(form != undefined ){ 
        form.$setPristine();
      }

      buscarTodos();
    }

    /* Direciona para Salvar ou atualizar */
    contexto.salvar = function () {
      contexto.abastecimento.idAbastecimento > 0 ? atualizar() : novo();
    };

    /* Cadastrar */
    function novo() {         
      contexto.abastecimento.$save().then(function () {          
          contexto.abastecimento = new serviceAbastecimento();
          buscarTodos();
          contexto.limparCampos();
           toaster.pop('success', 'Atenção', 'Cadastro realizado com sucesso.', 4000); //exibe um alerta na tela
      }).catch( function (erro) {
          toaster.pop('error', 'Atenção', 'Erro ao cadastrar o abastecimento.', 4000); //exibe um alerta na tela  
      });
    };

    /*  Atualizar */
    function atualizar() { 

      contexto.abastecimento.$update({params: contexto.abastecimento.idAbastecimento},
          function () {
              contexto.abastecimento = new serviceAbastecimento();
              buscarTodos();
              contexto.limparCampos();
              toaster.pop('success', 'Atenção', 'Cadastro atualizado com sucesso.', 4000); //exibe um alerta na tela
          },

          function (erro) {
              toaster.pop('error', 'Atenção', 'Erro ao atualizar o abastecimento.', 4000); //exibe um alerta na tela  
          }
      );
    };

    /* Editar */
    contexto.edit = function (abastecimento) {
      contexto.abastecimento = abastecimento;       
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

