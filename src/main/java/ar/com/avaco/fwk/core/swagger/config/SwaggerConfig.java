package ar.com.avaco.fwk.core.swagger.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket swaggerDocket() {

		return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.any()).build()

				// ---------- SECURITY ----------
				.securitySchemes(Arrays.asList(new ApiKey("Bearer", "Authorization", "header")))
				.securityContexts(Arrays.asList(SecurityContext.builder().securityReferences(defaultAuth()).build()));
	}

	private List<SecurityReference> defaultAuth() {

		AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");

		return Arrays.asList(new SecurityReference("Bearer", new AuthorizationScope[] { scope }));
	}
}
