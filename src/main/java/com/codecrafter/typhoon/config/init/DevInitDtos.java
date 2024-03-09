package com.codecrafter.typhoon.config.init;

import static java.lang.String.*;

import java.util.Random;

public class DevInitDtos {

	private static final Random random = new Random();

	static record tmpUser(
		String email, String password, String loginType, String shopName, String description, String logoPath,
		String realName, String phone

	) {
		public tmpUser(int i) {
			this(
				"user%s@user%s.com".formatted(i, i),
				"user%s' password".formatted(i),
				"BASIC",
				"user%s' shopName".formatted(i),
				"user%s' description".formatted(i),
				"user%s' logoPath".formatted(i),
				"user%s' realName".formatted(i),
				join("-", valueOf(i % 10).repeat(3), valueOf(i % 10).repeat(3),
					valueOf(i % 10).repeat(3))
			);
		}

	}

	public record TmpFollow(
		Long follwerId,
		Long followingId
	) {
	}

	public record TmpPost(

	)
}
