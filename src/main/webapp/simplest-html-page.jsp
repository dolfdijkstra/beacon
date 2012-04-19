<%@ page session="false" 
%><%@page contentType="text/html; charset=UTF-8"
%><% 
response.addHeader("Cache-Control", "private, no-cache, no-cache=Set-Cookie, proxy-revalidate");
%><!DOCTYPE html>
<html>
<head>
<title>Simplest Page</title>
</head>
<body>Hello World<script>
(function(w, d, s) {
function go() {
  var js, fjs = d.getElementsByTagName(s)[0], load = function(url, id,objName) {
    if (d.getElementById(id)) {return;}
    if (objName && window[objName]) {return;}
    js = d.createElement(s); js.src = url; js.id = id;
    fjs.parentNode.insertBefore(js, fjs);
  };
  load('beacon.js', 'beacon','beacon');
}
  if (w.addEventListener) { w.addEventListener("load", go, false); }
  else if (w.attachEvent) { w.attachEvent("onload",go); }
}(window, document, 'script'));
</script>
</body>
</html>
