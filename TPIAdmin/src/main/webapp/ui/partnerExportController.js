var ABApp = angular.module('ABApp');


ABApp.controller('partnerExportController', function($scope,$modal, $http, $window,$interval,$filter) {
	
	
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