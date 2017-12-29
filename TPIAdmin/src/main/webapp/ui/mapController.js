var ABApp = angular.module('ABApp');

ABApp.directive('uppercased', function() {
    return {
        require: 'ngModel',        
        link: function(scope, element, attrs, modelCtrl) {
            modelCtrl.$parsers.push(function(input) {
                return input ? input.toUpperCase() : "";
            });
            element.css("text-transform","uppercase");
        }
    };
});

ABApp.filter('unique', function() {
    return function(input, key) {
        var unique = {};
        var uniqueList = [];
        for(var i = 0; i < input.length; i++){
            if(typeof unique[input[i][key]] == "undefined"){
                unique[input[i][key]] = "";
                uniqueList.push(input[i]);
            }
        }
        return uniqueList;
    };
});
ABApp.controller('mapController', function($scope,$modal, $http, $window,$interval,$filter) {
	
	
	
$scope.stateL = [];
	
	$scope.initstate = function()
	{
		//$scope.contactGroup = 'N/A';
		
		
		 $http.get('/TPIAdmin/app/environmentInfoForParam?paramName=READONLY_DB')
	     .success(function (response, status, headers, config) {
	    	 $scope.stateL = angular.copy(response);
	    	// console.log(JSON.stringify($scope.grouplist));
	     })
	     .error(function (data, status, header, config) {
	         $scope.ResponseDetails = "Data: " + data +
	             "<br />status: " + status ;
	         //    "<br />headers: " + jsonFilter(header) +
	           //  "<br />config: " + jsonFilter(config);
	     })
		 .then(function(response) {
		        $scope.state = response.data.paramVal;
		    });


		
		};
	
	
	
	
	$scope.abdc = true;
	
	
	 
	
	
	$scope.ErrorMsg = false;
	$scope.hideEdit=false;
	$scope.sortType     = 'name'; 
	  $scope.sortReverse  = false;
	$scope.scList = [];
	$scope.initSC = function()
	{
		
		
		 $http.get('/TPIAdmin/app/serviceCategories/distinctNames')
	     .success(function (response, status, headers, config) {
	    	 
	    	 $scope.scList=angular.copy(response);
	    //	 $scope.idd=angular.copy(response.id);
	    	// console.log("inputdata "+JSON.stringify(tempList));
	     })
	     .error(function (data, status, header, config) {
	    	
	         $scope.ResponseDetails = "Data: " + data +
	             "<br />status: " + status ;
	         //    "<br />headers: " + jsonFilter(header) +
	           //  "<br />config: " + jsonFilter(config);
	     })
	     	
		 .then(function(response) {
			//$scope.idd = response.data.id;
		 });
			
	};
	
	$scope.bsList = [];
	$scope.initBS = function()
	{
		
		
		 $http.get('/TPIAdmin/app/serviceTypes?serviceCat='+$scope.serviceCategory+'&projection=idAndNameProjection')
	     .success(function (response, status, headers, config) {
	    	 
	    	 $scope.bsList=angular.copy(response);
	    //	 $scope.idd=angular.copy(response.id);
	    	// console.log("inputdata "+JSON.stringify(tempList));
	     })
	     .error(function (data, status, header, config) {
	    	
	         $scope.ResponseDetails = "Data: " + data +
	             "<br />status: " + status ;
	         //    "<br />headers: " + jsonFilter(header) +
	           //  "<br />config: " + jsonFilter(config);
	     })
	     	
		 .then(function(response) {
			//$scope.idd = response.data.id;
		 });
			
	};
	
	
	$scope.mapList = [];
	$scope.initMap = function()
	{
		
		
		 $http.get('/TPIAdmin/app/maps')
	     .success(function (response, status, headers, config) {
	    	 
	    	 $scope.mapList=angular.copy(response);
	    //	 $scope.idd=angular.copy(response.id);
	    	// console.log("inputdata "+JSON.stringify(tempList));
	     })
	     .error(function (data, status, header, config) {
	    	
	         $scope.ResponseDetails = "Data: " + data +
	             "<br />status: " + status ;
	         //    "<br />headers: " + jsonFilter(header) +
	           //  "<br />config: " + jsonFilter(config);
	     })
	     	
		 .then(function(response) {
			//$scope.idd = response.data.id;
		 });
			
	};
	
	$scope.bsEnable  = function()
	{
		if($scope.serviceCategory == null){
		$scope.businessService = null;
		
		}
	};
	
	$scope.emptyList = [];
	$scope.items = [];
	$scope.inittable = function(id)
	{
	  $scope.items = angular.copy($scope.emptyList) ;
		var tableList=[];
		
		 $http.get('/TPIAdmin/app/serviceTypes/'+id+'/maps')
	     .success(function (response, status, headers, config) {
	    	 
	    	 tableList=angular.copy(response);
	    //	 $scope.idd=angular.copy(response.id);
	    	// console.log("inputdata "+JSON.stringify(tempList));
	     })
	     .error(function (data, status, header, config) {
	    	
	         $scope.ResponseDetails = "Data: " + data +
	             "<br />status: " + status ;
	         //    "<br />headers: " + jsonFilter(header) +
	           //  "<br />config: " + jsonFilter(config);
	     })
	     	
		 .then(function(response) {
			//$scope.idd = response.data.id;
			 
			 angular.forEach(tableList, function (item) {
				 try
				 {var obj=
				 {        
						 mapName:item.mapName,
						 mapDescription: item.mapDescription
						 
						 
					 };
				 
				 $scope.items.push(obj);
				
				 }catch(e)
				 {}
				 });
				 
		 });
		 console.log("list "+JSON.stringify($scope.items) );	
	};
	
	$scope.saveMap = function(){
		//$scope.ErrorMsg=true;
		var submitObj=
			{   
				id : $scope.map
				
			};
				$http.post("/TPIAdmin/app/serviceTypes/"+$scope.businessService+"/maps",submitObj)
				.success(function (response, status, headers, config) {
					 //$scope.grouplist = angular.copy(response);
				//	$scope.idd=angular.copy(response.id); 
					
					console.log(JSON.stringify(response));
					 
				 })
				 .error(function (data, status, header, config) {
					
				     $scope.ResponseDetails = "Data: " + data +
				         "<br />status: " + status ;
				     //    "<br />headers: " + jsonFilter(header) +
				       //  "<br />config: " + jsonFilter(config);
				 })
				.then(function(response) {
					// $scope.myWelcome = response;
					 $scope.clickModal();
					// $scope.clickModal();
				    
				});
						
						
					}
	
	$scope.saveNewMap = function(){
		//$scope.ErrorMsg=true;
		var submitObj=
			{   
				mapName : $scope.newMap,
				mapDescription : $scope.newMap
				
			};
				$http.post("/TPIAdmin/app/serviceTypes/"+$scope.businessService+"/maps",submitObj)
				.success(function (response, status, headers, config) {
					 //$scope.grouplist = angular.copy(response);
				//	$scope.idd=angular.copy(response.id); 
					
					console.log(JSON.stringify(response));
					 
				 })
				 .error(function (data, status, header, config) {
					
				     $scope.ResponseDetails = "Data: " + data +
				         "<br />status: " + status ;
				     //    "<br />headers: " + jsonFilter(header) +
				       //  "<br />config: " + jsonFilter(config);
				 })
				.then(function(response) {
					// $scope.myWelcome = response;
					 $scope.clickModal();
					// $scope.clickModal();
				    
				});
						
						
					}
	
	
	
	$scope.hideremove=false;
	$scope.hideEdit=false;
	$scope.hidefield=function()
	{
		$scope.hideremove=true;
		$scope.onlyNew=true;
	
	}
	
	$scope.enableField=function()
	{
	
		$scope.hideEdit=false;
	}
	
	$scope.user={del:false,edit:false};
	$scope.deleteService=function()
	{
		$scope.user.del=true;
		$scope.user.edit=false;
		  $scope.onlyNew=false;
		 $scope.clickModal();
		 

	}
	
	$scope.userData = [
        {
            name: 'John Doe',
            selectedProducts: [
                'coffee',
                'beer',
                'wine']
        },
        {
            name: 'Jane Doe',
            selectedProducts: [
                'coffee',
                'tea']
        }
    ];
  $scope.products = ['coffee', 'beer', 'wine', 'tea', 'milk'];
  $scope.onlyNew=false;

$scope.clickModal=function()
{
	var txt="hello";
	
		
	
	{
	$scope.user.msg="Map has been added";
	}
		
	
	if($scope.user.del)
	{
		$scope.user.msg="Are you sure to delete?";	
	}
	if($scope.user.edit)
	{
		$scope.user.msg="Are you sure to delete the contact ?";	
	}
	
	var modalInstance = $modal.open({
        templateUrl: 'myModalContent.html',
        controller: 'ModalInstanceCtrl',
        size: 'sm',
    
        
        //size: size,
        resolve: {
            user: function() {
                return $scope.user;
            },
            
            selectedProducts: function() {
                return $scope.userData.selectedProducts;
            },
            products: function () {
                //console.log($scope.selectedProducts);
                return $scope.products; // get all available products
            }
        }
    });
    
    modalInstance.result.then(function (selectedItems) {
    	if(selectedItems=="delete")
    		{
    	 console.log('eventX found on Controller1 $scope');
         $http.delete(' /TPIAdmin/app/serviceCategories/'+$scope.idd+'/lightWellPartners/'+$scope.id)
         .success(function (data, status, headers) {
        	 location.reload();
         })
         .error(function (data, status, header, config) {
             $scope.ServerResponse = htmlDecode("Data: " + data +
                 "\n\n\n\nstatus: " + status +
                 "\n\n\n\nheaders: " + header +
                 "\n\n\n\nconfig: " + config);
         });
    		}
    	else	if(selectedItems=="edit")
		{
    		var index = $scope.contactList.indexOf($scope.localdel);
	    	  $scope.contactList.splice(index, 1);  
	    	  $scope.localdel=null;
		}
    	
    }, function () {
     
    });
};






});

