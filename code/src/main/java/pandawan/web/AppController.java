package pandawan.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pac4j.core.client.Clients;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.oauth.client.LinkedIn2Client;
import org.pac4j.springframework.security.authentication.Pac4jAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import pandawan.dao.UtilisateurRepository;
import pandawan.entities.Utilisateur;

@Controller
public class AppController {

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Qualifier("sessionRegistry")
	private SessionRegistry sessionRegistry;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAutoGrowCollectionLimit(768);
	}

	@Autowired
	Clients clients;

	@RequestMapping("/login")
	String login(HttpServletRequest request, HttpServletResponse response, Model model) throws HttpAction {
		if (isAuthenticated()) {
			return "redirect:/home";
		}
		List<Utilisateur>  utilisateurs = utilisateurRepository.findAll();
		model.addAttribute("ListeUtilisateurs", utilisateurs);
		final WebContext context = new J2EContext(request, response);
		final LinkedIn2Client linkedinClient = (LinkedIn2Client) clients.findClient("LinkedIn2Client");
		model.addAttribute("linkedinAuthUrl", getClientLocation(linkedinClient, context));
		return "login";
	}

	public String getClientLocation(LinkedIn2Client client, WebContext context) throws HttpAction {
		return client.getRedirectAction(context).getLocation();
	}

	protected boolean isAuthenticated() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication auth = context.getAuthentication();
		return (auth != null && auth instanceof Pac4jAuthentication);
	}


	@RequestMapping(value="/home", method=RequestMethod.GET)
	public String home(Model model, WebRequest request){
		return "home";
	}
	
	@RequestMapping(value="/gotohome", method=RequestMethod.GET)
	public String goToHome(Model model, WebRequest request){
		if (isAuthenticated()) {
			return "redirect:/home";
		} else {
			return "login";
		}
	}

}
