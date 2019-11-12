package com.ty.fm.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ty.fm.models.RelationshipLineRoutePattern;
import com.ty.fm.models.RequestRelationshipLine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouteRequestRelationship
{
    @JsonProperty("line")
    private RequestRelationshipLine requestRelationshipLine;

    @JsonIgnore(true)
    @JsonProperty("route_patterns")
    private RelationshipLineRoutePattern relationshipLineRoutePattern;

}
