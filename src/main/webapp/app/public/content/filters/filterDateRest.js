'use strict';

angular.module('app').filter('dateRest', function () {
    return function (data, to) {

        if (!data) { return ''; }
        else {           
        return moment(data).format("YYYY-MM-DDTHH:mm:ss-0300")
        }
    };
});