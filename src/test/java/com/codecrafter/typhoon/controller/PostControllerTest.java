package com.codecrafter.typhoon.controller;

import static com.codecrafter.typhoon.service.JWTService.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.stream.IntStream;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.codecrafter.typhoon.config.UserPrincipal;
import com.codecrafter.typhoon.domain.entity.Member;
import com.codecrafter.typhoon.domain.request.PostCreateRequest;
import com.codecrafter.typhoon.repository.hashtag.HashtagRepository;
import com.codecrafter.typhoon.repository.member.MemberRepository;
import com.codecrafter.typhoon.repository.post.PostRepository;
import com.codecrafter.typhoon.service.JWTService;
import com.codecrafter.typhoon.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private HashtagRepository hashtagRepository;

	@Autowired
	private JWTService jwtService;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PostService postService;

	Member getSavedMember() {
		Member member = Member.builder()
			.email("email@email.com")
			.shopName("mockshopname")
			.realName("testrealName")
			.description("description")
			.password("sdfsefaef")
			.phone("111-111-1111")
			.build();
		memberRepository.save(member);
		return member;
	}

	String getJwt(Member member) {
		return "Bearer " + jwtService.createAccessToken(new UserPrincipal(member));
	}

	@AfterEach
	void clean() {
		postRepository.deleteAll();
		postRepository.physicalDeleteForTest();
		hashtagRepository.deleteAll();
		memberRepository.deleteAll();

	}

	private PostCreateRequest createPostRequest() {
		List<PostCreateRequest.ImageRequest> collect = IntStream.range(0, 10)
			.mapToObj(i -> new PostCreateRequest.ImageRequest("/test" + i, i == 0))
			.toList();

		PostCreateRequest postCreateRequest = new PostCreateRequest(
			"this is title",
			"this is content",
			collect,
			List.of("abc", "efg", "hij", "wer")
		);
		return postCreateRequest;
	}

	@Test
	@DisplayName("post 생성 ")
	void test1() throws Exception {
		Member member = getSavedMember();
		String jwt = getJwt(member);
		String json = objectMapper.writeValueAsString(createPostRequest());
		System.out.println("json = " + json);

		mockMvc.perform(post("/api/post/")
				.contentType(APPLICATION_JSON)
				.content(json)
				.header(ACCESS_TOKEN_HEADER, jwt)
			).andExpect(status().isCreated())
			.andExpect(header().string("Location", Matchers.matchesPattern("https?://[^/]+/api/post/[0-9]+")))
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@DisplayName("post 조회")
	void test2() throws Exception {
		Member member = getSavedMember();
		Long postId = postService.createPost(createPostRequest(), member);
		mockMvc.perform(get("/api/post/{postId}", postId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(postId))
			.andExpect(jsonPath("$.member").exists())
			.andExpect(jsonPath("$.member.id").value(member.getId()))
			.andExpect(jsonPath("$.title").exists())
			.andExpect(jsonPath("$.content").exists())
			.andExpect(jsonPath("$.status").exists())
			.andExpect(jsonPath("$.imageList").exists())
			.andExpect(jsonPath("$.hashtagList").exists())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@DisplayName("다중 조회 + 페이징")
	void test3() throws Exception {
		Member member = getSavedMember();
		List<Long> postIdList = IntStream.range(0, 30)
			.mapToObj(i -> createPostRequest())
			.map(req -> postService.createPost(req, member))
			.toList();

		mockMvc.perform(get("/api/post/list")
				.param("page", "2")
				.param("size", "5")
				.contentType(APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content", hasSize(5)))
			.andExpect(jsonPath("$.pageable.pageNumber").value(2))
			.andExpect(jsonPath("$.last").value(false))
			.andDo(MockMvcResultHandlers.print());
		System.out.println("postIdList = " + postIdList);
	}

}