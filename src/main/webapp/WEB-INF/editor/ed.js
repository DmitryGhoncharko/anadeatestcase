
var textarea;
var content;
document.write("<link href=\"/WEB-INF/editor/styles.css\" rel=\"stylesheet\" type=\"text/css\">");


function edToolbar(obj) {

	document.write("<img class=\"button\" src=\"/static/images/bold.gif\" name=\"btnBold\" title=\"Bold\" onClick=\"doAddTags('<strong>','</strong>','" + obj + "')\">");
    document.write("<img class=\"button\" src=\"/static/images/italic.gif\" name=\"btnItalic\" title=\"Italic\" onClick=\"doAddTags('<em>','</em>','" + obj + "')\">");
	document.write("<img class=\"button\" src=\"/static/images/underline.gif\" name=\"btnUnderline\" title=\"Underline\" onClick=\"doAddTags('<u>','</u>','" + obj + "')\">");
	document.write("<img class=\"button\" src=\"/static/images/link.gif\" name=\"btnLink\" title=\"Insert Link\" onClick=\"doURL('" + obj + "')\">");
	document.write("<img class=\"button\" src=\"/static/images/image.gif\" name=\"btnPicture\" title=\"Insert Picture\" onClick=\"doImage('" + obj + "')\">");
	document.write("<img class=\"button\" src=\"/static/images/ordered.gif\" name=\"btnList\" title=\"Ordered List\" onClick=\"doList('<ol>','</ol>','" + obj + "')\">");
	document.write("<img class=\"button\" src=\"/static/images/unordered.gif\" name=\"btnList\" title=\"Unordered List\" onClick=\"doList('<ul>','</ul>','" + obj + "')\">");
	document.write("<img class=\"button\" src=\"/static/images/quote.gif\" name=\"btnQuote\" title=\"Quote\" onClick=\"doAddTags('<blockquote>','</blockquote>','" + obj + "')\">");
  	document.write("<img class=\"button\" src=\"/static/images/code.gif\" name=\"btnCode\" title=\"Code\" onClick=\"doAddTags('<code>','</code>','" + obj + "')\">");
    document.write("<br>");
	//document.write("<textarea id=\""+ obj +"\" name = \"" + obj + "\" cols=\"" + width + "\" rows=\"" + height + "\"></textarea>");
	
	
		}

function doImage(obj)
{
textarea = document.getElementById(obj);
var url = prompt('Enter the Image URL:','http://');

var scrollTop = textarea.scrollTop;
var scrollLeft = textarea.scrollLeft;

if (url != '' && url != null) {

	if (document.selection) 
			{
				textarea.focus();
				var sel = document.selection.createRange();
				sel.text = '<img src="' + url + '">';
			}
   else 
    {
		var len = textarea.value.length;
	    var start = textarea.selectionStart;
		var end = textarea.selectionEnd;
		
        var sel = textarea.value.substring(start, end);
	    //alert(sel);
		var rep = '<img src="' + url + '">';
        textarea.value =  textarea.value.substring(0,start) + rep + textarea.value.substring(end,len);
		textarea.scrollTop = scrollTop;
		textarea.scrollLeft = scrollLeft;
	}
 }
}

function doURL(obj)
{
var sel;
textarea = document.getElementById(obj);
var url = prompt('Enter the URL:','http://');
var scrollTop = textarea.scrollTop;
var scrollLeft = textarea.scrollLeft;

if (url != '' && url != null) {

	if (document.selection) 
			{
				textarea.focus();
				var sel = document.selection.createRange();
				
				if(sel.text==""){
					sel.text = '<a href="' + url + '">' + url + '</a>';
					} else {
					sel.text = '<a href="' + url + '">' + sel.text + '</a>';
					}
				//alert(sel.text);
				
			}
   else 
    {
		var len = textarea.value.length;
	    var start = textarea.selectionStart;
		var end = textarea.selectionEnd;
		
		var sel = textarea.value.substring(start, end);
		
		if(sel==""){
		sel=url; 
		} else
		{
        var sel = textarea.value.substring(start, end);
		}
	    //alert(sel);
		
		
		var rep = '<a href="' + url + '">' + sel + '</a>';;
        textarea.value =  textarea.value.substring(0,start) + rep + textarea.value.substring(end,len);
		textarea.scrollTop = scrollTop;
		textarea.scrollLeft = scrollLeft;
	}
 }
}

function doAddTags(tag1,tag2,obj)
{
textarea = document.getElementById(obj);
	// Code for IE
		if (document.selection) 
			{
				textarea.focus();
				var sel = document.selection.createRange();
				//alert(sel.text);
				sel.text = tag1 + sel.text + tag2;
			}
   else 
    {  // Code for Mozilla Firefox
		var len = textarea.value.length;
	    var start = textarea.selectionStart;
		var end = textarea.selectionEnd;
		
		var scrollTop = textarea.scrollTop;
		var scrollLeft = textarea.scrollLeft;
		
        var sel = textarea.value.substring(start, end);
	    //alert(sel);
		var rep = tag1 + sel + tag2;
        textarea.value =  textarea.value.substring(0,start) + rep + textarea.value.substring(end,len);
		
		textarea.scrollTop = scrollTop;
		textarea.scrollLeft = scrollLeft;
	}
}

function doList(tag1,tag2,obj){
textarea = document.getElementById(obj);

// Code for IE
		if (document.selection) 
			{
				textarea.focus();
				var sel = document.selection.createRange();
				var list = sel.text.split('\n');
		
				for(i=0;i<list.length;i++) 
				{
				list[i] = '<li>' + list[i] + '</li>';
				}
				//alert(list.join("\n"));
				sel.text = tag1 + '\n' + list.join("\n") + '\n' + tag2;
				
			} else
			// Code for Firefox
			{

		var len = textarea.value.length;
	    var start = textarea.selectionStart;
		var end = textarea.selectionEnd;
		var i;
		
		var scrollTop = textarea.scrollTop;
		var scrollLeft = textarea.scrollLeft;

		
        var sel = textarea.value.substring(start, end);
	    //alert(sel);
		
		var list = sel.split('\n');
		
		for(i=0;i<list.length;i++) 
		{
		list[i] = '<li>' + list[i] + '</li>';
		}
		//alert(list.join("<br>"));
        
		
		var rep = tag1 + '\n' + list.join("\n") + '\n' +tag2;
		textarea.value =  textarea.value.substring(0,start) + rep + textarea.value.substring(end,len);
		
		textarea.scrollTop = scrollTop;
		textarea.scrollLeft = scrollLeft;
 }
}