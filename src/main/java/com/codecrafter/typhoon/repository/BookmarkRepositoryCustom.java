package com.codecrafter.typhoon.repository;

import java.util.List;

import com.codecrafter.typhoon.domain.response.Boomark.BookmarkResponse;

public interface BookmarkRepositoryCustom {

	public List<BookmarkResponse> getMyBookmarkList(Long memberId);
}