ABApp.controller('ModalInstanceCtrl', function ($scope, $http, $modalInstance, products, selectedProducts, user) {

  //console.log('user', user);
  $scope.products = products;
    $scope.user=user;
  $scope.selected = selectedProducts;
  $scope.cancel = function (flag,editf,trans) {
	    
	    if(flag!=undefined&&flag==true)
    	{
	    	  $scope.deletef();
    	}
	    else if(editf!=undefined &&editf==true)
	    	{
	    	$scope.Editfuc();
	    	}
	    else if(trans!=undefined &&trans==true)
    	{
    	$scope.transFuc();
    	}
	    else
	    	{
	    	location.reload();
	    	}
	    
	  };
	  
	  $scope.Editfuc = function () {
		  $modalInstance.close("edit");
		   
		  };
		  $scope.transFuc = function () {
			  $modalInstance.close("transedit");
			   
			  };
		  
	  $scope.deletef = function () {
		  $modalInstance.close("delete");
		   
		  };
		  $scope.close = function () {
			//  user.del=false;
			//  user.edit=false;
			  $modalInstance.close('cancel');
			   
			  };
		  
			  
  $scope.chkChange = function(item) {
      console.log(item);
      var index  = $scope.selected.indexOf(item);
      if (index > -1) {
          $scope.selected.splice(index, 1);
      }
      else {
          // not selected --> we have to add it
          $scope.selected.push(item);
      }
      console.log($scope.selected);
  };
  //console.log(selectedProducts);
  $scope.ok = function () {
      // prepare everything for sending to sever
      // --> probably check here if the data have changed or not (not implemented yet)
      
      
    var data = $.param({
            json: JSON.stringify({
                user: user.name,
                products: $scope.selected
            })
        });
       
  };
});
