'use strict';

angular.module('app').controller('ManutencaoCtrl', function ($scope, $rootScope, $filter, toaster, $location, serviceManutencao,
  serviceUsuario, serviceVeiculo, serviceOficina, serviceItem ) {

  /*Verifica se o o usuário está logado*/
    if($rootScope.userDados.token == null){        
        $location.path("/");
        toaster.pop('info', 'Atenção', 'Favor entrar no sistema!', 4000); //exibe um alerta na tela
    }
    
    var contexto = this;

    $scope.$emit('menu', true); //Exibe o menu após o login

/*---------------------------------------- Variáveis ------------------------------------------------*/    
    contexto.manutencao = new serviceManutencao();
    contexto.manutencoes = [];     

/*---------------------------------------- Funções Combobox -----------------------------------------*/
    /*Combobox de veículos cadastrados*/
    contexto.veiculo = new serviceVeiculo();
    contexto.veiculos = [];
     function buscarTodosVeiculos() {
     
          serviceVeiculo.query(function (veiculos) {
          contexto.veiculos = veiculos; 
          },
          function (erro) {
              toaster.pop('warning', 'Atenção', 'Não foi possível carregar os veiculos cadastrados!', 4000); //exibe um alerta na tela              
          });
        };

    buscarTodosVeiculos();

     /*Combobox de usuarios cadastrados*/
    contexto.usuario = new serviceUsuario();   
    contexto.usuarios = []; 
     function buscarUsuario() {
     
          serviceUsuario.query(function (usuarios) {
            contexto.usuarios = usuarios;
            for (var x in usuarios) {
              if(usuarios[x].idUsuario == $rootScope.userDados.idUsuario){
                contexto.manutencao.usuario = usuarios[x];
              }
            }
          },
          function (erro) {
                toaster.pop('warning', 'Atenção', 'Não foi possível carregar o usuário cadastrado!', 4000); //exibe um alerta na tela             
          });
        };

    buscarUsuario();

      /*Combobox de postos cadastrados*/
    contexto.oficina = new serviceOficina();   
    contexto.oficinas = []; 
     function buscarPosto() {
     
          serviceOficina.query(function (oficinas) {
            contexto.oficinas = oficinas;            
          },
          function (erro) {
                toaster.pop('warning', 'Atenção', 'Não foi possível carregar os oficinas cadastradas!', 4000); //exibe um alerta na tela
          });
        };

    buscarPosto();

/*--------- Recebe a transmissão feita pelo controller das directivas de datas através do $emite. ---*/   
    
    $scope.$on('dataSaida', function (event, value) {
      contexto.manutencao.dtinicio = $filter('dateRest')(value.date);
    });

     
    $scope.$on('dataRetorno', function (event, value) {
      contexto.manutencao.dtfim = $filter('dateRest')(value.date);
    }); 

/*---------------------------------------------- Ítens ----------------------------------------------*/
    contexto.itens = [];
    contexto.botoesEdit = false;
    contexto.botaoAdd = true; 

     /*Limpa os campos do formulário*/
    contexto.limparCamposItem = function (){
      contexto.itens.descricao = ''; 
      contexto.itens.qtd = ''; 
      contexto.itens.valorUnitario = ''; 
      contexto.botoesEdit = false;
      contexto.botaoAdd = true;        
    }

    contexto.adicionaItem = function (item) {
      if(item.descricao != "" && item.qtd != "" && item.valorUnitario != ""){
        contexto.itens.push({descricao: item.descricao,
                           qtd: item.qtd,
                           valorUnitario: item.valorUnitario});
        contexto.valorTotal =  item.qtd *  item.valorUnitario; 

        contexto.limparCamposItem(); 
        return;  
        }else{
           toaster.pop('info', 'Atenção', 'Preencha os 3 campos do ítem!', 4000); //exibe um alerta na tela 
        }                  
    };

    contexto.editItem = function (item) {
      contexto.itens.idItem = item.idItem; 
      contexto.itens.descricao = item.descricao; 
      contexto.itens.qtd = item.qtd; 
      contexto.itens.valorUnitario = item.valorUnitario;    
      contexto.botoesEdit = true;
      contexto.botaoAdd = false;   
    };

    contexto.addEditItem = function (item) {
      for (var x in contexto.itens) {
        if(contexto.itens[x] != item){
          contexto.itens[x].idItem = item.idItem; 
          contexto.itens[x].descricao = item.descricao; 
          contexto.itens[x].qtd = item.qtd; 
          contexto.itens[x].valorUnitario = item.valorUnitario;  
          contexto.valorTotal =  item.qtd *  item.valorUnitario;
          contexto.limparCamposItem();
          return; 
        }
      };                     
    };

     contexto.deleteItem = function (item) {
      for (var x in contexto.itens) {
        if(contexto.itens[x] != item){
          contexto.itens.splice(contexto.itens[x], 1);
          contexto.limparCamposItem();
          return; 
        }
      };                     
    };    

     contexto.removeItensTable = function () {
     /* for (var x in contexto.itens) {        
          contexto.itens.splice(contexto.itens[x]);*/
          contexto.itens = [];
         // contexto.limparCamposItem();          
       // }
      //};                     
    };    

/*---------------------------------------- Funções Rest e Regras--------------------------------------*/
    /*Cadastro e Edição */
    /*Traz todos  os cadastrados ativos*/
    function buscarTodos() {
        serviceManutencao.query(
            function (manutencoes) {
              contexto.manutencoes = manutencoes;            
            },

            function (erro) {
                toaster.pop('warning', 'Atenção', 'Não foi possível carregar as manutenções cadastradas!', 4000); //exibe um alerta na tela
        });
    };

    buscarTodos();//chamada da função ao carregar a pagina

    /*Limpa os campos do formulário*/
    contexto.limparCampos = function (form, manutencao){
      angular.copy({}, manutencao);  
      contexto.manutencao.descricao = ''; 

      contexto.removeItensTable();

      if(form != undefined ){ 
        form.$setPristine();
      }

      buscarTodos();
    }

    /* Direciona para Salvar ou atualizar */
    contexto.salvar = function () {
      contexto.manutencao.idManutencao > 0 ? atualizar() : novo();
    };

    /* Cadastrar */
    function novo() { 
      contexto.manutencao.itens = contexto.itens;        
      contexto.manutencao.$save().then(function () {          
          contexto.manutencao = new serviceManutencao();
          contexto.limparCampos();
          toaster.pop('success', 'Atenção', 'Cadastro realizado com sucesso.', 4000); //exibe um alerta na tela
      }).catch( function (erro) {         
          toaster.pop('error', 'Atenção', 'Erro ao cadastrar a manutenção!', 4000); //exibe um alerta na tela           
      });
    };

    /*  Atualizar */
    function atualizar() {
     contexto.manutencao.itens = contexto.itens; 
      contexto.manutencao.$update({params: contexto.manutencao.idManutencao},
          function () {
              contexto.manutencao = new serviceManutencao();
              contexto.limparCampos();             
              toaster.pop('success', 'Atenção', 'Cadastro atualizado com sucesso.', 4000); //exibe um alerta na tela
          },

          function (erro) {
              toaster.pop('error', 'Atenção', 'Erro ao atualizar a manutenção!', 4000); //exibe um alerta na tela  
          }
      );
    };

    /* Editar */
    contexto.edit = function (manutencao) {
      contexto.manutencao = manutencao;  

      //Adiciona os ítens vindos do banco
      for (var x in contexto.manutencao.itens) {
          contexto.itens.push({idItem: contexto.manutencao.itens[x].idItem,
            descricao: contexto.manutencao.itens[x].descricao,
            qtd: contexto.manutencao.itens[x].qtd,
            valorUnitario: contexto.manutencao.itens[x].valorUnitario});
          contexto.valorTotal =  contexto.manutencao.itens[x].qtd *  contexto.manutencao.itens[x].valorUnitario; 
      };
    
      //Envia as datas selecionadas para ser exibida no formulário
      $scope.$broadcast('SelectedDataSaida', $filter('dateFormat')(manutencao.dtinicio));
      $scope.$broadcast('SelectedDataRetorno', $filter('dateFormat')(manutencao.dtfim));  
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

