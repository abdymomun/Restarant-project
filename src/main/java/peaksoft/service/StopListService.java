package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoStopList.StopListRequest;
import peaksoft.dto.dtoStopList.StopListResponse;

import java.util.List;

public interface StopListService {
    SimpleResponse save(StopListRequest stopListRequest,Long menuItemId);
    List<StopListResponse>getAll();
    StopListResponse getById(Long id);
    SimpleResponse update(Long id,StopListRequest stopListRequest);
    SimpleResponse delete(Long id);
}
