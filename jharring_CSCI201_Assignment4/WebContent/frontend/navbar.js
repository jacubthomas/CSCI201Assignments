window.onload = function(){
	let btn1 = document.getElementById("hs_btn");
	let btn2 = document.getElementById("ls_btn");
	let curpage = window.location.pathname;
	console.log(curpage);
	if(curpage == "/jharring_CSCI201_Assignment4/frontend/index.html"){
		btn1.classList.add("currentpage");
		btn2.classList.add("otherpage");
		btn1.classList.remove("otherpage");
		btn2.classList.remove("currentpage");
	} else if(curpage == "/jharring_CSCI201_Assignment4/frontend/loginsignup.html"){
		btn1.classList.add("otherpage")
		btn2.classList.add("currentpage");
		btn1.classList.remove("currentpage");
		btn2.classList.remove("otherpage");
	}
}