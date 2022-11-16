package org.dagbok;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostManager {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	public static final List<Post> POSTS = new ArrayList<>();

	/**
	 * CreatePost is a method that handles creation of posts, it asks the user to type in a title and description
	 * that will be added to the post object and later added to the list of posts
	 */
	public static void createPost(Scanner scanner) {
		System.out.println("Enter the title: ");
		Post post = new Post();
		String title = scanner.next();
		post.setTitle(title);
		scanner = new Scanner(System.in);
		System.out.println("Enter description: ");
		String description = scanner.nextLine();
		post.setDescription(description);
		post.setEntryDate(Instant.now().truncatedTo(ChronoUnit.MINUTES));
		POSTS.add(post);
		System.out.println(post.getTitle() + " " + post.getEntryDate() + "\n" + "\n" + post.getDescription() + "\n" + "\n");

		savePostToFile(post, title);
	}

	/**
	 * SavePostToFile is a method that saves a post to file
	 */
	private static void savePostToFile(Post post, String title) {
		String path = "src/main/resources/posts/" + title + ".json";

		configureObjectMapper();
		try {
			OBJECT_MAPPER.writeValue(new File(path), post);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ReadPosts is a method that shows user all saved posts in terminal
	 */

	public static void readPosts(Scanner scanner) {
		System.out.println("Here are your posts:" + "\n");
		configureObjectMapper();
		List<Post> posts = readPostsFromFiles();
		for (Post post : posts) {
			System.out.println(post.getTitle() + " " + post.getEntryDate() + "\n" + post.getDescription() + "\n" + "\n");
		}
		System.out.println("1: Go to the main menu");
		if (1 == scanner.nextInt()) {
			Main.goToMainMenu();
		}
	}

	/**
	 * ReadPostsFromFiles is a method that reads a post to file
	 */

	private static List<Post> readPostsFromFiles() {
		List<Post> posts = new ArrayList<>();
		File folder = new File("src/main/resources/posts");
		File[] files = folder.listFiles();
		try {
			if (files != null) {
				for (File file : files) {
					Post post = OBJECT_MAPPER.readValue(Paths.get(file.getPath()).toFile(), Post.class);
					posts.add(post);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return posts;
	}

	/**
	 * ConfigureObjectMapper allows serialization of dates as timestamps
	 */

	private static void configureObjectMapper() {
		OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		OBJECT_MAPPER.registerModule(new JavaTimeModule());
	}
}
