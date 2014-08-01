application.controller('DirectoryBrowserCtrl', function ($scope, $http, $modalInstance, initialPath) {

    $scope.changeDir = function(path) {
        var params = path ? { path : path } : {};
        $http.get("/rest/directory", { params : params }).success(function(data) {
            $scope.directory = data;
        });
    };

    $scope.loadGallery = function(path) {
        $modalInstance.close(path);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };

    $scope.changeDir(initialPath);

});