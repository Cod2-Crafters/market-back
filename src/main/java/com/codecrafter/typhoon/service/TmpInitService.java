package com.codecrafter.typhoon.service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.codecrafter.typhoon.domain.entity.Category;
import com.codecrafter.typhoon.domain.entity.Member;
import com.codecrafter.typhoon.domain.entity.Post;
import com.codecrafter.typhoon.domain.entity.PostImage;
import com.codecrafter.typhoon.domain.request.PostCreateRequest;
import com.codecrafter.typhoon.repository.category.CategoryRepository;
import com.codecrafter.typhoon.repository.member.MemberRepository;
import com.codecrafter.typhoon.repository.post.PostRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// @Profile("dev")
@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!test")
public class TmpInitService implements ApplicationRunner {
	private final MemberRepository memberRepository;
	private final MemberService memberService;
	private final PostService postService;
	private final PostRepository postRepository;
	private final CategoryRepository categoryRepository;

	private final List<Long> CATEGORIES = Arrays.asList(11L, 12L, 13L, 21L, 22L, 23L, 31L, 32L, 33L, 41L, 42L, 43L);
	private final Random random = new Random();

	private final Integer getRandomInt() {
		int i = random.nextInt(Integer.MAX_VALUE);
		return i > 0 ? i : -i;
	}

	private PostCreateRequest createPostRequest(int num) {
		int idx = random.nextInt(CATEGORIES.size());
		Long categoryId = CATEGORIES.get(idx);
		List<PostCreateRequest.ImageRequest> images = IntStream.range(0, num)
			.mapToObj(i -> new PostCreateRequest.ImageRequest("/test" + i, i == 0))
			.collect(Collectors.toList());

		String title = "this is title " + num;
		String content = "this is content " + num;
		List<String> tags = List.of("해시", "태그", "리스트", "반복" + num); // Add dynamic tag based on num
		PostCreateRequest postCreateRequest = new PostCreateRequest(categoryId, title, content, images, tags,
			getRandomInt());

		return new PostCreateRequest(categoryId, title, content, images, tags, getRandomInt());
	}

	@Transactional
	@Override
	public void run(ApplicationArguments args) {

		List<Member> allMembers = memberRepository.findAll();
		int num = 1;
		for (int i = 0; i < allMembers.size(); i++) {
			Member member = allMembers.get(i);
			for (int j = 0; j <= i; j++) {
				PostCreateRequest postRequest = createPostRequest(num++);
				postService.createPost(postRequest, member);
			}
		}

		Post post = postRepository.findById(1L)
			.orElseThrow(RuntimeException::new);
		post.addImages(
			List.of(
				PostImage.createPostImage("/api/file/static/test1.jpeg", true),
				PostImage.createPostImage("/api/file/static/tes2.jpeg", false),
				PostImage.createPostImage("/api/file/static/test3.jpeg", false),
				PostImage.createPostImage("/api/file/static/test4.jpeg", false),
				PostImage.createPostImage("/api/file/static/test5.jpeg", false)
			)
		);
	}

	@PostConstruct
	@Transactional
	public void init() {

		setAllCategory();

	}

	private void setAllCategory() {

		if (categoryRepository.count() != 0) {
			return;
		}

		log.warn("#############\n카테고리삽입중..............\b#############");
		// 대카테고리 생성
		Category electronics = new Category(1L, "전자제품");
		Category fashion = new Category(2L, "패션/의류");
		Category homeLiving = new Category(3L, "생활/가정");
		Category hobbies = new Category(4L, "취미/여가");
		Category others = new Category(0L, "기타");

		// 각 대카테고리에 속한 소카테고리 생성 및 설정
		electronics.addChild(new Category(11L, "스마트폰/태블릿"));
		electronics.addChild(new Category(12L, "컴퓨터/노트북"));
		electronics.addChild(new Category(13L, "가전제품"));

		fashion.addChild(new Category(21L, "남성 의류"));
		fashion.addChild(new Category(22L, "여성 의류"));
		fashion.addChild(new Category(23L, "액세서리"));

		homeLiving.addChild(new Category(31L, "주방용품"));
		homeLiving.addChild(new Category(32L, "가구/인테리어"));
		homeLiving.addChild(new Category(33L, "생활용품"));

		hobbies.addChild(new Category(41L, "스포츠용품"));
		hobbies.addChild(new Category(42L, "도서/음반"));
		hobbies.addChild(new Category(43L, "게임/취미"));

		// 카테고리 저장
		categoryRepository.save(electronics);
		categoryRepository.save(fashion);
		categoryRepository.save(homeLiving);
		categoryRepository.save(hobbies);
		categoryRepository.save(others);
	}
}
