<html>
<head>
  <title>WikiShare - Page not found - Error 404</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="description" content="WikiShare 0.1" />
    <meta name="author" content="Adam Musial-Bright" />
    <link href="<%= request.getContextPath() %>/public/wiki/css/wikishare.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div id="container">
  <div id="header">

    <div id="pageHeader">
      <h1><span>WikiShare</span></h1>
      <h2>2009 by Adam Musial-Bright</h2>
    </div>
  </div>

  <div id="controllerViewContent">
    <div class="contentBox">
      <h3>You are not authenticated.</h3>
      Please go back to <a href="<%= request.getContextPath() %>/wiki/frontpage">frontpage</a>
    </div>
  </div>
</div>
</body>
</html>