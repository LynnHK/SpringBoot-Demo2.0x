package com.example.demo.gracefulecoding;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GuessNumGameTest {
	
	@Test
	public void test() {
		int secret = 13579;
		GuessNumGame game = new GuessNumGame(secret);
		
		assertEquals("0A0B", game.guess(24680));
		
		assertEquals("0A1B", game.guess(24681));
		assertEquals("1A0B", game.guess(14680));
		
		assertEquals("0A5B", game.guess(97351));
		assertEquals("5A0B", game.guess(13579));
		
		assertEquals("2A2B", game.guess(15349));
	}

}