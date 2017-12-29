var ABApp = angular.module('ABApp');


ABApp.controller('lwController', function($scope,$modal, $http, $window,$interval,$filter) {
	
	
	$scope.emptyList = [];
	$scope.items = [];
	$scope.partnerId=[];
	$scope.Id=[];
	$scope.lwIdrequest=[];
	$scope.excelList=[];
	$scope.getExcel=function(partnerId,check,lwId)
	{
		 $scope.partnerId  = angular.copy($scope.emptyList);
		 $scope.partnerId.push(partnerId);
			  if (!check) {
				  
				 
					$scope.lwIdrequest.push(lwId);
			  } 
					
					else{
						var index1 = $scope.lwIdrequest.indexOf(lwId);
						  $scope.lwIdrequest.splice(index1, 1);
					}
			
		
	};
	
	$scope.direction = 'OUT';
	 $scope.downloadExcel = function () {
		//for(var i=0;i<$scope.bussidrequest.length;i++) { 
			  

		 $window.open('/TPIAdmin/app/lightWellIdentity?pId='+$scope.partnerId+'&lIds='+$scope.lwIdrequest,'_blank');
		 
		// }
		// $scope.idrequest.length=0;
		 };
	$scope.table = function(srid)
	{
		 $scope.items = angular.copy($scope.emptyList) ;
		var tableList=[];
		 $http.get('/TPIAdmin/app/search?q=lightWellIdentityBySrId&filter='+srid)
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
				 {       partnerId:item.partnerId, 
						 lwId:item.lwId,
						 partnerName:item.partnerName,
						 gsId:item.gsId,
						checked:false
					 };
				 
				 $scope.items.push(obj);
				 }catch(e)
				 {}
				
			 });
			
		 
		 });
		 console.log("list "+JSON.stringify($scope.items) );
		 
	
	};
	
	
	
	$scope.display = function(name)
	{
		 $scope.items = angular.copy($scope.emptyList) ;
		var tableList=[];
		 $http.get('/TPIAdmin/app/search?q=lightWellIdentityByPartnerName&filter='+name)
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
				 {       partnerId:item.partnerId, 
						 lwId:item.lwId,
						 partnerName:item.partnerName,
						 gsId:item.gsId,
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