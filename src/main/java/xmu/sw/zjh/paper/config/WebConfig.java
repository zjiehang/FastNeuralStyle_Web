package xmu.sw.zjh.paper.config;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages= {"xmu.sw.zjh.paper"})
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware
{
	
	
	@SuppressWarnings("unused")
	private ApplicationContext context;
	
	public void setApplicationContext(ApplicationContext context) throws BeansException 
	{
		this.context = context;
	}
	
	@Bean
	public ViewResolver viewResolver()
	{
		FreeMarkerViewResolver freeMarkerViewResolver=new FreeMarkerViewResolver();
		freeMarkerViewResolver.setSuffix(".ftl");
		freeMarkerViewResolver.setContentType("text/html; charset=UTF-8");
		freeMarkerViewResolver.setExposeRequestAttributes(true);
		freeMarkerViewResolver.setExposeSessionAttributes(true);
		freeMarkerViewResolver.setExposeSpringMacroHelpers(true);
		freeMarkerViewResolver.setCache(true);
		freeMarkerViewResolver.setOrder(0);
		freeMarkerViewResolver.setRequestContextAttribute("request");
		return freeMarkerViewResolver;
	}
	
	@SuppressWarnings("deprecation")
	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer(WebApplicationContext applicationContext)
	throws IOException,TemplateException
	{
		FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
		freeMarkerConfigurer.setServletContext(applicationContext.getServletContext());
		
		freemarker.template.Configuration configuration = freeMarkerConfigurer.createConfiguration();
		
		configuration.setServletContextForTemplateLoading(applicationContext.getServletContext(), "/WEB-INF/");
		configuration.setIncompatibleImprovements(freemarker.template.Configuration.VERSION_2_3_23);
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		configuration.setTagSyntax(freemarker.template.Configuration.AUTO_DETECT_TAG_SYNTAX);
		configuration.setTemplateUpdateDelay(1);
		configuration.setDefaultEncoding("UTF-8");
		configuration.setOutputEncoding("UTF-8");
		configuration.setURLEscapingCharset("UTF-8");
		configuration.setLocale(Locale.SIMPLIFIED_CHINESE);
		configuration.setDateFormat("yyyy-MM-dd");
		configuration.setTimeFormat("HH:mm:ss");
		configuration.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
		configuration.setClassicCompatible(true);
		//yangmengyang
		//configuration.setTemplateLoader(new WebappTemplateLoader(servletContext));
		freeMarkerConfigurer.setConfiguration(configuration);
		/*freeMarkerConfigurer.setDefaultEncoding("UTF-8");
		
		Properties properties = new Properties();
		properties.put("template_update_delay", 2);
		properties.put("datetime_format", "yyyy-MM-dd HH:mm:ss");
		properties.put("date_format","yyyy-MM-dd");
		properties.put("time_format","HH:mm:ss" );
		properties.put("number_format","#.##");
		
		freeMarkerConfigurer.setFreemarkerSettings(properties);
		*/
		return freeMarkerConfigurer;
	}
	 
	
	@Bean
	public CommonsMultipartResolver multipartResolver()
	{
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		resolver.setMaxUploadSize(40000000);
		return resolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) 
	{
		registry.addResourceHandler("/resources/images/**").addResourceLocations("/resources/images/");
		registry.addResourceHandler("/resources/css/**").addResourceLocations("/resources/css/");
		registry.addResourceHandler("/resources/js/**").addResourceLocations("/resources/js/");
		
	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
	{
		configurer.enable();
	}

	@Bean
	public MappingJackson2HttpMessageConverter converter()
	{
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(mapper());
		return converter;
	}
	
	@Bean
	public ObjectMapper mapper()
	{
		return new ObjectMapper();
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) 
	{
		converters.add(converter());
	}
}
