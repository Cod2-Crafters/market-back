package com.codecrafter.typhoon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codecrafter.typhoon.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member,Long> {

	Optional<Member> findByEmail(String Email);

	Boolean existsByEmail(String email);
	Boolean existsByShopName(String shopName);

}
