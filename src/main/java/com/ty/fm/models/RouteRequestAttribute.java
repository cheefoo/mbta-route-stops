package com.ty.fm.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouteRequestAttribute
{
	@JsonProperty("color")
	private String color;
	@JsonProperty("description")
	private String  description;
	@JsonProperty("direction_destinations")
	private String [] direction_destinations;
	@JsonProperty("direction_names")
	private String [] direction_names;
	@JsonProperty("fare_class")
	private String   fare_class;
	@JsonProperty("long_name")
	private String   long_name;
	@JsonProperty("short_name")
	private String   short_name;
	@JsonProperty("sort_order")
	private String   sort_order;
	@JsonProperty("text_color")
	private String   text_color;
	@JsonProperty("type")
	private String   type;
}
