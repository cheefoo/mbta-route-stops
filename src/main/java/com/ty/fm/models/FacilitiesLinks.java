package com.ty.fm.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FacilitiesLinks
{
    @JsonIgnore(true)
    @JsonProperty("related")
    private String related;
}
