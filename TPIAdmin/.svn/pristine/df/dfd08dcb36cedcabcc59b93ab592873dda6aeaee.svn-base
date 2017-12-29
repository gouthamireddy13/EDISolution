function buildContactsTable(parentClassID, data)
{
	var tble = ("#" + parentClassID);
	var $table = $(tble);
	$table.empty();
	var $head = $("<thead/>");
	$table.append($head);

	var $row = $("<tr/>");
	$row.addClass('row-header');
	
	var $col = $("<th/>");
	$col.text(' ')
	$row.append($col);
	
	var $col = $("<th/>");
	$col.text('Contact Name');
	$row.append($col);
	
	var $col = $("<th/>");
	$col.text('Title');	
	$row.append($col);
	
	var $col = $("<th/>");
	$col.text('Phone');
	$row.append($col);

	var $col = $("<th/>");
	$col.text('Email');
	$row.append($col);

	$head.append($row);
	var $body = $("<tbody/>");
	$table.append($body);

	for ( var i in data) {
		var item = data[i];
		var $row = $("<tr/>");
		
		var $col = $("<td />").html('<input type="checkbox" id="contact_"' + item.id  + ' value="' + item.id + '" />');
		$row.append($col);
		
		var $col = $("<td/>");
		$col.text(item.contactName);
		$row.append($col);
		
		var $col = $("<td/>");
		$col.text(item.contactTitle);
		$row.append($col);
		var $col = $("<td/>");
		$col.text(item.contactPhone);
		$row.append($col);
		var $col = $("<td/>");
		$col.text(item.contactEmail);
		$row.append($col);
		$body.append($row);
	}
}

function buildListTransactionsTable(data) {
	var $table = $("#transaction-list-table");
	$table.empty();
	var $head = $("<thead/>");
	$table.append($head);

	var $row = $("<tr/>");
	$row.addClass('row-header');
	var $col = $("<td/>");
	$col.text('Transaction Set');
	$row.append($col);
	var $col = $("<td/>");
	$col.text('ST Control#');
	$row.append($col);
	var $col = $("<td/>");
	$col.text('User Reference');
	$row.append($col);

	$head.append($row);
	var $body = $("<tbody/>");
	$table.append($body);

	for ( var i in data) {
		var item = data[i];
		var $row = $("<tr/>");
		var $col = $("<td/>");
		$col.text(item.transSet);
		$row.append($col);
		var $col = $("<td/>");
		$col.text(item.stCntrlNum);
		$row.append($col);
		var $col = $("<td/>");
		$col.text(item.userReference);
		$row.append($col);
		$body.append($row);
	}

}