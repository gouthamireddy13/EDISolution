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

ABApp.filter('unique', function(list) {
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


ABApp.controller('serviceController', function($scope,$modal, $http, $window,$interval,$filter) {
	
	
	
	
	
	
	
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
	$scope.disdelitList=[];
	//
	$scope.elementdimitDisable=false;
	$scope.disableDilimeters = function(){
		var tempList=[];
		//$scope.disdelitList.indexOf($scope.elementDelimiter);
		$scope.disdelitList=angular.copy(tempList);
		if($scope.segmentDelimiter!=null&&$scope.segmentDelimiter!=undefined)
		{
			$scope.delimitersEList = angular.copy(tempList);
			angular.forEach( $scope.eBackList , function (item) {
				
				if(item.id!= $scope.segmentDelimiter){
					$scope.delimitersEList.push(item);	
				}
			});
			
		}
		if($scope.elementDelimiter!=null&&$scope.elementDelimiter!=undefined){
			
			
			$scope.delimitersCList = angular.copy(tempList);
				angular.forEach( $scope.cBackList , function (item) {
					
					if(item.id!= $scope.segmentDelimiter &&  item.id!= $scope.elementDelimiter ){
						$scope.delimitersCList.push(item);	
					}
				});
		}
		if($scope.compositeElementDelimiter!=null&& $scope.compositeElementDelimiter!=undefined){
		
			$scope.delimitersRList = angular.copy(tempList);
			angular.forEach( $scope.rBackList , function (item) {
				
				if(item.id!= $scope.segmentDelimiter &&  item.id!= $scope.elementDelimiter && item.id!= $scope.compositeElementDelimiter ){
					$scope.delimitersRList.push(item);	
				}
			});
		
		
		
		}
		//$scope.disdelitList.indexOf(elementDelimiter)
	}
	$scope.resetDilimeters = function(){
		if($scope.segmentDelimiter==null||$scope.segmentDelimiter==undefined){
			$scope.elementDelimiter=null;
			$scope.compositeElementDelimiter=null;
			$scope.repeatDelimiter=null;
			};
			
		if($scope.elementDelimiter==null||$scope.elementDelimiter==undefined){
				
				$scope.compositeElementDelimiter=null;
				$scope.repeatDelimiter=null;
				};
			
		if($scope.compositeElementDelimiter==null||$scope.compositeElementDelimiter==undefined){
					
					
					$scope.repeatDelimiter=null;
					};	
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
		
		var temp = $filter("filter")($scope.versionList, {id:data.version});
		if(temp.length!=0)
			{
			tempobj.version=temp[0];
		
			}
		
		var tempp = $filter("filter")($scope.pList, {id:data.protocol});
		if(tempp.length!=0)
			{
			tempobj.protocol=tempp[0];
		
			}
		if(data.map!= null || data.map!= undefined){
		var tempm = $filter("filter")($scope.mapList, {id:data.map});
		if(tempm.length!=0)
			{
			tempobj.map=tempm[0];
		
			}
		}
		else{
			tempobj.map = null;
		}
		var tempt = $filter("filter")($scope.bserviceList, {id:data.businessService});
		if(tempt.length!=0)
			{
			
			tempobj.businessService=tempt[0];
			
			}
		tempobj.ackPeriod=data.ackperiod;
		tempobj.notes=data.notes;
		tempobj.ack=data.ack;
		tempobj.complianceCheck = data.complianceCheck;
		tempobj.srId = data.srId ;
		tempobj.stControlNum = data.stControlNum;
		tempobj.isaControlNum = data.isaControlNum;
		tempobj.stAcceptorLookUpAlias = data.stAcceptorLookUpAlias;
		tempobj.gsIdVersion = data.gsIdVersion;
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
	$scope.delimitErr= false;
	$scope.checkdelimiter=function()
	{
		
		
		if($scope.segmentDelimiter==$scope.elementDelimiter)
		{
		$scope.delimitErr= true;
		}
		if($scope.elementDelimiter==$scope.compositeDelimiter)
		{
		$scope.delimitErr= true;
		}
		if($scope.compositeDelimiter==$scope.repeatDelimiter)
		{
		$scope.delimitErr= true;
		}
		
		else
	{
			$scope.delimitErr= false;
	}
	}
	$scope.showNm=function(list,obj)
	{
		
		
			var tempsc = $filter("filter")(list, {id:obj}, true);
			if(tempsc.length!=0)
				{
				$scope.showackP= tempsc[0].businessServiceName.indexOf("_O")!=-1;
				$scope.bussService.ackperiod = '36';
				}
			else
				
				{
				$scope.showackP= false;
				}
			if($scope.bussService.ack==null||$scope.bussService.ack==undefined||$scope.bussService.ack!='true')
				{
				$scope.showackP= false;
				}
			if($scope.showackP == false){
				$scope.bussService.ackperiod = undefined;
			}
		
		
	};
	
	$scope.showFour=function(list,obj)
	{
		
		
			var tempsc = $filter("filter")(list, {id:obj}, true);
			if(tempsc.length!=0)
				{
				$scope.showFields= tempsc[0].businessServiceName.indexOf("_O")!=-1;
				
				}
			else
				
				{
				$scope.showFields= false;
				
			
				}
			
			
		
		
	};
	
	
	$scope.editBService= function(item){
		$scope.upbsbt=true;
		
		$scope.bussService={};
		$scope.bussService.businessService=item.businessService.id;
		$scope.bussService.version= item.version.id;
		$scope.bussService.protocol= item.protocol.id;
		$scope.showMap($scope.bussService.businessService);
		if(item.map != undefined && item.map != null)
   	 {
		$scope.bussService.map= item.map.id;
   	 }
		else{
			$scope.bussService.map= null;
		}
		$scope.bussService.serviceCategory= item.lightWellPartner.id;
	/*	if(item.ack==true || item.ack == "true"){
		$scope.bussService.ack= "true";
		}
		else{
			$scope.bussService.ack = "false";
		}*/
		$scope.bussService.srId = item.srId;
		$scope.bussService.ack = item.ack.toString();
		$scope.bussService.complianceCheck = item.complianceCheck.toString();
		$scope.showNm($scope.bserviceList,$scope.bussService.businessService);
         if(item.ackPeriod!=undefined&&item.ackPeriod!=null)
        	 {
		$scope.bussService.ackperiod= item.ackPeriod.toString();
        	 }
         if(item.notes!=undefined&&item.notes!=null)
    	 {
	$scope.bussService.notes= item.notes;
    	 }
         
         if(item.stControlNum!=undefined&&item.stControlNum!=null)
    	 {
 		$scope.bussService.stControlNum = item.stControlNum;
    	 }
         if(item.isaControlNum!=undefined&&item.isaControlNum!=null)
    	 {
 		$scope.bussService.isaControlNum = item.isaControlNum;
    	 }
         if(item.stAcceptorLookUpAlias!=undefined&&item.stAcceptorLookUpAlias!=null)
    	 {
 		$scope.bussService.stAcceptorLookUpAlias = item.stAcceptorLookUpAlias;
    	 }
         if(item.gsIdVersion!=undefined&&item.gsIdVersion!=null)
    	 {
 		$scope.bussService.gsIdVersion = item.gsIdVersion;
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
					versionNum:item.services.versionNum,
					segmentDelimiter:{},
					elementDelimiter:{},
					compositeElementDelimiter:{},
					repeatDelimiter:{},
					lightWellPartner:{},
					srId : '""',
					notes:'""',
					businessServices:[]
				};
			
			
			
		if(item.services.tpp !=null)
			{
			var temps = $filter("filter")($scope.ttpNameList, {name:item.services.tpp});
			if(temps.length!=0)
				{
				sarobj.tpp= {id:temps[0].id};
				}
			
			}
			//serviceobj.services.segtmentDelimiter = $scope.segmentDelimiter;
		var temps1 = $filter("filter")($scope.delimitersSList, {delimiter:item.services.segmentDelimiter});
		if(temps1.length!=0)
			{
			sarobj.segmentDelimiter.id=temps1[0].id;
			}
		else
			{
		angular.forEach($scope.delimitersSList, function (item1) {
			if(item1.delimiter==item.services.segmentDelimiter)
				{
				sarobj.segmentDelimiter.id=item1.id;
				}
		});
			}
		var tempe = $filter("filter")($scope.eBackList, {delimiter:item.services.elementDelimiter});
		if(tempe.length!=0)
			{
			sarobj.elementDelimiter.id=tempe[0].id;
			}
		else
		{
	angular.forEach($scope.eBackList, function (item1) {
		if(item1.delimiter==item.services.elementDelimiter)
			{
			sarobj.elementDelimiter.id=item1.id;
			}
	});
		}
		
		var tempc = $filter("filter")($scope.cBackList, {delimiter:item.services.compositeElementDelimiter});
		if(tempc.length!=0)
			{
			sarobj.compositeElementDelimiter.id=tempc[0].id;
			}
		else
		{
	angular.forEach($scope.cBackList, function (item1) {
		if(item1.delimiter==item.services.compositeElementDelimiter)
			{
			sarobj.compositeElementDelimiter.id=item1.id;
			}
	});
		}
		if(item.services.repeatDelimiter != null || item.services.repeatDelimiter != undefined){
		var tempr = $filter("filter")($scope.rBackList, {delimiter:item.services.repeatDelimiter});
		if(tempr.length!=0)
			{
			sarobj.repeatDelimiter.id=tempr[0].id;
			}
		
		else{
			angular.forEach($scope.rBackList, function (item1) {
				if(item1.delimiter==item.services.repeatDelimiter)
					{
					sarobj.repeatDelimiter.id=item1.id;
					}
			});
			
			
		}
		}
		
		else{
			sarobj.repeatDelimiter = null;	
		}
		//sarobj.segmentDelimiter.id=$scope.segmentDelimiter;
				
		
				//sarobj.elementDelimiter.id=$scope.elementDelimiter;
				
						
				//sarobj.compositeElementDelimiter.id=$scope.compositeDelimiter;
				
			//serviceobj.services.elementDelimiter = $scope.elementDelimiter;
			//serviceobj.services.compositeElementDelimiter = $scope.compositeDelimiter;
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
			if(item.map != null || item.map != undefined){
			 
				 var ob={
							id:item.id,
							versionNum : item.versionNum,
						 serviceType:{id:item.businessService.id},
							 version:{id:item.version.id},
							 protocol:{id:item.protocol.id},
							 lightWellPartner:{id:item.lightWellPartner.id },
							 ack:item.ack,
							 complianceCheck:item.complianceCheck,
							 map:{id:item.map.id} ,
							 srId:item.srId,
							 notes:item.notes,
							 stControlNum : item.stControlNum || null,
							 isaControlNum: item.isaControlNum || null,
							 stAcceptorLookUpAlias: item.stAcceptorLookUpAlias || null,
							 gsIdVersion:item.gsIdVersion || null,
							 ackPeriod:item.ackPeriod
				 };
			}
			else{
				var ob={
						id:item.id,
						versionNum : item.versionNum,
						 serviceType:{id:item.businessService.id},
							 version:{id:item.version.id},
							 protocol:{id:item.protocol.id},
							 lightWellPartner:{id:item.lightWellPartner.id },
							 ack:item.ack,
							 complianceCheck:item.complianceCheck,
							 stControlNum : item.stControlNum || null,
							 isaControlNum: item.isaControlNum || null,
							 stAcceptorLookUpAlias: item.stAcceptorLookUpAlias || null,
							 gsIdVersion:item.gsIdVersion || null,
							 srId:item.srId,
							 notes:item.notes,
							 ackPeriod:item.ackPeriod
				 };
			}
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
		$scope.protocolList();
		$scope.bsList($scope.scname);
		
		var objlist=[];
		if(	$scope.eidtser)
			{
			
    		//var index = $scope.ServiceobjList.indexOf(servdel);
    		//$scope.ServiceobjList.splice(index, 1);
			angular.forEach($scope.ServiceobjList, function (serviceobj) {
				var tppn="";
								var temptpp = $filter("filter")($scope.ttpNameList, {id: $scope.tppName});
								if(temptpp.length!=0)
									{
									tppn = temptpp[0].name;
									}
								if(serviceobj.id==$scope.id)
									{
									//serviceobj.services.serviceCategory = $scope.serviceCategory;
									//serviceobj.services.segtmentDelimiter = $scope.segmentDelimiter;
									var temps = $filter("filter")($scope.delimitersSList, {id:$scope.segmentDelimiter},true);
									if(temps.length!=0)
										{
										serviceobj.services.segmentDelimiter=temps[0].delimiter;
										}
									var tempe = $filter("filter")($scope.delimitersEList, {id:$scope.elementDelimiter});
									if(tempe.length!=0)
										{
										serviceobj.services.elementDelimiter=tempe[0].delimiter;
										}
									var tempc = $filter("filter")($scope.delimitersCList, {id:$scope.compositeElementDelimiter});
									if(tempc.length!=0)
										{
										serviceobj.services.compositeElementDelimiter=tempc[0].delimiter;
									
										}
									
									if($scope.repeatDelimiter != null || $scope.repeatDelimiter != undefined){
									var tempr = $filter("filter")($scope.delimitersRList, {id:$scope.repeatDelimiter});
									if(tempr.length!=0)
										{
										serviceobj.services.repeatDelimiter=tempr[0].delimiter;
										}
									}
									
									else{
										serviceobj.services.repeatDelimiter = null;
									}
									//serviceobj.services.elementDelimiter = $scope.elementDelimiter;
									//serviceobj.services.compositeElementDelimiter = $scope.compositeDelimiter;
									//serviceobj.services.lightWellPartner.id = $scope.lightWellPartner.id;
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
					lightWellPartner:{}
				}
				
		};
		serviceobj.partner = $scope.selected;
		idn=idn+1;
		serviceobj.id=idn;
		if($scope.tppName!=null)
		{
			var temptpp = $filter("filter")($scope.ttpNameList, {id: $scope.tppName});
			if(temptpp.length!=0)
				{
				serviceobj.services.tpp = temptpp[0].name;
				}
		}
		//serviceobj.services.serviceCategory = $scope.serviceCategory;
		//serviceobj.services.segtmentDelimiter = $scope.segmentDelimiter;
		var temps = $filter("filter")($scope.delimitersSList, {id:$scope.segmentDelimiter});
		if(temps.length!=0)
			{
			serviceobj.services.segmentDelimiter=temps[0].delimiter;
			}
		var tempe = $filter("filter")($scope.delimitersEList, {id:$scope.elementDelimiter});
		if(tempe.length!=0)
			{
			serviceobj.services.elementDelimiter=tempe[0].delimiter;
			}
		var tempc = $filter("filter")($scope.delimitersCList, {id:$scope.compositeElementDelimiter});
		if(tempc.length!=0)
			{
			serviceobj.services.compositeElementDelimiter=tempc[0].delimiter;
			}
		if($scope.delimitersRList!=null){
		var tempr = $filter("filter")($scope.delimitersRList, {id:$scope.repeatDelimiter});
		if(tempr.length!=0)
			{
			serviceobj.services.repeatDelimiter=tempr[0].delimiter;
			}
		}
		//serviceobj.services.elementDelimiter = $scope.elementDelimiter;
		//serviceobj.services.compositeElementDelimiter = $scope.compositeDelimiter;
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
					segmentDelimiter:{},
					elementDelimiter:{},
					compositeElementDelimiter:{},
					repeatDelimiter:{},
					lightWellPartner:{},
					srId:'""',
					notes:'""'
				};
			serviceobj.partner.id = $scope.selected;
			serviceobj.services.tpp = $scope.tppName;
		serviceobj.serviceCategory.id = $scope.scname;
			//serviceobj.services.segtmentDelimiter = $scope.segmentDelimiter;
			var temps = $filter("filter")($scope.delimitersSList, {id:$scope.segmentDelimiter});
			if(temps.length!=0)
				{
				sarobj.segmentDelimiter.id=temps[0].delimiter;
				}
			var tempe = $filter("filter")($scope.delimitersEList, {id:$scope.elementDelimiter});
			if(tempe.length!=0)
				{
				sarobj.elementDelimiter.id=tempe[0].delimiter;
				}
			var tempc = $filter("filter")($scope.delimitersCList, {id:$scope.compositeElementDelimiter});
			if(tempc.length!=0)
				{
				sarobj.compositeElementDelimiter=tempc[0].delimiter;
				}
	
			var tempr = $filter("filter")($scope.delimitersRList, {id:$scope.repeatDelimiter});
			if(tempr.length!=0)
				{
				sarobj.repeatDelimiter=tempr[0].delimiter;
				}
			//serviceobj.services.elementDelimiter = $scope.elementDelimiter;
			//serviceobj.services.compositeElementDelimiter = $scope.compositeDelimiter;
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
							 version:item.version.id,
							protocol:item.protocol.id,
							 ack:item.ack,
							 complianceCheck:item.complianceCheck,
							 map:item.map.id,
							 srId:item.srId,
							notes:item.notes,
							stControlNum: item.stControlNum,
							isaControlNum: item.isaControlNum,
							stAcceptorLookUpAlias: item.stAcceptorLookUpAlias,
							gsIdVersion: item.gsIdVersion,
							  ackPeriod:item.ackPeriod
						 
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
    	
    	var temps = $filter("filter")($scope.delimitersSList, {delimiter:item.services.segmentDelimiter});
		if(temps.length!=0)
			{
			$scope.segmentDelimiter=temps[0].id;
			}
		else{
			angular.forEach($scope.delimitersSList, function (item1) {
				if(item1.delimiter==item.services.segmentDelimiter)
					{
					$scope.segmentDelimiter=item1.id;
					}
			});
			
			
		}
		var tempe = $filter("filter")($scope.delimitersEList, {delimiter:item.services.elementDelimiter});
		if(tempe.length!=0)
			{
			$scope.elementDelimiter=tempe[0].id;
			}
		else{
			angular.forEach($scope.delimitersEList, function (item1) {
				if(item1.delimiter==item.services.elementDelimiter)
					{
					$scope.elementDelimiter=item1.id;
					}
			});
			
			
		}
		//$scope.delimitersCList= angular.copy($scope.cBackList);
		var tempc = $filter("filter")($scope.delimitersCList, {delimiter:item.services.compositeElementDelimiter});
		if(tempc.length!=0)
			{
			$scope.compositeElementDelimiter=tempc[0].id;
			}
		else{
			angular.forEach($scope.delimitersCList, function (item1) {
				if(item1.delimiter==item.services.compositeElementDelimiter)
					{
					$scope.compositeElementDelimiter=item1.id;
					}
			});
			
			
		}
		
		//$scope.delimitersRList= angular.copy($scope.rBackList);
		
		if(item.services.repeatDelimiter!= null || item.services.repeatDelimiter!= undefined ){
		var tempr = $filter("filter")($scope.delimitersRList, {delimiter:item.services.repeatDelimiter});
		if(tempr.length!=0)
			{
			$scope.repeatDelimiter=tempr[0].id;
			}
		else{
			angular.forEach($scope.delimitersRList, function (item1) {
				if(item1.delimiter==item.services.repeatDelimiter)
					{
					$scope.repeatDelimiter=item1.id;
					}
			});
			
			
		}
		
		}
		
		else{
			var tempr = $filter("filter")($scope.delimitersRList, {delimiter:item.services.repeatDelimiter});
			if(tempr.length!=0)
				{
				$scope.repeatDelimiter= null;
				}
		}
    	$scope.lightWellPartner={};
    	$scope.selecttpp = false;
    	if(item.services.tpp!= null){
    	$scope.selecttpp = true;
    	var temptpp = $filter("filter")($scope.ttpNameList, {name: item.services.tpp});
		
    	$scope.delimitersDisabled = true;
    	
    	if(temptpp.length!=0)
			 {
			$scope.tppName = temptpp[0].id;
			angular.forEach($scope.tppCodeList, function (item) {
				 if(item.id==temptpp[0].id){
			$scope.lightWellPartnerDisabled = true;
			}
			});
			}
		
		
    	}
    	
    	
    //	$scope.tppName= item.services.tpp;
    	$scope.typeID = item.services.typeID;
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
		 if($scope.tppName == null || $scope.tppName == undefined){
			 $scope.bsList($scope.scname);
			 $scope.protocolList();
			 
		 } 
		 if($scope.tppName != null || $scope.tppName != undefined){
		 $scope.bspList($scope.scname,$scope.tppName);
		 $scope.pcolList($scope.tppName);
		 }
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
		 
		 $scope.delimitersDisabled=false;
		 $scope.select3PPdelimiters=function(id)
		 {
			 
			 $scope.delimitersDisabled=false; 
			 angular.forEach($scope.tppdelimitersList, function (item) {
				 if(item.id==id)
					 {
				 $scope.segmentDelimiter=item.segmentDelimiter;
				 $scope.elementDelimiter=item.elementDelimiter;
				 $scope.compositeElementDelimiter=item.compositeElementDelimiter;
				 $scope.repeatDelimiter=item.repeatDelimiter;
				 $scope.delimitersDisabled=true;
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
					 $scope.versList();
				 });
					
			};
			
			$scope.showMap = function(id){
				$http.get('/TPIAdmin/app/maps?serviceTypeId='+id)
			     .success(function (response, status, headers, config) {
			    	 
			    	 $scope.mapList = angular.copy(response);
			    	 
			     })
			     .error(function (data, status, header, config) {
			         $scope.ResponseDetails = "Data: " + data +
			             "<br />status: " + status ;
			         //    "<br />headers: " + jsonFilter(header) +
			           //  "<br />config: " + jsonFilter(config);
			     })
				 .then(function(response) {
				       
               //  $scope.myWelcome = response.data;
					
							 
				    });

				
			};
			
			$scope.versList = function()
			{
				
				
				 $http.get('/TPIAdmin/app/versions')
			     .success(function (response, status, headers, config) {
			    	 
			    	 $scope.versionList = angular.copy(response);
			    	 
			     })
			     .error(function (data, status, header, config) {
			         $scope.ResponseDetails = "Data: " + data +
			             "<br />status: " + status ;
			         //    "<br />headers: " + jsonFilter(header) +
			           //  "<br />config: " + jsonFilter(config);
			     })
				 .then(function(response) {
				       
                //  $scope.myWelcome = response.data;
					 $scope.protocolList();
							 
				    });


				
				};
				
				$scope.pList =[];
				$scope.protocolList = function()
				{
					
					
					 $http.get('/TPIAdmin/app/protocols')
				     .success(function (response, status, headers, config) {
				    	 $scope.pList = angular.copy(response);
				    	 
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
					$scope.pcolList = function(id)
					{
						
						
						 $http.get('/TPIAdmin/app/tpps/'+id+'/protocols')
					     .success(function (response, status, headers, config) {
					    	 $scope.pList = angular.copy(response);
					    	 
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
					
				$scope.serviceNameList=[];
				$scope.initname = function()
				{
					
					
					 $http.get('/TPIAdmin/app/serviceCategories/distinctNames')
				     .success(function (response, status, headers, config) {
				    	 
				    	 $scope.serviceNameList=angular.copy(response);
				    	// console.log("inputdata "+JSON.stringify(tempList));
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
					    				display:item.productionGsId
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
							 $scope.dlSList();
							 
						 });
							
					};
			
					$scope.delimitersSList=[];
					$scope.sBackList = [];
					$scope.dlSList = function()
					{
						
						
						 $http.get('/TPIAdmin/app/delimiters?type=segment')
					     .success(function (response, status, headers, config) {
					    	 
					    	 
					    	 $scope.delimitersSList=angular.copy(response);
					    	 $scope.sBackList = angular.copy(response);
					    	// console.log("inputdata "+JSON.stringify(tempList));
					     })
					     .error(function (data, status, header, config) {
					         $scope.ResponseDetails = "Data: " + data +
					             "<br />status: " + status ;
					         //    "<br />headers: " + jsonFilter(header) +
					           //  "<br />config: " + jsonFilter(config);
					     })
					     	
						 .then(function(response) {
							 $scope.dlEList(); 
						 });
							
					};
					
                  $scope.delimitersEList=[];
                  $scope.eBackList = [];
					
					$scope.dlEList = function()
					{
						
						
						 $http.get('/TPIAdmin/app/delimiters?type=element')
					     .success(function (response, status, headers, config) {
					    	 
					    	 
					    	 $scope.delimitersEList=angular.copy(response);
					    	 $scope.eBackList = angular.copy(response);
					    	// console.log("inputdata "+JSON.stringify(tempList));
					     })
					     .error(function (data, status, header, config) {
					         $scope.ResponseDetails = "Data: " + data +
					             "<br />status: " + status ;
					         //    "<br />headers: " + jsonFilter(header) +
					           //  "<br />config: " + jsonFilter(config);
					     })
					     	
						 .then(function(response) {
							 $scope.dlCList(); 
						 });
							
					};
					
					
                 $scope.delimitersCList=[];
                 $scope.cBackList = [];
					
					$scope.dlCList = function()
					{
						
						
						 $http.get('/TPIAdmin/app/delimiters?type=composite')
					     .success(function (response, status, headers, config) {
					    	 
					    	 
					    	$scope.delimitersCList=angular.copy(response);
					    	 $scope.cBackList = angular.copy(response);
					    	// console.log("inputdata "+JSON.stringify(tempList));
					     })
					     .error(function (data, status, header, config) {
					         $scope.ResponseDetails = "Data: " + data +
					             "<br />status: " + status ;
					         //    "<br />headers: " + jsonFilter(header) +
					           //  "<br />config: " + jsonFilter(config);
					     })
					     	
						 .then(function(response) {
							 $scope.dlRList();  
						 });
							
					};
					
					
                   $scope.delimitersRList=[];
                   $scope.rBackList = [];
					$scope.dlRList = function()
					{
						
						
						 $http.get('/TPIAdmin/app/delimiters?type=repeat')
					     .success(function (response, status, headers, config) {
					    	 
					    	 
					    	 $scope.delimitersRList=angular.copy(response);
					    	 $scope.rBackList = angular.copy(response);
					    	// console.log("inputdata "+JSON.stringify(tempList));
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
							$http.post("/TPIAdmin/app/serviceSubscriptions",submitObj)
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
							 if(item.serviceCategoryName.indexOf("Intercompany")==-1){	
							 
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
					$scope.disableDilimeters();
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
				     $scope.protocolList();
					
					
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
						
						
						 if(data.tpp !=null){
						 serviceobj.services.tpp = data.tpp.name;
						 serviceobj.services.typeID = data.tpp.type.id;
						 
						 }
						 
						 
						 serviceobj.services.segmentDelimiter = data.segmentDelimiter.delimiter;
						 serviceobj.services.elementDelimiter = data.elementDelimiter.delimiter;
						 serviceobj.services.compositeElementDelimiter = data.compositeElementDelimiter.delimiter;
						 if( data.repeatDelimiter !=null){
						 serviceobj.services.repeatDelimiter = data.repeatDelimiter.delimiter;
						 }
						 $scope.lightWellPartner.id = data.lightWellPartner.id;
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
							 
							 var protocolobj =  angular.copy(temp.protocol);
							 var versionobj =  angular.copy(temp.version);
							 var  businessServiceobj =  angular.copy(temp.serviceType);
							var lightWellPartnerobj = angular.copy(temp.lightWellPartner);
							 var ack = angular.copy(temp.ack);
							 var complianceCheck = angular.copy(temp.complianceCheck);
							 var mapobj =  angular.copy(temp.map);
							 var srId = angular.copy(temp.srId);
							 var stControlNum = angular.copy(temp.stControlNum);
							 var isaControlNum = angular.copy(temp.isaControlNum);
							 var stAcceptorLookUpAlias = angular.copy(temp.stAcceptorLookUpAlias);
							 var gsIdVersion = angular.copy(temp.gsIdVersion);
							 var notes = angular.copy(temp.notes);
							 var ackPeriod = angular.copy(temp.ackPeriod);
                         //  var businessServiceobj = {};
                          //  businessServiceobj.businessServiceName = temp.serviceType.businessServiceName;
						 var finalobj = {
                                   id:temp.id,
                                   versionNum:temp.versionNum,
								 businessService:businessServiceobj,
								 version:versionobj,
								 protocol:protocolobj,
								 lightWellPartner:lightWellPartnerobj,
								 ack : ack,
								 complianceCheck : complianceCheck,
								 map:mapobj,
								 srId: srId,
								 notes: notes,
								 stControlNum: stControlNum,
								 isaControlNum: isaControlNum,
								 stAcceptorLookUpAlias : stAcceptorLookUpAlias,
								 gsIdVersion: gsIdVersion,
								 ackPeriod : ackPeriod
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