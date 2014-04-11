package org.haw.cas.guiadapter.example;
import com.google.common.eventbus.Subscribe;

public class MySubscribe {
	
	@Subscribe
	public void handleEvent(MessageEvent event) {

		System.out.println("abc2" +  event);
	}

}
