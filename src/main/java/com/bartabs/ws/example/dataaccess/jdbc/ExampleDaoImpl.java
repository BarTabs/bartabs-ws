package com.bartabs.ws.example.dataaccess.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

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
	public Example getData()
	{
		final String sql = "SELECT name FROM bartabs.test where objectid = :objectId";

		final MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("objectId", "1");

		return template.queryForObject(sql, params, new RowMapper<Example>()
		{

			@Override
			public Example mapRow(ResultSet rs, int arg1) throws SQLException
			{
				final Example shop = new Example();
				shop.setName(rs.getString("name"));
				// shop.setStaffName("BarTabs");

				return shop;
			}

		});
	}

}
