package com.eshoppingzone.productservice.entity;

import java.time.LocalDateTime;


import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorResponse {

	private HttpStatus statusmessage;
	private String message;
	private LocalDateTime datetime;

}
