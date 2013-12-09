<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="../scripts/automate.js"></script>
</head>
<body>
	<div id="root">
	<form action="action" method="post" id="automate_form"
		name="automate_form">
		<div id="input_data">
			<span>terminals:</span>
			<input type="text" name="terminals_field" size="20"><br>
			
			<span>chain:</span>
			<input type="text" name="chain_field" size="30"><br>
			
			<span>startState:</span>
			<input type="text" name="start_state_field" size="10">
			
			<span>finalState:</span>
			<input type="text" name="final_state_field" size="10"><br>
			
			<span>Automate:</span><br>

			<script type="text/javascript">
				var i = 0;
				addState(i, 'input_data');
			</script>

		</div>
		<input type="hidden" name="html_page" id="html_page">
		<input type="button" value="addState" style="text-align:center" onClick="addState(++i, 'input_data');"><br><br>
		<input type="submit" style="text-align:center" onClick="var elem = document.getElementById('html_page'); elem.value = document.getElementById('root').innerHTML;">
	</form><br>
	<div id="log">
		log:<br>
	</div>
	</div>
</body>
</html>