<%@page contentType="text/html;charset=UTF-8"
%><%!

/*  
* The code and concept for this page was heavily inspired by Steve Souder's loadtimer: http://loadtimer.org/
*/

int[] doURLS(int num, int[][] variants) {
    int next = 1;
    int[] row = new int[variants.length];
    for (int i = 0; i < variants.length; i++) {
        int val = (num / next) % variants[i].length;
        row[i] = variants[i][val];
        next *= variants[i].length;
    }
    return row;

}


%><!doctype html>
<html>
<head>
<title>Loadtimer</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<style>
html, body, div, span, applet, object, iframe,
h1, h2, h3, h4, h5, h6, p, blockquote, pre,
a, abbr, acronym, address, big, cite, code,
del, dfn, em, font, img, ins, kbd, q, s, samp,
small, strike, strong, sub, sup, tt, var,
b, u, i, center,
dl, dt, dd, ol, ul, li,
fieldset, form, label, legend,
table, caption, tbody, tfoot, thead, tr, th, td {
	   margin: 0;
	   padding: 0;
	   border: 0;
	   outline: 0;
	   font-size: 100%;
	   vertical-align: baseline;
	   background: transparent;
}
body { font: .813em "Lucida Grande","Lucida Sans Unicode",sans-serif; color: #333; line-height: 1.4em; padding: 8px; }
code { font-size: 1.2em; }
h1 { font: 2em Arial, Helvetica, sans-serif; color: #1D2432; font-weight: bold; margin-bottom: .8em; }
h2 { font: 1.75em Arial, Helvetica, sans-serif; font-weight: bold; margin-bottom: .5em; }
h3 { font: 1.5em Arial, Helvetica, sans-serif; font-weight: bold; margin-bottom: .5em; }
h3 a { color: #FFF; text-decoration: none; }
h3 a:hover { text-decoration: underline; }
h4 { font: 1.25em Arial, Helvetica, sans-serif;font-weight: bold; margin-bottom: .5em; }
ul, ol { font-size: 1em; margin: 0 0 1.25em 1.538em; }
li { margin-bottom: .25em; }
p { margin-bottom: 1em; }
p.meta { font: .923em Arial, Helvetica, sans-serif; color: #43768B; }
p.meta a { color: #43768B; }
p.meta a:hover { color: #973100; }
a { color: #315667; }
a:hover, #sidebar a:hover { color: #973100; }
dt { margin-left: 1em; }
dd { margin-left: 2em; margin-bottom: .25em; }
dl { margin: 0 0 1.25em 0; }
#navlinks LI { display: inline; margin: 0 8px; }
#footer { margin-top: 2em; width: 800px; text-align: center; }
</style>
</head>
<body>
<ul style="position: absolute; left: 500px;" id=navlinks>
  <li> <a href="#about">about</a>
  <li> <a href="http://code.google.com/p/loadtimer/">code</a>
  <li> <a href="http://groups.google.com/group/loadtimer/topics">contact</a>
</ul>

<h1 style="color: #353335; font-size: 2.5em;">
<a href="loadtimer.jsp" style="text-decoration: none; color: #353335;"> Loadtimer</a>
</h1>

<div style="padding: 8px;">

<form onsubmit="toggleStart();doLoadUrls(); return false;">
<table cellspacing="0" cellpadding="0" border="0" style="margin-bottom: 1em;">
<tr>
<td>
<nobr>
URLs:
<textarea id=urls rows=10 cols=100 style="vertical-align: top; font-family: monospace; font-size: 10pt;">
<%
	    String[] args ="bdoctypes,adoctypes,aheads,abodys,ahtmls,images".split(",");

        
        int[][] variants = new int[args.length][];
        variants[0] = new int[] { 0, 200 };
        variants[1] = new int[] { 0, 200 };
        variants[2] = new int[] { 0, 200 };
        variants[3] = new int[] { 0, 200 };
        variants[4] = new int[] { 0, 200 };
        variants[5] = new int[] { 0, 25 };
        int num = 1;
        for (int i = 0; i < variants.length; i++) {
            num *= variants[i].length;
        }

        int[][] table = new int[num][variants.length];

        for (int i = 0; i < num; i++) {
            table[i] = doURLS(i, variants);
        }
        for (int i = 0; i < table.length; i++) {
            out.print("simplest-html-page.jsp?") ;
            for (int j = 0; j < table[i].length; j++) {
                if(j>0){
                    out.print( "&");
                }
                out.print(args[j] + "="+Integer.toString(table[i][j]));
                
            }
            out.println();
        }
	    
	    
	%></textarea>
</nobr>
</td>
<td>
<div id=dprint style="font-family: monospace; font-size: 10pt; margin-left: 2em;">
</div>
</td>
</tr>
	
<tr>
<td colspan=2>
<div style="margin-top: 0.5em;"></div>

<input id=startbtn type=submit value="Start" style="margin-top: 1em;">
</td>
</tr>
</table>
</form>



<iframe id=iframe1 src="about:blank" frameborder="1" style="margin-top: 3em; border: 1px solid;" width=98% height=500 onload="doFrameLoad()" onerror="doFrameLoad(true)"></iframe>

<h2 id=about style="margin-top: 2em;">about</h2>

<p>
Loadtimer is intended to be used to measure page load times on mobile devices.
Here's how to use it:
</p>
<ul>
  <li> Edit the list of URLs - one per line.
  <li> Clear your cache if you want to test without a pre-loaded cache.
  <li> Click "Start" to load the URLs one-by-one into the iframe.
  <li> The iframe load time is displayed.
</ul>

<p>
Notes about this test harness:
</p>
<ul>
  <li> Some websites have "framebusting" code that prevent them from loading in an iframe. 
Some sites simply won't load (e.g., <a href="http://www.google.com/">Google</a>, 
<a href="http://www.youtube.com/">YouTube</a>, and 
<a href="http://www.twitter.com/">Twitter</a>).
Other sites will break out of the iframe (e.g., <a href="http://www.nytimes.com/">NYTimes</a>).
  <li> It's possible that loading the URL in an iframe affects load times in some way. 
       Extra steps have been taken to mitigate the effect:
      <ul style="margin-bottom: 0">
        <li> In between pages <code>about:blank</code> is loaded in the iframe so that one page's <u><strong>un</strong></u>load time does not affect the next page's load time.
        <li> The order of the preset URLs is randomized so that any interdependencies are mitigated (e.g., URL 1 loads resources used by URL 2).
        <li> In some browsers a website's favicon is not downloaded when the website is loaded in an iframe. This could affect page load times, but is likely negligible.
      </ul>
  <li> There's a simple check to help remind you to clear the cache between runs.
</ul>


</div> <!-- end wrapper -->


<script>
var t_start;
var gbStarted = false;
var giUrl = 0; // zero-based
var gaUrls;



function doLoadUrls() {
	gbStarted = true;
	giUrl = 0;
	parseUrls();
	clearDprint();
	doNextUrl();
}


function parseUrls() {
	var urls = document.getElementById("urls");
	gaUrls = urls.value.split("\n");
}


function doBlank() {
	var iframe1 = document.getElementById('iframe1');
	iframe1.src = "about:blank";
	setTimeout(doNextUrl, 200);
}


function doNextUrl() {
	if ( giUrl < gaUrls.length ) {
		var url = gaUrls[giUrl];
		var iframe1 = document.getElementById('iframe1');
		t_start = Number(new Date());
		iframe1.src = url;
	}
}


function doFrameLoad(bError) {
	var t_end = Number(new Date());
	var loadtime = t_end - t_start;
	var iframe1 = document.getElementById("iframe1");
	if ( "about:blank" != iframe1.src ) {
		var url = gaUrls[giUrl];
		giUrl++;
		dprint(giUrl + ": " + ( bError ? "error, " : loadtime + " ms, " ) + url);
		if ( giUrl < gaUrls.length && "" != gaUrls[giUrl] ) {
			setTimeout(doBlank, 2000);
		}
		else {
			gbStarted = false;
			// display alert dialog async so we don't block other onload behavior in the iframe's document
			setTimeout("toggleStart(); alert('Done');", 10);
		}
	}
}


function toggleStart() {
	var btn = document.getElementById('startbtn');
	if ( btn.disabled ) {
		btn.disabled = false;
		btn.value = "Start";
	}
	else {
		btn.disabled = true;
		btn.value = "running";
	}
}


function dprint(sText) {
	var elem = document.getElementById("dprint");
	elem.innerHTML += sText + "<br>";
}


function clearDprint(sText) {
	var elem = document.getElementById("dprint");
	elem.innerHTML = "";
}
</script>

</body>

</html>

