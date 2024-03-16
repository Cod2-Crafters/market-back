package com.codecrafter.typhoon.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codecrafter.typhoon.domain.entity.Category;
import com.codecrafter.typhoon.domain.entity.Hashtag;
import com.codecrafter.typhoon.domain.entity.Member;
import com.codecrafter.typhoon.domain.entity.Post;
import com.codecrafter.typhoon.domain.entity.PostImage;
import com.codecrafter.typhoon.domain.request.HashtagsRequest;
import com.codecrafter.typhoon.domain.request.post.ImageRequest;
import com.codecrafter.typhoon.domain.request.post.PostCreateRequest;
import com.codecrafter.typhoon.domain.request.post.PostUpdateRequest;
import com.codecrafter.typhoon.domain.response.SimplePostDto;
import com.codecrafter.typhoon.domain.response.post.PostDetailResponse;
import com.codecrafter.typhoon.domain.response.post.SimplePostResponse;
import com.codecrafter.typhoon.exception.NoPostException;
import com.codecrafter.typhoon.exception.NotExistException;
import com.codecrafter.typhoon.repository.BookmarkRepository;
import com.codecrafter.typhoon.repository.category.CategoryRepository;
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

	private final CategoryRepository categoryRepository;

	private final BookmarkRepository bookmarkRepository;

	private final RedisService redisService;

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
			.price(postCreateRequest.price())
			.build();
		if (postCreateRequest.categoryId() != null) {
			Category category = categoryRepository.findById(postCreateRequest.categoryId())
				.orElseGet(() -> new Category(0L, "기타"));
			post.setCategory(category);
		}

		List<PostImage> list = postCreateRequest.postImageRequestList().stream()
			.map(ImageRequest::toEntity)
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
	 * SimplePostDto 상세조회 로직
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

		Long totalPostViewCount = postRepository.getTotalPostViewCount(postId);
		postDetailResponse.setTotalViewCount(totalPostViewCount);

		int bookmarkCount = bookmarkRepository.countByPostId(postId);
		postDetailResponse.setBookmarkCount(bookmarkCount);

		return postDetailResponse;
	}

	public Slice<SimplePostResponse> getPostList(Pageable pageable) {
		Slice<Post> postSlices = postRepository.findWithMemberBy(pageable);
		Slice<SimplePostResponse> simplePostResopnseSlice = postSlices.map(SimplePostResponse::new);
		return simplePostResopnseSlice;
	}

	/**
	 * @param postId            계시글번호
	 * @param postUpdateRequest 업데이트 dto 객체
	 * @return postID
	 */
	public Long updatePost(Long postId, PostUpdateRequest postUpdateRequest) {
		Post post = postRepository.findById(postId)
			.orElseThrow(NoPostException::new);
		if (post.isDeleted()) {
			throw new NotExistException("삭제된 계시물입니다 복구 후 진행 바람");
		}

		Category category = categoryRepository.findById(postUpdateRequest.CategoryId())
			.orElseThrow(() -> new NotExistException("존재하지 않는 카테고리입니다"));
		post.setCategory(category);

		post.updateTitle(postUpdateRequest.title());
		post.updateContent(postUpdateRequest.content());
		post.updateStatus(postUpdateRequest.postStatus());
		post.updatePrice(postUpdateRequest.price());
		return post.getId();
	}

	public void deletePost(Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(NoPostException::new);
		postRepository.delete(post);
	}

	/**
	 * 해시태그배열
	 *
	 * @param postId
	 * @param hashtagsRequest 해시태그목록 List
	 */
	public void addHashtagsToPost(Long postId, HashtagsRequest hashtagsRequest) {
		Post post = postRepository.findById(postId)
			.orElseThrow(NoPostException::new);
		hashtagsRequest.hashTagList()
			.stream()
			.map(h -> hashtagRepository.findByTagName(h)
				.orElseGet(() -> hashtagRepository.save(new Hashtag(h)))
			).forEach(post::addPostHashtag);
	}

	public void removeHashtagsFromPost(Long postId, HashtagsRequest hashtagsRequest) {
		Post post = postRepository.findPostByIdWithHashTags(postId)
			.orElseThrow(NoPostException::new);
		post.getPostHashtagList(). // TODO N+1
			removeIf(ph -> {
			return hashtagsRequest.hashTagList().contains(ph.getHashtag().getTagName());
		});

	}

	/**
	 * @param postId
	 * @param imageRequest 하나의 Image
	 */
	public void addImagesToPost(Long postId, ImageRequest imageRequest) {
		Post post = postRepository.findById(postId)
			.orElseThrow(NoPostException::new);
		PostImage postImage = imageRequest.toEntity();
		post.addImages(postImage);
	}

	public void removePostImage(Long postId, Long postImageId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(NoPostException::new);
		post.getPostImageList()
			.removeIf(postImage -> postImage.getId() == postImageId);
	}

	public List<SimplePostDto> getSimplePostDtoList(List<Long> postIdList) {
		return postRepository.getSimplePostDtoList(postIdList);
	}
}
