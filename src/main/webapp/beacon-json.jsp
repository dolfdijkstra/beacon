<%@page import="java.util.concurrent.*"
%><%@page import="java.io.*"%><%!
   private BlockingQueue<String> queue = new LinkedBlockingQueue<String>();

    private boolean stop = false;
    private File file;

    public void jspInit() {
        Consumer consumer = new Consumer();
        new Thread(consumer, "beacon-log-writer").start();
    }

    public void jspDestroy() {
        stop = true;
    }

    class Consumer implements Runnable {

        private Writer writer;

        public void run() {
            try {
                while (!stop) {
                   try {
                    String x = queue.poll(50, TimeUnit.MILLISECONDS);
                    consume(x);
                   } catch (Throwable ex) {
                       log(ex.getMessage());
                   }
                }
            } catch (Throwable e) {
            } finally {
                close();
            }
        }

        void consume(String x) {
            if (x != null) {
                ensureOpen();
                try {
                  writer.write(x + '\n');
                } catch(IOException e){
                       log(e.getMessage());
                }
            } else {
                close();
            }
        }

        void ensureOpen() {
            if (writer == null) {
                if (file == null)
                    file = new java.io.File(getServletContext().getRealPath("/WEB-INF/beacon-json.log"));
                try {
                   writer = new java.io.FileWriter(file, true);
                } catch(IOException e){
                       log(e.getMessage());
                }
            }
        }

        void close() {
            if (writer != null) {
                org.apache.commons.io.IOUtils.closeQuietly(writer);
                writer = null;
            }
        }
    }

%><%
try{
  java.io.InputStream input = request.getInputStream();
  String x = org.apache.commons.io.IOUtils.toString(input, "UTF-8");
  org.apache.commons.io.IOUtils.closeQuietly(input);
  if(x!=null && x.length() >10)queue.offer(x);
} catch(Throwable e){
  log("Exception reading input stream",e);
}

response.setStatus(204); 
response.setHeader("Cache-Control","no-store");

%>
