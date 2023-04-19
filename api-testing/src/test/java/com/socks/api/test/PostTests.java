package com.socks.api.test;

import com.github.javafaker.Faker;
import com.socks.api.ProjectConfig;
import com.socks.api.core.secvices.PostCommentApiService;
import com.socks.api.core.secvices.WebsitePostApiService;
import com.socks.api.domain.WebsitePost;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Locale;

import static com.socks.api.core.common.SchemaNames.*;
import static com.socks.api.core.conditions.Conditions.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;

public class PostTests {
    private final WebsitePostApiService postsApiService = new WebsitePostApiService();
    private final PostCommentApiService postCommentApiService = new PostCommentApiService();
    private Faker faker;

    @BeforeMethod
    public void setUp() {
        ProjectConfig prop = ConfigFactory.create(ProjectConfig.class, System.getProperties());
        RestAssured.baseURI = prop.baseURL();
        faker = new Faker(new Locale(prop.locale()));
    }


// Smoke Tests

    @Test
    public void smokeTestGetCommentsFromCommentsService() {
        WebsitePost[] posts =  postsApiService.getAllPosts().asPojo(WebsitePost[].class);

        postCommentApiService.getPostComments(posts[faker.number().numberBetween(0, posts.length)].getId())
                .shouldHave(statusCode(SC_OK))
                .shouldHave(body(not(isEmptyOrNullString())))
                .shouldHave(schema(POST_COMMENTS_SCHEMA));
    }

    @Test
    public void smokeTestGetPosts() {
        postsApiService.getAllPosts()
                .shouldHave(statusCode(SC_OK))
                .shouldHave(body(not(isEmptyOrNullString())))
                .shouldHave(schema(WEBSITE_POSTS_SCHEMA));
    }

    @Test
    public void smokeTestGetPostById() {
        WebsitePost[] posts =  postsApiService.getAllPosts().asPojo(WebsitePost[].class);
        postsApiService.getPostById(posts[faker.number().numberBetween(0, posts.length)].getId())
                .shouldHave(statusCode(SC_OK))
                .shouldHave(body(not(isEmptyOrNullString())))
                .shouldHave(schema(WEBSITE_POST_SCHEMA));
    }

    @Test
    public void smokeTestGetCommentsByPostId() {
        WebsitePost[] posts =  postsApiService.getAllPosts().asPojo(WebsitePost[].class);
        postsApiService.getPostComments(
                posts[faker.number().numberBetween(0, posts.length)].getId())
                .shouldHave(statusCode(SC_OK))
                .shouldHave(body(not(isEmptyOrNullString())))
                .shouldHave(schema(POST_COMMENTS_SCHEMA));
    }

// Positive Tests

    @Test
    public void positiveTestCreatePost() {
        WebsitePost[] posts =  postsApiService.getAllPosts().asPojo(WebsitePost[].class);
        String userId = posts[faker.number().numberBetween(0, posts.length)].getUserId();
        WebsitePost post = new WebsitePost();
        post.setUserId(userId);
        post.setTitle(faker.name().title());
        post.setBody(faker.howIMetYourMother().quote());

        postsApiService.createPost(post)
                .shouldHave(statusCode(SC_CREATED))
                .shouldHave(bodyEqualsToPojo(post));
    }

    @Test
    public void positiveTestUpdatePost() {
        WebsitePost[] posts =  postsApiService.getAllPosts().asPojo(WebsitePost[].class);
        WebsitePost post = posts[faker.number().numberBetween(0, posts.length)];
        post.setTitle(faker.name().title());
        post.setBody(faker.howIMetYourMother().quote());

        postsApiService.updatePost(post)
                .shouldHave(statusCode(SC_OK))
                .shouldHave(bodyEqualsToPojo(post));
    }

    @Test
    public void positiveTestPatchPost() {
        WebsitePost[] posts =  postsApiService.getAllPosts().asPojo(WebsitePost[].class);
        WebsitePost post = posts[faker.number().numberBetween(0, posts.length)];
        post.setTitle(faker.name().title());
        post.setBody(faker.howIMetYourMother().quote());

        postsApiService.patchPost(post)
                .shouldHave(statusCode(SC_OK))
                .shouldHave(bodyEqualsToPojo(post));
    }

    @Test
    public void positiveTestDeletePost() {
        WebsitePost[] posts =  postsApiService.getAllPosts().asPojo(WebsitePost[].class);
        WebsitePost post = posts[faker.number().numberBetween(0, posts.length)];

        postsApiService.deletePost(post)
                .shouldHave(statusCode(SC_OK));
    }

// Negative Tests

    @Test
    public void negativeTestGetPostByInvalidId() {
        postsApiService.getPostById(faker.number().numberBetween(10000,1000000))
                .shouldHave(statusCode(SC_NOT_FOUND));
    }

    @Test // TODO This test found a defect (response code is 200)
    public void negativeTestGetCommentsByInvalidPostId() {
        postsApiService.getPostComments(faker.number().numberBetween(10000,1000000))
                .shouldHave(statusCode(SC_NOT_FOUND));
    }

    @Test // TODO This test found a defect (response code is 200)
    public void negativeTestGetCommentsFromCommentsServiceByInvalidPostId() {
        postCommentApiService.getPostComments(faker.number().numberBetween(10000,1000000))
                .shouldHave(statusCode(SC_NOT_FOUND));
    }
}
