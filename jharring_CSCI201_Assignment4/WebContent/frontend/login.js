function login(){
    var username = document.getElementById('usr').value
	var password = document.getElementById('pwd').value
    if(!(username && password)){
		alert("Please fill out all fields.")
		return
	}

    fetch('http://localhost:8080/Group_Project/login?' + new URLSearchParams({
		username: username,
		password: password
	}), {
		method: "GET"
	})
    .then(response => response.text())
    .then(response => {
        if(response === "Invalid Login"){
            alert(response)
        }else{
            userData = JSON.parse(response) 
            console.log(userData)
            localStorage.setItem("UserID", userData.UserID)
            localStorage.setItem("username", userData.username);
            localStorage.setItem("firstname", userData.firstname);
            localStorage.setItem("lastname", userData.lastname);
            window.location.href = "dashboard.html"
        }
	})
}