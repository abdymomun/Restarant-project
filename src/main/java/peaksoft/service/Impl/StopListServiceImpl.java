package peaksoft.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoStopList.StopListRequest;
import peaksoft.dto.dtoStopList.StopListResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.StopList;
import peaksoft.exception.AccessDeniedException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.StopListRepository;
import peaksoft.service.StopListService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StopListServiceImpl implements StopListService {
    private final StopListRepository stopListRepository;
    private final MenuItemRepository menuItemRepository;
    @Override
    public SimpleResponse save(StopListRequest stopListRequest, Long menuItemId) {
        try {
            StopList stopList = StopList.builder()
                    .reason(stopListRequest.getReason())
                    .date(stopListRequest.getDate())
                    .build();
            MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() ->
                    new NotFoundException("Menu with id: " + menuItemId + " not found !"));
            menuItem.setStopList(stopList);
            stopList.setMenuItem(menuItem);
            stopListRepository.save(stopList);
            return new SimpleResponse(HttpStatus.OK, "Stop with id: " + menuItem.getId() + "successfully saved !");
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }

    @Override
    public List<StopListResponse> getAll() {
        try {
            List<StopList> stopLists = stopListRepository.findAll();
            List<StopListResponse> stopListResponses = new ArrayList<>();
            stopLists.forEach(stopList -> {
                MenuItem menuItem = stopList.getMenuItem();
                StopListResponse stopListResponse = StopListResponse.builder()
                        .id(stopList.getId())
                        .reason(stopList.getReason())
                        .date(stopList.getDate())
                        .menuItemId(menuItem.getId())
                        .build();
                stopListResponses.add(stopListResponse);
            });
            return stopListResponses;
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }

    @Override
    public StopListResponse getById(Long id) {
        try {
            StopList stopList = stopListRepository.findById(id).orElseThrow(() ->
                    new NotFoundException("Stop List with id: " + id + " not found !"));
            MenuItem menuItem = stopList.getMenuItem();

            return StopListResponse.builder()
                    .id(stopList.getId())
                    .reason(stopList.getReason())
                    .date(stopList.getDate())
                    .menuItemId(menuItem.getId())
                    .build();
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }

    @Override
    public SimpleResponse update(Long id, StopListRequest stopListRequest) {
        try {
            StopList stopList = stopListRepository.findById(id).orElseThrow(() ->
                    new NotFoundException("Stop List with id: " + id + " not found !"));
            stopList.setReason(stopListRequest.getReason());
            stopList.setDate(stopListRequest.getDate());
            Long menuItemId = stopListRequest.getMenuItemId();
            MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() ->
                    new NotFoundException("Menu with id: " + menuItemId + " not found !"));
            stopList.setMenuItem(menuItem);
            menuItem.setStopList(stopList);
            stopListRepository.save(stopList);
            return new SimpleResponse(HttpStatus.OK, "Stop list with id: " + " successfully updated !");
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }

    @Override
    public SimpleResponse delete(Long id) {
        try {
            StopList stopList = stopListRepository.findById(id).orElseThrow(() ->
                    new NotFoundException("Stop List with id: " + id + " not found !"));
            MenuItem menuItem = stopList.getMenuItem();
            menuItem.setStopList(null);
            stopList.setMenuItem(null);
            stopListRepository.delete(stopList);
            return new SimpleResponse(HttpStatus.OK, "Stop list with id: " + id + " successfully deleted !");
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }
}
