<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Partner</title>
</head>
<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css" />

<link rel="stylesheet" href="assets/css/edi.css" />

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<body>
	<header class="container-fluid">
		<nav class="navbar navbar-default nav-edi">
			<div class="navbar-header">
				<div id="logo" style="padding-right: 20px;">
					<img src="assets/img/ABC_logo_SSO_2012x55.png" />
				</div>

			</div>

			<div class="col-sm-3 col-md-3 navbar-right">
				<form class="navbar-form" role="search">
					<div class="input-group">
						<input type="text"
							class="form-control" placeholder="Search" id="searchEntity">
							<span class="input-group-addon"><span
							class="glyphicon glyphicon-search"></span></span> 
					</div>
				</form>
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
		<div class="row">
			<div class="col-md-6">
				<button type="button"
					class="btn btn-default btn-lg btn-block large-menu-button" onclick="searchPartners()">
					<span class="glyphicon glyphicon-search"></span> Search Partners
				</button>
				<button type="button"
					class="btn btn-primary btn-lg btn-block large-menu-button"
					onclick="showAddPartner()">Add New Partner</button>
			</div>
			<div class="col-md-6">
				<button type="button"
					class="btn btn-default btn-lg btn-block large-menu-button" onclick="search3pp()">
					<span class="glyphicon glyphicon-search"></span> Search 3PP
				</button>
				<button type="button"
					class="btn btn-primary btn-lg btn-block large-menu-button">Add
					New 3PP</button>
			</div>
		</div>
	</section>
	<section id="searchResult">
		
	</section>
	<div class="modal fade" id="addPartnerModalForm" tabindex="-1">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4>Add Partner</h4>
				</div>
				<div class="modal-body" id="addPartnerModalBody">
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
								<div class="row">
									<div class="col-xs-6">
										<div class="input-group">
											<span class="input-group-addon"><span
												class="glyphicon glyphicon-user"></span></span>
											<f:input type="text" class="form-control"
												placeholder="Contact Name"
												path="contactDetails[0].contactName" />
										</div>
									</div>
									<div class="col-xs-6">
										<div class="input-group">
											<span class="input-group-addon"><span
												class="glyphicon glyphicon-user"></span></span>
											<f:input type="text" class="form-control" placeholder="Title"
												path="contactDetails[0].contactTitle" />
										</div>
									</div>
									<div style="display: inline-block; height: 10px; width: auto;">
									</div>
								</div>
								<div class="row">
									<div class="col-xs-6">
										<div class="input-group">
											<span class="input-group-addon"><span
												class="glyphicon glyphicon-phone-alt"></span></span>
											<f:input type="text" class="form-control"
												placeholder="Contact Phone"
												path="contactDetails[0].contactPhone" />
										</div>
									</div>
									<div class="col-xs-6">
										<div class="input-group">
											<span class="input-group-addon"><span
												class="glyphicon glyphicon-envelope"></span></span>
											<f:input type="text" class="form-control"
												placeholder="Contact Email"
												path="contactDetails[0].contactEmail" />
										</div>
									</div>

								</div>
							</div>
						</div>
					</f:form>
				</div>
				<div class="modal-footer">
					<button type="submit" id="btnAddContact" value="addContact"
						class="btn btn-default  btn-edi" tabindex="-1"
						onclick="submitFormSimple('addPartnerForm')">
						<span class="glyphicon glyphicon-floppy-disk"></span> Save
					</button>
					<button type="submit" id="btnAddContactPlus" value="addContactPlus"
						class="btn btn-default  btn-edi" tabindex="-1"
						onclick="submitFormSimple('addPartnerForm','editPartner.html')">
						<span class="glyphicon glyphicon-circle-arrow-right"></span> Save
						and Edit
					</button>
					<button class="btn btn-success btn-edi" data-dismiss="modal">Cancel</button>
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