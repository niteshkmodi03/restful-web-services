package com.example.rest.webservices.restfulwebservices.sample.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest.webservices.restfulwebservices.sample.bean.HelloWorldBean;
import com.example.rest.webservices.restfulwebservices.sample.bean.SomeBean;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class HelloWorldController {

	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(path="/hello-world")
	public String getHello() {
		return "Hello-World";
	}
	
	@GetMapping(path="/hello-world-bean")
	public HelloWorldBean getHelloBean() {
		return new HelloWorldBean("Hello-World-Bean");
	}
	
	@GetMapping(path="/hello-world-internationalized")
	public String getHelloInternationalized(
		//	@RequestHeader(name="Accept-Language",required=false) Locale locale
			) {
		StringBuilder builder = new StringBuilder();
		builder.append(messageSource.getMessage("good.morning.msgs", null, "Default Message",LocaleContextHolder.getLocale()));
		builder.append(messageSource.getMessage("inspiring.msgs", null, "Default Message",LocaleContextHolder.getLocale()));
		return builder.toString();
	}
	
	@GetMapping(path="/filtered-bean")
	public SomeBean getFilteredBean() {
		return new SomeBean("value1", "value2", "value3");
	}
	
	@GetMapping(path="/filtered-list-bean")
	public List<SomeBean> getFilteredListBean() {
		return Arrays.asList(new SomeBean("value1", "value2", "value3"),new SomeBean("value1", "value2", "value3"));
	}
	
	@GetMapping(path="/dynamic-filtered-bean")
	public MappingJacksonValue getDynamicallyFilteredBean() {
		SomeBean someBean = new SomeBean("value1", "value2", "value3");
		MappingJacksonValue mapping= new MappingJacksonValue(someBean);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2","field3");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		mapping.setFilters(filters);
		return mapping;
	}
	
	
	@GetMapping(path="/dynamic-filtered-list-bean")
	public MappingJacksonValue getDynamicallyFilteredListBean() {
		List<SomeBean> list = Arrays.asList(new SomeBean("value1", "value2", "value3"),new SomeBean("value1", "value2", "value3"));
		MappingJacksonValue mapping= new MappingJacksonValue(list);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2","field4");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		mapping.setFilters(filters);
		return mapping;
	}
}

