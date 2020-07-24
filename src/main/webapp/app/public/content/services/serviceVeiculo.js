'use strict';
angular.module('app')
    .factory('serviceVeiculo', ['$resource', '$rootScope', function ($resource, $rootScope) {
        var token = $rootScope.userDados.token;
        return $resource($SERVICES_CONTEXT + 'veiculo/:params', {}, {
            update: {
                method: 'PUT',
                 headers: { 'API-Token': 'Bearer ' + token}                 
            },

            tipos: {
                method: 'GET',
                params: {params: 'tipo'},                
                headers: { 'API-Token': 'Bearer ' + token},
                isArray: true       
            }
        });
}]);