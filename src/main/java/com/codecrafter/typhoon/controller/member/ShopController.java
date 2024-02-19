package com.codecrafter.typhoon.controller.member;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codecrafter.typhoon.domain.request.MemberRequest;
import com.codecrafter.typhoon.domain.response.ShopResponse;
import com.codecrafter.typhoon.domain.response.member.SimpleShopResponse;
import com.codecrafter.typhoon.service.AuthService;
import com.codecrafter.typhoon.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/shop")
public class ShopController {

	private final AuthService authService;

	private final MemberService memberService;

	@GetMapping("/list")
	public ResponseEntity<?> getShopList(
		@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		List<SimpleShopResponse> shopList = memberService.getShopList(pageable);
		return ResponseEntity.ok().body(shopList);
	}

	@GetMapping("/{memberId}")
	public ResponseEntity<ShopResponse> getShopInfo(@PathVariable Long memberId) {
		ShopResponse shopResponse = authService.getShopInfo(memberId);
		return ResponseEntity.ok().body(shopResponse);
	}

	@Operation(summary = "내상점 화면 조회", description = "로그인한 회원의 상점 관리 화면을 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "ok"),
		@ApiResponse(responseCode = "400", description = "bad request"),
		@ApiResponse(responseCode = "404", description = "not found")
	})
	@GetMapping("/{memberId}/shop")
	public ResponseEntity<?> getMyShop(@PathVariable Long memberId) {
		return ResponseEntity.ok().body("getMyShop");
	}

	@Operation(summary = "내상점 수정", description = "내상점 정보 수정")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "ok"),
		@ApiResponse(responseCode = "400", description = "not found"),
		@ApiResponse(responseCode = "500", description = "internal err")
	})
	@PutMapping("/{memberId}/shop")
	public ResponseEntity<?> updateShop(@PathVariable Long memberId, @RequestBody MemberRequest member) {
		return ResponseEntity.ok().body("updateShop");
	}

}
