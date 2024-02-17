package com.codecrafter.typhoon.domain.request.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateMemberRequest {
	@NotBlank(message = "상점명은 필수!")
	@Size(min = 4, max = 20, message = "상점명은 4~20글자 이내")
	private String shopName;

	private String description;

	@Pattern(regexp = "^\\d{2,4}-\\d{2,4}-\\d{2,4}$", message = "전화번호 형식이 아님")
	private String phone;
}
