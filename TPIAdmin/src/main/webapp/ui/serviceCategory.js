var ABApp = angular.module('ABApp');

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
ABApp.controller('serviceCategoryController', function($scope,$modal, $http, $window,$interval,$filter) {
	
	
	
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
	$scope.sortType     = 'name'; 
	  $scope.sortReverse  = false;
	
	 $scope.filteredTodos = [];
	 $scope.currentPage = 1;
	  $scope.numPerPage = 25;
	  $scope.maxSize = 5;
	  var cols = [{
		    name: 'serviceCategory',
		    orderDesc: false
		  }, {
		    name: 'categoryID',
		    orderDesc: true
		  },
		  {
			    name: 'businessUnit',
			    orderDesc: false
			  },
			  {
				    name: 'businessSubUnit',
				    orderDesc: false
				  },
				  {
					    name: 'partnerSubscription',
					    orderDesc: false
					  }
					  
						   ];

		  $scope.totalItems = 0;
		  $scope.currentPage = 1;
		  $scope.itemsPerPage = 25;

		  $scope.$watch('currentPage', function() {
		    setPagingData($scope.currentPage);
		  });

		  function setPagingData(page) {
		    $scope.currentPage = page;
		    var pagedData = $scope.items.slice((page - 1) * $scope.itemsPerPage, page * $scope.itemsPerPage);
		    $scope.filteredTodos = pagedData;
		  }

		  $scope.sortData = function(sortCol) {
		    // make sure it a valid column
		    var column = cols.find(function(col) {
		      return col.name === sortCol;
		    });

		    if (!column) return;
		    
		    column.orderDesc = !column.orderDesc;

		    var order = !column.orderDesc ? 1 : -1;
		    $scope.items.sort(function(a, b) {
		      if (a[column.name] < b[column.name])
		        return -1 * order;
		      if (a[column.name] > b[column.name])
		        return 1 * order;
		      return 0;
		    });
		    
		    setPagingData($scope.currentPage);
		  };

	 
	
		  $scope.ServerErrorMsg="";
		  $scope.showErrorMsg=function(s)
		  {
			 
			  $scope.ServerErrorMsg=s;
			  $("#SHhErrMsg").show();
			  $("#extra").scrollTop(0);
			  setTimeout(function() { $("#SHhErrMsg").hide(); }, 5000);
		  }
		  
		  
		  
		  
		  
		  $scope.$watch('currentPage + numPerPage', function() {
			  $scope.createPagination();
		  });
	$scope.createPagination = function(){
		var begin = (($scope.currentPage - 1) * $scope.numPerPage);
		var end = begin + $scope.numPerPage;
		

	    
	    $scope.filteredTodos = $scope.items.slice(begin, end);
	    $scope.searchPage($scope.spname,true);
	};
	
	$scope.tList = [];
	$scope.searchPage = function(search,flag){
		var temp = $filter("filter")($scope.items, {serviceCategory:search});
		if(temp.length!=0)
			{
			$scope.tList = angular.copy(temp);
			var begin = (($scope.currentPage - 1) * $scope.numPerPage);
			var end = begin + $scope.numPerPage;
		    
		    $scope.filteredTodos =$scope.tList.slice(begin, end);
		
			}
		else{
			
var begin = (($scope.currentPage - 1) * $scope.numPerPage), end = begin + $scope.numPerPage;
		    
		    $scope.filteredTodos = $scope.items.slice(begin, end);
		}
		if(flag==undefined)
		{
			$scope.currentPage=1;
		}
	};
	
	
	
	$scope.ErrorMsg = false;
	$scope.hideEdit=false;
	
	 
	

	$scope.validateName="";
	$scope.validName=false;
	$scope.validateId="";
	$scope.validId=false;
	  $scope.onlyNew = false;
	  
		$scope.checkScNameandSubmit = function(scname,flag)
		{
			
			
			
			$scope.validName=false;
			$scope.ErrorMsg=false;
			$scope.validId=false;
			
			
			 $http.get('/TPIAdmin/app/sd/sdServiceCategoryDefs/exists?attribute=servicecat&value='+scname)
		     .success(function (response, status, headers, config) {
		    		$scope.validateName=response;
		     })
		     .error(function (data, status, header, config) {
		         $scope.ResponseDetails = "Data: " + data +
		             "<br />status: " + status ;
		         //    "<br />headers: " + jsonFilter(header) +
		           //  "<br />config: " + jsonFilter(config);
		     })
			 .then(function(response) {
			        $scope.myWelcome = response.data;
			        if($scope.validateName!="true")
			        	{
			        	
			        	$scope.checkids2(flag);
			        	
			        	}
			        else
			        	{
			        //	$scope.ErrorMsg=true;
			        	$scope.validName=true;
			        	}
			    });
			 };
			 
			 $scope.checkids2 = function(flag)
				{
					
					
					
					$scope.validName=false;
					$scope.ErrorMsg=false;
					$scope.validId=false;
					
					
					 $http.get('/TPIAdmin/app/sd/sdServiceCategoryDefs/exists?attribute=catid&value='+$scope.categoryID)
				     .success(function (response, status, headers, config) {
				    		$scope.validateId=response;
				     })
				     .error(function (data, status, header, config) {
				         $scope.ResponseDetails = "Data: " + data +
				             "<br />status: " + status ;
				         //    "<br />headers: " + jsonFilter(header) +
				           //  "<br />config: " + jsonFilter(config);
				     })
					 .then(function(response) {
					        $scope.myWelcome = response.data;
					        if($scope.validateId!="true")
					        	{
					        	
					        	$scope.saveABC(flag);
					        	
					        	}
					        else
					        	{
					        //	$scope.ErrorMsg=true;
					        	$scope.validId=true;
					        	}
					    });
					 };
	  
	  
	
	$scope.saveABC = function(flag){
		$scope.ErrorMsg=true;
		var submitObj=
			{   
				serviceCategory : {name:$scope.serviceCategory},
				categoryID  : $scope.categoryID,
				businessUnit : {id:$scope.businessUnit},
				businessSubUnit	: {id:$scope.businessSubUnit},
				partnerSubscription	: $scope.partnerSubscription,
				notes : $scope.notes
				
				
				
			};
				$http.post("/TPIAdmin/app/sd/sdServiceCategoryDefs",submitObj)
				.success(function (response, status, headers, config) {
					 //$scope.grouplist = angular.copy(response);
				//	$scope.idd=angular.copy(response.id); 
					
					console.log(JSON.stringify(response));
					 
				 })
				 .error(function (data, status, header, config) {
					 $scope.showErrorMsg("Save is not successful");
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
	
	
	$scope.clearabc = function(){
		$scope.user.edit=false;
		$scope.hideEdit=false;
		$scope.ErrorMsg=false;
		$scope.validName=false;
		$scope.validId=false;
		 $scope.serviceCategory = null;
		 $scope.categoryID = null;
		 $scope.businessUnit = null;
		 $scope.businessSubUnit = null;
		 $scope.partnerSubscription = null;
		 $scope.notes = null;
		// $scope.name = null;
	    // $scope.partnerName = null;
		// $scope.orgname = null;
	    
		
	};
	
	$scope.showData=function(item)
	{
		$scope.hideremove=false;
		
		$scope.validName = false;
		$scope.validId = false;
		$scope.ErrorMsg = false;
		$scope.onlyNew=false;
		$scope.enabletypeEdit = true;
		$scope.hideEdit=true;
		$scope.id = item.id;
		 $scope.serviceCategory = item.serviceCategory.name;
		 $scope.categoryID = item.categoryID;
		 $scope.businessUnit = item.businessUnit.id;
		 $scope.initSubBsList($scope.businessUnit);
		 $scope.businessSubUnit = item.businessSubUnit.id;
		 $scope.partnerSubscription = item.partnerSubscription;
		 $scope.notes = item.notes;
		 
		// $scope.orgname = item.organizationName;
		 
		
	};
	
	$scope.bsList = []
	$scope.initBsList = function()
	{
		
		
		 $http.get('/TPIAdmin/app/sd/businessUnits?projection=idAndName')
	     .success(function (response, status, headers, config) {
	    	 $scope.bsList = angular.copy(response);
	    	// console.log(JSON.stringify($scope.ttypeList));
	     })
	     .error(function (data, status, header, config) {
	         $scope.ResponseDetails = "Data: " + data +
	             "<br />status: " + status ;
	         //    "<br />headers: " + jsonFilter(header) +
	           //  "<br />config: " + jsonFilter(config);
	     })
		 .then(function(response) {
			 
		       
		    });


		
		};
		
		
		
		$scope.initSubBsList = function(id)
		{
			
			
			
			 $http.get('/TPIAdmin/app/sd/businessUnits/'+id)
		     .success(function (response, status, headers, config) {
		    	 $scope.subBsList = [];
		    	// $scope.dropList = angular.copy(response);
		    	// console.log(JSON.stringify($scope.ttypeList));
		     })
		     .error(function (data, status, header, config) {
		         $scope.ResponseDetails = "Data: " + data +
		             "<br />status: " + status ;
		         //    "<br />headers: " + jsonFilter(header) +
		           //  "<br />config: " + jsonFilter(config);
		     })
			 .then(function(response) {
				 angular.forEach(response, function (data) {
					 
					 angular.forEach(data.subUnits, function (item) {
					var obj=
					 {       id:item.id, 
							
							 subUnitName:item.subUnitName
							 
							
							
							 
						 };
					 
					 $scope.subBsList.push(obj);
					
					 
					 });
					 
				 });
				console.log("list "+JSON.stringify($scope.subBsList) ); 
			       
			    });


			
			};
	
	
	$scope.items = [];
	$scope.inittable = function()
	{
		
		var tableList=[];
		 $http.get('/TPIAdmin/app/sd/sdServiceCategoryDefs')
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
				 {       id : item.id,
				 
						 serviceCategory:item.serviceCategory.name,
						 businessUnit:item.businessUnit.name,
						 businessSubUnit:item.businessSubUnit.subUnitName,
						 categoryID : item.categoryID,
						 partnerSubscription : item.partnerSubscription,
						 data : item
						 
					 };
				 
				 $scope.items.push(obj);
				
				 }catch(e)
				 {}
				
				 
			 });
			 $scope.totalItems = $scope.items.length;
			//console.log(JSON.stringify($scope.items));
			 $scope.createPagination();	 
			 $scope.sortData('categoryID');
		 });
		// console.log("list "+JSON.stringify($scope.items) );	
	};
	
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
	
		
	if($scope.onlyNew)
	{
	$scope.user.msg="Service Category Definition has been added";
	$scope.user.del=false;
	$scope.user.edit=false;
	}
else
	{
	$scope.user.msg="Service Category Definition has been added";
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
         $http.delete(' /TPIAdmin/app/sd/sdServiceCategoryDefs/'+$scope.id)
         .success(function (data, status, headers) {
        	 location.reload();
         })
         .error(function (data, status, header, config) {
        	 $scope.showErrorMsg("Delete is not successful");
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
