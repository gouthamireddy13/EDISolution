describe('patnerManagementController', function() {
    var controller = null;
    $scope = null;

    beforeEach(function () {
        module('ABApp');
    });


    beforeEach(inject(function ($controller, $rootScope) {
        $scope = $rootScope.$new();
        controller = $controller('patnerManagementController', {
            $scope: $scope
        });
    }));

	
	afterEach(function() {
       // httpBackend.verifyNoOutstandingExpectation();
      //  httpBackend.verifyNoOutstandingRequest();
    });
 
  it('Condition is true', function() {
var flag=false;
var contact={

    "businessPhoneCountry": null,
      "businessPhoneExt": null,
     "contactEmail": "test@gmail.com",
      "contactEmail2": null,
    "contactName": "test",
      "contactTitle": null,
      "mobilePhone": null,
      "mobilePhoneCountry": null,
      "mobilePhoneExt": null,
      "personalPhone": null,
      "personalPhoneCountry": null,
      "personalPhoneExt": null,
      "transactionType":
      {id: 18}
};
	 $scope.addContact(flag,contact);
  });

    it('Condition is true1', function() {
        var contact={

            "businessPhoneCountry": null,
            "businessPhoneExt": null,
            "contactEmail": "test@gmail.com",
            "contactEmail2": null,
            "contactName": "test",
            "contactTitle": null,
            "mobilePhone": null,
            "mobilePhoneCountry": null,
            "mobilePhoneExt": null,
            "personalPhone": null,
            "personalPhoneCountry": null,
            "personalPhoneExt": null,
            "transactionType":
                {id: 18}
        };
        $scope.editcontact(contact);
    });
});