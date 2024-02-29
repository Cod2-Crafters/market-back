package com.codecrafter.typhoon.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.codecrafter.typhoon.domain.entity.Member;
import com.codecrafter.typhoon.domain.entity.Post;
import com.codecrafter.typhoon.domain.request.post.ImageRequest;
import com.codecrafter.typhoon.domain.request.post.PostCreateRequest;
import com.codecrafter.typhoon.domain.response.post.PostDetailResponse;
import com.codecrafter.typhoon.repository.hashtag.HashtagRepository;
import com.codecrafter.typhoon.repository.member.MemberRepository;
import com.codecrafter.typhoon.repository.post.PostRepository;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
class PostServiceTest {

	@Autowired
	private EntityManager em;

	@Autowired
	private PostService postService;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private HashtagRepository hashtagRepository;

	@Autowired
	private MemberRepository memberRepository;

	public Member getSavedMember() {
		Member member = Member.builder()
			.email("member1@member1.com")
			.password("password")
			.shopName("testShopname1")
			.description("testDescription1")
			.realName("test1")
			.phone("010-0000-0000")
			.build();
		return memberRepository.save(member);
	}

	public PostCreateRequest createPostRequest() {
		List<ImageRequest> collect = IntStream.range(0, 10)
			.mapToObj(i -> new ImageRequest("/test" + i, i == 0))
			.toList();

		PostCreateRequest postCreateRequest = new PostCreateRequest(
			null,
			"this is title",
			"this is content",
			collect,
			List.of("abc", "efg", "hij", "wer"),
			111
		);
		return postCreateRequest;
	}

	@BeforeEach
	void clean() {
		postRepository.deleteAll();
		hashtagRepository.deleteAll();
		memberRepository.deleteAll();
	}

	@DisplayName("Post 단건 생성")
	@Test
	void test1() {
		Member member = getSavedMember();
		PostCreateRequest postRequest = createPostRequest();
		Long postId = postService.createPost(postRequest, member);

		em.flush();
		em.clear();
		Post post = postRepository.findById(postId).get();
		assertThat(post.getId()).isEqualTo(postId);

		assertThat(post.getPostImageList().size()).isEqualTo(postRequest.postImageRequestList().size());
		assertThat(post.getPostHashtagList().size()).isEqualTo(postRequest.hashTagList().size());
	}

	@DisplayName("Post 단건 조회")
	@Test
	void test2() {
		Member member = getSavedMember();
		PostCreateRequest postRequest = createPostRequest();
		Long postId = postService.createPost(postRequest, member);
		em.flush();
		em.clear();

		PostDetailResponse postResopnse = postService.getPostDetail(postId);
		assertThat(postResopnse.getTitle()).isEqualTo(postRequest.title());
		assertThat(postResopnse.getMember().id()).isEqualTo(member.getId());
		assertThat(postResopnse.getContent()).isEqualTo(postRequest.content());
		assertThat(postResopnse.getHashtagList().size()).isEqualTo(postRequest.hashTagList().size());
		assertThat(postResopnse.getImageList().size()).isEqualTo(postRequest.postImageRequestList().size());
	}

	@DisplayName("post 다중 조회")
	@Test
	void test3() {
		Member member = getSavedMember();
		List<Long> postIdList = IntStream.range(0, 10)
			.mapToObj(i -> createPostRequest())
			.map(req -> postService.createPost(req, member))
			.toList();
		assertThat(postIdList.size()).isEqualTo(10);

	}

}