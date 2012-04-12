(function(w){

function log() {
try {
console.log.apply(console, arguments); 
} catch(e) { 
try {
opera.postError.apply(opera, arguments);
} catch(e){
alert(Array.prototype.join.call( arguments, " "));
}
}
}

function sendRequest(url,postData) {
    if(!postData) return;
    var req = createXMLHTTPObject();
    if (!req) return;
    req.open("POST",url,true);
    req.setRequestHeader('Content-type','application/json;charset=UTF-8');
    req.onreadystatechange = function () {
        if (req.readyState != 4) return;
        if (req.status != 200 && req.status != 304) {
            return;
        }
    }
    if (req.readyState == 4) return;
    req.send(postData);
}

var XMLHttpFactories = [
    function () {return new XMLHttpRequest()},
    function () {return new ActiveXObject("Msxml2.XMLHTTP")},
    function () {return new ActiveXObject("Msxml3.XMLHTTP")},
    function () {return new ActiveXObject("Microsoft.XMLHTTP")}
];

function createXMLHTTPObject() {
    var xmlhttp = false;
    for (var i=0;i<XMLHttpFactories.length;i++) {
        try {
            xmlhttp = XMLHttpFactories[i]();
        }
        catch (e) {
            continue;
        }
        break;
    }
    return xmlhttp;
}


var beacon = {

send_json: function(data){
		   data = data || beacon.collectPerformanceData();
                  sendRequest("beacon-json.jsp", JSON.stringify(data)); 
	   },

copyProperties: function(obj,p){
			for(var prop in obj) {
				if(!this.isUpperCase(prop)) { //don't collect CONSTANTS
					var value=obj[prop];
					switch(typeof value){
						case 'string':
							p[prop]=quote(value);
							break;
						case 'number':
							//p[prop]= isFinite(value) ? String(value) : 'null';
							p[prop]= isFinite(value) ? value : 'null';
							break;
						case 'boolean':
						case 'null':
							p[prop]= String(value);
							break;
					}
				}
			}
		},
isUpperCase:function ( string ) {
		    return string == string.toUpperCase();
	    },

collectPerformanceData: function(){
				var p={};
				this.copyProperties(w.performance.timing,p);
				this.copyProperties(w.performance.navigation,p);
				this.copyProperties(w.screen,p);

				p.location=w.location.href;
				p.userAgent=w.navigator.userAgent;
				p.title=document.title;
				p.innerHeight=w.innerHeight;
				p.innerWidth=w.innerWidth;
				p.outerHeight=w.outerHeight;
				p.outerWidth=w.outerWidth;
				p.pageXOffset=w.pageXOffset;
				p.pageYOffset=w.pageYOffset;
				p.screenLeft=w.screenLeft;
				p.screenTop=w.screenTop;
				p.screenX=w.screenX;
				p.screenY=w.screenY;
				p.referrer=document.referrer;

				return p;
			}
}

setTimeout( function(){beacon.send_json()},5); //wrap send_json call in function, because FF sends lateness argument to function. https://developer.mozilla.org/en/DOM/window.setTimeout

}(window));
