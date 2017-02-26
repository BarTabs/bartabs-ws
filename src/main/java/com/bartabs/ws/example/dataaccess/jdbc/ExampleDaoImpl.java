package com.bartabs.ws.example.dataaccess.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bartabs.ws.example.dataaccess.ExampleDao;
import com.bartabs.ws.example.model.Example;

@Repository
public class ExampleDaoImpl implements ExampleDao
{

	@Autowired
	NamedParameterJdbcTemplate template;

	@Override
	public List<Example> getData()
	{
		final String sql = "SELECT name FROM bartabs.test";

		final MapSqlParameterSource params = new MapSqlParameterSource();

		return template.query(sql, params, new RowMapper<Example>()
		{

			@Override
			public Example mapRow(ResultSet rs, int arg1) throws SQLException
			{
				final Example example = new Example();
				example.setName(rs.getString("name"));
				// shop.setStaffName("BarTabs");

				return example;
			}

		});
	}

}
