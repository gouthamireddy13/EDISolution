var ABApp = angular.module('ABApp');


ABApp.controller('tppExportController', function($scope,$modal, $http, $window,$interval,$filter) {
	
	
	$scope.emptyList = [];
	$scope.items = [];
	$scope.idRequest=[];
	$scope.Id=[];
	$scope.bussidrequest=[];
	$scope.excelList=[];
	$scope.getExcel=function(tppid,check)
	{
		//$scope.partnerId  = angular.copy($scope.emptyList);
		// $scope.partnerId.push(partnerId);
			  if (!check) {
				  
				 
					$scope.idRequest.push(tppid);
			  } 
					
					else{
						var index1 = $scope.idRequest.indexOf(tppid);
						  $scope.idRequest.splice(index1, 1);
					}
			
		
	};
	
	$scope.direction = 'OUT';
	 $scope.downloadExcel = function () {
		//for(var i=0;i<$scope.bussidrequest.length;i++) { 
			  

		 $window.open('/TPIAdmin/app/tppReportForTPPName?id='+$scope.idRequest, '_blank');
		 
		// }
		// $scope.idrequest.length=0;
		 };
	$scope.table = function()
	{
		$scope.items = angular.copy($scope.emptyList) ;
		
		var tableList=[];
		 $http.get('/TPIAdmin/app/search?q=tppName&filter='+$scope.name)
	     .success(function (response, status, headers, config) {
	    	// $scope.items = angular.copy($scope.emptyList) ;
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
			 //$scope.items = angular.copy($scope.emptyList) ;
			
			 angular.forEach(tableList, function (item) {
				 
				 try
				 {var obj=
				 {       id:item.id, 
						 
						 tppName:item.name,
						 type:item.type.description,
						 

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