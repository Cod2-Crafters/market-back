package com.codecrafter.typhoon.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * 테이블 이름 구현 (대문자, 스네이크케이스)
 */
public class CustomUpperCaseSnakeCasePhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
		String newName = convertCamelCaseToSnakeCase(name.getText()).toUpperCase();
		return new Identifier(newName, name.isQuoted());
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
		String newName = convertCamelCaseToSnakeCase(name.getText()).toUpperCase();
		return new Identifier(newName, name.isQuoted());
	}

	private String convertCamelCaseToSnakeCase(String input) {
		return input.replaceAll("([a-z])([A-Z])", "$1_$2");
	}
}
