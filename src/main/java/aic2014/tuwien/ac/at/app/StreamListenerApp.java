package aic2014.tuwien.ac.at.app;

import aic2014.tuwien.ac.at.services.PublicStreamService;

public class StreamListenerApp {

	public static void main(String[] args) {
		System.out.println("Starting StreamListenerApp...  terminate when done streaming");
		
		PublicStreamService streamService = new PublicStreamService();

		streamService.listenToStream();
		
	}

}
