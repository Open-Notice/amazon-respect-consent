<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/style.css" TYPE="text/css" MEDIA="screen" />
<link rel="stylesheet" href="css/colorbox.css" TYPE="text/css" MEDIA="screen" />
<link rel="stylesheet" href="css/jquery-ui-1.10.3.custom.min.css" />
<script type="text/javascript" src="js/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.colorbox-min.js"></script>
<script type="text/javascript" src="xdi.js"></script>

<script type="text/javascript">

$(document).ready(function() {
	$.colorbox({href:"iframe_receipt.html",iframe:true,scrolling:true,width:"800px",height:"600px"});
});

</script>

</head>

  <body>

	<img src="img/fakeamazon.png">

	<h2>Thanks <%= request.getAttribute("name") %> for signing up</h2>

  </body>

</html>
