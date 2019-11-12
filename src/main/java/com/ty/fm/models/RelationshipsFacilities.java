package com.ty.fm.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RelationshipsFacilities
{
    @JsonProperty("links")
    private FacilitiesLinks facilitiesLinks;
}
