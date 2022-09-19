package br.com.via1.pad.acesso;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


public class AcessoConfig implements WebMvcConfigurer{

	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AdminInterceptor())
			.addPathPatterns(new String[]{"/adm", "/adm/*"});
}
}