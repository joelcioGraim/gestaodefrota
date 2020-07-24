'use strict';

angular.module('app').filter('dateFormat', function () {
    return function (data, to) {

        if (!data) { return ''; }
        else {

            var parsedDate;

            if (data[0] == '/')
                parsedDate = new Date(parseInt(data.substr(6)));
            else
                parsedDate = Date.parse(data);

            //método de conversão tradicional, contem falhas! Quando entrar aqui procurar passar um formato aceitavel acima
            if (parsedDate == null) { 
                parsedDate = new Date(data);
            }


            if (typeof to == 'undefined') to = 'dd-MM-yyyy HH:mm:ss';

            return parsedDate.toString(to);

        }
    };
});