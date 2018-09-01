<html>
<head>
	<meta name='layout' content='accueil'/>
	<title>Authentification</title>
		<style type='text/css' media='screen'>
	#login {
		margin: 15px 0px;
		padding: 0px;
		text-align: center;
	}

	#login .inner {
		width: 400px;
		padding-bottom: 6px;
		margin: 40px auto;
		margin-left: 10px;
		text-align: left;
		border: 1px solid #990000;
		background-color: #FFFFFF;
		-moz-box-shadow: 2px 2px 2px #eee;
		-webkit-box-shadow: 2px 2px 2px #eee;
		-khtml-box-shadow: 2px 2px 2px #eee;
		box-shadow: 2px 2px 2px #eee;
	}

	#login .inner .fheader {
		padding: 18px 26px 14px 26px;
		background-color: #f7fff7;
		margin: 0px 0 14px 0;
		color: #2e4137;
		font-size: 18px;
		font-weight: bold;
	}

	#login .inner .cssform p {
		clear: left;
		margin: 0;
		padding: 4px 0 3px 0;
		padding-left: 105px;
		margin-bottom: 20px;
		height: 1%;
	}

	#login .inner .cssform input[type='text'] {
		width: 160px;
	}

	#login .inner .cssform label {
		font-weight: bold;
		float: left;
		text-align: right;
		margin-left: -105px;
		width: 110px;
		padding-top: 3px;
		padding-right: 10px;
	}

	#login #remember_me_holder {
		padding-left: 100px;
	}

	#login #submit {
		margin-left: 15px;
	}

	#login #remember_me_holder label {
		float: none;
		margin-left: 0;
		text-align: left;
		width: 240px
	}

	#login .inner .login_message {
		padding: 6px 25px 20px 25px;
		color: #c33;
	}

	#login .inner .text_ {
		width: 160px;
	}

	#login .inner .chk {
		height: 12px;
	}
	</style>
</head>

<body>
<div id='login' style="margin:50Px">
	<g:if test='${flash.message}'>
		<div class='login_message'>${flash.message}</div>
	</g:if>
	<div class='inner' style="width:400px; margin:0 auto;">
		
	<form action='/medgmao/authentification/auth' method='POST' id='loginForm' class='cssform'  style="padding:20Px">
		<p>
			<h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Autentification</h3>
		</p>
		<p>
			<label for='username'>Username</label>
			<input type='text' class='text_' name='username' id='username'/>
		</p>

		<p>
			<label for='password'>Password</label>
			<input type='password' class='text_' name='password' id='password'/>
		</p>
		
	
		<p>
			<input type='submit' id="submit" value='Envoyer'/>
		</p>
	</form>
	</div>
</div>
<script type='text/javascript'>
	(function() {
		document.forms['loginForm'].elements['username'].focus();
	})();
</script>

	<div id="spinner" class="spinner" style="display:none;">Traitement ...</div>
	<div class="footer" role="contentinfo" ><div style="float:left;">Université Internationale de Rabat</div><div style="float:right;">Version prototype</div></div>
		
</body>
</html>
