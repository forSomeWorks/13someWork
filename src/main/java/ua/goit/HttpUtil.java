package ua.goit;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HttpUtil {

    private static final Gson GSON = new Gson();
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    private static final String HOST = "https://jsonplaceholder.typicode.com";
    private static final String USERS = "/users";
    private static final String USER_NAME = "?username=";
    private static final String POSTS = "/posts";
    private static final String COMMENTS = "/comments";
    private static final String TODOS = "/todos";

    @SneakyThrows
    public static User postRequest(User newUser) {
        String requestBody = GSON.toJson(newUser);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s", HOST, USERS, newUser)))
                .header("Content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    @SneakyThrows
    public static User putRequest(int userId, User user) {
        String requestBody = GSON.toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s/%d", HOST, USERS, userId)))
                .header("Content-type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    @SneakyThrows
    public static User getRequest(int userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s/%d", HOST, USERS, userId)))
                .GET()
                .build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    @SneakyThrows
    public static List<User> getAllUsers() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(HOST + USERS))
                .GET()
                .build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), new TypeToken<List<User>>() {
        }.getType());
    }

    @SneakyThrows
    public static User getRequestByUserId(int id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s/%d", HOST, USERS, id)))
                .GET()
                .build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    @SneakyThrows
    public static List<User> getRequestUserByName(String name) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s%s%s", HOST, USERS, USER_NAME, name)))
                .GET()
                .build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), new TypeToken<List<User>>() {
        }.getType());
    }


    @SneakyThrows
    public static int deleteRequest(int id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s/%d", HOST, USERS, id)))
                .DELETE()
                .build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }

    @SneakyThrows
    public static String getRequestCommentsOfLastPost(User user) {
        Post lastPost = getLastPost(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s/%d%s", HOST, POSTS, lastPost.getId(), COMMENTS)))
                .GET()
                .build();
        String fileName = "user-" + user.getId() + "-post-" + lastPost.getId() + "-comments.json";
        HttpResponse<Path> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofFile(Paths.get(fileName)));
        return "File with comments : " + response.body();
    }

    @SneakyThrows
    private static Post getLastPost(User user) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s/%d%s", HOST, USERS, user.getId(), POSTS)))
                .GET()
                .build();
        HttpResponse<String> responsePosts = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        List<Post> posts = GSON.fromJson(responsePosts.body(), new TypeToken<List<Post>>() {
        }.getType());
        return Collections.max(posts, Comparator.comparingInt(Post::getId));
    }

    @SneakyThrows
    public static List<Task> getRequestByTasks(User user) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s/%d%s", HOST, USERS, user.getId(), TODOS)))
                .GET()
                .build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        List<Task> allTasks = GSON.fromJson(response.body(), new TypeToken<List<Task>>() {
        }.getType());
        return allTasks.stream()
                .filter(todo -> !todo.isCompleted())
                .collect(Collectors.toList());
    }
}
