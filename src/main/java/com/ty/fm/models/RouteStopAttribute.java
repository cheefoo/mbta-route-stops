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
public class RouteStopAttribute
{
    @JsonProperty("address")
    private String address;
    @JsonProperty("at_street")
    private String  at_street;
    @JsonProperty("description")
    private String description;
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("location_type")
    private String   location_type;
    @JsonProperty("longitude")
    private String   longitude;
    @JsonProperty("municipality")
    private String   municipality;
    @JsonProperty("name")
    private String   name;
    @JsonProperty("on_street")
    private String   on_street;
    @JsonProperty("platform_code")
    private String   platform_code;
    @JsonProperty("platform_name")
    private String   platform_name;
    @JsonProperty("vehicle_type")
    private String   vehicle_type;
    @JsonProperty("wheelchair_boarding")
    private String   wheelchair_boarding;

    @Override
    public String toString() {
        return "RouteStopAttribute{" +
                "address='" + address + '\'' +
                ", at_street='" + at_street + '\'' +
                ", description='" + description + '\'' +
                ", latitude='" + latitude + '\'' +
                ", location_type='" + location_type + '\'' +
                ", longitude='" + longitude + '\'' +
                ", municipality='" + municipality + '\'' +
                ", name='" + name + '\'' +
                ", on_street='" + on_street + '\'' +
                ", platform_code='" + platform_code + '\'' +
                ", platform_name='" + platform_name + '\'' +
                ", vehicle_type='" + vehicle_type + '\'' +
                ", wheelchair_boarding='" + wheelchair_boarding + '\'' +
                '}';
    }
}
