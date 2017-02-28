package com.bartabs.ws.menu.dataaccess.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bartabs.ws.menu.dataaccess.MenuDao;
import com.bartabs.ws.menu.model.Menu;
import com.bartabs.ws.menu.model.MenuItem;

@Repository("Menu.MenuDao")
public class MenuDaoImpl implements MenuDao
{
	@Autowired
	NamedParameterJdbcTemplate template;

	@Override
	public Menu getMenuByBarID(Long barID)
	{
		// @formatter:off
		final String sql = ""
				+ "SELECT objectid, bar_id "
				+ "FROM bartabs.menu "
				+ "WHERE bar_id = :barID";
		// @formatter:on

		final MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("barID", barID);

		return template.queryForObject(sql, params, Menu.class);
	}

	@Override
	public Menu getMenuByID(Long menuID)
	{
		// @formatter:off
		final String sql = ""
				+ "SELECT objectid, bar_id "
				+ "FROM bartabs.menu "
				+ "WHERE objectid = :menuID";
		// @formatter:on

		final MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("menuID", menuID);

		return template.queryForObject(sql, params, Menu.class);
	}

	@Override
	public Long createMenu(Menu menu)
	{
		// @formatter:off
		final String sql = ""
				+ "INSERT INTO bartabs.menu "
				+ "  (bar_id created_timestamp, modified_timestamp) "
				+ "VALUES "
				+ "  (:barId, now(), now()) "
				+ "RETURNING objectid";
		// @formatter:on

		final MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("barId", menu.getBarID());

		return template.queryForObject(sql, params, Long.class);
	}

	@Override
	public void updateMenu(Menu menu)
	{
		// @formatter:off
		final String sql = ""
				+ "UPDATE bartabs.menu "
				+ "SET bar_id = :barId, "
				+ "	   modified_timestamp = now()"
				+ "WHERE objectid = :objectID";
		// @formatter:on

		final MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("barId", menu.getBarID());
		params.addValue("objectID", menu.getObjectID());

		template.update(sql, params);

	}

	@Override
	public void removeMenu(Long menuID)
	{
		final String sql = "DELETE FROM bartabs.menu WHERE objectid = :objectID";

		final MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("objectID", menuID);

		template.update(sql, params);

	}

	@Override
	public List<String> getCategories(Long menuID)
	{
		// @formatter:off
		final String sql = ""
				+ "SELECT category "
				+ "FROM bartabs.menu_item "
				+ "WHERE menu_id = :menuID";
		// @formatter:on

		final MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("menuID", menuID);

		return template.query(sql, params, new RowMapper<String>()
		{

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				return rs.getString("category");
			}
		});
	}

	@Override
	public List<String> getTypes(Long menuID, String category)
	{
		// @formatter:off
		final String sql = ""
				+ "SELECT category "
				+ "FROM bartabs.menu_item "
				+ "WHERE menu_id = :menuID "
				+ "  AND category = :category";
		// @formatter:on

		final MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("menuID", menuID);
		params.addValue("category", category);

		return template.query(sql, params, new RowMapper<String>()
		{

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				return rs.getString("type");
			}
		});
	}

	@Override
	public List<MenuItem> getMenuItems(final Long menuID, final String category, final String type)
	{
		final StringBuilder wc = new StringBuilder();

		if (category != null) {
			wc.append(" AND category = :category ");
		}

		if (type != null) {
			wc.append(" AND type = :type ");
		}

		// @formatter:off
		final String sql = ""
				+ "SELECT objectid, menu_id, name, description, price, category, type "
				+ "FROM bartabs.menu_item "
				+ "WHERE menu_id = :menuID "
				+ wc.toString();
		// @formatter:on

		final MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("menuID", menuID);
		params.addValue("category", category);
		params.addValue("type", type);

		return template.query(sql, params, new RowMapper<MenuItem>()
		{

			@Override
			public MenuItem mapRow(ResultSet rs, int arg1) throws SQLException
			{
				final MenuItem row = new MenuItem();
				row.setObjectID(rs.getLong("objectid"));
				row.setMenuID(rs.getLong("menu_id"));
				row.setName(rs.getString("name"));
				row.setDescription(rs.getString("description"));
				row.setPrice(rs.getBigDecimal("price"));
				row.setCategory(rs.getString("category"));
				row.setType(rs.getString("type"));

				return row;
			}

		});
	}

	@Override
	public List<MenuItem> getMenuItems(final Long menuID)
	{
		return getMenuItems(menuID, null, null);
	}

	@Override
	public List<MenuItem> getMenuItemsByCategory(Long menuID, String category)
	{
		return getMenuItems(menuID, category, null);
	}

	@Override
	public List<MenuItem> getMenuItemsByType(Long menuID, String type)
	{
		return getMenuItems(menuID, null, type);
	}

	@Override
	public Long createMenuItem(MenuItem menuItem)
	{
		// @formatter:off
		final String sql = ""
				+ "INSERT INTO bartabs.menu_item "
				+ "  (menu_id, name, description, price, created_timestamp, modified_timestamp, "
				+ "   category, type) "
				+ "VALUES"
				+ "  (:menuID, :name, :description, :price, now(), now(), :category, :type) "
				+ "RETURNING objectid";
		// @formatter:on

		final MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("menuID", menuItem.getMenuID());
		params.addValue("name", menuItem.getName());
		params.addValue("description", menuItem.getDescription());
		params.addValue("price", menuItem.getPrice());
		params.addValue("category", menuItem.getCategory());
		params.addValue("type", menuItem.getType());

		return template.queryForObject(sql, params, Long.class);
	}

	@Override
	public void updateMenuItem(MenuItem menuItem)
	{
		// @formatter:off
		final String sql = "UPDATE bartabs.menu_item "
				+ "SET menuID = :menuID, "
				+ "    name = :name, "
				+ "    description = :description, "
				+ "	   price = :price, "
				+ "    category = :category, "
				+ "    type = :type "
				+ "WHERE objectid = :objectID";
		// @formatter:on
		final MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("objectID", menuItem.getObjectID());
		params.addValue("menuID", menuItem.getMenuID());
		params.addValue("name", menuItem.getName());
		params.addValue("description", menuItem.getDescription());
		params.addValue("price", menuItem.getPrice());
		params.addValue("category", menuItem.getCategory());
		params.addValue("type", menuItem.getType());

		template.update(sql, params);

	}

	@Override
	public void removeMenuItem(MenuItem menuItem)
	{
		final String sql = "DELETE FROM bartabs.menu_item WHERE objectid = :objectID";

		final MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("objectID", menuItem.getObjectID());

		template.update(sql, params);

	}

}