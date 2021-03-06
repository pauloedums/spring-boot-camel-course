package com.pauloedums.microservicecamela.routes.a;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class MyFirstTimerRouter extends RouteBuilder{
	
	@Autowired 
	private GetCurrentTimeBean getCurrentTimeBean;
	
	@Autowired
	private SimpleLogginProcessingComponent loggingComponent;

	@Override
	public void configure() throws Exception {
		// timer
		// transformation
		// log
		
		from("timer:first-timer")
		.log("${body}")
			//.transform().constant("Time now is " + LocalDateTime.now())
		
		// Processing
		// Transformation
		
		.bean(getCurrentTimeBean)
		.log("${body}")
		.bean(loggingComponent)
		.log("${body}")
		.process(new SimpleLoggingProcessor())
		.to("log:first-timer");
	}

	
}


@Component
class GetCurrentTimeBean {
	public String getCurrentTime() {
		return "Time now is " + LocalDateTime.now();
	}
}

@Component
class SimpleLogginProcessingComponent {
	private Logger logger = LoggerFactory.getLogger(SimpleLogginProcessingComponent.class);
	
	public void process(String message) {
		logger.info("SimpleLogginProcessingComponent {}", message);
	}
}


class SimpleLoggingProcessor implements Processor{

	private Logger logger = LoggerFactory.getLogger(SimpleLogginProcessingComponent.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("SimpleLoggingProcessor {}", exchange.getMessage().getBody());
	}
		
}