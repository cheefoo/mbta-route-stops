package com.ty.fm.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouteStops
{
    @JsonProperty("data")
    private List<RouteStop> routeStopList;

    @JsonIgnore(true)
    @JsonProperty("jsonapi")
    private RRJsonApi jsonApi;
}
