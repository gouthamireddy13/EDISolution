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


ABApp.controller('serviceinterController', function($scope,$modal, $http, $window,$interval,$filter) {
	$scope.sortType     = 'name'; 
	  $scope.sortReverse  = false;
	$scope.message="";
	$scope.tpplist=[];
	$scope.ErrorMsg1 = false;
	$scope.tppCodeList=[];
	$scope.emptylList=[];
	
	
	
	
	$scope.upbsbt=false;
	$scope.upgrbt=false;
	

	

$scope.idConversion = function(list,obj)
{
	$scope.value = "";
	if(obj!=undefined){
		var tempsc = $filter("filter")(list, {id:obj},true);
		if(tempsc.length!=0)
			{
			$scope.value = tempsc[0].subUnitName;
			}
	}
	
};

$scope.initSource = function(list,obj)
{ 
	
	$scope.value="";
	if(obj!=undefined){
		var tempsc = $filter("filter")(list, {id:obj},true);
		if(tempsc.length!=0)
			{
			$scope.value = tempsc[0].subUnitName;
			}
	}
	
	$scope.SourceList = [];
	var tableList=[];
	
	 $http.get('/TPIAdmin/app/search?q=SendToBU&filter='+$scope.value)
     .success(function (response, status, headers, config) {
    	 
    	// $scope.SourceList=angular.copy(response);
    	 
    	
    	// console.log("inputdata "+JSON.stringify(tempList));
     })
     .error(function (data, status, header, config) {
         $scope.ResponseDetails = "Data: " + data +
             "<br />status: " + status ;
         //    "<br />headers: " + jsonFilter(header) +
           //  "<br />config: " + jsonFilter(config);
     })
     	
	 .then(function(response) {
		 
		 angular.forEach(response.data, function (item) {
    		 var obj=
    			 {
    				
    				 lwID : item.id,
					 testIsaID:item.testIsaID,
					 productionIsaID:item.productionIsaID,
					 testIsaQualifier:item.testIsaQualifier,
					 productionIsaQualifier:item.productionIsaQualifier,
					 testGsId:item.testGsId,
					 productionGsId:item.productionGsId
    			 };
    		 $scope.SourceList.push(obj);
    	 });
			 
			 
			
			 
		 });
		
	
		

	
};



$scope.id2List = [];
$scope.id2Disabled = false;
$scope.showID=function(list,obj)
	{
		
		if(obj!=undefined){
			var tempsc = $filter("filter")(list,{testGsId:obj},true);
			if(tempsc.length!=0)
				{
				 $scope.lightWellPartner.id =tempsc[0].lwID;
				$scope.lightWellPartner.productionGsId = tempsc[0].productionGsId;
				$scope.lightWellPartner.testIsaID = tempsc[0].testIsaID;
				$scope.lightWellPartner.productionIsaID= tempsc[0].productionIsaID;
				$scope.lightWellPartner.testIsaQualifier= tempsc[0].testIsaQualifier;
				 $scope.lightWellPartner.productionIsaQualifier= tempsc[0].productionIsaQualifier;
				$scope.id2Disabled = true;
				}
		}
		
		

	};
	

$scope.initSendToBuList = function()
{
	
	
	
	 $http.get('/TPIAdmin/app/sd/businessUnits')
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
		 angular.forEach(response.data, function (item1) {
			 
			 angular.forEach(item1.subUnits, function (item) {
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

	
	$scope.shouldShow = function(list){
		
		
		
		return (list.subUnitName != 'All' );
		

	
}; 

	
	
	
	
	
	
	
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
	
	
	
	
	
	$scope.filteredTodos = [];
	 $scope.currentPage = 1;
	  $scope.numPerPage = 25;
	  $scope.maxSize = 5;
	  
	 
	  
	  
	  
	  var cols = [{
		    name: 'partnerName',
		    orderDesc: true
		  }, {
		    name: 'serviceCategoryName',
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
			  $("#extra1").scrollTop(0);
			  setTimeout(function() { $("#SHhErrMsg").hide(); }, 5000);
		  }
	  
	  
	  
	  
		  
		  $scope.$watch('currentPage + numPerPage', function() {
			  $scope.createPagination();
		  });
		  
	$scope.createPagination = function(){
		var begin = (($scope.currentPage - 1) * $scope.numPerPage);
		var end = begin + $scope.numPerPage;
		
		
	    
	    $scope.filteredTodos =  $scope.items.slice(begin, end);
	    $scope.searchPage($scope.spName,$scope.prodISA,$scope.prodGS,true);
	};
	
	$scope.tsList = [];
	$scope.repeatList= [];
	var counter=0;
	$scope.hideRepeating=function(item)
	{
		console.log(counter);
		
		if(item!=undefined)
			{
		var temp = $filter("filter")($scope.repeatList, {id:item.id});
		if(temp.length!=0)
			{
			return false;
			}
		else
			{
			$scope.repeatList.push(item);
			return true;
			}
			}
		counter++;
	};
	
	
	$scope.enableData=function(list)
	{
		 angular.forEach(list, function (item) {
			 
			 
			 item.show=$scope.hideRepeating(item);
			 
			 });
			
	};
	$scope.searchPage = function(search,search1,search2,flag){
		var searchobj={};
		var emptyList=[];
		$scope.repeatList=angular.copy(emptyList);
	var doempty=false;
		if(search!=undefined &&search!=null)
			{
			searchobj.partnerName=search;
			doempty=true;
			}
		if(search1!=undefined &&search1!=null)
		{
		searchobj.prodIsaId=search1;
		doempty=true;
		}
		
		if(search2!=undefined &&search2!=null)
		{
		searchobj.prodGsId=search2;
		doempty=true;
		}
		var temp = $filter("filter")($scope.items, searchobj);
		if(temp.length!=0)
			{
			 $scope.tsList = angular.copy(temp);
			var begin = (($scope.currentPage - 1) * $scope.numPerPage);
			var end = begin + $scope.numPerPage;
		    
		    $scope.filteredTodos =$scope.tsList.slice(begin, end);
		    angular.forEach($scope.filteredTodos, function (item) {
		    item.show=$scope.hideRepeating(item);
		    });
			}
		else{
			
var begin = (($scope.currentPage - 1) * $scope.numPerPage), end = begin + $scope.numPerPage;
		    
		    $scope.filteredTodos = $scope.items.slice(begin, end);
		    if(doempty)
		    	{
		    var empty=[];
		    $scope.filteredTodos = angular.copy(empty);
		    	}
		    else
		    	{
		    	 $scope.filteredTodos =$scope.tsList.slice(begin, end);
		    	}
		}
		//$scope.enableData($scope.filteredTodos);
		if(flag==undefined)
		{
			$scope.currentPage=1;
		}
	};
	
	
	
	
	
	
	
	
	
	
	
	$scope.sortType     = 'name'; 
	  $scope.sortReverse  = false;
	$scope.message="";
	$scope.tpplist=[];
	$scope.ErrorMsg1 = false;
	$scope.tppCodeList=[];
	$scope.emptylList=[];
	$scope.scSelectDisabled = false;
	$scope.idsDisabled = true;	
	$scope.selectAllDisabled = true;
	$scope.disableId = function()
	{
	$scope.idsDisabled = false;	
	}
	$scope.upbsbt=false;
	$scope.upgrbt=false;
	
/*	$scope.disableServiceC= function(id)
	{
		//$scope.scSelectDisabled = false;
		$scope.bsList(id);
		
	} */
	
	$scope.scNull = function(){
	    
		$scope.enabletppn=true;
		$scope.tppName = null;
		$scope.selecttpp = null;
		$scope.lightWellPartner = null;
		$scope.srId = null;
		$scope.notes = null;
		$scope.segmentDelimiter = null;
		$scope.compositeElementDelimiter = null;
		$scope.elementDelimiter = null;
		$scope.repeatDelimiter = null;
		$scope.delimitersDisabled = false;
		$scope.lightWellPartnerDisabled = false;
		$scope.bussService.businessService = null;
		$scope.bussService.serviceCategory = null;
		$scope.bussService.version = null;
		$scope.bussService.srId = null;
		$scope.bussService.protocol = null;
		$scope.bussService.map = null;
		$scope.bussService.ack = null;
		$scope.bussService.ackperiod = null;
		$scope.bussService.notes = null;
		$scope.bussService.complianceCheck= null;
		$scope.bussService.stControlNum = null;
		$scope.bussService.isaControlNum = null;
		$scope.bussService.stAcceptorLookUpAlias = null;
		$scope.bussService.gsIdVersion = null;
		
		
	
};
	$scope.tppNull = function(){
		$scope.srId = null;
		$scope.notes = null;
		
			$scope.bussService.businessService = null;
			$scope.bussService.serviceCategory = null;
			$scope.bussService.version = null;
			$scope.bussService.srId = null;
			$scope.bussService.protocol = null;
			$scope.bussService.map = null;
			$scope.bussService.ack = null;
			$scope.bussService.ackperiod = null;
			$scope.bussService.notes = null;
			$scope.bussService.complianceCheck= null;
			$scope.bussService.stControlNum = null;
			$scope.bussService.isaControlNum = null;
			$scope.bussService.stAcceptorLookUpAlias = null;
			$scope.bussService.gsIdVersion = null;
		
	}
	

	
	
	$scope.revertService = function(){
		var emptyL = [];
		$scope.bussServiceList = angular.copy(emptyL);
		$scope.selecttpp = null;
		$scope.upgrbt=false;
		$scope.tppName = null;
		$scope.protocolList();
		$scope.bsList($scope.scname);
		$scope.srId = null;
		$scope.notes = null;
		$scope.lightWellPartner.testIsaID = null;
		$scope.lightWellPartner.productionIsaID = null;
		$scope.lightWellPartner.testIsaQualifier = null;
		$scope.lightWellPartner.productionIsaQualifier = null;
		$scope.lightWellPartner.testGsId = null;
		$scope.lightWellPartner.productionGsId = null;
		$scope.segmentDelimiter = null;
		$scope.compositeElementDelimiter = null;
		$scope.elementDelimiter = null;
		$scope.repeatDelimiter = null;
		
		$scope.revertBusinessServiceAll();
		$scope.bussService.srId = null;
	}
	$scope.revertBusinessServiceAll = function(){
		$scope.servError=false;
		$scope.editService=false;
		$scope.upbsbt=false;
		$scope.bussService.businessService =null;
		$scope.bussService.version =null;
		$scope.bussService.srId = $scope.SerId2;
		$scope.bussService.protocol = null;
		$scope.bussService.map = null;
		$scope.bussService.ack = null;
		$scope.bussService.complianceCheck = null;
		$scope.bussService.stControlNum = null;
		$scope.bussService.isaControlNum = null;
		$scope.bussService.stAcceptorLookUpAlias = null;
		$scope.bussService.gsIdVersion = null;
		$scope.bussService.ackperiod = null;
		$scope.bussService.notes = null;
		$scope.bussService.serviceCategory = null;
	};
	$scope.upbsbt=false;
	$scope.ttpNameList=[];
$scope.bussService={};
$scope.emp={};
$scope.bussServiceList=[];
$scope.servError=false;
$scope.upbsbt=false;
	$scope.addBusinessService = function(flag,data)
	{
		$scope.upbsbt=false;
		$scope.servError=false;
		if(!flag)
			{
			$scope.servError=true;
			return false;
			}
		if($scope.editService)
			{
			
			$scope.deleteBService(editService);
	    	  editService=null;
			}
		
		$scope.editService=false;
		
		
		var obj=[],obj1=[];
		var tempobj={};
		
		
		
		
		
		var tempt = $filter("filter")($scope.bserviceList, {id:data.businessService});
		if(tempt.length!=0)
			{
			
			tempobj.businessService=tempt[0];
			
			}
		
		tempobj.notes=data.notes;
		
		
		tempobj.srId = data.srId ;
		
	//	tempobj.gsIdVersion = data.gsIdVersion;
		var tempbs = $filter("filter")($scope.serviceList, {id:data.serviceCategory});
		if(tempbs.length!=0)
			{
			
			tempobj.lightWellPartner=tempbs[0];
			
			}
		
	
		//tempobj.lightWellPartner=data.serviceCategory;
		
		$scope.bussServiceList.push(tempobj);
		var tempempty=[];
		$scope.bussService=angular.copy(tempempty);
		$scope.bussService.srId = $scope.SerId2;
		
	};
	$scope.editService=false;
	var editService=null;
$scope.SerId2=null;
	$scope.defaultSr = function(input){
		if(	$scope.bussService==null)
			{
		$scope.bussService={};
			}
		$scope.bussService.srId = input;
		$scope.SerId2= input;
	};
	
	$scope.showName=function(list,obj)
	{
		
		if(obj!=undefined){
			var tempsc = $filter("filter")(list, {id:obj});
			if(tempsc.length!=0)
				{
				return tempsc[0].display;
				}
		}
		
		return obj;	
		
	};
	$scope.showackP= false;
	$scope.showFields = false;
	
	

	
	
	
	
	$scope.editBService= function(item){
		$scope.upbsbt=true;
		
		$scope.bussService={};
		$scope.bussService.businessService=item.businessService.id;
		
		$scope.bussService.serviceCategory= item.lightWellPartner.id;
	/*	if(item.ack==true || item.ack == "true"){
		$scope.bussService.ack= "true";
		}
		else{
			$scope.bussService.ack = "false";
		}*/
		$scope.bussService.srId = item.srId;
		
		//$scope.showNm($scope.bserviceList,$scope.bussService.businessService);
        
         if(item.notes!=undefined&&item.notes!=null)
    	 {
	$scope.bussService.notes= item.notes;
    	 }
         
         
		editService=item;
		$scope.editService=true;
	};
	$scope.getforDB=function(dataList)
	{
		var temp = [];
		$scope.ServiceobjdbList = angular.copy(temp);
		
		angular.forEach(dataList, function (item) {
			
			var sarobj=
			{
					id:item.services.id,
					sdBusinessSubUnitId:{},
					lightWellPartner:{},
					srId : '""',
					notes:'""',
					businessServices:[]
				};
			
			
			
			var temps = $filter("filter")($scope.subBsList, {subUnitName:item.services.sdBusinessSubUnitId});
			if(temps.length!=0)
				{
				sarobj.sdBusinessSubUnitId= {id:temps[0].id};
				}
			
		sarobj.lightWellPartner.id = item.services.lightWellPartner.id;	
		sarobj.lightWellPartner.testIsaID = item.services.lightWellPartner.testIsaID;
			sarobj.lightWellPartner.productionIsaID = item.services.lightWellPartner.productionIsaID;
			sarobj.lightWellPartner.testIsaQualifier = item.services.lightWellPartner.testIsaQualifier;
			sarobj.lightWellPartner.productionIsaQualifier = item.services.lightWellPartner.productionIsaQualifier;
			sarobj.lightWellPartner.testGsId = item.services.lightWellPartner.testGsId;
			sarobj.lightWellPartner.productionGsId = item.services.lightWellPartner.productionGsId;
			
			sarobj.srId = item.services.srId;
			sarobj.notes = item.services.notes;
			
			
			
			
			angular.forEach(item.businessServices, function (item) {

				 
				 var ob={
							id:item.id,
							versionNum : item.versionNum,
						 serviceType:{id:item.businessService.id},
							 
							 lightWellPartner:{id:item.lightWellPartner.id },
							 
							 
							 srId:item.srId,
							 notes:item.notes
							 
				 };
			
			
				 sarobj.businessServices.push(ob);
				
			 });
			//serviceobj.push(sarobj);
			$scope.ServiceobjdbList.push(sarobj);
			
			 
		 });

		//console.log(JSON.stringify($scope.ServiceobjdbList));
		
		
		 $scope.cleargroup();
		 
	}
$scope.deleteBService= function(editService){
	var index = $scope.bussServiceList.indexOf(editService);
	  $scope.bussServiceList.splice(index, 1);	
	};
	
	
	
	
	
	
	$scope.validName=false;
	$scope.validateName = "";
	
	$scope.checkIDsandSubmit = function(flag)
	{
		
		
		
		$scope.validName=false;
		$scope.ErrorMsg=false;
		//$scope.validId=false;
		if ($scope.typeID != '1' || $scope.typeID == null || $scope.typeID == undefined ){
		var url= '/TPIAdmin/app/lightWellPartners/existsfor?testIsaId='+$scope.lightWellPartner.testIsaID+'&prodIsaId='+$scope.lightWellPartner.productionIsaID+'&testGsId='+$scope.lightWellPartner.testGsId+'&prodGsId='+$scope.lightWellPartner.productionGsId; 
		
		if($scope.lightWellPartner.id!=undefined && $scope.lightWellPartner.id!=null){
			url = url+'&lwId='+$scope.lightWellPartner.id;
		 }
		 
		 $http.get(url )
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
		        	
		        	
		        	$scope.addServiceCategory();
		        	
		        	
		        	}
		        else
		        	{
		        	$scope.validName=true;
		        	}
		        
		    });
		}
		
		else{
			$scope.addServiceCategory();
		}
		 };
	
	
	
	
	
	
	
	
	
	
	
	
	
	$scope.serviceList=[];
	$scope.ServiceobjList=[];
	$scope.ServiceobjdbList=[];
	$scope.addServiceCategory= function(){
		
		$scope.ErrorMsg1 = true;
		$scope.upgrbt=false;
		$scope.validName=false;
		$scope.enabletppn=true;
		//$scope.idsDisabled = true;	
		$scope.selectAllDisabled = true;
	//	$scope.protocolList();
		$scope.bsList($scope.scname);
		
		var objlist=[];
		if(	$scope.eidtser)
			{
			
    		//var index = $scope.ServiceobjList.indexOf(servdel);
    		//$scope.ServiceobjList.splice(index, 1);
			angular.forEach($scope.ServiceobjList, function (serviceobj) {
				//sdBusinessSubUnitIdn = "";
				var tppn = ""
								if(serviceobj.id==$scope.id)
									{
									//serviceobj.services.serviceCategory = $scope.serviceCategory;
									//serviceobj.services.segtmentDelimiter = $scope.segmentDelimiter;
									
									
									
									//serviceobj.services.lightWellPartner.id = $scope.lightWellPartner.id;
									var temprsbu = $filter("filter")($scope.subBsList, {id: $scope.sendToSBU}, true);
									if(temprsbu.length!=0)
										{
										serviceobj.services.sdBusinessSubUnitId = temprsbu[0].subUnitName;
										}
									else{
										angular.forEach($scope.subBsList, function (item1) {
											if(item1.id==item.services.sdBusinessSubUnitId)
												{
												serviceobj.services.sdBusinessSubUnitId=item1.subUnitName;
												}
										});
										
										
									}
									serviceobj.services.lightWellPartner.testIsaID = $scope.lightWellPartner.testIsaID;
									serviceobj.services.lightWellPartner.productionIsaID = $scope.lightWellPartner.productionIsaID;
									serviceobj.services.lightWellPartner.testIsaQualifier = $scope.lightWellPartner.testIsaQualifier;
									serviceobj.services.lightWellPartner.productionIsaQualifier = $scope.lightWellPartner.productionIsaQualifier;
									serviceobj.services.lightWellPartner.testGsId = $scope.lightWellPartner.testGsId;
									serviceobj.services.lightWellPartner.productionGsId = $scope.lightWellPartner.productionGsId;
									
									serviceobj.services.srId = $scope.srId;
									serviceobj.services.notes = $scope.notes;
									
									 var emptyListData=[];
									 serviceobj.businessServices=angular.copy(emptyListData);
									angular.forEach($scope.bussServiceList, function (item) {
										 
										/* var ob={
												 serviceType:item.businessService.id,
													 version:item.version.id
										 };*/
										
										 serviceobj.businessServices.push(item);
									 });
										 //$scope.ServiceobjList.push(serviceobj);
									
									}
								 
							 });
			
			$scope.eidtser=false;
			}
		else
			{
			
			
		
		var serviceobj={
				
				services:{},
				businessServices:[],
				services:{
					lightWellPartner:{},
					sdBusinessSubUnitId:{}
				
				}
				
		};
		serviceobj.partner = $scope.selected;
		idn=idn+1;
		serviceobj.id=idn;
		var temprsbu = $filter("filter")($scope.subBsList, {id: $scope.sendToSBU});
		if(temprsbu.length!=0)
			{
			serviceobj.services.sdBusinessSubUnitId = temprsbu[0].subUnitName;
			}
		//serviceobj.services.serviceCategory = $scope.serviceCategory;
		//serviceobj.services.segtmentDelimiter = $scope.segmentDelimiter;
		
		//serviceobj.services.lightWellPartner.id = $scope.lightWellPartner.id;
		serviceobj.services.lightWellPartner.testIsaID = $scope.lightWellPartner.testIsaID;
		serviceobj.services.lightWellPartner.productionIsaID = $scope.lightWellPartner.productionIsaID;
		serviceobj.services.lightWellPartner.testIsaQualifier = $scope.lightWellPartner.testIsaQualifier;
		serviceobj.services.lightWellPartner.productionIsaQualifier = $scope.lightWellPartner.productionIsaQualifier;
		serviceobj.services.lightWellPartner.testGsId = $scope.lightWellPartner.testGsId;
		serviceobj.services.lightWellPartner.productionGsId = $scope.lightWellPartner.productionGsId;
		serviceobj.services.srId = $scope.srId;
		serviceobj.services.notes = $scope.notes;
		
		
		angular.forEach($scope.bussServiceList, function (item) {
		 
			/* var ob={
					 serviceType:item.businessService.id,
						 version:item.version.id
			 };*/
			 serviceobj.businessServices.push(item);
		 });
			 
		$scope.ServiceobjList.push(serviceobj);
		console.log(JSON.stringify($scope.ServiceobjList));
		
		
	//	$scope.getforDB();
			}
		$scope.getforDB($scope.ServiceobjList);
			 $scope.cleardata();
		//serviceobj.businessServices.version = $scope.bussService.version;
		
		//console.log(JSON.stringify($scope.ServiceobjList));
		
		
	};
	var idn=1;
	$scope.SaveService=function()
	{
		
			
			var serviceobj={
					
					partner:{},
					serviceCategory:{},
					
					businessServices:{},
					services:[],
					
			};
			var sarobj=
			{
					
					lightWellPartner:{},
					sdBusinessSubUnitId:{},
					srId:'""',
					notes:'""'
				};
			serviceobj.partner.id = $scope.selected;
			
		serviceobj.serviceCategory.id = $scope.scname;
			//serviceobj.services.segtmentDelimiter = $scope.segmentDelimiter;
		serviceobj.services.sdBusinessSubUnitId = $scope.sendToSBU;
		//	sarobj.lightWellPartner.id = $scope.lightWellPartner.id;
			sarobj.lightWellPartner.testIsaID = $scope.lightWellPartner.testIsaID;
			sarobj.lightWellPartner.productionIsaID = $scope.lightWellPartner.productionIsaID;
			sarobj.lightWellPartner.testIsaQualifier = $scope.lightWellPartner.testIsaQualifier;
			sarobj.lightWellPartner.productionIsaQualifier = $scope.lightWellPartner.productionIsaQualifier;
			sarobj.lightWellPartner.testGsId = $scope.lightWellPartner.testGsId;
			sarobj.lightWellPartner.productionGsId = $scope.lightWellPartner.productionGsId;
			sarobj.srId = $scope.srId;
			sarobj.notes = $scope.notes;
			
			angular.forEach($scope.bussServiceList, function (item) {
			
			 
				 var ob={
						 lightWellPartner:item.lightWellPartner.id,
						 serviceType:item.businessService.id,
							 
							 srId:item.srId,
							notes:item.notes
							
						 
				 };
				 sarobj.businessServices=angular.copy(obj);
				
			 });
			serviceobj.push(sarobj);
			 $scope.ServiceobjList.push(serviceobj);
			
			
	};
	$scope.id=null;
	var servdel=null;
	$scope.eidtser=false;
    $scope.editServiceCategory= function(item){
    	servdel=item;
    	$scope.validName=false;
    	$scope.upgrbt=true;
    	$scope.eidtser=true;
    	$scope.id=item.id;
    	$scope.bussService.srId = $scope.SerId2;
    	
    	
    	
    	$scope.lightWellPartner={};
    	$scope.selecttpp = false;
    	
    	var temprsbu = $filter("filter")($scope.subBsList, {subUnitName: item.services.sdBusinessSubUnitId});
    	if(temprsbu.length!=0)
		 {
    		$scope.sendToSBU = temprsbu[0].id;
		 }
    	
    	else{
			angular.forEach($scope.subBsList, function (item1) {
				if(item1.subUnitName==item.services.sdBusinessSubUnitId)
					{
					$scope.sendToSBU=item1.id;
					}
			});
			
			
		}
    	$scope.initSource($scope.subBsList,$scope.sendToSBU);
    //	$scope.tppName= item.services.tpp;
   // $scope.typeID = item.services.typeID;
    	$scope.lightWellPartner.id = item.services.lightWellPartner.id;
    	$scope.lightWellPartner.testIsaID = item.services.lightWellPartner.testIsaID;
		 $scope.lightWellPartner.productionIsaID = item.services.lightWellPartner.productionIsaID;
		 $scope.lightWellPartner.testIsaQualifier = item.services.lightWellPartner.testIsaQualifier;
		 $scope.lightWellPartner.productionIsaQualifier = item.services.lightWellPartner.productionIsaQualifier;
		 $scope.lightWellPartner.testGsId = item.services.lightWellPartner.testGsId;
		 $scope.lightWellPartner.productionGsId = item.services.lightWellPartner.productionGsId;
		 $scope.srId = item.services.srId;
		 $scope.notes = item.services.notes;
		 $scope.defaultSr($scope.srId);
		/* if($scope.tppName == null || $scope.tppName == undefined){
			 $scope.bsList($scope.scname);
			 $scope.protocolList();
			 
		 } 
		 if($scope.tppName != null || $scope.tppName != undefined){
		 $scope.bspList($scope.scname,$scope.tppName);
		 $scope.pcolList($scope.tppName);
		 }*/
		 $scope.bussServiceList = angular.copy($scope.emptylList);
		 angular.forEach(item.businessServices, function (item1) {
				$scope.bussServiceList.push(item1);
				 
			});
		
		// $scope.bussService.businessService=item.businessService.id;
		//$scope.bussService.version= item.version.id;
    //	$scope.bussServiceList = angular.copy($scope.emptylList);
    	//$scope.bussService =angular.copy($scope.emp);
	};
	
	var delsertempobj=null;
    $scope.deleteServiceCategory= function(item,list){
    	
    		var index = list.businessServices.indexOf(item);
    		list.businessServices.splice(index, 1);
    		$scope.getforDB($scope.ServiceobjList);
    	
    	
	};  
	
	$scope.inittpp = function()
	{
		
		
		
		 $http.get('/TPIAdmin/app/tpps')
	     .success(function (response, status, headers, config) {
	    	 $scope.tpplist = angular.copy(response);
	    	// console.log(JSON.stringify($scope.tpplist));
	     })
	     .error(function (data, status, header, config) {
	         $scope.ResponseDetails = "Data: " + data +
	             "<br />status: " + status ;
	         //    "<br />headers: " + jsonFilter(header) +
	           //  "<br />config: " + jsonFilter(config);
	     })
		 .then(function(response) {
			 var temp=[];
			 var temptpp=[];
			 var tempdeli = [];
			 angular.forEach($scope.tpplist, function (item) {
				 if(item.lightWellPartner!=null)
					 {
				 var obj={
						 id:item.id,
						 typeID : item.type.id,
						 lwID : item.lightWellPartner.id,
						 testIsaID:item.lightWellPartner.testIsaID,
						 productionIsaID:item.lightWellPartner.productionIsaID,
						 testIsaQualifier:item.lightWellPartner.testIsaQualifier,
						 productionIsaQualifier:item.lightWellPartner.productionIsaQualifier,
						 testGsId:item.lightWellPartner.testGsId,
						 productionGsId:item.lightWellPartner.productionGsId
				 };
				 temp.push(obj);
					 }
				 var obj2=
					 {
						id:item.id,
						name:item.name,
						type:item.type.typeCode
					 };
				 temptpp.push(obj2);
				 if(item.repeatDelimiter!=null){
				 var obj3={
						id:item.id,
				segmentDelimiter : item.segmentDelimiter.id,
				elementDelimiter  : item.elementDelimiter.id,
				compositeElementDelimiter:item.compositeElementDelimiter.id,
				
				repeatDelimiter : item.repeatDelimiter.id
				 }
				}
				 else{
					 var obj3={
								id:item.id,
						segmentDelimiter : item.segmentDelimiter.id,
						elementDelimiter  : item.elementDelimiter.id,
						compositeElementDelimiter:item.compositeElementDelimiter.id,
						repeatDelimiter :{}
						
						 } 
				 }
				tempdeli.push(obj3);
        	});
			 $scope.ttpNameList=angular.copy(temptpp);
			 $scope.tppCodeList=angular.copy(temp);
			 $scope.tppdelimitersList = angular.copy(tempdeli);
		    });
		 };
		 $scope.lightWellPartner={};
		 $scope.emptyList={};
		 $scope.lightWellPartnerDisabled=false;
		
		 $scope.select3PPName=function(id)
		 {
			 $scope.lightWellPartner=angular.copy( $scope.emptyList);
			 $scope.lightWellPartnerDisabled=false;
			 angular.forEach($scope.tppCodeList, function (item) {
				 if(item.id==id)
					 {
					// $scope.lwID = item.lwID;
					 $scope.typeID = item.typeID;
				 $scope.lightWellPartner.id =item.lwID;
				 $scope.lightWellPartner.testIsaID=item.testIsaID;
				 $scope.lightWellPartner.productionIsaID=item.productionIsaID;
				 $scope.lightWellPartner.testIsaQualifier=item.testIsaQualifier;
				 $scope.lightWellPartner.productionIsaQualifier=item.productionIsaQualifier;
				 $scope.lightWellPartner.testGsId=item.testGsId;
				 $scope.lightWellPartner.productionGsId=item.productionGsId;
				 $scope.lightWellPartnerDisabled=true;
					 }
			 });
						
				
			 
				
			 
		 }
		 
		 
		 
		/* $scope.partChange1=function(pt)
		 {
			 console.log(JSON.stringify($scope.selected));
		//	 var temp = $filter("filter")($scope.partnerlist, {PracticeByFacilityId:id});
			 if($scope.selected==undefined)
				{
				 $scope.selectAllDisabled = true;
				 $scope.message = "partner name does not exist";
				}
			 else
				 {
				 $scope.selectAllDisabled = false;
				 $scope.message = " ";
				 }
		 };  
		 */
		 
		 $scope.partChange=function(pt)
		 {
			 console.log(JSON.stringify($scope.selected));
		//	 var temp = $filter("filter")($scope.partnerlist, {PracticeByFacilityId:id});
			 if($scope.selected==undefined)
				{
				 $scope.selectAllDisabled = true;
				 $scope.message = "partner name does not exist";
				 
				}
			 else
				 {
				 $scope.selectAllDisabled = false;
				 $scope.message = " ";
				 }
		 };
		 
		 $scope.partnerlist=[];
		 $scope.enabletppn=true;
			$scope.changeSelTpp=function()
			{
				$scope.enabletppn=!$scope.enabletppn;
				if($scope.enabletppn==true)
					{
					$scope.tppName=null;
					$scope.segmentDelimiter = null;
					$scope.elementDelimiter = null;
					$scope.compositeElementDelimiter = null;
					$scope.repeatDelimiter = null;
					$scope.srId= null;
					$scope.notes= null;
					
					$scope.lightWellPartner=angular.copy( $scope.emptyList);
					$scope.lightWellPartnerDisabled=false;
					 $scope.delimitersDisabled=false; 
					}
			};
		 $scope.findPartner = function(name)
			{
				
				$scope.editEnable=false;
				var tempList=[];
				 $http.get('/TPIAdmin/app/partners?projection=partnerName')
			     .success(function (response, status, headers, config) {
			    	 $scope.partnerlist=angular.copy(response);
			    // console.log("inputdata "+JSON.stringify(response));
			     })
			     .error(function (data, status, header, config) {
			         $scope.ResponseDetails = "Data: " + data +
			             "<br />status: " + status ;
			         //    "<br />headers: " + jsonFilter(header) +
			           //  "<br />config: " + jsonFilter(config);
			     })
			     	
				 .then(function(response) {
					// $scope.versList();
				 });
					
			};
			
			
					
				$scope.serviceNameList=[];
				$scope.initname = function()
				{
					$scope.serviceNameList1 = [];
					
					 $http.get('/TPIAdmin/app/serviceCategories/distinctNames')
				     .success(function (response, status, headers, config) {
				    	 
				    	 $scope.serviceNameList1=angular.copy(response);
				    	// console.log("inputdata "+JSON.stringify(tempList));
				     })
				     .error(function (data, status, header, config) {
				    	
				         $scope.ResponseDetails = "Data: " + data +
				             "<br />status: " + status ;
				         //    "<br />headers: " + jsonFilter(header) +
				           //  "<br />config: " + jsonFilter(config);
				     })
				     	
					 .then(function(response) {
						  $scope.serviceNameList = $filter("filter")($scope.serviceNameList1, {name:"Intercompany"});
							
						  
					 });
						
				};
				
				
				$scope.serviceList=[];
				 
				 $scope.serviceId = function(id,item)
					{
						
						
						 $http.get('/TPIAdmin/app/serviceCategories/'+id)
					     .success(function (response, status, headers, config) {
					    	 var emptyListData=[];
					    	 $scope.serviceList=angular.copy(emptyListData);
					    	 
					    	 angular.forEach(response.lightWellPartners, function (item) {
					    		 var obj=
					    			 {
					    				id : item.id,
					    				//display:item.productionIsaID+"_"+item.productionGsId
					    				display:item.productionIsaID
					    			 };
					    		 $scope.serviceList.push(obj);
					    	 });
					    	// console.log("inputdata "+JSON.stringify(tempList));
					     })
					     .error(function (data, status, header, config) {
					    	
					         $scope.ResponseDetails = "Data: " + data +
					             "<br />status: " + status ;
					         //    "<br />headers: " + jsonFilter(header) +
					           //  "<br />config: " + jsonFilter(config);
					     })
					     	
						 .then(function(response) {
							 if(item!=undefined){
							 $scope.serviceCategory = item.serviceCategory.id;
							 }
							// $scope.dlSList();
							 
						 });
							
					};
			
					
					
					
					
					$scope.bserviceList=[];
					$scope.bsList = function(id)
					{
						
						
						 $http.get('/TPIAdmin/app/serviceTypes?serviceCat='+id)
					     .success(function (response, status, headers, config) {
					    	 
					    	 $scope.bserviceList = angular.copy(response);
					    	 
					     })
					     .error(function (data, status, header, config) {
					         $scope.ResponseDetails = "Data: " + data +
					             "<br />status: " + status ;
					         //    "<br />headers: " + jsonFilter(header) +
					           //  "<br />config: " + jsonFilter(config);
					     })
						 .then(function(response) {
						       
		                  $scope.myWelcome = response.data;
									 
						    });


						
						};
						//$scope.bservicepList = [];
						$scope.bspList = function(id,pid)
						{
							
							
							 $http.get('/TPIAdmin/app/serviceTypes?serviceCat='+id+'&tpp='+pid)
						     .success(function (response, status, headers, config) {
						    	 
						    	 $scope.bserviceList = angular.copy(response);
						    	 
						     })
						     .error(function (data, status, header, config) {
						         $scope.ResponseDetails = "Data: " + data +
						             "<br />status: " + status ;
						         //    "<br />headers: " + jsonFilter(header) +
						           //  "<br />config: " + jsonFilter(config);
						     })
							 .then(function(response) {
							    console.log(JSON.stringify($scope.bserviceList));   
			                  $scope.myWelcome = response.data;
										 
							    });


							
							};
$scope.cleargroup=function()
{
	$scope.segmentDelimiter =null;
	$scope.elementDelimiter =null;
	$scope.compositeElementDelimiter =null;
	$scope.repeatDelimiter =null;

	$scope.lightWellPartner={};
	$scope.sendToSBU = null;
	$scope.selecttpp = false;
	$scope.tppName= null;
	$scope.srId = null;
	$scope.notes = null;
	
	$scope.bussServiceList = angular.copy($scope.emptylList);
	$scope.bussService =angular.copy($scope.emp);

	
};


$scope.onlyNew = false;
$scope.comboCheck = function(id1,id2)
{
	$scope.combo = false;
	if(!$scope.onlyNew ){
		$scope.saveSS();
		return true;
	}
	
	 $http.get('/TPIAdmin/app/serviceSubscriptions/exists?partnerId='+id1.id+'&serviceCategoryId='+id2)
     .success(function (response, status, headers, config) {
    	 var temp =  angular.copy(response);
    	
    	 if(temp!="true"){
    		$scope.saveSS();
    	}
    	else{
    	$scope.combo = true;	
    	}
    // console.log("inputdata "+JSON.stringify(response));
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
$scope.cdata = function(){
	$('.extra1').scrollTop(0);
	$scope.idd = null;
	$scope.upbsbt=false;
	$scope.upgrbt=false;
	$scope.validName=false;
	$scope.servError=false;
	$scope.ErrorMsg1 = false;
	$scope.combo = false;
	$scope.enabletppn = true;
	$scope.scSelectDisabled = false;
	$scope.idsDisabled = true;	
	$scope.selectAllDisabled = true;
	$scope.hideEdit = false;
	//$scope.scSelectDisabled = true;
//	$scope.selectAllDisabled = true;
//	$scope.idsDisabled = true;
	//$scope.enabletppn = true;
	//$scope.selected = null;
	$scope.message="";
	//$scope.serviceCategory=null;
	$scope.selected = null;
	$scope.scname = null;
	$scope.selecttpp = null;
	$scope.lightWellPartner={};
	$scope.sendToSBU = null;
	 $scope.emptyList={};
	 $scope.lightWellPartnerDisabled=false;
	 $scope.delimitersDisabled=false; 
	$scope.tppName = null;
	$scope.segmentDelimiter =null;
	$scope.srId = null;
	$scope.notes = null;
	$scope.elementDelimiter =null;
	$scope.compositeElementDelimiter =null;
	$scope.repeatDelimiter =null;
//	$scope.bussService.businessService =null;
	//$scope.bussService.version =null;
	//$scope.ServiceobjList = angular.copy($scope.emptylList);
	$scope.bussServiceList = angular.copy($scope.emptylList);
	$scope.bussService =angular.copy($scope.emptylList);
	$scope.ServiceobjList = angular.copy($scope.emptylList);
};					
                          $scope.cleardata=function()
							{
								$scope.upbsbt=false;
								$scope.servError=false;
								$scope.ErrorMsg1 = false;
								//$scope.scSelectDisabled = true;
							//	$scope.selectAllDisabled = true;
							//	$scope.idsDisabled = true;
								//$scope.enabletppn = true;
								//$scope.selected = null;
								$scope.message="";
								//$scope.serviceCategory=null;
								$scope.selecttpp = null;
								$scope.lightWellPartner={};
								$scope.sendToSBU = null;
								 $scope.emptyList={};
								 $scope.lightWellPartnerDisabled=false;
								 $scope.delimitersDisabled=false; 
								$scope.tppName = null;
								$scope.segmentDelimiter =null;
								$scope.elementDelimiter =null;
								$scope.compositeElementDelimiter =null;
								$scope.repeatDelimiter =null;
								$scope.srId  = null;
								$scope.notes  = null;
							//	$scope.bussService.businessService =null;
								//$scope.bussService.version =null;
								//$scope.ServiceobjList = angular.copy($scope.emptylList);
								$scope.bussServiceList = angular.copy($scope.emptylList);
								$scope.bussService =angular.copy($scope.emp);
							};
				$scope.saveSS = function(){
					//delsertempobj=item;
		    	//	var index1 = $scope.ServiceobjdbList.indexOf(item);
		    	//	$scope.ServiceobjdbList.splice(index, 1);	
					var submitObj=
						{
							id : $scope.idd,
							versionNum : $scope.versionNum,
							partner : {id:$scope.selected.id},
							
							serviceCategory : {id:$scope.scname},
							services:$scope.ServiceobjdbList
						};
							$http.post("/TPIAdmin/app/intercompanySubscriptions",submitObj)
							.success(function (response, status, headers, config) {
								 //$scope.grouplist = angular.copy(response);
								$scope.idd=angular.copy(response.id); 
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
								$scope.clickModal();
							    
							});
									
									
								}
				
				$scope.items = [];
				$scope.items1 = [];
				$scope.inittable = function()
				{
					
					var tableList=[];
					 $http.get('/TPIAdmin/app/search?q=serviceSubscriptionsWithLW')
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
							 if(item.serviceCategoryName.indexOf("Intercompany")!=-1){	 
							 try
							 {var obj=
							 {       id:item.serviceSubscriptionId, 
									 partnerName:item.partnerName,
									 serviceCategoryName:item.serviceCategoryName,
									 prodGsId:item.prodGsId,
									 prodIsaId:item.prodIsaId,
									
								 };
							 obj.show=$scope.hideRepeating(obj);
							 $scope.items.push(obj);
							 
							 var obj1=
							 {       id:item.serviceSubscriptionId, 
									 partnerName:item.partnerName,
									 serviceCategoryName:item.serviceCategoryName,
									 
								 };
							 
							if($scope.items1!= null){
								var temp = $filter("filter")($scope.items1, obj1);
								if(temp.length!=0)
									{
									//throw error message from here
									
									return false;
									}
							}
							
							var obj2=
							 {       id:item.serviceSubscriptionId, 
									 partnerName:item.partnerName,
									 serviceCategoryName:item.serviceCategoryName,
									 
								 };
							
							 $scope.items1.push(obj2); 
							
							 }catch(e)
							 {}
							 }
							 });
						 $scope.totalItems = $scope.items.length;
							
						 
						 $scope.createPagination();	 
						 $scope.sortData('partnerName');	 
					 });
					 console.log("list "+JSON.stringify($scope.items) );	
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
				
				$scope.initdisplay = function(item)
				{
					$scope.deleteId=item;
					$scope.hideremove=false;
					var displayList={};
					 $http.get('/TPIAdmin/app/serviceSubscriptions/'+item.id)
				     .success(function (response, status, headers, config) {
				    	 
				    	 displayList=angular.copy(response);
				    	//console.log("inputdata "+JSON.stringify(response));
				     })
				     .error(function (data, status, header, config) {
				    	
				         $scope.ResponseDetails = "Data: " + data +
				             "<br />status: " + status ;
				         //    "<br />headers: " + jsonFilter(header) +
				           //  "<br />config: " + jsonFilter(config);
				     })
				     	
					 .then(function(response) {
						$scope.showData(displayList); 
						  
					 });
					 console.log("list "+JSON.stringify($scope.items) );	
				};
				$scope.nulllist = [];
				$scope.enabletypeEdit = false;
				$scope.deleteId=null;
				
				
				
				
				
				
				
				
				
				$scope.showData=function(item)
				{
					//$scope.disableDilimeters();
					$scope.showackP= true;
					$scope.showFields = true;
					$scope.idd=item.id;
					$scope.versionNum = item.versionNum;
					$scope.scSelectDisabled = false;
					$scope.enabletypeEdit = true;
					$scope.enabletppn=true;
					$scope.idsDisabled = false;
					$scope.onlyNew=false;
					$scope.hideEdit=true;
					$scope.ServiceobjList=angular.copy($scope.nulllist);
					$scope.selected = item.partner;
					$scope.partChange(item.partner.id);
				   //  $scope.protocolList();
					
					
					$scope.serviceId(item.serviceCategory.id,item);
					//$scope.disableServiceC(item.serviceCategory.id);
					
                     
					$scope.scname = item.serviceCategory.id;
					
					$scope.bsList(item.serviceCategory.id);
					
				//$scope.serviceCategory = item.serviceCategory.id;
				
					angular.forEach(item.services, function (data) {
						 
						 var serviceobj={
								 
								services:{ lightWellPartner:{},
									},
								
								 businessServices:[],
								 }
						 serviceobj.id=data.id;
						serviceobj.services.id=data.id;
						 serviceobj.services.versionNum=data.versionNum;
						
						
						serviceobj.services.sdBusinessSubUnitId = data.sdBusinessSubUnitId.subUnitName;
						 
						$scope.initSource($scope.subBsList,data.sdBusinessSubUnitId.subUnitName); 
						
						// $scope.lightWellPartner.id = data.lightWellPartner.id;
						 serviceobj.services.lightWellPartner.id = data.lightWellPartner.id;
						 serviceobj.services.lightWellPartner.testIsaID = data.lightWellPartner.testIsaID;
						 serviceobj.services.lightWellPartner.testIsaQualifier = data.lightWellPartner.testIsaQualifier;
						 serviceobj.services.lightWellPartner.testGsId = data.lightWellPartner.testGsId;
						 serviceobj.services.lightWellPartner.productionIsaID= data.lightWellPartner.productionIsaID;
						 serviceobj.services.lightWellPartner.productionIsaQualifier = data.lightWellPartner.productionIsaQualifier;
						 serviceobj.services.lightWellPartner.productionGsId = data.lightWellPartner.productionGsId;
						 serviceobj.services.srId = data.srId;
						 serviceobj.services.notes = data.notes;

						 angular.forEach(data.businessServices, function (temp) {
							 
							 
							 var  businessServiceobj =  angular.copy(temp.serviceType);
							var lightWellPartnerobj = angular.copy(temp.lightWellPartner);
							 
							 var srId = angular.copy(temp.srId);
							 
							 var notes = angular.copy(temp.notes);
							
                         //  var businessServiceobj = {};
                          //  businessServiceobj.businessServiceName = temp.serviceType.businessServiceName;
						 var finalobj = {
                                   id:temp.id,
                                   versionNum:temp.versionNum,
								 businessService:businessServiceobj,
								 
								 lightWellPartner:lightWellPartnerobj,
								 
								 srId: srId,
								 notes: notes
								 
						 }
								 
						serviceobj.businessServices.push(finalobj);
						 });
						//serviceobj.businessServices.push(item);
						 $scope.ServiceobjList.push(serviceobj);
					//	 
						// console.log(JSON.stringify($scope.ServiceobjList));
					 });
					
					$scope.getforDB($scope.ServiceobjList);
					// $scope.shwdtDisable=false;

					 
				
					 };
					 
					 
					 $scope.shwdtDisable=false;
					 
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
						$scope.user.msg=$scope.selected.partnerName +" has been subscribed";
						$scope.user.del=false;
						$scope.user.edit=false;
							}
						else 
							{
							$scope.user.msg=$scope.selected.partnerName +" subscription has been updated";
							}
						if($scope.user.del)
						{
							$scope.user.msg="Are you sure to delete the "+$scope.selected.partnerName+" Partner ?";	
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
				             $http.delete('/TPIAdmin/app/serviceSubscriptions/' + $scope.deleteId.id)
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
					
					
					
				   
					
					
				
	
			 var self = this,  j= 0, counter = 0;
	            self.modes = [ ];
	            self.activated = true;
	            self.determinateValue = 30;
	            self.toggleActivation = function() {
	               if ( !self.activated ) self.modes = [ ];
	               if (  self.activated ) j = counter = 0;
	            };
	            
	            $interval(function() {
	               self.determinateValue += 1;
	               if (self.determinateValue > 100) {
	                  self.determinateValue = 30;
	               }
	               if ( (j < 5) && !self.modes[j] && self.activated ) {
	                  self.modes[j] = 'indeterminate';
	               }
	               if ( counter++ % 4 == 0 ) j++;
	            }, 100, 0, true);
						
});