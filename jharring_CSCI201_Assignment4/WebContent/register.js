function register(){
	var username = document.getElementById('su_user').value
	var password = document.getElementById('su_pass').value
	var confirmPassword = document.getElementById('su_c_pass').value
	var email = document.getElementById('su_mail').value
	
	if(!(username && password && confirmPassword && email)){
		alert("Please fill out all fields.")
		return
	}
	if(password != confirmPassword){
		alert("Passwords do not match.")
		return
	}
	//console.log("username: " + username);
	//console.log("password: " + password);
	//console.log("confirmPassword: " + confirmPassword);
	//console.log("email: " + email);
	
	// figure out how the hash the passwords later
	fetch('http://localhost:8080/jharring_CSCI201_Assignment4/register?' + new URLSearchParams({
		User: username,
		Pass: password,
		Email: email
	}), {
		method: "GET"
	})
    .then(response => response.text())
    .then(response => {
		var userData = JSON.parse(response) 
		console.log(userData)	
		localStorage.setItem("Username", username);
		localStorage.setItem("Password", password);
		localStorage.setItem("Email", email);
		console.log(localStorage.getItem("Username"));
		console.log(localStorage.getItem("Email"));
		window.location.href = "index.html"
	})
}