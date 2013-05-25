package com.alphatek.tylt.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

public interface JdbcDao {
	JdbcTemplate getJdbcTemplate();
	SimpleJdbcCall getCallable();
	NamedParameterJdbcTemplate getNamedParameterJdbcTemplate();
}