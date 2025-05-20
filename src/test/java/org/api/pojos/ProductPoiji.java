package org.api.pojos;

import org.api.base.BasePojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.poiji.annotation.ExcelCellName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@JsonIgnoreProperties(value = {"id"}, ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductPoiji extends BasePojo {

	@ExcelCellName("title")
	private String title;
	@ExcelCellName("description")
	private String description;
	@ExcelCellName("price")
	private String price;
	@ExcelCellName("discountPercentage")
	private int discountPercentage;
	@ExcelCellName("rating")
	private int rating;
	@ExcelCellName("stock")
	private String stock;
	@ExcelCellName("brand")
	private String brand;
	@ExcelCellName("category")
	private String category;
	
//	@ExcelUnknownCells
//	private Map<String, String> extraCells;

}
