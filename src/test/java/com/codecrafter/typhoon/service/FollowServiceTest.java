package com.codecrafter.typhoon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.codecrafter.typhoon.repository.FollowRepository;
import com.codecrafter.typhoon.repository.member.MemberRepository;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
class FollowServiceTest {

	@Autowired
	private EntityManager em;

	@Autowired
	private FollowService followService;

	@Autowired
	private FollowRepository followRepository;

	@Autowired
	private MemberRepository memberRepository;

	// Member getMember1() {
	//
	// 	return Member.builder()
	// 		.email("member1@member1.com")
	// 		.password("password")
	// 		.shopName("testShopname1")
	// 		.description("testDescription1")
	// 		.realName("test1")
	// 		.phone("010-0000-0000")
	// 		.build();
	// }
	//
	// Member getMember2() {
	// 	return Member.builder()
	// 		.email("member2@member2.com")
	// 		.password("password")
	// 		.shopName("testShopname2")
	// 		.description("testDescription2")
	// 		.realName("test1")
	// 		.phone("010-0000-0000")
	// 		.build();
	// }
	//
	// Member getMe() {
	// 	return Member.builder()
	// 		.email("me@me.com")
	// 		.password("password")
	// 		.shopName("myshopname")
	// 		.description("testDescription2")
	// 		.realName("test2")
	// 		.phone("010-0000-0000")
	// 		.build();
	// }
	//
	// @AfterEach
	// void clean() {
	// 	memberRepository.deleteAll();
	// 	followRepository.deleteAll();
	// }
	//
	// @DisplayName(value = "member1을 내가 팔로우")
	// @Test
	// @Transactional
	// void test1() {
	// 	Member member1 = getMember1();
	// 	Member me = getMe();
	// 	memberRepository.save(member1);
	// 	memberRepository.save(me);
	// 	followService.followMember(member1.getId(), me);
	//
	// 	assertThat(followRepository.count()).isEqualTo(1);
	// 	Follow follow = followRepository.findAll().get(0);
	// 	assertThat(follow.getFollower().getId()).isEqualTo(member1.getId());
	// 	assertThat(follow.getFollowing().getId()).isEqualTo(me.getId());
	//
	// }
	//
	// @DisplayName(value = "member1을 내가 언팔로우")
	// @Test
	// void test2() {
	// 	Member member1 = getMember1();
	// 	Member me = getMe();
	// 	memberRepository.save(member1);
	// 	memberRepository.save(me);
	// 	followService.followMember(member1.getId(), me);
	// 	followService.unFollowMember(member1.getId(), me);
	// 	assertThat(followRepository.count()).isEqualTo(0);
	// }
	//
	// @DisplayName("팔로워, 팔로잉 리스트 테스트")
	// @Test
	// void test3() {
	// 	Member member1 = getMember1();
	// 	Member member2 = getMember2();
	// 	Member me = getMe();
	// 	memberRepository.save(member1);
	// 	memberRepository.save(member2);
	// 	memberRepository.save(me);
	// 	followService.followMember(member1.getId(), me);
	// 	followService.followMember(member2.getId(), me);
	// 	followService.followMember(member1.getId(), member2);
	//
	// 	FollowerResponse followers1 = followService.getFollowers(me.getId());
	// 	assertThat(followers1.followerList().size()).isEqualTo(2);
	// 	assertThat(followers1.follwingId()).isEqualTo(me.getId());
	// 	FollowerResponse followers2 = followService.getFollowers(member2.getId());
	// 	assertThat(followers2.followerList().size()).isEqualTo(1);
	//
	// 	FollowingResponse followings1 = followService.getFollowings(member1.getId());
	// 	assertThat(followings1.followerId()).isEqualTo(member1.getId());
	// 	assertThat(followings1.followingList().size()).isEqualTo(2);
	// }
	//
	// @DisplayName("중복테스트")
	// @Test
	// void test4() {
	// 	Member member1 = getMember1();
	// 	Member me = getMe();
	// 	memberRepository.save(member1);
	// 	memberRepository.save(me);
	// 	followService.followMember(member1.getId(), me);
	// 	assertThrows(AlreadyExistException.class,
	// 		() -> followService.followMember(member1.getId(), me));
	// }

}