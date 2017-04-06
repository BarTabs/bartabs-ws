package com.bartabs.ws.employee.dataaccess.jbdc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bartabs.ws.employee.dataaccess.EmployeeDao;
import com.bartabs.ws.employee.model.Employee;
import com.bartabs.ws.util.NameFormatter;

@Repository("Employee.EmployeeDao")
public class EmployeeDaoImpl implements EmployeeDao
{

	@Autowired
	NamedParameterJdbcTemplate template;

	@Override
	public List<Employee> getEmployeesByBarID(Long barID)
	{
		// @formatter:off
		String sql = ""
				+ "SELECT be.objectid as employee_id, u.objectid as user_id, first_name, last_name, middle_initial, phone_number, "
				+ "		location_id, username, password, user_type, salt, be.bar_id "
				+ "FROM bartabs.bar_employee be "
				+ "JOIN bartabs.user u ON be.user_id = u.objectid "
				+ "WHERE bar_id = :barID "
				+ "		AND active = 1"; 
		// @formatter:on

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("barID", barID);

		return template.query(sql, params, new RowMapper<Employee>()
		{

			@Override
			public Employee mapRow(ResultSet rs, int arg1) throws SQLException
			{
				Employee row = new Employee();
				row.setEmployeeId(rs.getLong("employee_id"));
				row.setUserId(rs.getLong("user_id"));
				row.setObjectID(rs.getLong("user_id"));
				row.setBarId(rs.getLong("bar_id"));
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String middleInitial = rs.getString("middle_initial");

				row.setFirstName(firstName);
				row.setLastName(lastName);
				row.setMiddleInitial(middleInitial);
				row.setFormattedName(NameFormatter.format(firstName, lastName, middleInitial, false));

				row.setPhoneNumber(rs.getString("phone_number"));
				row.setUsername(rs.getString("username"));
				row.setPassword(rs.getString("password"));
				row.setUserType(rs.getInt("user_type"));
				row.setSalt(rs.getBytes("salt"));

				return row;
			}

		});
	}

	@Override
	public Employee getEmployeeByID(Long employeeID)
	{
		// @formatter:off
		String sql = ""
				+ "SELECT be.objectid as employee_id, u.objectid as user_id, first_name, last_name, middle_initial, phone_number, location_id, "
				+ "		username, password, user_type, salt "
				+ "FROM bartabs.bar_employee be "
				+ "JOIN bartabs.user u ON be.user_id = u.objectid "
				+ "WHERE be.objectid = :employeeID ";
		// @formatter:on

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("employeeID", employeeID);

		return template.queryForObject(sql, params, new RowMapper<Employee>()
		{

			@Override
			public Employee mapRow(ResultSet rs, int arg1) throws SQLException
			{
				Employee row = new Employee();
				row.setEmployeeId(rs.getLong("employee_id"));
				row.setUserId(rs.getLong("user_id"));
				row.setObjectID(rs.getLong("user_id"));

				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String middleInitial = rs.getString("middle_initial");

				row.setFirstName(firstName);
				row.setLastName(lastName);
				row.setMiddleInitial(middleInitial);
				row.setFormattedName(NameFormatter.format(firstName, lastName, middleInitial, false));

				row.setPhoneNumber(rs.getString("phone_number"));
				row.setUsername(rs.getString("username"));
				row.setPassword(rs.getString("password"));
				row.setUserType(rs.getInt("user_type"));
				row.setSalt(rs.getBytes("salt"));

				return row;
			}
		});
	}

	@Override
	public Long linkEmployeeToBar(Long barId, Long userID)
	{
		// @formatter:off
		String sql = ""
				+ "INSERT INTO bartabs.bar_employee "
				+ "  (user_id, bar_id) "
				+ "VALUES"
				+ "  (:userID, :barID)";
		// @formatter:on

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("userID", userID);
		params.addValue("barID", barId);

		template.update(sql, params);

		final String getIDQuery = "SELECT MAX(objectid) FROM bartabs.bar_employee ";

		return template.queryForObject(getIDQuery, new MapSqlParameterSource(), Long.class);
	}

	@Override
	public Employee getEmployeeByUserID(Long userID, Long barID)
	{
		// @formatter:off
		String sql = ""
				+ "SELECT be.objectid as employee_id, u.objectid as user_id, first_name, last_name, middle_initial, phone_number, location_id, "
				+ "		username, password, user_type, salt "
				+ "FROM bartabs.bar_employee be "
				+ "JOIN bartabs.user u ON be.user_id = u.objectid "
				+ "WHERE be.user_id = :userID "
				+ "	 AND be.bar_id = :barID";
		// @formatter:on

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("userID", userID);
		params.addValue("barID", barID);

		return template.queryForObject(sql, params, new RowMapper<Employee>()
		{

			@Override
			public Employee mapRow(ResultSet rs, int arg1) throws SQLException
			{
				Employee row = new Employee();
				row.setEmployeeId(rs.getLong("employee_id"));
				row.setUserId(rs.getLong("user_id"));
				row.setObjectID(rs.getLong("user_id"));

				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String middleInitial = rs.getString("middle_initial");

				row.setFirstName(firstName);
				row.setLastName(lastName);
				row.setMiddleInitial(middleInitial);
				row.setFormattedName(NameFormatter.format(firstName, lastName, middleInitial, false));

				row.setPhoneNumber(rs.getString("phone_number"));
				row.setUsername(rs.getString("username"));
				row.setPassword(rs.getString("password"));
				row.setUserType(rs.getInt("user_type"));
				row.setSalt(rs.getBytes("salt"));

				return row;
			}
		});
	}

}
