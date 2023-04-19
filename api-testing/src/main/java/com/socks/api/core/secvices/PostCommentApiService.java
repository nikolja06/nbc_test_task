package com.socks.api.core.secvices;

import com.socks.api.core.assertions.AssertableResponse;
import static com.socks.api.core.common.PathParams.ID;
import static com.socks.api.core.common.PostCommentsServiceURLs.COMMENTS_BY_POST_ID;

public class PostCommentApiService extends BaseApiService {

    public AssertableResponse getPostComments(int id) {
        return new AssertableResponse(setUp().when()
                .pathParams(ID, id)
                .get(COMMENTS_BY_POST_ID));
    }
}
