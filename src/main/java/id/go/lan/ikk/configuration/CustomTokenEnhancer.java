package id.go.lan.ikk.configuration;

import id.go.lan.ikk.module.user.entity.UserEntity;
import id.go.lan.ikk.module.user.model.CoordinatorTypeDto;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.LinkedHashMap;
import java.util.Map;


public class CustomTokenEnhancer extends JwtAccessTokenConverter {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		UserEntity user = (UserEntity) authentication.getPrincipal();

		Map<String, Object> info = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());

		 info.put("email", user.getEmail());
			info.put("name", user.getName());
			info.put("jenisKoordinator", (user.getCoordinatorTypeEntity() == null) ? null : new CoordinatorTypeDto(user.getCoordinatorTypeEntity().getId(), user.getCoordinatorTypeEntity().getName()));
			info.put("instansi", user.getAgency().getName());

		DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
		customAccessToken.setAdditionalInformation(info);

		return super.enhance(customAccessToken, authentication);
	}
}