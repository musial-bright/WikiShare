<html>
<head>
  <title>WikiShare - You are not authenticated - error 401</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="description" content="WikiShare 0.1" />
    <meta name="author" content="Adam Musial-Bright" />
    <link href="<%= request.getContextPath() %>/public/wiki/css/main.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <div id="main_navigation"></div>
    <div id="wikipage_content">
        <h1>You are not authenticated</h1>
        <a href="<%= request.getContextPath() %>/wiki/frontpage">Go back to frontpage</a>
   </div>
   <div id="footer">
        WikiShare by <a href="mailto:webmaster@int-design.de">Adam Musial-Bright</a> 2009 -
        <a href="http://www.musial-bright.com">www.musial-bright.com</a>
    </div>
</body>
</html>