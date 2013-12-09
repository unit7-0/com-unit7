function addState(i, name) {
	var form = document.getElementById(name);
	var span = document.createElement('span');
	var input = document.createElement('input');
	
	span.innerHTML = "State" + i + ":\t";
	
	input.type = 'text';
	input.size = '15';
	input.name = 'state$' + i;
	
	form.appendChild(span);
	form.appendChild(input);
	
	span = document.createElement('span');
	span.innerHTML = "&nbsp;jump:\t";
	
	input = document.createElement('input');
	input.type = 'text';
	input.size = '15';
	input.name = 'jump$' + i;
	
	form.appendChild(span);
	form.appendChild(input);
	
	span = document.createElement('span');
	span.innerHTML = "&nbsp;to state:\t";
	
	input = document.createElement('input');
	input.type = 'text';
	input.size = '15';
	input.name = 'to_state$' + i;
	
	form.appendChild(span);
	form.appendChild(input);
	
	var br = document.createElement('br');
	form.appendChild(br);
}
