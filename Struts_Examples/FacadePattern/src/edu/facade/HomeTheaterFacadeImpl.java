package edu.facade;

import edu.Amplifier;
import edu.DVDPlayer;
import edu.Lights;
import edu.PopCorn;
import edu.Projector;
import edu.Screen;
import edu.Speakers;
import edu.Woofers;

public class HomeTheaterFacadeImpl implements HomeTheaterFacade {
	Amplifier amplifier;
	DVDPlayer player;
	Lights lights;
	PopCorn corn;
	Projector projector;
	Screen screen;
	Speakers speakers;
	Woofers woofers;

	public HomeTheaterFacadeImpl(Amplifier amplifier, DVDPlayer player,
			Lights lights, PopCorn corn, Projector projector, Screen screen,
			Speakers speakers, Woofers woofers) {
		super();
		this.amplifier = amplifier;
		this.player = player;
		this.lights = lights;
		this.corn = corn;
		this.projector = projector;
		this.screen = screen;
		this.speakers = speakers;
		this.woofers = woofers;
	}

	@Override
	public void endMovie() {
		corn.off();
		lights.on();
		player.stop();
		player.eject();
		speakers.off();
		woofers.off();
		player.off();
		projector.off();
		screen.off();
		System.out.println("SONY SYSTEM IS SHUTTING DOWN");
	}

	@Override
	public void watchMovie() throws InterruptedException {
		System.out.println("SONY HOME THEATER  WELCOMES  YOU");
		corn.on();
		corn.pop();
		lights.on();
		lights.dim();
		amplifier.on();
		screen.on();
		projector.on();
		woofers.on();
		speakers.on();
		player.on();
		amplifier.setSurroundings();
		amplifier.setSound(20);
		amplifier.setDVD("SONY");
		player.play("SKY LINE");
		Thread.sleep(2000);
		System.out
				.println("====================================================");
	}
}
