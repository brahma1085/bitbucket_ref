package edu.test;

import edu.Amplifier;
import edu.DVDPlayer;
import edu.Lights;
import edu.PopCorn;
import edu.Projector;
import edu.Screen;
import edu.Speakers;
import edu.Woofers;
import edu.facade.HomeTheaterFacade;
import edu.facade.HomeTheaterFacadeImpl;

public class HTFTest {
	public static void main(String[] args) throws Exception{
		Amplifier amplifier = new Amplifier();
		DVDPlayer player = new DVDPlayer();
		Lights lights = new Lights();
		PopCorn corn = new PopCorn();
		Projector projector = new Projector();
		Screen screen = new Screen();
		Speakers speakers = new Speakers();
		Woofers woofers = new Woofers();
		HomeTheaterFacade facade = new HomeTheaterFacadeImpl(amplifier, player,
				lights, corn, projector, screen, speakers, woofers);
		facade.watchMovie();
		facade.endMovie();
	}
}