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
public class RouteLinks
{
    @JsonProperty("self")
    private String self;

    @Override
    public String toString() {
        return "RouteLinks{" +
                "self='" + self + '\'' +
                '}';
    }
}
