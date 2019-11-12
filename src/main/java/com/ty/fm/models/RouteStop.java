package com.ty.fm.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class RouteStop
{
    @JsonProperty("attributes")
    private RouteStopAttribute routeStopAttribute;
    @JsonIgnore(true)
    @JsonProperty("relationships")
    private RouteStopRelationship route;
    @JsonProperty("id")
    private String id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("links")
    private RouteLinks routeLinks;
}
