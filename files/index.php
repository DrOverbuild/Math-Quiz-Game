<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Redirecting...</title>
	</head>
	<body>
	<p>Redirecting... If it doesn't work, go to <a href="https://sourceforge.net/projects/mathquizgame/">https://sourceforge.net/projects/mathquizgame/</a></p>
	
	<?php
		/* Redirect browser */
		header("Location: https://sourceforge.net/projects/mathquizgame/");
		/* Make sure that code below does not get executed when we redirect. */
		exit;
	?>
</html>
