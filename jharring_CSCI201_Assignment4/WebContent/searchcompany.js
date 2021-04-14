function searchComp(){
	var ticker = document.getElementById("searchticker").value;
	if(ticker == null){
		alert("Please enter a ticker")
		return
	}
	let tick = document.getElementById("result_ticker");
	tick.innerText="";
	let name = document.getElementById("result_name");
	name.innerText="";
	let exchange = document.getElementById("result_exchange");
	exchange.innerText="";
	let start = document.getElementById("result_start");
	start.innerText="";
	let descript =  document.getElementById("result_description");
	descript.innerText="";
	let high = document.getElementById("request_high");
	high.innerText="";
	let mid = document.getElementById("request_mid");
	mid.innerText="";
	let low = document.getElementById("request_low");
	low.innerText="";
	let ap = document.getElementById("request_ap");
	ap.innerText="";
	let op =  document.getElementById("request_op");
	op.innerText="";
	let as = document.getElementById("request_as");
	as.innerText="";
	let pc = document.getElementById("request_pc");
	pc.innerText="";
	let bp = document.getElementById("request_bp");
	bp.innerText="";
	let volume = document.getElementById("request_volume");
	volume.innerText="";
	let bs = document.getElementById("request_bs");
	bs.innerText="";
	
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
            var compData = JSON.parse(response) 
            console.log(compData)
			document.getElementById("result_contain").classList.remove("hidden");
			
			//let tick = document.getElementById("result_ticker");
			txt = document.createTextNode(compData.ticker);
			tick.appendChild(txt);
			//let name = document.getElementById("result_name");
			txt = document.createTextNode(compData.companyname);
			name.appendChild(txt);
			//let exchange = document.getElementById("result_exchange");
			txt =  document.createTextNode(compData.exchangecode);
			exchange.appendChild(txt);
			//let start = document.getElementById("result_start");
			txt = document.createTextNode("Start date: " + compData.startdate);
			start.appendChild(txt);
			//let descript =  document.getElementById("result_description");
			txt = document.createTextNode(compData.description);
			descript.appendChild(txt);
			
		fetch('http://localhost:8080/jharring_CSCI201_Assignment4/lateststock?' + new URLSearchParams({
		Ticker: ticker,
		CID: compData.CID
	}), {
		method: "GET"
	})
    .then(response => response.text())
    .then(response => {
        if(response === "Invalid ticker"){
            alert(response)
        }else{
            var latestData = JSON.parse(response) 
            console.log(latestData)
			
			//let high = document.getElementById("request_high");
			txt = document.createTextNode(latestData.high_price);
			high.appendChild(txt);
			
			//let mid = document.getElementById("request_mid");
			txt = document.createTextNode(latestData.mid_price);
			mid.appendChild(txt);
			
			//let low = document.getElementById("request_low");
			txt =  document.createTextNode(latestData.low_price);
			low.appendChild(txt);
			
			//let ap = document.getElementById("request_ap");
			txt = document.createTextNode(latestData.ask_price);
			ap.appendChild(txt);
			
			//let op =  document.getElementById("request_op");
			txt = document.createTextNode(latestData.open);
			op.appendChild(txt);
			
			//let as = document.getElementById("request_as");
			txt = document.createTextNode(latestData.ask_size);
			as.appendChild(txt);
			
			//let pc = document.getElementById("request_pc");
			txt = document.createTextNode(latestData.prev_close);
			pc.appendChild(txt);
			
			//let bp = document.getElementById("request_bp");
			txt =  document.createTextNode(latestData.bid_price);
			bp.appendChild(txt);
			
			//let volume = document.getElementById("request_volume");
			txt = document.createTextNode(latestData.volume);
			volume.appendChild(txt);
			
			//let bs = document.getElementById("request_bs");
			txt =  document.createTextNode(latestData.bid_size);
			bs.appendChild(txt);
			
			let dateTime = latestData.date_timestamp;
        }
	})
        }
	})
	
}