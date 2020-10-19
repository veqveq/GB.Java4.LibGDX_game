package ru.starwars;

import com.badlogic.gdx.Game;

import ru.starwars.screen.MenuScreen;

public class StarWars extends Game {
	@Override
	public void create() {
		setScreen(new MenuScreen());
	}
}
