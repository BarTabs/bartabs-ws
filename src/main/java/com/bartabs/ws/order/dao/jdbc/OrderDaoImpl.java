package com.bartabs.ws.order.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bartabs.ws.menu.model.MenuItem;
import com.bartabs.ws.order.dao.OrderDao;
import com.bartabs.ws.order.model.Order;
import com.bartabs.ws.util.NameFormatter;

@Repository("Order.OrderDao")
public class OrderDaoImpl implements OrderDao
{

	@Autowired
	NamedParameterJdbcTemplate template;

	@Override
	public Long placeOrder(final Order order)
	{
		// @formatter:off
		String sql = ""
				+ "INSERT INTO bartabs.orders "
				+ "  (ordered_by, bar_id) "
				+ " VALUES "
				+ "  (:orderedBy, :barID)";
		// @formatter:on

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderedBy", order.getOrderedBy());
		params.addValue("barID", order.getBarID());

		template.update(sql, params);

		String getIDQuery = "SELECT MAX(objectid) FROM bartabs.menu ";

		return template.queryForObject(getIDQuery, new MapSqlParameterSource(), Long.class);
	}

	@Override
	public List<Order> getBarOrders(Long barID, Boolean openOnly)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss ");
		StringBuilder wc = new StringBuilder();

		if (openOnly != null) {
			wc.append(" AND complete = :activeOnly ");
		}

		// @formatter:off
		String sql = ""
				+ "SELECT bo.objectid, bo.ordered_by, bo.order_timestamp, bo.complete, bo.completed_by, "
				+ "		bo.completed_timestamp, bar_id c.first_name AS completed_by_fname, "
				+ "		c.last_name AS completed_by_lname, c.username AS completed_by_uname, "
				+ "		o.first_name AS ordered_by_fname, o.last_name AS ordered_by_lname, "
				+ "		o.username AS ordered_by_name "
				+ "FROM bartabs.orders bo "
				+ "JOIN bartabs.users c ON c.objectid = bo.completed_by "
				+ "JOIN bartabs.users o ON o.objectid = bo.ordered_by "
				+ "WHERE bo.bar_id = :barID "
				+ wc.toString()
				+ "ORDER BY order_timestamp DESC";
		// @formatter:on

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("activeOnly", !openOnly);
		params.addValue("barID", barID);

