angular.module('plunker', ['ui.bootstrap']);

    function ListCtrl($scope, $dialog) {

      $scope.items = [
        {ticket: '123', description: 'foo desc',account:'foo'},
        {ticket: '111', description: 'boo desc',account:'boo'},
        {ticket: '222', description: 'eco desc',account:'eco'}
      ];


    }
    // the dialog is injected in the specified controller
    function EditCtrl($scope, item, dialog){

      $scope.item = item;

      $scope.save = function() {
        dialog.close($scope.item);
      };

      $scope.close = function(){
        dialog.close(undefined);
      };
    }
