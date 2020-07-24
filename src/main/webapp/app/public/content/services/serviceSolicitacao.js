'use strict';
angular.module('app')
    .factory('serviceSolicitacao', ['$resource', '$rootScope', function ($resource, $rootScope) {
        var token = $rootScope.userDados.token;
        return $resource($SERVICES_CONTEXT + 'solicitacao/:params', {}, {
            update: {
                method: 'PUT',
                 /*headers: { 'API-Token': 'Bearer ' + token}   */              
            },

            tipos: {
                method: 'GET',
                params: {params: 'tipo'},                
                headers: { 'API-Token': 'Bearer ' + token},
                isArray: true       
            }
        });
}]);