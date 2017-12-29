var ABApp = angular.module('ABApp');


ABApp.controller('migration', function($scope,$modal, $http, $window,$interval,$filter) {
	
	
	$scope.emptyList = [];
	$scope.items = [];
	$scope.idRequest=[];
	$scope.Id=[];
	$scope.bussidrequest=[];
	$scope.excelList=[];
	$scope.getExcel=function(bussid,check)
	{
		//$scope.partnerId  = angular.copy($scope.emptyList);
		// $scope.partnerId.push(partnerId);
			  if (!check) {
				  
				 
					$scope.idRequest.push(bussid);
			  } 
					
					else{
						var index1 = $scope.idRequest.indexOf(bussid);
						  $scope.idRequest.splice(index1, 1);
					}
			
		
	};
	
	$scope.direction = 'OUT';
	 $scope.downloadExcel = function () {
		//for(var i=0;i<$scope.bussidrequest.length;i++) { 
			  

		 $window.open('/TPIAdmin/app/promotionExport?id='+$scope.idRequest, '_blank');
		 
		// }
		// $scope.idrequest.length=0;
		 };
	$scope.table = function(srid)
	{
		 $scope.items = angular.copy($scope.emptyList) ;
		var tableList=[];
		 $http.get('/TPIAdmin/app/serviceSubscriptionReportForSrId?id='+srid)
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
						 srId:item.srId,
						 bsrId: item.bsSRId,

						 businessServiceName:item.businessServiceName,
						 partnerName:item.partnerName,
						 serviceCategoryName: item.serviceCategoryName,

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