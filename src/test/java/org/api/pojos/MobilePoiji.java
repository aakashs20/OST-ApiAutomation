package org.api.pojos;

import org.api.base.BasePojo;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellRange;

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
public class MobilePoiji extends BasePojo{
	
	@ExcelCell(0)
	private String name;
	@ExcelCellRange
	private MobileDataPoiji data;

}
