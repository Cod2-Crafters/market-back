package com.codecrafter.typhoon.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import com.codecrafter.typhoon.domain.entity.Member;
import com.codecrafter.typhoon.domain.entity.Post;
import com.codecrafter.typhoon.domain.entity.PostImage;
import com.codecrafter.typhoon.domain.request.PostCreateRequest;
import com.codecrafter.typhoon.repository.member.MemberRepository;
import com.codecrafter.typhoon.repository.post.PostRepository;

import lombok.RequiredArgsConstructor;

@Profile("dev")
@RequiredArgsConstructor
public class TmpInitService implements ApplicationRunner {
	private final MemberRepository memberRepository;
	private final MemberService memberService;
	private final PostService postService;
	private final PostRepository postRepository;

	private PostCreateRequest createPostRequest(int num) {
		List<PostCreateRequest.ImageRequest> images = IntStream.range(0, num)
			.mapToObj(i -> new PostCreateRequest.ImageRequest("/test" + i, i == 0))
			.collect(Collectors.toList());

		String title = "this is title " + num;
		String content = "this is content " + num;
		List<String> tags = List.of("해시", "태그", "리스트", "반복" + num); // Add dynamic tag based on num

		return new PostCreateRequest(title, content, images, tags);
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
				PostImage.createPostImage("localhost:8080/api/file/static/test1.jpeg", true),
				PostImage.createPostImage("localhost:8080/api/file/static/tes2.jpeg", false),
				PostImage.createPostImage("localhost:8080/api/file/static/test3.jpeg", false),
				PostImage.createPostImage("localhost:8080/api/file/static/test4.jpeg", false),
				PostImage.createPostImage("localhost:8080/api/file/static/test5.jpeg", false)
			)

		);

	}
}
