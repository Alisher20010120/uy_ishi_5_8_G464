package uz.pdp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public interface DB {
    List<TgUser>TG_USERS=new ArrayList<>();
    List<User>USERS=new ArrayList<>();
    List<Post>POSTS=new ArrayList<>();
    List<Comment>COMMENTS=new ArrayList<>();


    static void generate() throws IOException, InterruptedException {
        HttpClient httpClient=HttpClient.newHttpClient();
        HttpRequest request= HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts/1/users"))
                .GET()
                .build();
        HttpResponse<String>response= httpClient.send(request,HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        Gson gson=new Gson();
        List<User> users = gson.fromJson(body, new TypeToken<List<User>>() {
        }.getType());
        USERS.addAll(users);

        HttpClient httpClient1=HttpClient.newHttpClient();
        HttpRequest request1= HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts/1/posts"))
                .GET()
                .build();
        HttpResponse<String>response1=httpClient1.send(request1,HttpResponse.BodyHandlers.ofString());
        String body1 = response1.body();
        Gson gson1=new Gson();
        List<Post> posts = gson1.fromJson(body1, new TypeToken<List<Post>>() {
        }.getType());
        POSTS.addAll(posts);

        HttpClient httpClient2=HttpClient.newHttpClient();
        HttpRequest request2= HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts/1/comments"))
                .GET()
                .build();
        HttpResponse<String> response2=httpClient2.send(request2,HttpResponse.BodyHandlers.ofString());
        String body2 = response2.body();
        Gson gson2=new Gson();
        List<Comment> comment = gson2.fromJson(body2, new TypeToken<List<Comment>>() {
        }.getType());
        COMMENTS.addAll(comment);

    }
}
