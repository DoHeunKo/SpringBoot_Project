package com.hk.study.dtos;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Alias(value="FileDto")
public class FileDto {
	
	private int file_no;
	private String file_name;
	private int file_size;
	private Integer room_no;
	private String file_fname;
}
