'use strict';

angular.module('app').controller('SolicitacaoCtrl', function ($scope, $rootScope, $filter, toaster, $location, serviceSolicitacao,
  serviceUsuario, serviceVeiculo ) {

  /*Verifica se o o usuário está logado*/
    if($rootScope.userDados.token == null){        
        $location.path("/");
        toaster.pop('info', 'Atenção', 'Favor entrar no sistema.', 5000); //exibe um alerta na tela
    }
    
    var contexto = this;

    $scope.$emit('menu', true); //Exibe o menu após o login

/*---------------------------------------- Variáveis ------------------------------------------------*/    
    contexto.solicitacao = new serviceSolicitacao();
    contexto.solicitacoes = [];   

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

     /*Combobox de veículos cadastrados*/
    contexto.usuario = new serviceUsuario();   
    contexto.usuarios = []; 
     function buscarUsuario() {
     
          serviceUsuario.query(function (usuarios) {
            contexto.usuarios = usuarios;
            for (var x in usuarios) {
              if(usuarios[x].idUsuario == $rootScope.userDados.idUsuario){
                contexto.solicitacao.responsavel = usuarios[x];
              }
            }
          },
          function (erro) {
              toaster.pop('warning', 'Atenção', 'Não foi possível carregar o usuário cadastrado.', 4000); //exibe um alerta na tela
          });
        };

    buscarUsuario();

/*---------- Recebe a transmissão feita pelo controller das directivas de datas através do $emite. ---*/   
    
    $scope.$on('dataSaida', function (event, value) {
      contexto.solicitacao.data1 = $filter('dateFormat')(value.date);
    });

     
    $scope.$on('dataRetorno', function (event, value) {
      contexto.solicitacao.data2 = $filter('dateFormat')(value.date);
    }); 

/*---------------------------------------- Funções Rest e Regras--------------------------------------*/
/*Cadastro e Edição */
    /*Traz todos  os cadastrados ativos*/
    function buscarTodos() {
        serviceSolicitacao.query(
            function (solicitacoes) {
              contexto.solicitacoes = solicitacoes;
            },

            function (erro) {
              toaster.pop('warning', 'Atenção', 'Não foi possível carregar as solicitações.', 4000); //exibe um alerta na tela
            }
        );
    }

        buscarTodos();//chamada da função ao carregar a pagina

    /*Limpa os campos do formulário*/
    contexto.limparCampos = function (form, solicitacao){
     angular.copy({}, solicitacao);  
     
     /*Reseta as datas*/
     $scope.$broadcast('RefreshdDataSaida');
     $scope.$broadcast('RefreshDataRetorno');

      if(form != undefined ){ 
        form.$setPristine();
      }

      buscarTodos();
    }

    /* Direciona para Salvar ou atualizar */
    contexto.salvar = function () {
      contexto.solicitacao.idSolicitacao > 0 ? atualizar() : novo();
    };

    /* Cadastrar */
    function novo() {         
      contexto.solicitacao.$save().then(function () {          
          contexto.solicitacao = new serviceSolicitacao();
          contexto.limparCampos();
          toaster.pop('success', 'Atenção', 'Cadastro realizado com sucesso.', 4000); //exibe um alerta na tela
      }).catch( function (erro) {
          toaster.pop('error', 'Atenção', 'Erro ao cadastrar a solicitação.', 4000); //exibe um alerta na tela  
      });
    };

    /*  Atualizar */
    function atualizar() { 
      //Seta null para não dá CORS   
      contexto.solicitacao.dtsaida = null;
      contexto.solicitacao.dtretorno = null; 

      contexto.solicitacao.$update({params: contexto.solicitacao.idSolicitacao},
          function () {
              contexto.solicitacao = new serviceSolicitacao();
              contexto.limparCampos();
              toaster.pop('success', 'Atenção', 'Cadastro atualizado com sucesso.', 4000); //exibe um alerta na tela
          },

          function (erro) {
              toaster.pop('error', 'Atenção', 'Erro ao atualizar a solicitação.', 4000); //exibe um alerta na tela  
          }
      );
    };

    /* Editar */
    contexto.edit = function (solicitacao) {
      contexto.solicitacao = solicitacao;  
    
      //Envia as datas selecionadas para ser exibida no formulário
      $scope.$broadcast('SelectedDataSaida', $filter('date')(solicitacao.dtsaida, "dd/MM/yyyy"));
      $scope.$broadcast('SelectedDataRetorno', $filter('date')(solicitacao.dtretorno, "dd/MM/yyyy")); 

      //Adiciona as datas vindas do banco aos transientes
      contexto.solicitacao.data1 = $filter('dateFormat')(solicitacao.dtsaida);
      contexto.solicitacao.data2 = $filter('dateFormat')(solicitacao.dtretorno);             
    };

/*---------------------------------------- Text Area --------------------------------------*/
    contexto.customMenu = [
        ['bold', 'italic', 'underline', 'strikethrough', 'subscript', 'superscript'],
        ['format-block'],
        ['font'],
        ['font-size'],
       /* ['font-color', 'hilite-color'],*/
        ['remove-format'],
        ['ordered-list', 'unordered-list', 'outdent', 'indent'],
        ['left-justify', 'center-justify', 'right-justify'],
        ['code', 'quote', 'paragraph'],
       /* ['link', 'image']*/
    ];
    
/*---------------------------------------- Paginação Grid --------------------------------------*/    
    contexto.quantidade = 5;
    contexto.pageSize = contexto.quantidade;
    contexto.currentPage = 1;

    contexto.setItemsPerPage = function(num) {
      contexto.pageSize = num;
      contexto.currentPage = 1; //reseta para o primeiro
    };

});

