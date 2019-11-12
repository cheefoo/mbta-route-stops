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
public class RouteRequest
{
    @JsonProperty("attributes")
    private RouteRequestAttribute routeRequestAttribute;
    @JsonProperty("relationships")
    private RouteRequestRelationship routeRequestRelationship;
    @JsonProperty("id")
    private String id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("links")
    private RouteLinks routeLinks;


}
