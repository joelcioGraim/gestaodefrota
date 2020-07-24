'use strict';

angular.module('app').controller('VeiculoCtrl', function ($scope, $rootScope, $filter, toaster, $location, serviceVeiculo ) {

  /*Verifica se o o usuário está logado*/
    if($rootScope.userDados.token == null){        
        $location.path("/");
        toaster.pop('info', 'Atenção', 'Favor entrar no sistema.', 5000); //exibe um alerta na tela
    }

    var contexto = this;

    $scope.$emit('menu', true); //Exibe o menu após o login

/*---------------------------------------- Funções Rest --------------------------------------*/
    /*Cadastro e Edição de Usuários*/

    contexto.veiculo = new serviceVeiculo();
    contexto.veiculos = [];   

    /*Traz todos  os usuários cadastrados*/
    function buscarTodos() {
        serviceVeiculo.query(
            function (veiculos) {
              contexto.veiculos = veiculos;
            },

            function (erro) {
                toaster.pop('warning', 'Atenção', 'Não foi possível carregar todos os veículos.', 4000); //exibe um alerta na tela
            }
        );
    }

        buscarTodos();//chamada da função ao carregar a pagina

     /*Limpa os campos do formulário*/
    contexto.limparCampos = function (form, veiculo){    
     angular.copy({}, veiculo); 
      contexto.veiculo.observacao = ''; 

      if(form != undefined ){ 
        form.$setPristine();
      }

      buscarTodos();
    }

        /*Salva e atualizar um usuário*/
        contexto.salvar = function () {
            contexto.veiculo.idVeiculo > 0 ? atualizar() : novo();
        };

        /*Cadastra um usuário*/
        function novo() {

            console.log(contexto.veiculo);

            contexto.veiculo.$save().then(function () {
                contexto.veiculo = new serviceVeiculo();                
                contexto.limparCampos();
                toaster.pop('success', 'Atenção', 'Cadastro realizado com sucesso.', 4000); //exibe um alerta na tela
            }).catch( function (erro) {
                toaster.pop('error', 'Atenção', 'Erro ao cadastrar o veículo.', 4000); //exibe um alerta na tela
            });

        };

        /*Atualiza um usuário*/
        function atualizar() {

            contexto.veiculo.$update({params: contexto.veiculo.idVeiculo}, 
                function () {
                    contexto.veiculo = new serviceVeiculo();                    
                    contexto.limparCampos();
                    toaster.pop('success', 'Atenção', 'Cadastro atualizado com sucesso.', 4000); //exibe um alerta na tela
                },
                function (erro) {
                    toaster.pop('error', 'Atenção', 'Erro ao atualizar o veículo.', 4000); //exibe um alerta na tela
                }
            );

        };

        /*Edita um usuário*/
        contexto.edit = function (veiculo) {
            contexto.veiculo = veiculo;           
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

