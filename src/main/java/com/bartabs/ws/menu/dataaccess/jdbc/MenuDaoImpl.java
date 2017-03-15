package com.bartabs.ws.menu.dataaccess.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bartabs.ws.menu.criteria.MenuCriteria;
import com.bartabs.ws.menu.dataaccess.MenuDao;
import com.bartabs.ws.menu.model.Menu;
import com.bartabs.ws.menu.model.MenuItem;

@Repository("Menu.MenuDao")
public class MenuDaoImpl implements MenuDao
{
	@Autowired
	NamedParameterJdbcTemplate template;

	@Override
	public Menu getMenuByBarID(final Long barID)
	{
		// @formatter:off
		final String sql = ""
				+ "SELECT objectid, bar_id "
				+ "FROM bartabs.menu "
				+ "WHERE bar_id = :barID";
		// @formatter:on

		final MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("barID", barID);

		return template.queryForObject(sql, params, new RowMapper<Menu>()
		{

			@Override
			public Menu mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				final Menu row = new Menu();
				row.setObjectID(rs.getLong("objectid"));
				row.setBarID(rs.getLong("bar_id"));

				return row;
			}

		});
	}

	@Override
	public Menu getMenuByID(final Long menuID)
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
	public Long createMenu(final Menu menu)
	{
		// @formatter:off
		final String sql = ""
				+ "INSERT INTO bartabs.menu "
				+ "  (bar_id created_timestamp, modified_timestamp) "
				+ "VALUES "
				+ "  (:barId, now(), now()) ";
		// @formatter:on

		final MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("barId", menu.getBarID());

		template.update(sql, params);

		final String getIDQuery = "SELECT MAX(objectid) FROM bartabs.menu ";

		return template.queryForObject(getIDQuery, new MapSqlParameterSource(), Long.class);
	}

	@Override
	public void updateMenu(final Menu menu)
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
	public void removeMenu(final Long menuID)
	{
		final String sql = "DELETE FROM bartabs.menu WHERE objectid = :objectID";

		final MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("objectID", menuID);

		template.update(sql, params);

	}

	@Override
	public List<String> getCategories(final MenuCriteria criteria)
	{
		// @formatter:off
		final String sql = ""
				+ "SELECT category "
				+ "FROM bartabs.menu_items mi "
				+ "JOIN bartabs.menu m ON m.objectid = mi.menu_id "
				+ "WHERE m.bar_id = :barID "
				+ "GROUP BY category";
		// @formatter:on

		final MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("barID", criteria.getBarID());

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
	public List<String> getTypes(final MenuCriteria criteria)
	{
		// @formatter:off
		final String sql = ""
				+ "SELECT type "
				+ "FROM bartabs.menu_items mi "
				+ "JOIN bartabs.menu m ON m.objectid = mi.menu_id "
				+ "WHERE m.bar_id = :barID "
				+ "	AND mi.category = :category "
				+ "GROUP BY type";
		// @formatter:on

		final MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("barID", criteria.getBarID());
		params.addValue("category", criteria.getCategory());

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
	public MenuItem getMenuItemsByID(Long objectID)
	{
		// @formatter:off
		final String sql = ""
				+ "SELECT objectid, menu_id, name, description, price, category, type "
				+ "FROM bartabs.menu_items "
				+ "WHERE objectid = :objectID ";
		// @formatter:on

		final MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("objectID", objectID);

		return template.queryForObject(sql, params, new RowMapper<MenuItem>()
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
	public List<MenuItem> getMenuItems(final MenuCriteria criteria)
	{
		final StringBuilder wc = new StringBuilder();

		final String category = criteria.getCategory();
		if (category != null) {
			wc.append(" AND category = :category ");
		}

		final String type = criteria.getType();
		if (type != null) {
			wc.append(" AND type = :type ");
		}

		// @formatter:off
		final String sql = ""
				+ "SELECT objectid, menu_id, name, description, price, category, type "
				+ "FROM bartabs.menu_items "
				+ "WHERE menu_id = :menuID "
				+ wc.toString();
		// @formatter:on

		final MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("menuID", criteria.getMenuID());
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
	public List<MenuItem> getMenuItemsByMenuID(final Long menuID)
	{
		MenuCriteria criteria = new MenuCriteria();
		criteria.setMenuID(menuID);

		return getMenuItems(criteria);
	}

	@Override
	public List<MenuItem> getMenuItemsByCategory(final Long menuID, final String category)
	{
		MenuCriteria criteria = new MenuCriteria();
		criteria.setMenuID(menuID);
		criteria.setCategory(category);

		return getMenuItems(criteria);
	}

	@Override
	public List<MenuItem> getMenuItemsByType(final Long menuID, final String type)
	{
		MenuCriteria criteria = new MenuCriteria();
		criteria.setMenuID(menuID);
		criteria.setType(type);

		return getMenuItems(criteria);
	}

	@Override
	public Long createMenuItem(final MenuItem menuItem)
	{
		// @formatter:off
		final String sql = ""
				+ "INSERT INTO bartabs.menu_items "
				+ "  (menu_id, name, description, price, created_timestamp, modified_timestamp, "
				+ "   category, type) "
				+ "VALUES"
				+ "  (:menuID, :name, :description, :price, now(), now(), :category, :type)";
		// @formatter:on

		final MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("menuID", menuItem.getMenuID());
		params.addValue("name", menuItem.getName());
		params.addValue("description", menuItem.getDescription());
		params.addValue("price", menuItem.getPrice());
		params.addValue("category", menuItem.getCategory());
		params.addValue("type", menuItem.getType());

		template.update(sql, params);

		final String getIDQuery = "SELECT MAX(objectid) FROM bartabs.menu_items ";

		return template.queryForObject(getIDQuery, new MapSqlParameterSource(), Long.class);
	}

	@Override
	public void updateMenuItem(final MenuItem menuItem)
	{
		// @formatter:off
		final String sql = ""
				+ "UPDATE bartabs.menu_items "
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
	public void removeMenuItem(final Long menuItemID)
	{
		final String sql = "DELETE FROM bartabs.menu_items WHERE objectid = :objectID";

		final MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("objectID", menuItemID);

		template.update(sql, params);

	}

}