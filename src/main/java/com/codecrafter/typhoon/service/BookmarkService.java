package com.codecrafter.typhoon.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codecrafter.typhoon.domain.entity.Bookmark;
import com.codecrafter.typhoon.domain.entity.Member;
import com.codecrafter.typhoon.domain.entity.Post;
import com.codecrafter.typhoon.domain.response.Boomark.BookmarkResponse;
import com.codecrafter.typhoon.exception.AlreadyExistException;
import com.codecrafter.typhoon.exception.NoPostException;
import com.codecrafter.typhoon.exception.NotExistException;
import com.codecrafter.typhoon.repository.BookmarkRepository;
import com.codecrafter.typhoon.repository.post.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkService {

	private final BookmarkRepository bookmarkRepository;

	private final PostRepository postRepository;

	/**
	 * 북마크 추가
	 *
	 * @param me     currentUser
	 * @param postId
	 */
	@Transactional(readOnly = false)
	public void addBookmark(Member me, Long postId) {
		bookmarkRepository.findByMemberIdAndPostId(me.getId(), postId)
			.ifPresent((bookmark) -> {
				throw new AlreadyExistException();
			});
		Post post = postRepository.findById(postId)
			.orElseThrow(NoPostException::new);
		bookmarkRepository.save(new Bookmark(me, post));

	}

	public void deleteBookmark(Member me, Long postId) {
		Bookmark bookmark = bookmarkRepository.findByMemberIdAndPostId(me.getId(), postId)
			.orElseThrow(NotExistException::new);
		bookmarkRepository.delete(bookmark);
	}

	public List<BookmarkResponse> getMyBookmark(Long memberId) {
		List<BookmarkResponse> myBookmarkList = bookmarkRepository.getMyBookmarkList(memberId);
		return myBookmarkList;
	}
}
