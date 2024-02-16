package com.codecrafter.typhoon.controller.member;

import com.codecrafter.typhoon.domain.request.MemberRequest;
import com.codecrafter.typhoon.repository.member.MemberRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/shop")
public class ShopController {
    MemberRepository memberRepository;

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
