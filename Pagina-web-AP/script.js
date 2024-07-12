const $submit = document.getElementById("submit"),
  $email = document.getElementById("email"),
  $password = document.getElementById("password");

function validateInputs() {
  const emailRegex = /^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6})*$/;
  const passwordRegex =
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

  if ($email.value === "") {
    alert("No podes dejar el campo de email vacío.");
    return false;
  }
  if ($password.value === "") {
    alert("No podes dejar el campo de contraseña vacío.");
    return false;
  }

  if (!emailRegex.test($email.value)) {
    alert("Ingrese el formato correcto en el email");
    $email.style.border = "1px solid red";
    return false;
  }

  if (!passwordRegex.test($password.value)) {
    alert("No ha ingresado una contraseña válida.");
    $password.style.border = "1px solid red";
    return false;
  }

  return true;
}

document.addEventListener("click", (e) => {
  if (e.target === $submit) {
    //event.preventDefault();
    validateInputs();
  }
});



const url = "https://imdb-top-100-movies.p.rapidapi.com/series/";
const options = {
  method: "GET",
  headers: {
    "x-rapidapi-key": "326b5c24c9mshede2f1b9a4b6429p1c1e0fjsn7b82588f64f7",
    "x-rapidapi-host": "imdb-top-100-movies.p.rapidapi.com",
  },
};

fetch(url, options)
  .then((response) => response.json())
  .then((data) => mostrarData(data))
  .catch((error) => console.log(error));



const mostrarData = (data) => {
  console.log(data);
  let body = "";
  for (let i = 0; i < 20; i++) {
    body += `<div class="col"><div class="card" style="width: 15rem;"><img src="${data[i].image}" class="card-img-top" alt="..." onerror="this.onerror=null; this.src='https://http2.mlstatic.com/D_NQ_NP_843835-MCO72929232475_112023-O.webp';">
<div class="card-body">
<h5 class="card-title">${data[i].title}</h5>
<p class="card-text"><mark>Rating: ${data[i].rating}</mark> </p>
</div>
<div class="d-flex justify-content-around mb-5">
<button type="button" class="btn btn-primary">Ver Ahora</button>
</div>
</div>
   </div>`;
    //body += `<tr><td>${data[i].title}</td></tr><img src="${data[i].image}">`
  }

  document.getElementById("data").innerHTML = body;
};









