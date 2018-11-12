Creating a drop-down navigation menu using jQuery and CSS is simple.

Start off with some html for a basic navigation menu. This menu only has two links, the second of which is going to have a drop-down menu.


<div id="menu">
  <ul>
    <li><a href="#">Link One</a></li>
    <li><a href="#">Link Two</a>
      <ul class="dropdown-menu">
        <li><a href="#">Dropdown Link</a></li>
        <li><a href="#">Another Dropdown Link</a></li>
      </ul>
    </li>
  </ul>
</div>
Make sure you insert that second <ul> inside of the <li> tags of the drop-down menu’s parent link.

In the CSS, set a display: none on your drop-down menu so that it doesn’t appear upon the initial page load.

#menu a{
 text-decoration: none;
 color: #333;
 font-family: sans-serif;
 padding: 5px;
}

#menu ul li{
 display: inline;
}

.dropdown-menu{
 display: none;
}
All that’s left to do is write a few lines of jQuery that allow for the drop-down menu to slideDown when the parent link is hovered upon (I’ve also added a line of code to make the drop-down menu smoothly slide back up when the user is done hovering). It should look something like this:


$(document).ready(function(){
  $("#menu li").hover(function(){
    $(".dropdown-menu", this).slideDown(100);
  }, function(){
    $(".dropdown-menu", this).stop().slideUp(100);
  });
})
The .slideUp and .slideDown parameters determine how long the sliding animation lasts, so be sure to customize these to your liking. This code will accommodate as many navigation links and drop-down menus as you need, just don’t forget to give every drop-down menu <ul> the same class that you used to call that first drop-down menu in the jQuery code.

$(document).ready(function() {
	  // executes when HTML-Document is loaded and DOM is ready
	  console.log("document is ready");
	});


	$(window).load(function() {
	  // executes when complete page is fully loaded, including all frames, objects and images
	  console.log("window is loaded");
	});
	
	document.addEventListener("click", function(){
	    document.getElementById("demo").innerHTML = "Hello World";
	});
	
	document.addEventListener("mouseover", myFunction);
	document.addEventListener("click", someOtherFunction);
	document.addEventListener("mouseout", someOtherFunction);
	
	// Attach an event handler to the document
	document.addEventListener("mousemove", myFunction);

	// Remove the event handler from the document
	document.removeEventListener("mousemove", myFunction);
	
	if (document.addEventListener) {                // For all major browsers, except IE 8 and earlier
	    document.addEventListener("click", myFunction);
	} else if (document.attachEvent) {              // For IE 8 and earlier versions
	    document.attachEvent("onclick", myFunction);
	}