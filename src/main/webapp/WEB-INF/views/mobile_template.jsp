<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1"> 
		<title>Multi-page template</title> 
		<link rel="stylesheet" href="resources/themes/closing-the-word-gap.min.css" />
		<link rel="stylesheet" href="resources/themes/jquery.mobile.icons.min.css" />
		<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile.structure-1.4.5.min.css" />
		<script src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
		<script src="http://code.jquery.com/mobile/1.1.1/jquery.mobile-1.1.1.min.js"></script>
	</head> 
	<body> 
	<!-- Start of first page: #one -->
	<div data-role="page" id="one" data-theme="a">
		<div data-role="header">
			<h1>Closing the Word Gap</h1>
		</div><!-- /header -->

		<div data-role="content">	
			<h2>Closing the Word Gap</h2>
			
			<p>I have an <code>id</code> of "one" on my page container. I'm first in the source order so I'm shown when the page loads.</p>	
			
			<p>This is a multi-page boilerplate template that you can copy to build your first jQuery Mobile page. This template contains multiple "page" containers inside, unlike a <a href="page-template.html"> single page template</a> that has just one page within it.</p>	
			<p>Just view the source and copy the code to get started. All the CSS and JS is linked to the jQuery CDN versions so this is super easy to set up. Remember to include a meta viewport tag in the head to set the zoom level.</p>
			<p>You link to internal pages by referring to the <code>id</code> of the page you want to show. For example, to <a href="#two" >link</a> to the page with an <code>id</code> of "two", my link would have a <code>href="#two"</code> in the code.</p>	

			<h3>Show internal pages:</h3>
			<p><a href="#two" data-role="button">Show page "two"</a></p>	
			<p><a href="#popup" data-role="button" data-rel="dialog" data-transition="pop">Show page "popup" (as a dialog)</a></p>
		</div><!-- /content -->
		
		<div data-role="footer">
			<h4></h4>
		</div><!-- /footer -->
	</div><!-- /page one -->


	<!-- Start of second page: #two -->
	<div data-role="page" id="two" data-theme="a">

		<div data-role="header">
			<h1>Two</h1>
		</div><!-- /header -->
		<div data-role="content">	
			<h2>Two</h2>
			<p>Test</p>
			<a href="#" data-role="button" data-icon="star">Star button</a>
			<p><a href="#one" data-direction="reverse" data-role="button">Back to page "one"</a></p>	
		</div><!-- /content -->
		
		<div data-role="footer">
			<h4></h4>
		</div><!-- /footer -->
	</div><!-- /page two -->


	<!-- Start of third page: #popup -->
	<div data-role="page" id="popup" data-theme="a">

		<div data-role="header">
			<h1>Dialog</h1>
		</div><!-- /header -->

		<div data-role="content">	
			<h2>Popup</h2>
			<p>I have an id of "popup" on my page container and only look like a dialog because the link to me had a <code>data-rel="dialog"</code> attribute which gives me this inset look and a <code>data-transition="pop"</code> attribute to change the transition to pop. Without this, I'd be styled as a normal page.</p>		
			<p><a href="#one" data-rel="back" data-role="button" data-inline="true" data-icon="back">Back to page "one"</a></p>	
		</div><!-- /content -->
		
		<div data-role="footer">
			<h4></h4>
		</div><!-- /footer -->
	</div><!-- /page popup -->

	</body>
</html>