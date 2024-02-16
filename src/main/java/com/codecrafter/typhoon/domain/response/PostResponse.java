package com.codecrafter.typhoon.domain.response;

import com.codecrafter.typhoon.domain.enumeration.PostStatus;

public record PostResponse(Long id, String dltYn, Long price, String mdmeberId, String title, PostStatus postStatus) {

}


