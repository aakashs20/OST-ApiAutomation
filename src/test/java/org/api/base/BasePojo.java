package org.api.base;

import com.poiji.annotation.ExcelCellName;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class BasePojo {
	
	@ExcelCellName("id")
	private String id;

}
