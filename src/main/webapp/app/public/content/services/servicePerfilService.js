'use strict';
angular.module('app')
    .factory('servicePerfil', ['$resource', function ($resource) {

        return $resource($SERVICES_CONTEXT + 'perfil/:params', {}, {
          tipos: {
                method: 'GET',
                params: {params: 'tipo'},
                isArray: true
            }
        });

    }]);