(function () {
  "use strict";

  // ======= Sticky
  window.onscroll = function () {
    const ud_header = document.querySelector(".ud-header");
    const sticky = ud_header.offsetTop;
    const logo = document.querySelector(".navbar-brand img");

    if (window.pageYOffset > sticky) {
      ud_header.classList.add("sticky");
    } else {
      ud_header.classList.remove("sticky");
    }

    // === logo change
    if (ud_header.classList.contains("sticky")) {
      logo.src = "assets/images/logo/logo-2.svg";
    } else {
      logo.src = "assets/images/logo/logo.svg";
    }

    // show or hide the back-top-top button
    const backToTop = document.querySelector(".back-to-top");
    if (
      document.body.scrollTop > 50 ||
      document.documentElement.scrollTop > 50
    ) {
      backToTop.style.display = "flex";
    } else {
      backToTop.style.display = "none";
    }
  };

  //===== close navbar-collapse when a  clicked
  let navbarToggler = document.querySelector(".navbar-toggler");
  const navbarCollapse = document.querySelector(".navbar-collapse");

  document.querySelectorAll(".ud-menu-scroll").forEach((e) =>
    e.addEventListener("click", () => {
      navbarToggler.classList.remove("active");
      navbarCollapse.classList.remove("show");
    })
  );
  navbarToggler.addEventListener("click", function () {
    navbarToggler.classList.toggle("active");
    navbarCollapse.classList.toggle("show");
  });

  // ===== submenu
  const submenuButton = document.querySelectorAll(".nav-item-has-children");
  submenuButton.forEach((elem) => {
    elem.querySelector("a").addEventListener("click", () => {
      elem.querySelector(".ud-submenu").classList.toggle("show");
    });
  });

  // ===== wow js
  new WOW().init();

  // ====== scroll top js
  function scrollTo(element, to = 0, duration = 500) {
    const start = element.scrollTop;
    const change = to - start;
    const increment = 20;
    let currentTime = 0;

    const animateScroll = () => {
      currentTime += increment;

      const val = Math.easeInOutQuad(currentTime, start, change, duration);
      element.scrollTop = val;

      if (currentTime < duration) {
        setTimeout(animateScroll, increment);
      }
    };

    animateScroll();
  }

  Math.easeInOutQuad = function (t, b, c, d) {
    t /= d / 2;
    if (t < 1) return (c / 2) * t * t + b;
    t--;
    return (-c / 2) * (t * (t - 2) - 1) + b;
  };

  document.querySelector(".back-to-top").onclick = () => {
    scrollTo(document.documentElement);
  };

  /**********************************/
 
 let url_post_peliculas = "http://localhost:8080/apisimple/series";

 function validateForm(form)
 {
	var inputs = form.querySelectorAll("input, textarea");
	
	for(let i = 0; i < inputs.length; i++)
	{
		if(!inputs[i].value)
		{
			return false;
		}
	}
	
	return true;
 }

 
 window.postMovie = function(e)
 {
	 e.preventDefault();
	 
	 var form = document.querySelector("#myForm");
	 var elements = form.elements;
	 var btn = document.querySelector("#btnSubmit");
	 btn.setAttribute("disabled", true);
	 
	 var obj = {};
	 for(let i =0; i < elements.length; i++)
	 {
		var element = elements[i];
		if(element.name)
		{
			obj[element.name] = element.value;
		}
	 }
	 
	 console.log(obj);	 
	

	const response = fetch(url_post_peliculas, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(obj),
	})
	.then(response => {
		
		Swal.fire({
			icon: "success",
			title: "OK!",
			text: "La película fue insertada exitosamente"
		});
		
		btn.removeAttribute('disabled');
		form.reset();
	})
	.catch(error => {
		
		Swal.fire({
			icon: "error",
			title: "Oops...",
			text: "Hubo problemas al tratar de insertar la película"
		});
		
		btn.removeAttribute('disabled');
	});
	
	 return false;
 }










 document.addEventListener("DOMContentLoaded", (event) => {
  
  var blog = document.querySelector(".ud-single-blog");
  
  //Page doesn't contains blog element
  if(!blog)
  {
	  return;
  }
  
  fetch(url_post_peliculas).then(f => f.json()).then(a => {
	  
	  for(let i = 0; i < a.length; i++)
	  {
		  var clone = blog.cloneNode(true);
		  
		  var img = clone.querySelector("img");
		  var titulo = clone.querySelector(".ud-blog-title");
		  var btn = clone.querySelector(".ud-blog-date");
		  
		  img.src = a[i].imagen;
		  titulo.innerText = a[i].titulo;

		  
		  btn.onclick = function(){
			  deleteMovie(a[i]);
		  };
		  
		  var div = document.createElement("div");
		  
		  div.classList.add("col-lg-4")
		  div.classList.add("col-md-6")
		  div.appendChild(clone);
		  
		  var itemsRow = document.querySelector(".itemsRow");
		  itemsRow.appendChild(div);
	  }
  })
});	

window.deleteMovie = function(e){

		Swal.fire({
			title: "Desea eliminar la película "+ e.titulo + "?",
			showCancelButton: true,
			confirmButtonText: "Delete",
			confirmButtonColor: "red"
		}).then(result => {
	
		if (result.isConfirmed)	
		{
			fetch(url_post_peliculas, {
				method: "DELETE",
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify(e.idSerie),
			}).then(e => {
				Swal.fire({
					title: "Borrada exitosamente!",
					icon : "success",
					allowOutsideClick: false
				})
				.then(e => {								
					location.reload();
				});
				
			}).catch(e => {
				Swal.fire("Error!", "", "error");
			})
			
		}
		});
	} 

})();
