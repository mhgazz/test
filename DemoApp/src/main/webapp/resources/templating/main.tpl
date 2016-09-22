<html lang="en">
<head>
	<title>Demo Application</title>
	<link rel="stylesheet" type="text/css" href="${resourcetPath}/css/hello.css" />
	<link rel="stylesheet" type="text/css" href="${resourcetPath}/css/bootstrap.min.css" />
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
	<div class="navbar-header">
		<a class="navbar-brand" href="#">Welcome to my demo application</a>
	</div>
  </div>
</nav>
 
<div class="jumbotron">
  	<div class="container">
		<h1>Demo Aplication</h1>
		<p>
			This application works with JS Server-side as templating engine.
	    </p>
		<script type="server/javascript">
			var person = com.demo.Person.lookup(id);
		</script>
		<p>
			<div title="${person.name}">${person.name}</div>
			<div data-if="person.married" title="${person.spouse}">Spouse:${person.spouse}</div>
			<div data-for-child="person.children">Child: ${child}</div>
	    </p>
	</div>
</div>
 
<div class="container">
	<div class="row">
	<div class="col-md-4">
		<h2>Components used</h2>
		<li>Spring 4 MVC</li>
		<li>Jetty contained</li>
		<li>Maven packaged and managed</li>
		<li>Nashorn JS server side engine</li>
	</div>
  </div>
 
 
  <hr/>
  <footer>
	<p>Written by Mariano Gazzola</p>
  </footer>
</div>
 
<script src="${resourcetPath}/js/hello.js"></script>
<script src="${resourcetPath}/js/bootstrap.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
</body> 
</html>
