package com.bartabs.ws.order.model;

import java.math.BigDecimal;
import java.util.Date;

public class OrderInfo
{
	private Long barID;
	private Long orderID;
	private String barName;
	private BigDecimal total;
	private Long orderedBy;
	private String orderedByDisplay;
	private Date orderedDate;
	private String orderedDateDisplay;
	private Boolean completed;
	private Long completedBy;
	private String completedByDisplay;
	private Date completedDate;
	private String completedDateDisplay;

	public Long getBarID()
	{
		return barID;
	}

	public void setBarID(Long barID)
	{
		this.barID = barID;
	}

	public Long getOrderID()
	{
		return orderID;
	}

	public void setOrderID(Long orderID)
	{
		this.orderID = orderID;
	}

	public String getBarName()
	{
		return barName;
	}

	public void setBarName(String barName)
	{
		this.barName = barName;
	}

	public BigDecimal getTotal()
	{
		return total;
	}

	public void setTotal(BigDecimal total)
	{
		this.total = total;
	}

	public Long getOrderedBy()
	{
		return orderedBy;
	}

	public void setOrderedBy(Long orderedBy)
	{
		this.orderedBy = orderedBy;
	}

	public String getOrderedByDisplay()
	{
		return orderedByDisplay;
	}

	public void setOrderedByDisplay(String orderedByDisplay)
	{
		this.orderedByDisplay = orderedByDisplay;
	}

	public Date getOrderedDate()
	{
		return orderedDate;
	}

	public void setOrderedDate(Date orderedDate)
	{
		this.orderedDate = orderedDate;
	}

	public String getOrderedDateDisplay()
	{
		return orderedDateDisplay;
	}

	public void setOrderedDateDisplay(String orderedDateDisplay)
	{
		this.orderedDateDisplay = orderedDateDisplay;
	}

	public Boolean getCompleted()
	{
		return completed;
	}

	public void setCompleted(Boolean completed)
	{
		this.completed = completed;
	}

	public Long getCompletedBy()
	{
		return completedBy;
	}

	public void setCompletedBy(Long completedBy)
	{
		this.completedBy = completedBy;
	}

	public String getCompletedByDisplay()
	{
		return completedByDisplay;
	}

	public void setCompletedByDisplay(String completedByDisplay)
	{
		this.completedByDisplay = completedByDisplay;
	}

	public Date getCompletedDate()
	{
		return completedDate;
	}

	public void setCompletedDate(Date completedDate)
	{
		this.completedDate = completedDate;
	}

	public String getCompletedDateDisplay()
	{
		return completedDateDisplay;
	}

	public void setCompletedDateDisplay(String completedDateDisplay)
	{
		this.completedDateDisplay = completedDateDisplay;
	}

}
