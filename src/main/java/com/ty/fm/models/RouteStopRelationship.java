package com.ty.fm.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RouteStopRelationship
{
    @JsonProperty("child_stops")
    private RelationshipsChildStop relationshipsChildStop;
    @JsonProperty("facilities")
    private RelationshipsFacilities relationshipsFacilities;
    @JsonProperty("parent_station")
    private RelationshipsParentStation relationshipsParentStation;
    @JsonProperty("recommended_transfers")
    private RelationshipRecommendedTransfers relationshipRecommendedTransfers;
    @JsonProperty("zone")
    private RelationshipsZone relationshipsZone;

    @Override
    public String toString() {
        return "RouteStopRelationship{" +
                "relationshipsChildStop=" + relationshipsChildStop +
                ", relationshipsFacilities=" + relationshipsFacilities +
                ", relationshipsParentStation=" + relationshipsParentStation +
                ", relationshipRecommendedTransfers=" + relationshipRecommendedTransfers +
                ", relationshipsZone=" + relationshipsZone +
                '}';
    }
}
