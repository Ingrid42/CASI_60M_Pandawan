var isLogin;
var isPassword;

function valideLogin() {
	isLogin = null;
	isPassword = null;
	checkLogin();
}

//function setGlobal(varName, varValue) { window[varName] = varValue;}

function checkLogin() { // check le mot de passe dans la base de données
	// aprés le changement du champ login
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
				checkPasswordLogin();

			} else {
				$("#labelMessage").html("ERREUR: <b>Login incorrect</b>");
				if (!$('#divMessage').isActive()) {
					$('#divMessage').toggle();
				}
				isLogin = false;
			}
		}
	});
}
function checkPasswordLogin() { // check le mot de passe dans la base de données
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
						$("#labelMessage").html(
								"login: <b>Mot de passe incorrect</b>");
						if (!$('#divMessage').isActive()) {
							$('#divMessage').toggle();
						}

						isPassword = false;
					}
				}
			});
}
