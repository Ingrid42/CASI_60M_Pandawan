var isLogin;
var isPassword;

function valideLogin() {
	console.log("passage1")
	isLogin = null;
	isPassword = null;
	checkLogin();
}


function checkLogin() { // check le mot de passe dans la base de données
	// aprés le changement du champ login
	console.log("passage2")
	var Url = "checkUsername/" + $('#loginLogin').val();
	$.ajax({
		url : Url,
		async : false,
		cache : false,
		type : 'GET',
		data : "",
		success : function(result) {
			if (result) {
				$("#labelMessage").html("");
				checkPassword();

			} else {
				$("#labelMessage").html("ERREUR: <b>Login incorrect</b>");
				isLogin = false;
			}
		}
	});
}
function checkPassword() {
	console.log("passage3")
	var Url = "checkPassword";
	var user = {

		login : $('#loginLogin').val(),
		password : $('#passwordLogin').val()
	}
	
	$
			.ajax({
				url : Url,
				async : false,
				cache : false,
				type : 'GET',
				data : user,
				success : function(result) {
					if (result) {
						$("#labelMessage").html("");
						$("#formLogin").submit();
					} else {
						$("#labelMessage").html("<b>Mot de passe incorrect</b>");
						isPassword = false;
					}
				}
			});
}
