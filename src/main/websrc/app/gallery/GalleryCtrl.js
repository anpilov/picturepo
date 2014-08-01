application.controller('GalleryCtrl', function ($scope, $http, $location, $modal) {

    $scope.path = $location.search().path;
    $scope.$watch("path", function () {
        $location.search("path", $scope.path);
        $scope.loadGallery();
    });

    $scope.loadGallery = function () {
        $http.get("/rest/gallery", { params: { path : $scope.path} }).success(function (data) {
            $scope.gallery = data;
        });
    };

    $scope.openBrowsePopup = function() {
        var modalInstance = $modal.open({
            templateUrl: 'directory/DirectoryBrowser.html',
            controller: 'DirectoryBrowserCtrl',
            size: 'sm',
            resolve: {
                initialPath: function () {
                    return $scope.path;
                }
            }
        });

        modalInstance.result.then(function (path) {
            $scope.path = path;
        });
    };

});