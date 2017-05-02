package com.bartabs.ws.location.dataaccess.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bartabs.ws.location.dataaccess.LocationDao;
import com.bartabs.ws.location.model.Location;

@Repository("Location.LocationDao")
public class LocationDaoImpl implements LocationDao {
	@Autowired
	NamedParameterJdbcTemplate template;

	@Override
	public Location getLocationByID(final Long locationID) {
		// @formatter:off
		String sql = ""
			+ "SELECT objectid, address1, address2, city, state, zip, latitude, longitude, radius "
			+ "FROM bartabs.location "
			+ "WHERE objectid = :locationID";
		// @formatter:on

		return template.queryForObject(sql, new MapSqlParameterSource("locationID", locationID),
				new RowMapper<Location>() {

					@Override
					public Location mapRow(ResultSet rs, int arg1) throws SQLException {
						Location location = new Location();
						location.setObjectID(rs.getLong("objectid"));
						location.setAddress1(rs.getString("address1"));
						location.setAddress2(rs.getString("address2"));
						location.setCity(rs.getString("city"));
						location.setState(rs.getString("state"));
						location.setZipCode(rs.getString("zip"));
						location.setLatitude(rs.getString("latitude"));
						location.setLongitude(rs.getString("longitude"));
						location.setRadius(rs.getDouble("radius"));

						return location;
					}

				});
	}

	@Override
	public Long createLocation(final Location location) {
		// @formatter:off
		String sql = ""
			+ "INSERT INTO bartabs.location "
			+ "    (address1, address2, city, state, zip, latitude, longitude, radius) "
			+ "VALUES "
			+ "    (:address1, :address2, :city, :state, :zipCode, :latitude, :longitude, :radius) ";
		// @formatter:on

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("address1", location.getAddress1());
		params.addValue("address2", location.getAddress2());
		params.addValue("city", location.getCity());
		params.addValue("state", location.getState());
		params.addValue("zipCode", location.getZipCode());
		params.addValue("latitude", location.getLatitude());
		params.addValue("longitude", location.getLongitude());
		params.addValue("radius", location.getRadius());

		template.update(sql, params);

		final String getIDQuery = "SELECT MAX(objectid) FROM bartabs.location ";

		return template.queryForObject(getIDQuery, new MapSqlParameterSource(), Long.class);

	}

	@Override
	public void updateLocation(final Location location) {
		// @formatter:off
		String sql = ""
			+ "UPDATE bartabs.location "
			+ "SET  address1 = :address1, "
			+ "		address2 = :address2, "
			+ "		city = :city, "
			+ "		state = :state, "
			+ "		zip = :zipCode, "
			+ "		latitude = :latitude, "
			+ "		longitude = :longitude, "
			+ "		radius = :radius "
			+ "WHERE objectid = :objectID";
		// @formatter:on

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("objectID", location.getObjectID());
		params.addValue("address1", location.getAddress1());
		params.addValue("address2", location.getAddress2());
		params.addValue("city", location.getCity());
		params.addValue("state", location.getState());
		params.addValue("zipCode", location.getZipCode());
		params.addValue("latitude", location.getLatitude());
		params.addValue("longitude", location.getLongitude());
		params.addValue("radius", location.getRadius());

		template.update(sql, params);

	}

	@Override
	public void removeLocation(final Location location) {
		// @formatter:off
		String sql = ""
			+ "DELETE FROM bartabs.location "
			+ "WHERE objectid = :objectID";
		// @formatter:on

		template.update(sql, new MapSqlParameterSource("objectID", location.getObjectID()));
	}

}
