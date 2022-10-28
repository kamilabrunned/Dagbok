package org.dagbok;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("Welcome to the diary you will now have three options with what you can do in your diary");

		goToMainMenu();
	}

	public static void goToMainMenu() {
		System.out.println("1: Create a new post");
		System.out.println("2: Show posts");
		System.out.println("3: Exit the program");

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
		System.out.println("Good bye");
		System.exit(0);
	}
}