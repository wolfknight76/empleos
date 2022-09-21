package mx.gob.rppjal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Value("${empleosapp.ruta.imagenes}")
	private String ruta;

	@Value("${empleosapp.ruta.cv}")
	private String rutaCv;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		// Configuración de los recursos estáticos (imagenes de las vacantes)
		registry.addResourceHandler("/logos/**").addResourceLocations("file:" + ruta);
		
		// Configuración de los recursos estáticos (archivos de los CV)
		registry.addResourceHandler("/cv/**").addResourceLocations("file:" + rutaCv);
	}
}