		return template.query(sql, params, new RowMapper<Order>()
		{

			@Override
			public Order mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				Order row = new Order();

				Long orderID = rs.getLong("objectid");
				row.setObjectID(orderID);
				row.setOrderedBy(rs.getLong("ordered_by"));
				row.setOrderedDate(rs.getTimestamp("order_timestamp"));

				if (row.getOrderedDate() != null) {
					row.setOrderedDateDisplay(sdf.format(row.getOrderedDate()));
				}

				String orderedByFirstName = rs.getString("ordered_by_fname");
				String orderedByLastName = rs.getString("ordered_by_lname");
				String orderedFormattedName = NameFormatter.format(orderedByFirstName, orderedByLastName);

				if (orderedFormattedName == null) {
					row.setOrderedByDisplay(orderedFormattedName);
				} else {
					String orderedByUsername = rs.getString("ordered_by_uname");
					row.setOrderedByDisplay(orderedByUsername);
				}

				row.setCompleted(rs.getBoolean("complete"));
				row.setCompletedBy(rs.getLong("completed_by"));

				String completedByFirstName = rs.getString("completed_by_fname");
				String completedByLastName = rs.getString("completed_by_lname");
				String completedByFormattedName = NameFormatter.format(completedByFirstName, completedByLastName);

				if (completedByFormattedName != null) {
					row.setCompletedByDisplay(completedByFormattedName);
				} else {
					String completedByUsername = rs.getString("completed_by_uname");
					row.setCompletedByDisplay(completedByUsername);
				}

				List<MenuItem> orderItems = getOrderItems(orderID);
				row.setOrderItems(orderItems);

				return row;
			}

		});
	}

	@Override
	public List<Order> getUserOrders(Long userID)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss ");

		// @formatter:off
		String sql = ""
				+ "SELECT bo.objectid, bo.ordered_by, bo.order_timestamp, bo.complete, bo.completed_by, "
				+ "		bo.completed_timestamp, bar_id c.first_name AS completed_by_fname, "
				+ "		c.last_name AS completed_by_lname, c.username AS completed_by_uname, "
				+ "		o.first_name AS ordered_by_fname, o.last_name AS ordered_by_lname, "
				+ "		o.username AS ordered_by_name "
				+ "FROM bartabs.orders bo "
				+ "JOIN bartabs.users c ON c.objectid = bo.completed_by "
				+ "JOIN bartabs.users o ON o.objectid = bo.ordered_by "
				+ "WHERE bo.ordered_by = :userID "
				+ "ORDER BY order_timestamp DESC";
		// @formatter:on

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("userID", userID);

		return template.query(sql, params, new RowMapper<Order>()
		{

			@Override
			public Order mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				Order row = new Order();

				Long orderID = rs.getLong("objectid");
				row.setObjectID(orderID);
				row.setOrderedBy(rs.getLong("ordered_by"));
				row.setOrderedDate(rs.getTimestamp("order_timestamp"));

				if (row.getOrderedDate() != null) {
					row.setOrderedDateDisplay(sdf.format(row.getOrderedDate()));
				}

				String orderedByFirstName = rs.getString("ordered_by_fname");
				String orderedByLastName = rs.getString("ordered_by_lname");
				String orderedFormattedName = NameFormatter.format(orderedByFirstName, orderedByLastName);

				if (orderedFormattedName == null) {
					row.setOrderedByDisplay(orderedFormattedName);
				} else {
					String orderedByUsername = rs.getString("ordered_by_uname");
					row.setOrderedByDisplay(orderedByUsername);
				}

				row.setCompleted(rs.getBoolean("complete"));
				row.setCompletedBy(rs.getLong("completed_by"));

				String completedByFirstName = rs.getString("completed_by_fname");
				String completedByLastName = rs.getString("completed_by_lname");
				String completedByFormattedName = NameFormatter.format(completedByFirstName, completedByLastName);

				if (completedByFormattedName != null) {
					row.setCompletedByDisplay(completedByFormattedName);
				} else {
					String completedByUsername = rs.getString("completed_by_uname");
					row.setCompletedByDisplay(completedByUsername);
				}

				List<MenuItem> orderItems = getOrderItems(orderID);
				row.setOrderItems(orderItems);

				return row;
			}

		});
	}

	@Override
	public Order getOrder(Long orderID)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss ");

		// @formatter:off
		String sql = ""
				+ "SELECT bo.objectid, bo.ordered_by, bo.order_timestamp, bo.complete, bo.completed_by, "
				+ "		bo.completed_timestamp, bar_id c.first_name AS completed_by_fname, "
				+ "		c.last_name AS completed_by_lname, c.username AS completed_by_uname, "
				+ "		o.first_name AS ordered_by_fname, o.last_name AS ordered_by_lname, "
				+ "		o.username AS ordered_by_name "
				+ "FROM bartabs.orders bo "
				+ "JOIN bartabs.users c ON c.objectid = bo.completed_by "
				+ "JOIN bartabs.users o ON o.objectid = bo.ordered_by "
				+ "WHERE bo.objectid = :orderID ";
		// @formatter:on

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("objectID", orderID);

		return template.queryForObject(sql, params, new RowMapper<Order>()
		{

			@Override
			public Order mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				Order row = new Order();
				row.setObjectID(rs.getLong("objectid"));
				row.setOrderedBy(rs.getLong("ordered_by"));
				row.setOrderedDate(rs.getTimestamp("order_timestamp"));

				if (row.getOrderedDate() != null) {
					row.setOrderedDateDisplay(sdf.format(row.getOrderedDate()));
				}

				String orderedByFirstName = rs.getString("ordered_by_fname");
				String orderedByLastName = rs.getString("ordered_by_lname");
				String orderedFormattedName = NameFormatter.format(orderedByFirstName, orderedByLastName);

				if (orderedFormattedName == null) {
					row.setOrderedByDisplay(orderedFormattedName);
				} else {
					String orderedByUsername = rs.getString("ordered_by_uname");
					row.setOrderedByDisplay(orderedByUsername);
				}

				row.setCompleted(rs.getBoolean("complete"));
				row.setCompletedBy(rs.getLong("completed_by"));

				String completedByFirstName = rs.getString("completed_by_fname");
				String completedByLastName = rs.getString("completed_by_lname");
				String completedByFormattedName = NameFormatter.format(completedByFirstName, completedByLastName);

				if (completedByFormattedName != null) {
					row.setCompletedByDisplay(completedByFormattedName);
				} else {
					String completedByUsername = rs.getString("completed_by_uname");
					row.setCompletedByDisplay(completedByUsername);
				}

				List<MenuItem> orderItems = getOrderItems(orderID);
				row.setOrderItems(orderItems);

				return row;
			}

		});
	}

	@Override
	public List<MenuItem> getOrderItems(Long orderID)
	{
		// @formatter:off
		String sql = ""
				+ "SELECT mi.objectid, mi.menu_id, mi.name, mi.description, mi.price, mi.category, mi.type"
				+ "FROM bartabs.order_items oi "
				+ "JOIN bartabs.menu_items mi ON mi.objectid = oi.menu_item_id "
				+ "WHERE oi.order_id = :orderID";
		// @formatter:on

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderID", orderID);

		return template.query(sql, params, new RowMapper<MenuItem>()
		{

			@Override
			public MenuItem mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				MenuItem row = new MenuItem();
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
	public void linkOrderItem(Long orderID, MenuItem orderItem)
	{
		// @formatter:off
		String sql = ""
				+ "INSERT bartabs.order_items "
				+ "  (order_id, menu_item_id) "
				+ "VALUES"
				+ "  (:orderID, :menuItemID)";
		// @formatter:on

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderID", orderID);
		params.addValue("menuItemID", orderItem.getObjectID());

		template.update(sql, params);

	}

}
