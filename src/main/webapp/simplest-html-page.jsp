<%@ page session="false" buffer="none"
 %><%@page contentType="text/html;charset=UTF-8"%><%!
void sleep(HttpServletRequest request,HttpServletResponse response,String s) throws Exception {
 if(request.getParameter(s) !=null) {
  response.flushBuffer();
  Thread.sleep(Long.parseLong(request.getParameter(s)));
 } 
}
%><!DOCTYPE HTML>
<% sleep(request,response, "adoctypes");
 %><html>
<head>
<title>Simplest Page</title>
</head>
<% sleep(request,response, "aheads");
 %><body>Hello World</body>
<% sleep(request,response, "abodys");
 %></html>
<% sleep(request,response, "ahtmls"); %>
