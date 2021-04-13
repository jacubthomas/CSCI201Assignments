function searchComp(){
	var ticker = document.getElementById("searchticker").value;
	if(!(ticker == null)){
		alert("Please enter a ticker")
		return
	}
	
	 fetch('http://localhost:8080/jharring_CSCI201_Assignment4/company?' + new URLSearchParams({
		Ticker: ticker
	}), {
		method: "GET"
	})
    .then(response => response.text())
    .then(response => {
        if(response === "Invalid ticker"){
            alert(response)
        }else{
            var userData = JSON.parse(response) 
            console.log(userData)
            localStorage.setItem("CID", userData.Username);
			localStorage.setItem("Ticker", userData.Username);
			localStorage.setItem("CompanyName", userData.Username);
			localStorage.setItem("ExchangeCode", userData.Username);
			localStorage.setItem("Description", userData.Username);
        }
	})
	
	
	
}