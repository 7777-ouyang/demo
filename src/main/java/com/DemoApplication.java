package com;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.okali.concurrency.HttpFilter;
import com.okali.concurrency.HttpInterceptor;

/**
 * 动态数据源需要添加<code>exclude = {DataSourceAutoConfiguration.class</code>排除加载
 * @author OKali
 *
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DemoApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	public FilterRegistrationBean httpFilter() { // springboot使用filter
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new HttpFilter());
		filterRegistrationBean.addUrlPatterns("/threadlocal/*");  // 需要拦截的url
		return filterRegistrationBean;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {  // springboot使用interceptor
		registry.addInterceptor(new HttpInterceptor()).addPathPatterns("/**");
	}
}
