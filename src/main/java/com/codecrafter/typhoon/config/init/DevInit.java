package com.codecrafter.typhoon.config.init;

import static com.codecrafter.typhoon.config.init.DevInitDtos.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * dev (베포나 마찬가지임) 초기 데이터 설정
 */
@Component
@Profile("dev")
@Slf4j
@RequiredArgsConstructor
public class DevInit implements ApplicationRunner {

	private final JdbcTemplate jdbcTemplate;

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private static final int tmpMemberCount = 12;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("INSERT_MEMBER");
		insertTmpMember();
		log.info("INSERT_FOLLOW");
		insertTmpFollow();
	}

	/**
	 * member insert (첫번쨔로 실행)
	 */
	private void insertTmpMember() {

		String sql = """
			INSERT INTO MEMBER (EMAIL, PASSWORD, LOGIN_TYPE, SHOP_NAME,description, LOGO_PATH ,REAL_NAME, PHONE,CREATED_AT)
			values (?,?,?,?,?,?,?,?,now());
			""";

		List<tmpUser> tmpUsers = IntStream.range(1, tmpMemberCount)
			.mapToObj(tmpUser::new)
			.toList();

		jdbcTemplate.batchUpdate(sql,
			new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					tmpUser tmpUser = tmpUsers.get(i);
					ps.setString(1, tmpUser.email());
					ps.setString(2, tmpUser.password());
					ps.setString(3, tmpUser.loginType());
					ps.setString(4, tmpUser.shopName());
					ps.setString(5, tmpUser.description());
					ps.setString(6, tmpUser.logoPath());
					ps.setString(7, tmpUser.realName());
					ps.setString(8, tmpUser.phone());
				}

				@Override
				public int getBatchSize() {
					return tmpUsers.size();
				}
			});
	}

	private void insertTmpFollow() {
		String sql = """
			INSERT INTO  FOLLOW (follower_id,following_id,created_at)
			values(?,?,now())
			""";
		List<TmpFollow> tmpFollows = LongStream.range(1, tmpMemberCount)
			.boxed()
			.flatMap(i -> LongStream.range(1, i)
				.mapToObj(j -> new TmpFollow(i, j)))
			.toList();
		jdbcTemplate.batchUpdate(sql,
			tmpFollows,
			tmpFollows.size(),
			(ps, tmpFollow) -> {
				ps.setLong(1, tmpFollow.follwerId());
				ps.setLong(2, tmpFollow.followingId());
			}
		);

	}

	private void initTmpPost() {

	}

}
