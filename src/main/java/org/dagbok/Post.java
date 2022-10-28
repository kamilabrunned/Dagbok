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

public class Post {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public static final List<Post> POSTS = new ArrayList<>();
	private Instant entryDate;
	private String title;
	private String description;

	public Post() {
	}

	public Instant getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Instant entryDate) {
		this.entryDate = entryDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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

	private static void savePostToFile(Post post, String title) {
		String path = "src/main/resources/posts/" + title + ".json";

		configureObjectMapper();
		try {
			OBJECT_MAPPER.writeValue(new File(path), post);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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

	private static void configureObjectMapper() {
		OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		OBJECT_MAPPER.registerModule(new JavaTimeModule());
	}
}
