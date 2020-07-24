'use strict';
angular.module('app')
    .factory('serviceUsuario', function serviceUsuario($resource, $rootScope, $http) {

        $http.defaults.headers.common.Authorization = $rootScope.userDados.token;
        //$http.defaults.headers.common.Authorization = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjEzODY4OTkxMzEsImlzcyI6ImppcmE6MTU0ODk1OTUiLCJxc2giOiI4MDYzZmY0Y2ExZTQxZGY3YmM5MGM4YWI2ZDBmNjIwN2Q0OTFjZjZkYWQ3YzY2ZWE3OTdiNDYxNGI3MTkyMmU5IiwiaWF0IjoxMzg2ODk4OTUxfQ.uKqU9dTB6gKwG6jQCuXYAiMNdfNRw98Hw_IWuA5Ma';  
        return $resource($SERVICES_CONTEXT + 'usuario/:params', {}, {
            update: {
                method: 'PUT'     
            },
            tipos: {
                method: 'GET',
                params: {params: 'tipo'},               
                isArray: true
            }
        });   
    });