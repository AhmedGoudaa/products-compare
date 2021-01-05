package com.task.demo.dto.response;


import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class ProductResponse {

	private String name;

	private String logo;

	private BigDecimal price;

	private Date updatedAt;

	private int categoryId;

	private int sourceId;

}
