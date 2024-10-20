package com.ArtifactsMMO.ArtifactsMMO.model.wrapper;

import lombok.Data;

import java.util.List;

@Data
public class GrandExchangeListApiWrapper {
    private List<GrandExchangeApiResponse> data;
}
