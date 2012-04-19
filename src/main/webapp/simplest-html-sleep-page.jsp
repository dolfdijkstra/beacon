<%@ page session="false" buffer="none"
 %><%@page contentType="text/html;charset=UTF-8"%><%!
/*
* This page is designed to test the influence of various backend processing delays on how the browser renders the page.
* The question to answer is: Are processing delays in certains area's of the page more or less problematic for the perceived response time?
* The parameters to pass in are adoctypes aheads abodys ahtmls. The 'a' prefix stand for after, the 'b' for before.
*/
 
 void sleep(HttpServletRequest request,HttpServletResponse response,String s) throws Exception {
 if(request.getParameter(s) !=null) {
  response.flushBuffer();
  Thread.sleep(Long.parseLong(request.getParameter(s)));
 } 
}
 
%><% 
response.addHeader("Cache-Control", "private, no-cache, no-cache=Set-Cookie, proxy-revalidate");
sleep(request,response, "bdoctypes"); %><!DOCTYPE html>
<% sleep(request,response, "adoctypes");
 %><html>
<head>
<title>Simplest Page</title>
</head>
<% sleep(request,response, "aheads");
 %><body>Hello World<script>
(function(w, d, s) {
function go() {
  var js, fjs = d.getElementsByTagName(s)[0], load = function(url, id,objName) {
    if (d.getElementById(id)) {return;}
    if (objName && window[objName]) {return;}
    js = d.createElement(s); js.src = url; js.id = id;
    fjs.parentNode.insertBefore(js, fjs);
  };
  //load('/cs/jquery-latest.js', 'jquery','jQuery');
  load('beacon.js', 'beacon','beacon');
}
  if (w.addEventListener) { w.addEventListener("load", go, false); }
  else if (w.attachEvent) { w.attachEvent("onload",go); }
}(window, document, 'script'));
</script><%
int images=0;
if(request.getParameter("images") !=null){
    images= Integer.parseInt(request.getParameter("images"));
}
//long t = System.currentTimeMillis();
for(int i=0;i<images;i++){%><img src="320px-Yellow_bird.JPG?c=<%=Integer.toString(i)%>"/><%} %>
</body>
<% sleep(request,response, "abodys");
 %></html>
<% sleep(request,response, "ahtmls"); %>
