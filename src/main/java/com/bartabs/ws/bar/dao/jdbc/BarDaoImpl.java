package com.bartabs.ws.bar.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bartabs.ws.bar.criteria.BarCriteria;
import com.bartabs.ws.bar.dao.BarDao;
import com.bartabs.ws.bar.model.Bar;
import com.bartabs.ws.location.model.Location;
import com.bartabs.ws.location.service.LocationService;

@Repository("Bar.BarDao")
public class BarDaoImpl implements BarDao {

	@Autowired
	NamedParameterJdbcTemplate template;

	@Qualifier("Location.LocationService")
	@Autowired
	private LocationService locationService;

	@Override
	public List<Bar> getBars(BarCriteria criteria) {
		StringBuilder wc = new StringBuilder();

		Long ownerID = criteria.getOwnerID();
		if (ownerID != null) {
			wc.append(" AND owner_id = :ownerID ");
		}

		Long barID = criteria.getBarID();
		if (barID != null) {
			wc.append(" AND objectid = :barID ");
		}

		// @formatter:off
		String sql = ""
				+ "SELECT objectid, name, location_id, owner_id "
				+ "FROM bartabs.bars "
				+ "WHERE 1=1 "
				+ wc.toString();
	    // @formatter:on

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("ownerID", ownerID);
		params.addValue("barID", barID);

		return template.query(sql, params, new RowMapper<Bar>() {

			@Override
			public Bar mapRow(ResultSet rs, int arg1) throws SQLException {
				Bar row = new Bar();
				row.setObjectID(rs.getLong("objectid"));
				row.setName(rs.getString("name"));
				row.setOwnerID(rs.getLong("owner_id"));

				Long locationID = rs.getLong("location_id");
				if (locationID != null) {
					Location location = locationService.getLocationByID(locationID);
					row.setLocation(location);
				}

				return row;
			}

		});
	}

	@Override
	public Bar getBar(Long objectID) {
		// @formatter:off
		String sql = ""
				+ "SELECT objectid, name, location_id, owner_id "
				+ "FROM bartabs.bars "
				+ "WHERE objectid = :objectID ";
		// @formatter:on

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("objectID", objectID);

		return template.queryForObject(sql, params, new RowMapper<Bar>() {

			@Override
			public Bar mapRow(ResultSet rs, int arg1) throws SQLException {
				Bar row = new Bar();
				row.setObjectID(rs.getLong("objectid"));
				row.setName(rs.getString("name"));
				row.setOwnerID(rs.getLong("owner_id"));

				Long locationId = rs.getLong("location_id");
				Location location = locationService.getLocationByID(locationId);
				row.setLocation(location);

				return row;
			}

		});
	}

	@Override
	public Long createBar(Bar bar) {
		// @formatter:off
		String sql = ""
				+ "INSERT INTO bartabs.bars "
				+ "  (name, location_id, owner_id, created_timestamp) "
				+ "VALUES "
				+ "  (:name, :locationID, :ownerID, now())";
		// @formatter:on

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", bar.getName());
		params.addValue("locationID", bar.getLocationID());
		params.addValue("ownerID", bar.getOwnerID());

		template.update(sql, params);

		final String getIDQuery = "SELECT MAX(objectid) FROM bartabs.bars ";

		return template.queryForObject(getIDQuery, new MapSqlParameterSource(), Long.class);
	}

	@Override
	public void updateBar(Bar bar) {
		// @formatter:off
		String sql = ""
				+ "UPDATE bartabs.bars "
				+ "SET name = :name, "
				+ "    location_id = :locationID, "
				+ "    owner_id = :ownerID, "
				+ "	   modified_timestamp = now() "
				+ "WHERE objectid = :objectID";
		// @formatter:on

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", bar.getName());
		params.addValue("locationID", bar.getLocationID());
		params.addValue("ownerID", bar.getOwnerID());
		params.addValue("objectID", bar.getObjectID());
	}

	@Override
	public void deleteBar(Long objectID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkIn(Long barID, Long userID) {
		// @formatter:off
		String sql = ""
				+ "INSERT INTO bartabs.customer "
				+ "  (user_id, bar_id, checkin_timestamp) "
				+ "VALUES "
				+ "  (:userID, :barID, now()";
		// @formatter:on

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("userID", userID);
		params.addValue("barID", barID);

		template.update(sql, params);
	}

}
