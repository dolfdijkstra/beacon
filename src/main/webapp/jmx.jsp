<%@ page import="java.util.*"
%><%@ page import="java.io.IOException"
%><%@ page import="javax.servlet.jsp.JspWriter"
%><%@ page import="javax.management.*"
%><%@ page import="java.lang.management.ManagementFactory"
%><%@ page import="javax.management.openmbean.CompositeData"
%><%@ page import="javax.management.openmbean.TabularData"
%><%@ page import="javax.management.openmbean.CompositeType"
%><%!

private Comparator<ObjectName> comparator = new Comparator<ObjectName>(){
     public int compare(ObjectName o1, ObjectName o2){
         return o1.toString().compareTo(o2.toString());
     }

     public boolean equals(Object obj){
        return false;
     }
};


class BeanRenderer {
    private MBeanServer mBeanServer = null;
    private JspWriter w ;

    BeanRenderer(MBeanServer server,JspWriter out ){
        mBeanServer=server;
        this.w=out;
    }

    int listBeans( String qry , String[] attrs, boolean verbose) throws IOException    {

        Set names = null;
        try {
            names=mBeanServer.queryNames(new ObjectName(qry), null);
        } catch (Exception e) {
            w.println("Error - " + e.toString());
            return 0;
        }

        TreeSet<ObjectName> n = new TreeSet<ObjectName>(comparator);
        n.addAll(names);


        for(ObjectName oname:n) {
            if (verbose) w.print(oname.toString());

            try {
                MBeanInfo minfo=mBeanServer.getMBeanInfo(oname);
                //MBeanAttributeInfo attrs[]=minfo.getAttributes();
                Object value=null;

                for( int i=0; i< attrs.length; i++ ) {
                    w.print(":");
                    try {
                        value=mBeanServer.getAttribute(oname,attrs[i]);
                        
                        if( value != null ) {
                            //w.print( attrs[i] + ": ");
                            printValue(value);
                            
                        }
                    } catch( Throwable t) {
                        log("Error getting attribute " + oname + " " + attrs[i] + " " + t.toString());
                        continue;
                    }
                }
            } catch (Exception e) {
                // Ignore
            }
            if (verbose) w.println();
        }
        return n.size();
    }


    private void printValue(Object value) throws IOException{
        if (value==null) return;
        if (value instanceof CompositeData) {
        printCompositeData((CompositeData) value);
        } else if (value instanceof TabularData) {
        printTabularData((TabularData) value);
        } else if(value.getClass().isArray()){
        if (CompositeData.class.isAssignableFrom(value.getClass().getComponentType()) || TabularData.class.isAssignableFrom(value.getClass().getComponentType())){

            for (Object o: (Object[])value){
            printValue(o);
            w.print("\t");
            }
            w.println();
        } else {
            String valueString= String.valueOf(Arrays.asList((Object[])value)) ;
            w.print(escape(valueString));
        }
        } else {
        String valueString= value.toString();
        w.print(escape(valueString));
        }

    }

    private  void printTabularData(TabularData td) throws IOException {
        Set<String> keys=td.getTabularType().getRowType().keySet();

        w.write("<table>");
        w.write("<tr>");
        for (String key:keys){
        w.write("<th>");
        w.write(escape(key));
        w.write("</th>");
        }
        w.write("</tr>");
        for (Object o : td.values()) {
        if (o instanceof CompositeData) {
            CompositeData cd=(CompositeData) o;
            w.write("<tr>");
            for (String key:keys){
            w.write("<td>");
            printValue(cd.get(key));
            w.write("</td>");
            }
            w.write("</tr>");
        }
        }
        w.write("</table>");
    }

    private void printCompositeData(CompositeData cd) throws IOException {

        CompositeType ct = cd.getCompositeType();

        for (String key : ct.keySet()) {
        w.print(key + ": ");
        printValue(cd.get(key));
        }
        w.println();
    }

    String escape(String value){
        return value==null?"":org.apache.commons.lang.StringEscapeUtils.escapeHtml(value);
    }


    boolean isSupported( String type ) {
        return true;
    }
}
%><%

int prev=0;
MBeanServer pl= ManagementFactory.getPlatformMBeanServer();
int i=0;
boolean verbose = "true".equals(request.getParameter("verbose"));
BeanRenderer br=new BeanRenderer(pl,out);
for(;;){
   String qry=request.getParameter("qry"+i);
   if(qry==null) break;
   String attr=request.getParameter("attr"+i);
   if(attr==null) break;
   prev += br.listBeans(qry ,attr.split(","),verbose);
   i++;
}
if(!verbose) out.println();
%>
