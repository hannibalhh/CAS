package org.haw.cas.guiadapter.example;
import com.google.common.eventbus.EventBus;

public class Test {

	public static void main(String[] args) {
		MySubscribe foo = new MySubscribe();
		EventBus eventBus = new EventBus();
		eventBus.register(foo);
		eventBus.post(new MyMessageEvent());

	}

}
