package org.api.pojos;

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
public class MobileDataPoiji {
	
	@ExcelCellName("year")
	private String year;
	@ExcelCellName("price")
	private String price;
	@ExcelCellName("CPU model")
	private String cpuModel;
	@ExcelCellName("RAM size")
	private String ramSize;

}
