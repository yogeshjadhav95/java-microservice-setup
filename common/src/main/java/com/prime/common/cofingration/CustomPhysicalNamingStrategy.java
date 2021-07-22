package com.prime.common.cofingration;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import com.prime.common.util.StringUtils;

public class CustomPhysicalNamingStrategy implements PhysicalNamingStrategy {

	@Override
	public Identifier toPhysicalCatalogName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
		return convertToSnakeCase(identifier);
	}

	@Override
	public Identifier toPhysicalColumnName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
		return convertToSnakeCase(identifier);
	}

	@Override
	public Identifier toPhysicalSchemaName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
		return convertToSnakeCase(identifier);
	}

	@Override
	public Identifier toPhysicalSequenceName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
		return convertToSnakeCase(identifier);
	}

	@Override
	public Identifier toPhysicalTableName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
		return convertTTableToSnakeCase(identifier);
	}

	private Identifier convertToSnakeCase(final Identifier identifier) {

		if (identifier == null) {
			return null;
		}

		return Identifier.toIdentifier(StringUtils.toSnakeCase(identifier.getText()));
	}

	private Identifier convertTTableToSnakeCase(final Identifier identifier) {

		if (identifier == null) {
			return null;
		}
		final String newName = StringUtils.toSnakeCase(identifier.getText());
		return Identifier.toIdentifier(newName.trim());
	}
}