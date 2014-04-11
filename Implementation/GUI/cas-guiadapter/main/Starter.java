package main;

import org.adapter.IGUIAdapter;

public class Starter {

	public static void main(String[] args) {
		Starter starter = new Starter();
		starter.run();
	}

	private void run() {
		System.out.println("Starter: start..");

		IGUIAdapter instance = GUIAdapterImpl.getInstance();

		System.out.println("Starter: finish!");
	}

}
