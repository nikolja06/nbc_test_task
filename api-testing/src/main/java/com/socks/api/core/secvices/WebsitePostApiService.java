package com.socks.api.core.secvices;

import com.socks.api.core.assertions.AssertableResponse;
import com.socks.api.domain.WebsitePost;

import static com.socks.api.core.common.PathParams.ID;
import static com.socks.api.core.common.WebsitePostServiceURLs.*;

public class WebsitePostApiService extends BaseApiService {

    public AssertableResponse getAllPosts() {
        return new AssertableResponse(setUp().when().get(POSTS));
    }

    public AssertableResponse getPostById(int id) {
        return new AssertableResponse(setUp().when()
                .pathParams(ID, id)
                .get(POST_BY_ID));
    }

    public AssertableResponse getPostComments(int id) {
        return new AssertableResponse(setUp().when()
                .pathParams(ID, id)
                .get(POST_COMMENTS_BY_ID));
    }

    public AssertableResponse createPost(WebsitePost post) {
        return new AssertableResponse(setUp().when()
                .body(post)
                .post(POSTS));
    }

    public AssertableResponse updatePost(WebsitePost post) {
        return new AssertableResponse(setUp().when()
                .pathParams(ID, post.getId())
                .body(post)
                .put(POST_BY_ID));
    }

    public AssertableResponse patchPost(WebsitePost post) {
        return new AssertableResponse(setUp().when()
                .pathParams(ID, post.getId())
                .body(post)
                .patch(POST_BY_ID));
    }

    public AssertableResponse deletePost(WebsitePost post) {
        return new AssertableResponse(setUp().when()
                .pathParams(ID, post.getId())
                .delete(POST_BY_ID));
    }
}
