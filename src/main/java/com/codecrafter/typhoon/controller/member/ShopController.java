package com.codecrafter.typhoon.controller.member;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codecrafter.typhoon.domain.response.PostShopResponse;
import com.codecrafter.typhoon.domain.response.ShopResponse;
import com.codecrafter.typhoon.domain.response.member.SimpleShopResponse;
import com.codecrafter.typhoon.service.AuthService;
import com.codecrafter.typhoon.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/shop")
public class ShopController {

	private final AuthService authService;

	private final MemberService memberService;

	@Operation(summary = "상점목록 조회(페이징)",
		description = """
								★상점 전체목록 조회</br>
			page = 숫자 / size = 숫자 / sort = 정렬할 필드명(id, craterAt, ...)</br>
			{host}/api/shop/list?page=1
			""")
	@GetMapping("/list")
	public ResponseEntity<?> getShopList(
		@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		List<SimpleShopResponse> shopList = memberService.getShopList(pageable);
		return ResponseEntity.ok(shopList);
	}

	@GetMapping("/{memberId}")
	public ResponseEntity<ShopResponse> getShopInfo(@PathVariable Long memberId) {
		ShopResponse shopResponse = authService.getShopInfo(memberId);
		return ResponseEntity.ok(shopResponse);
	}

	@GetMapping("/{memberId}/simple")
	public ResponseEntity<PostShopResponse> getPostShopResponse(@PathVariable Long memberId) {
		PostShopResponse postShop = memberService.getPostShop(memberId);
		return ResponseEntity.ok(postShop);
	}

	// @Operation(summary = "내상점 수정",
	// 	description = """
	// 		★내상점정보 수정(상점명, 상점설명)</br>
	// 		MemberId = 숫자</br>
	// 		shopName = 상점명(문자) / description = 상점설명(문자)</br>
	// 		{</br>
	// 			"shopName": "천둥마켓",</br>
	// 			"description": "태풍마켓에서 변경."</br>
	// 		}
	// 		""")
	// @PutMapping("/{memberId}/shop")
	// public ResponseEntity<?> updateShop() {
	// 	return ResponseEntity.ok().body("updateShop");
	// }

}
