package pl.com.app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import pl.com.app.parsers.FileNames;
import pl.com.app.parsers.json.*;

import javax.persistence.Entity;
import javax.servlet.ServletContext;

@Configuration
@ComponentScan("pl.com.app")
@EnableWebMvc
@RequiredArgsConstructor
public class AppConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private final ServletContext servletContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public ViewResolver viewResolver()
    {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        resolver.setContentType("text/html;charset=UTF-8");
        return resolver;
    }

    @Bean
    public TemplateEngine templateEngine()
    {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setEnableSpringELCompiler(true);
        engine.addDialect(new Java8TimeDialect());
        engine.setTemplateResolver(templateResolver());
        return engine;
    }

    private ITemplateResolver templateResolver()
    {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("file:C:/ProgramowanieKM/ElectionCommitteeAppModule/web/src/main/webapp/static/")
                .setCachePeriod(1)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }

    @Bean
    public CandidateConverter candidateConverter() {
        return new CandidateConverter(servletContext.getRealPath("/") + FileNames.CANDIDATES);
    }

    @Bean
    public PoliticalPartyConverter politicalPartyConverter() {
        return new PoliticalPartyConverter(servletContext.getRealPath("/") + FileNames.POLITICAL_PARTIES);
    }

    @Bean
    public ConstituencyConverter constituencyConverter() {
        return new ConstituencyConverter(servletContext.getRealPath("/") + FileNames.CONSTITUENCIES);
    }

    @Bean
    public VoterConverter voterConverter() {
        return new VoterConverter(servletContext.getRealPath("/") + FileNames.VOTERS);
    }

    @Bean
    public TokenConverter tokenConverter() {
        return new TokenConverter(servletContext.getRealPath("/") + FileNames.TOKENS);
    }
}
