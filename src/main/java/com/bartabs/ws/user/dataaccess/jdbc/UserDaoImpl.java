package com.bartabs.ws.user.dataaccess.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bartabs.ws.exceptions.DuplicateUserNameException;
import com.bartabs.ws.exceptions.UserNotFoundException;
import com.bartabs.ws.location.model.Location;
import com.bartabs.ws.user.dataaccess.UserDao;
import com.bartabs.ws.user.model.User;
import com.bartabs.ws.util.NameFormatter;

@Repository("User.UserDao")
public class UserDaoImpl implements UserDao {
	@Autowired
	NamedParameterJdbcTemplate template;

	@Override
	public User getUserByID(final Long objectID) throws UserNotFoundException {
		try {
			// @formatter:off
			String sql = ""
				+ "SELECT u.objectid, u.first_name, u.last_name, u.middle_initial, u.phone_number, u.location_id, "
				+ "		u.user_type, u.username, u.password, l.objectid AS location_id, l.address1, l.address2, "
				+ "		l.city, l.state, l.zip "
				+ "FROM bartabs.user u "
				+ "LEFT JOIN bartabs.location l ON l.objectid = u.location_id "
				+ "WHERE u.objectid = :objectID ";
			// @formatter:on

			return template.queryForObject(sql, new MapSqlParameterSource("objectID", objectID), new RowMapper<User>() {

				@Override
				public User mapRow(ResultSet rs, int arg1) throws SQLException {
					User user = new User();
					user.setObjectID(rs.getLong("objectid"));

					String firstName = rs.getString("first_name");
					String lastName = rs.getString("last_name");
					String middleInitial = rs.getString("middle_initial");

					user.setFirstName(firstName);
					user.setLastName(lastName);
					user.setMiddleInitial(middleInitial);
					user.setFormattedName(NameFormatter.format(firstName, lastName, middleInitial, false));
					user.setPhoneNumber(rs.getString("phone_number"));

					Integer userType = rs.getInt("user_type");

					if (!rs.wasNull()) {
						user.setUserType(userType);
					} else {
						user.setUserType(userType);
					}

					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));

					Location location = new Location();
					location.setObjectID(rs.getLong("location_id"));
					location.setAddress1(rs.getString("address1"));
					location.setAddress2(rs.getString("address2"));
					location.setCity(rs.getString("city"));
					location.setState(rs.getString("state"));
					location.setZipCode(rs.getString("zip"));

					user.setLocation(location);

					return user;
				}

			});
		} catch (EmptyResultDataAccessException ex) {
			throw new UserNotFoundException();
		}
	}

	@Override
	public User getUserByUserName(final String username) throws UserNotFoundException {
		try {
			// @formatter:off
			String sql = ""
				+ "SELECT u.objectid, u.first_name, u.last_name, u.middle_initial, u.phone_number, u.location_id, "
				+ "		u.user_type, u.username, u.password, u.salt, l.objectid AS location_id, l.address1, l.address2, "
				+ "		l.city, l.state, l.zip "
				+ "FROM bartabs.user u "
				+ "LEFT JOIN bartabs.location l ON l.objectid = u.location_id "
				+ "WHERE username = :username";
			// @formatter:on

			return template.queryForObject(sql, new MapSqlParameterSource("username", username), new RowMapper<User>() {

				@Override
				public User mapRow(ResultSet rs, int arg1) throws SQLException {
					User user = new User();
					user.setObjectID(rs.getLong("objectid"));

					String firstName = rs.getString("first_name");
					String lastName = rs.getString("last_name");
					String middleInitial = rs.getString("middle_initial");

					user.setFirstName(firstName);
					user.setLastName(lastName);
					user.setMiddleInitial(middleInitial);
					user.setFormattedName(NameFormatter.format(firstName, lastName, middleInitial, false));
					user.setPhoneNumber(rs.getString("phone_number"));

					Integer userType = rs.getInt("user_type");

					if (!rs.wasNull()) {
						user.setUserType(userType);
					} else {
						user.setUserType(userType);
					}

					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setSalt(rs.getBytes("salt"));

					Location location = new Location();
					location.setObjectID(rs.getLong("location_id"));
					location.setAddress1(rs.getString("address1"));
					location.setAddress2(rs.getString("address2"));
					location.setCity(rs.getString("city"));
					location.setState(rs.getString("state"));
					location.setZipCode(rs.getString("zip"));

					user.setLocation(location);

					return user;
				}

			});
		} catch (EmptyResultDataAccessException ex) {
			throw new UserNotFoundException();
		}
	}

	@Override
	public Long createUser(final User user) throws DuplicateUserNameException {
		try {
			// @formatter:off
			String sql = "" 
				+ "INSERT INTO bartabs.user "
				+ "    (first_name, last_name, middle_initial, phone_number, location_id, user_type, "
				+ "     username, password, salt, created_timestamp, modified_timestamp) " 
				+ "VALUES "
				+ "    (:firstName, :lastName, :middleInitial, :phoneNumber, :locationID, :userType, "
				+ "     :username, :password, :salt, now(), now()) ";
			// @formatter:on

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("firstName", user.getFirstName());
			params.addValue("lastName", user.getLastName());
			params.addValue("middleInitial", user.getMiddleInitial());
			params.addValue("phoneNumber", user.getPhoneNumber());
			Long locationID = user.getLocation() != null ? user.getLocation().getObjectID() : null;
			params.addValue("locationID", locationID);
			params.addValue("userType", user.getUserType());
			params.addValue("username", user.getUsername());
			params.addValue("password", user.getPassword());
			params.addValue("salt", user.getSalt());

			template.update(sql, params);

			final String getIDQuery = "SELECT MAX(objectid) FROM bartabs.user ";

			return template.queryForObject(getIDQuery, new MapSqlParameterSource(), Long.class);
		} catch (DuplicateKeyException ex) {
			throw new DuplicateUserNameException();
		}
	}

	@Override
	public void updateUser(final User user) {
		// @formatter:off
		String sql = ""
			+ "UPDATE bartabs.user "
			+ "SET 	first_name = :firstName, "
			+ "		last_name = :lastName, "
			+ "		middle_initial = :middleInitial, "
			+ "		phone_number = :phoneNumber "
			+ "WHERE objectid = :objectID ";
		// @formatter:on

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("objectID", user.getObjectID());
		params.addValue("firstName", user.getFirstName());
		params.addValue("lastName", user.getLastName());
		params.addValue("middleInitial", user.getMiddleInitial());
		params.addValue("phoneNumber", user.getPhoneNumber());
		params.addValue("username", user.getUsername());
		params.addValue("password", user.getPassword());

		template.update(sql, params);

	}

	@Override
	public void removeUser(final User user) {
		// @formatter:off
		String sql = ""
			+ "UPDATE bartabs.user "
			+ "SET 	deleted = 1, "
			+ "		active = 0 "
			+ "WHERE objectid = :objectID ";
		// @formatter:on

		template.update(sql, new MapSqlParameterSource("objectID", user.getObjectID()));
	}

	@Override
	public void registerForFcmNotifications(final Long userID, final String fcmToken) {
		// @formatter:off
		String sql = ""
			+ "UPDATE bartabs.user "
			+ "SET 	fcm_token = :fcmToken "
			+ "WHERE objectid = :objectID ";
		// @formatter:on

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("objectID", userID);
		params.addValue("fcmToken", fcmToken);

		template.update(sql, params);
	}

	@Override
	public String retrieveFcmRegistrationToken(Long userID) {
		// @formatter:off
		String sql = ""
			+ "SELECT fcm_token "
			+ "FROM bartabs.user "
			+ "WHERE objectid = :objectID ";
		// @formatter:on

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("objectID", userID);

		return template.queryForObject(sql, params, String.class);
	}

	@Override
	public String refreshUuid(Long userID) {
		// @formatter:off
		String sql = ""
			+ "UPDATE bartabs.user "
			+ "SET 	uuid = uuid() "
			+ "WHERE objectid = :objectID ";
		// @formatter:on

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("objectID", userID);
		template.update(sql, params);

		// @formatter:off
		String query = ""
				+ "SELECT uuid "
				+ "FROM bartabs.user "
				+ "WHERE objectid = :objectID";
		// @formatter:on

		return template.queryForObject(query, params, String.class);
	}

	@Override
	public User getUserFromUuid(String uuid) {
		try {
			// @formatter:off
			String sql = ""
					+ "SELECT objectid "
					+ "FROM bartabs.user "
					+ "WHERE uuid = :uuid";
			// @formatter:on

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("uuid", uuid);
			Long userID = template.queryForObject(sql, params, Long.class);

			return getUserByID(userID);
		} catch (Exception ex) {
			return null;
		}
	}

}