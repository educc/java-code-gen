package com.ecacho.app.generated;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Greeter {

	@JsonProperty("tags.xxx")
	private Double powerGenerated = 0.0d;

	@JsonProperty("tags.yyy")
	private Long enginesOnline = 0L;


}
