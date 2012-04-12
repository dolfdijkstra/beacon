<%!
String[] args={
"navigationStart",
"unloadEventStart",
"unloadEventEnd",
"redirectStart",
"redirectEnd",
"fetchStart",
"domainLookupStart",
"domainLookupEnd",
"connectStart",
"connectEnd",
"secureConnectionStart",
"requestStart",
"responseStart",
"responseEnd",
"domLoading",
"domInteractive",
"domContentLoadedEventStart",
"domContentLoadedEventEnd",
"domComplete",
"loadEventStart",
"loadEventEnd",
"type",
"redirectCount"};

%><%

//java.io.InputStream input = request.getInputStream();
//String x = org.apache.commons.io.IOUtils.toString(input, "UTF-8");
//org.apache.commons.io.IOUtils.closeQuietly(input);

StringBuilder b = new StringBuilder();
b.append("\"");
b.append(request.getHeader("Referer"));
b.append("\",\"");
b.append(request.getHeader("User-Agent"));
b.append("\"");
for(String s:args){
	if(b.length()>0) b.append(",");
	String x = request.getParameter(s);
	if(x!=null) b.append(x);
}
b.append("\n");
java.io.File file = new java.io.File(getServletContext().getRealPath("/WEB-INF/beacon.log"));
java.io.FileWriter fw = new java.io.FileWriter(file,true);
fw.write(b.toString());
fw.close();


response.setStatus(204); 
response.setHeader("Cache-Control","no-store");

%>
