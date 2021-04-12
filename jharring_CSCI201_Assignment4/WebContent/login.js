function login(){
    var username = document.getElementById('lgn_user').value
	var password = document.getElementById('lgn_pass').value
    if(!(username && password)){
		alert("Please fill out all fields.")
		return
	}

    fetch('http://localhost:8080/jharring_CSCI201_Assignment4/login?' + new URLSearchParams({
		Username: username,
		Password: password
	}), {
		method: "GET"
	})
    .then(response => response.text())
    .then(response => {
        if(response === "Invalid Login"){
            alert(response)
        }else{
            var userData = JSON.parse(response) 
            console.log(userData)
            localStorage.setItem("Username", userData.Username);
            window.location.href = "index.html";
        }
	})
}