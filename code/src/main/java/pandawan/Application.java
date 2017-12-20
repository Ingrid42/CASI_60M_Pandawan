package pandawan;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


// import pandawan.dao.RoleRepository;
import pandawan.dao.UtilisateurRepository;

// import pandawan.entities.Role;
import pandawan.entities.Utilisateur;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		ApplicationContext ctx=SpringApplication.run(Application.class, args);
		// RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
		UtilisateurRepository utilisateurRepository=  ctx.getBean(UtilisateurRepository.class);

		if(true){

		// 	Role roleAdmin = roleRepository.save(new Role("ADMIN","FAIT DES CHOSES D'ADMIN"));
		// 	List<Role> listeRole = new ArrayList<Role>();
		// 	listeRole.add(roleAdmin);
			Utilisateur user0 = utilisateurRepository.save(new Utilisateur("test","test@test.test", "NomTest", "PrenomTest",new BCryptPasswordEncoder().encode("test") ));
		// 	Utilisateur user1 = utilisateurRepository.save(new Utilisateur("test1","test1@test.test", "NomTest", "PrenomTest",new BCryptPasswordEncoder().encode("test"),listeRole));
		// 	Utilisateur user2 = utilisateurRepository.save(new Utilisateur("test2","test2@test.test", "NomTest", "PrenomTest", new BCryptPasswordEncoder().encode("test"), listeRole));
		//
		}

	}

}
