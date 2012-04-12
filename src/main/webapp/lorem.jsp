<%@ page session="false" contentType="text/plain" %><%!
char[] lorem = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Integer mollis purus in diam ultrices pharetra. Ut sit amet nisl in nibh tincidunt hendrerit. Aliquam dictum. Nam at enim. Fusce aliquam, odio id dapibus accumsan, metus erat pharetra justo, eu tempus elit sapien tincidunt est. Aenean scelerisque. Fusce arcu metus, lobortis sit amet, pretium eget, imperdiet nec, lacus. Nunc posuere gravida eros. In ut dui. Aenean dignissim, nulla et commodo auctor, nibh dolor posuere ligula, ut commodo dui odio convallis est. Sed massa. Vivamus vel nisi. Vivamus sed nibh vel lectus gravida iaculis. Suspendisse non sem vel purus condimentum egestas. Fusce id tortor ut ligula adipiscing porttitor. Sed sollicitudin dolor. Aenean erat. Mauris lacinia, risus faucibus placerat tincidunt, sapien pede scelerisque dui, et scelerisque nunc diam non orci. Praesent ultrices. Cras a tortor. Integer sed libero. Vestibulum leo est, condimentum id, dictum sed, volutpat vel, arcu. Aenean ultricies congue tellus. Cras commodo nibh metu".toCharArray();
%><%
int size = request.getParameter("size") !=null? Integer.parseInt(request.getParameter("size")):100;
while (size >0){
    int t=Math.min(size,lorem.length);
    out.write(lorem,0,t);
    size -=t;
}
%>
