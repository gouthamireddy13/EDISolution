function submitForm(ctl, msg, formObj, actionName) {
	displayProgressMessage(ctl, msg);
	var form = $("#" + formObj);
	if (actionName) {
		form.attr("action", actionName);
	}

	form.submit();
}

function submitFormSimple(formObj, actionName, methodName) {
	var $form = $("#" + formObj);
	$form.attr("action", actionName);
	if (methodName) {
		$form.attr("method", methodName);
	}
	$form.submit();
}

function displayProgressMessage(ctl, msg) {
	$(ctl).prop("disabled", true).text(msg);
	$.LoadingOverlay("show");
	/*
	 * setTimeout(function(){ $.LoadingOverlay("hide"); }, 60000);
	 */

	return true;
}

function displayAlert() {
	$("#warningAlert").show();
}

function getSelectedTransactions() {
	var selectedItems = $('#transaction-table').find(
			'input[type="checkbox"]:checked');
	var result = selectedItems.toArray();
	return result;
}

function checkSelectedOneOnly(warningMessage, list) {
	var selectedTrans = list;
	if (selectedTrans && selectedTrans.length != 1) {
		var messageText = "";
		if (warningMessage) {
			messageText = warningMessage;
		}
		displayMessageBox(messageText);
		return null;
	}
	return selectedTrans;
}

function checkSelectedOneAtLeast(warningMessage) {
	var selectedTrans = getSelectedTransactions();
	if (selectedTrans && selectedTrans.length < 1) {
		var messageText = "";
		if (warningMessage) {
			messageText = warningMessage;
		}
		displayMessageBox(messageText);
		return null;
	}
	return selectedTrans;
}

function displayMessageBox(messageText) {
	var $messageControl = $('#messageBox');
	var $textBody = $('#messageBoxText');
	$textBody.html(messageText);
	$messageControl.modal('show');
	return false;

}

function showAddPartner() {
	var $formToDisplay = $('#addPartnerModalForm');
	$formToDisplay.modal('show');
	return false;

}

function listTransactionsAJAX(formName, actionName) {
	var warningMessage = "Please select a single row to list the transactions.";
	var items = checkSelectedOneOnly(warningMessage);
	if (items != null) {
		var itemKeys = createJSONData(items);
		var jsonString = JSON.stringify(itemKeys[0]);
		$.ajax({
			url : actionName,
			type : "POST",
			contentType : "application/json; charset=utf-8",
			data : jsonString,
			async : false,
			cache : false,
			processData : false,
			success : function(resposeJsonObject) {
				buildListTransactionsTable(resposeJsonObject)
				displayTransactionList();

			}
		});
	}
}

function createJSONData(selectedItems) {
	var transactionKeys = [];
	if (selectedItems)

	{
		for ( var i in selectedItems) {
			var item = selectedItems[i];
			transactionKeys.push({
				"contactId" : item.value
			});
		}
	}
	return transactionKeys;
}


function showAddContact() {
	var $formToDisplay = $('#addContactModalForm');
	$formToDisplay.modal('show');
	return false;

}

function showAddPartner() {
	var $formToDisplay = $('#addPartnerModalForm');
	$formToDisplay.modal('show');
	return false;

}

function addContact(formName, partnerId, actionName) {

	var contactName = $('#txtContactName').val();
	var contactTitle = $('#txtContactTitle').val();
	var contactPhone = $('#txtContactPhone').val();
	var contactEmail = $('#txtContactEmail').val();

	var data = {
		partnerId : partnerId,
		contactName : contactName,
		contactTitle : contactTitle,
		contactPhone : contactPhone,
		contactEmail : contactEmail
	};

	var jsonString = JSON.stringify(data);

	$.ajax({
		url : actionName,
		type : "POST",
		contentType : "application/json; charset=utf-8",
		data : jsonString,
		async : false,
		cache : false,
		processData : false,
		success : function(resposeJsonObject) {
			var $formToDisplay = $('#addContactModalForm');
			$('#txtContactName').val('');
			$('#txtContactTitle').val('');
			$('#txtContactPhone').val('');
			$('#txtContactEmail').val('');
			buildContactsTable('contact-detail-table',resposeJsonObject);
			$formToDisplay.modal('hide');
			;
		}
	});
}


function deleteContactDetail(actionName,partnerId) {
	var warningMessage = "Please select a single contact.";
	var items = checkSelectedOneOnly(warningMessage,getSelectedContactDetails());
	if (items != null) {
		var itemKeys = createContactJSONData(items,partnerId);
		var jsonString = JSON.stringify(itemKeys[0]);
		$.ajax({
			url : actionName,
			type : "POST",
			contentType : "application/json; charset=utf-8",
			data : jsonString,
			async : false,
			cache : false,
			processData : false,
			success : function(resposeJsonObject) {
				buildContactsTable('contact-detail-table',resposeJsonObject);

			}
		});
	}
}

function getSelectedContactDetails() {
	var selectedItems = $('#contact-detail-table').find(
			'input[type="checkbox"]:checked');
	var result = selectedItems.toArray();
	return result;
}

function createContactJSONData(selectedItems, partnerId) {
	var transactionKeys = [];
	if (selectedItems)
	{
		for ( var i in selectedItems) {
			var item = selectedItems[i];
			transactionKeys.push({
				"contactId" : item.value,
				"partnerId" : partnerId
			});
		}
	}
	return transactionKeys;
}

function searchPartners()
{
	var $searchBox = $("#searchEntity");
	var searchText = $searchBox.val().trim();
	if (searchText.length() > 0) 
		{
			var jsonString = JSON.stringify(searchText);
			$.ajax({
				url : "searchPartners.json",
				type : "POST",
				contentType : "application/json; charset=utf-8",
				data : jsonString,
				async : false,
				cache : false,
				processData : false,
				success : function(resposeJsonObject) {
				//buildListTransactionsTable(resposeJsonObject)
				//displayTransactionList();
			}
		});
		}
	else
		{
			alert("Enter name to search");
		}
}