package com.ty.fm.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ZoneData
{
    @JsonProperty("id")
    private String dataId;
    @JsonProperty("type")
    private String dataType;
}
