'use strict';
(function () {
angular.module("app").service('serviceYdn', function () {

    var schema = {
        stores: [{
            name: 'usuario',
            indexes: [{
               name: 'nome'
            }]
        },        
        {
            name: 'perfil',
            indexes: [{
               name: 'perfil'
            }]
        }]
    }

    this.db = new ydn.db.Storage('gestaodefrota', schema);

});
})();