<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Partner</title>
</head>
<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css" />



<link rel="stylesheet" href="assets/css/edi.css" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<body>
	<header class="container-fluid">
		<nav class="navbar navbar-default nav-edi">
			<div class="navbar-header">
				<div id="logo">
					<img src="assets/img/ABC_logo_SSO_2012x55.png" />
				</div>

			</div>

			<ul class="nav navbar-nav navbar-right">
				<li class="nav active"><a href="#">Partner</a></li>
				<li class="nav "><a href="#"> 3PP</a></li>
				<li class="nav"><a href="#"><span
						class="glyphicon glyphicon-home"></span> Home</a></li>
			</ul>
		</nav>
	</header>
	<section class="container container-with-border">
		<div class="page-header">
			<h4>EDI Trading Partners Configuration</h4>
		</div>
		<div>
			<f:form id="addPartnerForm" commandName="partner">


				<div class="form-group">
					<label for="inputPartnerName">Partner Name</label>
					<f:input class="form-control" id="inputPartnerName"
						placeholder="Partner Name" path="partnerName"></f:input>
				</div>

				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">Contact Details</h3>
					</div>
					<div class="panel-body">
						<div class="edi-table-contrainer">
							<table border="1" id="contact-detail-table"
								class="table table-bordered table-striped table-hover edi-table-small">
								<thead>
									<tr class="row-header">
										<th></th>
										<th>Contact Name</th>
										<th>Title</th>
										<th>Phone</th>
										<th>Email</th>
									</tr>
								</thead>								
								<tbody>
									<c:forEach var="contact" items="${partner.contactDetails}">
										<tr>
											<td><input type="checkbox" name="checkCustRows"
												value="${contact.id}" /></td>
											<td><c:out value="${contact.contactName}" /></td>
											<td><c:out value="${contact.contactTitle}" /></td>
											<td><c:out value="${contact.contactPhone}" /></td>
											<td><c:out value="${contact.contactEmail}" /></td>
										</tr>
									</c:forEach>
								</tbody>
							
							</table>

						</div>
					</div>
					<div class="panel-footer"></div>
				</div>
			</f:form>

			<div class="modal fade" id="addContactModalForm" tabindex="-1">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<h4>Add Contact</h4>
						</div>
						<div class="modal-body" id="addPartnerModalBody">
							<form id="addContactForm">
								<div class="panel panel-primary">
									<div class="panel-heading">
										<h3 class="panel-title">Contact Details</h3>
									</div>
									<div class="panel-body">
										<div class="row">
											<div class="col-xs-6">
												<div class="input-group">
													<span class="input-group-addon"><span
														class="glyphicon glyphicon-user"></span></span> <input
														type="text" class="form-control"
														placeholder="Contact Name" id="txtContactName"/>
												</div>
											</div>
											<div class="col-xs-6">
												<div class="input-group">
													<span class="input-group-addon"><span
														class="glyphicon glyphicon-user"></span></span> <input
														type="text" class="form-control" placeholder="Title"
														id="txtContactTitle">
												</div>
											</div>
											<div
												style="display: inline-block; height: 10px; width: auto;">
											</div>
										</div>
										<div class="row">
											<div class="col-xs-6">
												<div class="input-group">
													<span class="input-group-addon"><span
														class="glyphicon glyphicon-phone-alt"></span></span> <input
														type="text" class="form-control"
														placeholder="Contact Phone" id="txtContactPhone" />
												</div>
											</div>
											<div class="col-xs-6">
												<div class="input-group">
													<span class="input-group-addon"><span
														class="glyphicon glyphicon-envelope"></span></span> <input
														type="text" class="form-control"
														placeholder="Contact Email" id="txtContactEmail" />
												</div>
											</div>

										</div>
									</div>
								</div>
							</form>
						</div>
						<div class="modal-footer">
							<button id="btnAddContact" value="addContact"
								class="btn btn-default  btn-edi" tabindex="-1"
								onclick="addContact('addContact.json',${partner.id},'addContact.json')">
								<span class="glyphicon glyphicon-floppy-disk"></span> Save
							</button>
							<button class="btn btn-success btn-edi" data-dismiss="modal">Cancel</button>
						</div>
					</div>
				</div>
			</div>
			<button id="btnAddContactDetail" value="addContactDetail"
				class="btn btn-default  btn-edi" tabindex="-1"
				onclick="showAddContact()">
				<span class="glyphicon glyphicon glyphicon-plus-sign"></span> Add
				Contact
			</button>
			<button id="btnDeleteContactDetail" value="addContactDetail"
				class="btn btn-default  btn-edi" tabindex="-1" onclick="deleteContactDetail('deleteContact.json',${partner.id})">
				<span class="glyphicon glyphicon glyphicon-remove-sign"></span>
				Delete Contact
			</button>
			<button id="btnSaveContact" value="addContactPlus"
				class="btn btn-default  btn-edi" tabindex="-1"
				onclick="submitFormSimple('addPartnerForm','editPartner.html')">
				<span class="glyphicon glyphicon glyphicon-ok-sign"></span> Save
			</button>
		</div>
	</section>
	<div class="modal fade" id="messageBox" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4>Warning</h4>
				</div>
				<div class="modal-body">
					<p id="messageBoxText"></p>
				</div>
				<div class="modal-footer">
					<button class="btn btn-success" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<script src="assets/js/jquery-3.1.1.min.js"></script>
	<script src="assets/bootstrap/js/bootstrap.js"></script>
	<script src="assets/js/tpi.js"></script>
	<script src="assets/js/tpi_tables.js"></script>

</body>
</html>