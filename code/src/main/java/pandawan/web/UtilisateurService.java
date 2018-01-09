package pandawan.web;

import pandawan.dao.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UtilisateurService {

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@RequestMapping(value="/checkUsername/{login}", method=RequestMethod.GET)
	public boolean login(@PathVariable(value="login") String login){
		System.out.println("login="+login);
		if(utilisateurRepository.findByLogin(login) != null){
			return true;
		} else return false;
	}

	@RequestMapping(value="/checkPassword", method=RequestMethod.GET)
	public boolean password(@RequestParam(name="login") String login, @RequestParam(name="password") String password){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String existingPassword = password;
		String dbPassword = utilisateurRepository.findByLogin(login).getPassword();

		if (passwordEncoder.matches(existingPassword, dbPassword)) {
			System.out.println("password ok");
			return true;
		} else {
			System.out.println("password faux");
			return false;
		}
	}
}
