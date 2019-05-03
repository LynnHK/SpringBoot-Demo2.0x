package com.example.demo.gracefulecoding;

public class GuessNumGame {

	private String secret;
	
	public GuessNumGame(int secret) {
		this.secret = String.valueOf(secret);
	}

	public String guess(int inputNum) {
		String inputStr = String.valueOf(inputNum);
		
		int countA = 0;
		int countB = 0;
		
		for (int i = 0; i < inputStr.length(); i++) {
			char c = inputStr.charAt(i);
			int index = secret.indexOf(c);
			if (index == i) {
				countA++;
			}
			if (index >= 0 && index != i) {
				countB++;
			}
		}
		
		return String.format("%dA%dB", countA, countB);
	}
	
	public String guess2(int inputNum) {
		String inputStr = String.valueOf(inputNum);
		
		int countA = matchRuleACount(inputStr);
		int countB = matchRuleBCount(inputStr);
		
		return String.format("%dA%dB", countA, countB);
	}
	
	private int matchRuleACount(String inputStr) {
		int count = 0;
		for (int i = 0; i < inputStr.length(); i++) {
			char c = inputStr.charAt(i);
			int index = secret.indexOf(c);
			if (index == i) {
				count++;
			}
		}
		return count;
	}

	private int matchRuleBCount(String inputStr) {
		int count = 0;
		for (int i = 0; i < inputStr.length(); i++) {
			char c = inputStr.charAt(i);
			int index = secret.indexOf(c);
			if (index >= 0 && index != i) {
				count++;
			}
		}
		return count;
	}

}
