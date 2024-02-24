package com.codecrafter.typhoon.domain.entity;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.codecrafter.typhoon.repository.comment.CommentRepository;
import com.codecrafter.typhoon.repository.member.MemberRepository;
import com.codecrafter.typhoon.repository.post.PostRepository;

import jakarta.persistence.EntityManager;

@SpringBootTest
class SimplePostResponseTest {

	@Autowired
	private EntityManager em;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private CommentRepository commentRepository;

	@AfterEach
	void clean() {
		postRepository.deleteAll();
	}

	private int tmp;

	Member createNewMember() {
		return Member.builder()
			.email("abcd@abcd%d.com".formatted(tmp++))
			.password("password")
			.realName("realName" + tmp)
			.shopName("shopName" + tmp)
			.phone("010-010-010")
			.description("test" + tmp)
			.build();
	}

	@Test
	@Transactional
	@DisplayName("저장 테스트")
	public void test() throws Exception {

		Member member = createNewMember();
		memberRepository.save(member);

		Post post = Post.builder()
			.member(member)
			.title("title")
			.content("content")
			.build();
		postRepository.save(post);
		em.flush();
		em.clear();
		Post findPost = postRepository.findById(post.getId()).get();
		assertThat(findPost.getTitle()).isEqualTo(post.getTitle());
		assertThat(findPost.getContent()).isEqualTo(post.getContent());
	}

	@Test
	@Transactional
	@DisplayName("이미지저장 테스트")
	public void 이미지저장() throws Exception {
		Member author = createNewMember();
		Member commentor = createNewMember();
		memberRepository.save(author);
		memberRepository.save(commentor);
		Post post = Post.builder()
			.member(author)
			.title("title")
			.content("content")
			.build();

		PostImage img1 = PostImage.createPostImage("test1", true);
		PostImage img2 = PostImage.createPostImage("test2", true);
		post.addImages(img1, img2);
		postRepository.save(post);
		em.flush();
		em.clear();
		Post findPost = postRepository.findById(post.getId()).get();
		List<PostImage> postImageList = findPost.getPostImageList();
		assertThat(postImageList.size()).isEqualTo(2);
		Assertions.assertTrue(postImageList.get(0).isThumbnail());
		assertThat(postImageList.get(1).getImagePath()).isEqualTo("test2");

	}

}