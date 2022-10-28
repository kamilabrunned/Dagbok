package org.dagbok;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("Välkommen till dagboken du kommer nu få tre alternativ med vad du kan göra i din dagbok");

		goToMainMenu();
	}

	public static void goToMainMenu() {
		System.out.println("1: Skapa ett nytt inlägg");
		System.out.println("2: Visa inlägg");
		System.out.println("3: Avsluta programmet");

		Scanner scanner = new Scanner(System.in);

		switch (scanner.nextInt()) {
			case 1 -> {
				Post.createPost(scanner);
				goToMainMenu();
			}
			case 2 -> Post.readPosts(scanner);
			case 3 -> Main.shutDown();
		}
	}

	private static void shutDown() {
		System.out.println("Hejdå");
		System.exit(0);
	}
}