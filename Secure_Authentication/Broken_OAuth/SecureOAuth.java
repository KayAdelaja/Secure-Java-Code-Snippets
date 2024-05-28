import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SecureOAuth {

    @Value("${oauth2.resource-server.jwt.issuer-uri}")
    private String issuerUri;

    private final JwtDecoder jwtDecoder;

    public SecureOAuth(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @GetMapping("/secure-oauth")
    public Map<String, Object> getUserInfo(
            @RegisteredOAuth2AuthorizedClient("my-client") OAuth2AuthorizedClient authorizedClient,
            OAuth2AuthenticationToken authentication) {

        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        String tokenValue = accessToken.getTokenValue();

        try {
            Jwt jwt = jwtDecoder.decode(tokenValue);
            return jwt.getClaims();
        } catch (JwtException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }
}
