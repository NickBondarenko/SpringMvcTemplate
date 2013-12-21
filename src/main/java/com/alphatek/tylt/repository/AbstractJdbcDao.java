package com.alphatek.tylt.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;

public abstract class AbstractJdbcDao extends JdbcDaoSupport implements JdbcDao {
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SimpleJdbcCall jdbcCall;

	@Autowired public AbstractJdbcDao(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override protected void initTemplateConfig() {
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getJdbcTemplate());
		jdbcCall = new SimpleJdbcCall(getJdbcTemplate());
	}

	@Override	public SimpleJdbcCall getCallable() {
		return jdbcCall;
	}

	@Override	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}
}