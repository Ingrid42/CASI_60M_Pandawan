package pandawan.web;

import org.pac4j.core.authorization.authorizer.ProfileAuthorizer;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.oauth.profile.linkedin2.LinkedIn2Profile;

import java.util.List;

public class CustomAuthorizer extends ProfileAuthorizer<CommonProfile> {

    @Override
    public boolean isAuthorized(final WebContext context, final List<CommonProfile> profiles) throws HttpAction {
        return isAnyAuthorized(context, profiles);
    }

    @Override
    public boolean isProfileAuthorized(final WebContext context, final CommonProfile profile) {
        if (profile == null) {
            return false;
        }
        LinkedIn2Profile linkedinProfile = (LinkedIn2Profile) profile;
        System.out.println(linkedinProfile.getEmail());
        return linkedinProfile.getEmail().endsWith("@insa-rouen.fr");
    }
}