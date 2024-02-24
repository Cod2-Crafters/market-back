package com.codecrafter.typhoon.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codecrafter.typhoon.domain.entity.Hashtag;
import com.codecrafter.typhoon.domain.entity.Member;
import com.codecrafter.typhoon.domain.entity.Post;
import com.codecrafter.typhoon.domain.entity.PostImage;
import com.codecrafter.typhoon.domain.request.PostCreateRequest;
import com.codecrafter.typhoon.domain.response.post.PostDetailResponse;
import com.codecrafter.typhoon.domain.response.post.SimplePostResponse;
import com.codecrafter.typhoon.exception.NoPostException;
import com.codecrafter.typhoon.repository.hashtag.HashtagRepository;
import com.codecrafter.typhoon.repository.post.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostService {

	private final PostRepository postRepository;

	private final HashtagRepository hashtagRepository;

	/**
	 * 포스트 생성 로직
	 *
	 * @param postCreateRequest 포스트 생성용 request
	 * @param me                생성하는 유저
	 * @return postId
	 */
	@Transactional(readOnly = true)
	public Long createPost(PostCreateRequest postCreateRequest, Member me) {
		Post post = Post.builder()
			.member(me)
			.title(postCreateRequest.title())
			.content(postCreateRequest.content())
			.build();

		List<PostImage> list = postCreateRequest.postImageRequestList().stream()
			.map(PostCreateRequest.ImageRequest::toEntity)
			.toList();
		post.addImages(list);

		List<String> tagNames = postCreateRequest.hashTagList();
		for (var tagName : tagNames) {
			Hashtag hashtag = hashtagRepository.findByTagName(tagName)
				.orElseGet(() -> hashtagRepository.save(new Hashtag(tagName)));
			post.addPostHashtag(hashtag);
		}
		var savedPost = postRepository.save(post);
		return post.getId();
	}

	/**
	 * SimplePostResponse 상세조회 로직
	 *
	 * @param postId
	 * @return PostDetailResponse
	 */
	public PostDetailResponse getPostDetail(Long postId) {
		Post post = postRepository.findPostWithMemberById(postId)
			.orElseThrow(NoPostException::new);
		PostDetailResponse postDetailResponse = new PostDetailResponse(post);
		List<String> hashtagList = post.getPostHashtagList()
			.stream().map(postHashtag -> postHashtag.getHashtag().getTagName())
			.toList();
		postDetailResponse.setHashtagList(hashtagList);
		return postDetailResponse;
	}

	public Slice<SimplePostResponse> getPostList(Pageable pageable) {
		Slice<Post> postSlices = postRepository.findWithMemberBy(pageable);
		Slice<SimplePostResponse> simplePostResopnseSlice = postSlices.map(SimplePostResponse::new);
		return simplePostResopnseSlice;
	}
}
