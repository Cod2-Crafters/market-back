package com.codecrafter.typhoon.repository.member;

import com.codecrafter.typhoon.domain.response.PostShopResponse;

public interface MemberRepositoryCustom {

	PostShopResponse getPostShop(Long memberId);

}
