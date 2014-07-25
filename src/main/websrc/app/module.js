var application = angular.module('picturepoClient', [
    'ui.bootstrap',
    'ngRoute'
]);

application.config(function ($routeProvider) {
    $routeProvider
        .when('/placeholder', { templateUrl: 'placeholder/Placeholder.html', controller: 'PlaceholderCtrl' })
        .otherwise({ redirectTo: '/placeholder' });
});


