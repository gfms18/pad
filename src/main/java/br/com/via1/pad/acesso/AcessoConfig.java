package br.com.via1.pad.acesso;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AcessoConfig implements WebMvcConfigurer{

	public void addInterceptors(InterceptorRegistry registry) { 
		registry.addInterceptor(new AdminInterceptor())
			.addPathPatterns(new String[]{"/interno", "/interno/*", "/externo", "/externo/*"});
}
}
