let UID = localStorage.getItem("UID");

fetch('http://localhost:8080/jharring_CSCI201_Assignment4/balance?' + new URLSearchParams({
			UID: UID
	}), {
		method: "GET"
	})
    .then(response => response.text())
    .then(response => {
		let userFinance = JSON.parse(response);
		document.getElementById("cb").innerText = userFinance.Balance;
		document.getElementById("tav").innerText = userFinance.AccountValue;
	})
	
fetch('http://localhost:8080/jharring_CSCI201_Assignment4/portfolio?' + new URLSearchParams({
			UID: UID
	}), {
		method: "GET"
	})
    .then(response => response.json())
    .then(response => {
        console.log(response);
        console.log(response[0]);
        let divvy = document.getElementById("user_portfolio");
        for(var i=0; i<response.length; i++){
				 divvy.innerHTML += 
					"<table class=\"table is-fullwidth is-bordered is-striped\">" +
						"<thead>" +
							"<tr>" +
								"<th>" +
									"<span style=\"font-size:Large; font-weight:600; margin-right:10px;\">" + response[i].ticker+ "</span>" +
									"<span style=\"color:grey;\">" + response[i].company + "<span>" + 
								"</th>" +
							"</tr>" +
						"</thead>" +
						"<tbody> " +
							"<tr>" +
								"<td>" +
									"<div class=\"columns\">" +
										"<div class=\"column is-one-fourth\">" +
										 	"<strong style=\"float:left\">" +
										 	 	"Quantity:" +
									 	 	"</strong>" +
									 	 	"<span style=\"float:right; margin-right:20px;\">" +
									 	 		response[i].quantity +
											"</span>" +
										"</div>" +
										"<div class=\"column is-one-fourth\">" +
											"<strong style=\"float:left;\">" +
												"Change:" +
											"</strong>" +
											"<span style=\"float:right; margin-right:20px;\">" +
												response[i].change +
											"</span>" +
										"</div>" +
									"</div>" +
									"<div class=\"columns\">" +
										"<div class=\"column is-one-fourth\">" +
											"<strong style=\"float:left;\">" +
												"Avg. Cost / Share:" +
											"</strong>" +
											"<span style=\"float:right; margin-right:20px;\">" +
												response[i].average +
											"</span>" +
										"</div>" +
										"<div class=\"column is-one-fourth\">" +
											"<strong style=\"float:left;\">" +
												"Current Price:" +
											"</strong>" +
											"<span style=\"float:right; margin-right:20px;\">" +
												response[i].current +
											"</span>" +
										"</div>" +
									"</div>" +
									"<div class=\"columns\">" +
										"<div class=\"column is-one-fourth\">" +
											"<strong style=\"float:left;\">" +
												"Total Cost:" +
											"</strong>" +
											"<span style=\"float:right; margin-right:20px;\">" +
												response[i].total +
											"</span>" +
										"</div>" +
										"<div class=\"column is-one-fourth\">" +
											"<strong style=\"float:left;\">" +
												"Market Value:" +
											"</strong>" +
											"<span style=\"float:right; margin-right:20px;\"> " +
												response[i].market +
											"</span>" +
										"</div>" +
									"</div>" +
								"</td>" +
							"</tr>" +
						"</tbody>" +
						"<tfoot>" + 
							"<tr class=\"override\">" +
								"<td class=\"box has-text-centered\" style=\"box-shadow:none; background-color:#F0F0F0; border-radius: 0px;\"> " +
									"<div class=\"columns\">" +
										"<div class=\"column\"></div>" +
										"<div class=\"column\">" +
											"Quantity" +
											 "<input type=\"number\" id=\"quantity\" name=\"quantity\" min=\"1\" max=\""+ response[i].quantity + "\">" +
										"</div>" +
										"<div class=\"column\"></div>" +
									"</div>" +
									"<div class=\"columns\">" +
										"<div class=\"column\"></div>" +
										"<div class=\"column is-4 control form-group\">" +
											"<label class=\"radio\">" +
												"<input type=\"radio\" name=\"radio\" value=\"buy\">" +
													"Buy" +
											"</label>" +
											"<label class=\"radio\">" +
										   		"<input type=\"radio\" name=\"radio\" value=\"sell\">" +
											    	"Sell" +
											  "</label>" +
										"</div>" +	
										"<div class=\"column\"></div>" +
									"</div>" +
									"<div class=\"columns\">" +
										"<div class=\"column\"></div>" +
										"<div class=\"column\">"+
											"<button type=\"submit\" class=\"is-light\">Submit</button>" +
										"</div>" +
										"<div class=\"column\"></div>" +
									"</div>" +
								"</td>" + 
							"</tr>" +
						"</tfoot>" +
					"</table>";		
						
			}
		})
