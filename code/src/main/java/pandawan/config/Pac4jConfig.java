package pandawan.config;

import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.oauth.client.LinkedIn2Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pandawan.web.CustomAuthorizer;

@Configuration
class Pac4jConfig {

  @Value("${oauth.callback.url}")
  String oauthCallbackUrl;

  @Value("${oauth.linkedin.app.id}")
  String linkedinKey;

  @Value("${oauth.linkedin.app.scope}")
  String linkedinScope;

  @Value("${oauth.linkedin.app.secret}")
  String linkedinSecret;

  @Bean
  public Config config() {

    final Config config = new Config(clients());
    config.addAuthorizer("custom", new CustomAuthorizer());
    return config;
  }

  @Bean
  LinkedIn2Client linkedinClient() {
    LinkedIn2Client client = new LinkedIn2Client(linkedinKey, linkedinSecret);
    client.setScope(linkedinScope);
    return client;
  }

  @Bean
  Clients clients() {
    return new Clients(oauthCallbackUrl, linkedinClient());
  }

}
