package com.codecrafter.typhoon.domain.request;

import com.codecrafter.typhoon.domain.entity.Member;
import com.codecrafter.typhoon.domain.enumeration.LoginType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupRequest(
	@NotBlank(message = "이메일은 필수!")
	@Email(message = "이메일 형식이 아님")
	@Size(max = 100, message = "너무짧음")
	String email,

	@NotBlank(message = "비밀번호는 필수!")
	@Size(min = 4, max = 20, message = "비밀번호는 4~20글자 이내")
	String password,

	@NotBlank(message = "이름은 필수!")
	@Pattern(regexp = "^([가-힣]+|[a-zA-Z]+)$")
	String realName,
	@NotBlank(message = "상점명은 필수!")
	@Size(min = 4, max = 20, message = "상점명은 4~20글자 이내")
	String shopName,
	String description,
	@Pattern(regexp = "^\\d{2,4}-\\d{2,4}-\\d{2,4}$", message = "전화번호 형식이 아님")
	String phone
) {
	/**
	 * record to Entity => 추가적인 로직 필요
	 *
	 * @return Member
	 */
	public Member toEntity() {
		return Member.builder()
			.email(email)
			.password(password)
			.realName(realName)
			.shopName(shopName)
			.description(description)
			.phone(phone)
			.loginType(LoginType.BASIC)
			.build();
	}
}
