package com.me.game;

import main.ProgramLoop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "game";
		cfg.useGL20 = false;
		cfg.width = 1024;
		cfg.height = 678;
		
		new LwjglApplication(new ProgramLoop(), cfg);
	}
}
