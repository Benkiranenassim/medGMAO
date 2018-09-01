<!doctype html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>GMAO</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
		
		<r:require module="jquery"/>
		<!--r:require module="jquery-ui"/-->
		<r:layoutResources />
		<link rel="stylesheet" href="/medgmao/css/le-frog/jquery-ui-1.10.4.custom.min.css" type="text/css">
		
		<link rel="stylesheet" href="/medgmao/css/main.css" type="text/css">
		<link rel="stylesheet" href="/medgmao/css/mobile.css" type="text/css">
		<link rel="stylesheet" href="/medgmao/css/extention.css" type="text/css">
		
		
		<g:layoutHead/>
		
		
		<script type="text/javascript" src="/medgmao/js/interface.js"></script>
		<script type='text/javascript' src='/medgmao/js/jquery.simplemodal.js'></script>
		<script type='text/javascript' src='/medgmao/js/jquery-ui-1.10.4.custom.js'></script>
		
		
	</head>
	<body style="max-width:960px;min-height: 500Px;">
		<div id="grailsLogo" role="banner"><a href="/medgmao"><img src="${resource(dir: 'images', file: 'logo_uir.png')}" alt="HCK"/></a></div>
		<g:ifLoggedIn>			
				<g:layoutBody/>			
		</g:ifLoggedIn>
		
		<g:ifNotLoggedIn>
		
			<%----%>
			<div style="padding:10px;">
				<br/>
				<br/>
				<br/>
				
				<h4>Gestion de la maintenance assistée par ordinateur pour l'Université Internationale de Rabat</h4>
				<br/>
				<br/>
					
					<g:link controller="authentification" action="auth">Ouvrir une session</g:link>
				
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
				<h6>...</h6>
				
			</div>
			
			
			<script>
				//$('#news').load('/mederp/news/latestnews');
				$("#dock").hide();
			</script>

		</g:ifNotLoggedIn>
		
		</div>
		
		
		<!--Java script to put in a file-->
		<script type="text/javascript"> 
			$('input:submit').click(function(){
				var requiredFieldOk=1;
				/**/
				$('[required]').each(function() {
					if (this.value == '' ) {
						requiredFieldOk = requiredFieldOk*0;
					}
				});
				if (requiredFieldOk) {
					$('input:submit').hide();
					$('.keepvisible').show();
				}
			});
			
			<!--dock menu JS options -->
	
		$(document).ready(
			function() {
				$('#dock').Fisheye(
					{maxWidth: 50, items: 'a', itemsText: 'span', container: '.dock-container', itemWidth: 40, proximity: 90, halign : 'center'}
				)
				$('#dock2').Fisheye(
					{maxWidth: 60, items: 'a', itemsText: 'span', container: '.dock-container2', itemWidth: 40, proximity: 80, alignment : 'left', valign: 'bottom', halign : 'center'}
				)
			}
		);
		</script>
		
		<div id="spinner" class="spinner" style="display:none;">Traitement ...</div>
		<div class="footer" role="contentinfo" ><div style="float:left;">Université Internationale de Rabat</div><div style="float:right;">Version prototype</div></div>
		
	</body>
</html>