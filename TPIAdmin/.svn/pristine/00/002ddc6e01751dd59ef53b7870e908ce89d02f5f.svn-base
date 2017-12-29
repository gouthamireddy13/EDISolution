var ABApp = angular.module('ABApp');


ABApp.directive("fileread", [function () {
	  return {
	    link: function ($scope, $elm, $attrs) {
	      $elm.on('change', function (changeEvent) {
	        var reader = new FileReader();
	        
	        reader.onload = function (evt) {
	          var data = evt.target.result;
	        };
	        
	        reader.readAsBinaryString(changeEvent.target.files[0]);
	      });
	    }
	  }
	}]);
ABApp.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

ABApp.service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function(file, uploadUrl,entityName){
        var fd = new FormData();
        fd.append('file', file);
        fd.append('entityName',entityName)
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .success(function(response){
        	return response;
        })
        .error(function(){
        });
    }
}]);


ABApp.controller('masterDataController', function($scope,$modal, $http, $window,$interval,$filter,fileUpload) {
	
	
	
	
	
$scope.ResponseData={};
	
	$scope.uploadFile = function(){
        var file = $scope.myFile;
        console.log('file is ' );
        console.dir(file);
        var uploadUrl = "/TPIAdmin/app/uploadData/"+$scope.partner;
        var fd = new FormData();
        fd.append('file', file);
        
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .success(function(response, status, headers, config){
        $scope.ResponseData=response;
        console.log(  $scope.ResponseData.message);
        	return response;
        })
        .error(function(){
        });
    };
	
	
	
	
	
	
	
	
	
	$scope.emptyList = [];
	$scope.items = [];
	$scope.idRequest=[];
	$scope.Id=[];
	$scope.bussidrequest=[];
	$scope.excelList=[];
	$scope.getExcel=function(partnerid,check)
	{
		//$scope.partnerId  = angular.copy($scope.emptyList);
		// $scope.partnerId.push(partnerId);
			  if (!check) {
				  
				 
					$scope.idRequest.push(partnerid);
			  } 
					
					else{
						var index1 = $scope.idRequest.indexOf(partnerid);
						  $scope.idRequest.splice(index1, 1);
					}
			
		
	};
	
	$scope.direction = 'OUT';
	 $scope.downloadExcel = function () {
		//for(var i=0;i<$scope.bussidrequest.length;i++) { 
			  

		 $window.open('/TPIAdmin/app/partnerReportForpartnerName?id='+$scope.idRequest, '_blank');
		 
		// }
		// $scope.idrequest.length=0;
		 };
	$scope.table = function(partnerName)
	{
		$scope.items = angular.copy($scope.emptyList) ;
		
		var tableList=[];
		 $http.get('/TPIAdmin/app/search?q=partnerName&filter='+partnerName)
	     .success(function (response, status, headers, config) {
	    	
	    	 tableList=angular.copy(response);
	    	 
	    	// console.log("inputdata "+JSON.stringify(tempList));
	     })
	     .error(function (data, status, header, config) {
	    	
	         $scope.ResponseDetails = "Data: " + data +
	             "<br />status: " + status ;
	         //    "<br />headers: " + jsonFilter(header) +
	           //  "<br />config: " + jsonFilter(config);
	     })
	     	
		 .then(function(response) {
			
			 angular.forEach(tableList, function (item) {
				 
				 try
				 {var obj=
				 {       id:item.id, 
						 partnerGroup:item.partnerGroup.groupName,
						 partnerSubGroup:item.partnerGroup.subGroupName,
						 partnerName:item.partnerName,
						 contactName: item.contactDetails.contactName,

						checked:false
					 };
				 
				 $scope.items.push(obj);
				 }catch(e)
				 {}
				
			 });
			
		 
		 });
		 console.log("list "+JSON.stringify($scope.items) );
		 
	
	};
	
	
	

	
	
});