var application = angular.module('picturepoClient', [
    'ui.bootstrap',
    'ngRoute'
]);

application.config(function ($routeProvider) {
    $routeProvider
        .when('/placeholder', { templateUrl: 'placeholder/Placeholder.html', controller: 'PlaceholderCtrl' })
        .when('/gallery', { templateUrl: 'gallery/Gallery.html', controller: 'GalleryCtrl' })
        .otherwise({ redirectTo: '/placeholder' });
});


