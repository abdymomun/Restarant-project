package peaksoft.service.Impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoCheque.ChequeRequest;
import peaksoft.dto.dtoCheque.ChequeResponse;
import peaksoft.entity.*;
import peaksoft.exception.AccessDeniedException;
import peaksoft.exception.AlreadyExistException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.*;
import peaksoft.repository.template.ChequeTemplate;
import peaksoft.service.ChequeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository chequeRepository;
    private final MenuItemRepository menuItemRepository;
    private final ChequeTemplate chequeTemplate;
    private final RestaurantRepository restaurantRepository;
    private final EntityManager manager;
    private final UserRepository userRepository;
    private final StopListRepository stopListRepository;

    @Override
    public SimpleResponse save(ChequeRequest chequeRequest) {
        try {

            List<MenuItem> menuItems = menuItemRepository.findAllById(chequeRequest.getMenuItemId());
            List<StopList> stopLists = stopListRepository.findAll();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User user = userRepository.getUserByEmail(email).orElseThrow(()->
                    new NotFoundException("User with email: " + email + " not found !"));
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (MenuItem menuItem : menuItems) {
                totalPrice = totalPrice.add(menuItem.getPrice());
            }

            Cheque cheque = Cheque.builder()
                    .createdAt(LocalDate.now())
                    .priceAverage(totalPrice)
                    .build();
            user.setCheques(List.of(cheque));
            cheque.setUser(user);
            menuItems.forEach(menuItem -> {
                menuItem.setCheques(List.of(cheque));
            });


            List<Long> menuItemIds = chequeRequest.getMenuItemId();
            for (Long menuItemId : menuItemIds) {
                for (StopList stopList:stopLists) {
                    MenuItem menuItemm = stopList.getMenuItem();
                    if (menuItemId.equals(menuItemm.getId())){
                        throw new NotFoundException(menuItemm.getName() + " this dish is already on the stop list");
                    }
                }
            }
            chequeRepository.save(cheque);

        return new SimpleResponse(HttpStatus.OK, "Cheque with id: " + cheque.getId() + " successfully saved !");
        } catch (AccessDeniedException a) {
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }


    public List<ChequeResponse> getAll() {
            List<Cheque> allCheques = chequeRepository.findAll();

            List<ChequeResponse> chequeResponses = new ArrayList<>();

        for (Cheque cheque : allCheques) {
            List<MenuItem> menuItems = cheque.getMenuItems();
            if (!menuItems.isEmpty()) {
                Restaurant restaurant = menuItems.get(0).getRestaurant();
                BigDecimal serviceAsBigDecimal = BigDecimal.valueOf(restaurant.getService());
                BigDecimal grandTotal = cheque.getPriceAverage()
                        .multiply(serviceAsBigDecimal.divide(BigDecimal.valueOf(100)))
                        .add(cheque.getPriceAverage());

                ChequeResponse chequeResponse = ChequeResponse.builder()
                        .id(cheque.getId())
                        .restaurantName(restaurant.getName())
                        .waiterFullName(cheque.getUser().getFirstName() + " " + cheque.getUser().getLastName())
                        .menuName(menuItems.stream().map(MenuItem::getName).collect(Collectors.toList()))
                        .priceAverage(cheque.getPriceAverage())
                        .service(restaurant.getService())
                        .grandTotal(grandTotal)
                        .build();

                chequeResponses.add(chequeResponse);
            } else {

            }
        }
        return chequeResponses;

    }


    public ChequeResponse getById(Long id) {
        try {
            Cheque cheque = chequeRepository.findById(id).orElseThrow(()-> new NotFoundException("Cheque with id :"  + id + " not found !"));

//            if (cheque == null) {
//                return new ChequeResponse();
//            }

            Restaurant restaurant = cheque.getMenuItems().get(0).getRestaurant();
            BigDecimal serviceAsBigDecimal = BigDecimal.valueOf(restaurant.getService());
            BigDecimal grandTotal = cheque.getPriceAverage()
                    .multiply(serviceAsBigDecimal.divide(BigDecimal.valueOf(100)))
                    .add(cheque.getPriceAverage());

            ChequeResponse chequeResponse = ChequeResponse.builder()
                    .id(cheque.getId())
                    .restaurantName(restaurant.getName())
                    .waiterFullName(cheque.getUser().getFirstName() + " " + cheque.getUser().getLastName())
                    .menuName(cheque.getMenuItems().stream().map(MenuItem::getName).collect(Collectors.toList()))
                    .priceAverage(cheque.getPriceAverage())
                    .service(restaurant.getService())
                    .grandTotal(grandTotal)
                    .build();

            return chequeResponse;
        }catch (NotFoundException e ){
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public Double getChequeAverageSum(Long id, LocalDate date) {
        try {
            userRepository.findById(id).orElseThrow(()->
                    new NotFoundException("User with id : " + id + " not found !"));
            return chequeRepository.getChequeAverageSum(id, date);
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }catch (NotFoundException e ){
            throw new NotFoundException(e.getMessage());
        }
    }
@Override
    public ChequeResponse update(Long id, ChequeRequest chequeRequest) {
        Cheque cheque = chequeRepository.findById(id).orElse(null);

        chequeRepository.findById(id).orElseThrow(()-> new NotFoundException(" Cheque with id: " + id + " not found !"));

        if (cheque == null) {
            return new ChequeResponse();
        }

        List<Long> menuItemIds = chequeRequest.getMenuItemId();
        for (Long menuItemId : menuItemIds) {
            boolean menuItemExistsInCheque = cheque.getMenuItems().stream()
                    .anyMatch(item -> item.getId().equals(menuItemId));

            if (menuItemExistsInCheque) {
                throw new AlreadyExistException("Menu Item with id: " + menuItemId + " already exists in Check!");
            }
            MenuItem menuItemToAdd = menuItemRepository.findById(menuItemId)
                    .orElseThrow(() -> new NotFoundException("Menu Item with id: " + menuItemId + " not found !"));
            User user = cheque.getUser();

            if (!cheque.getMenuItems().contains(menuItemToAdd)) {
                cheque.getMenuItems().add(menuItemToAdd);
                menuItemToAdd.setCheques(List.of(cheque));
                cheque.setUser(user);
                user.setCheques(List.of(cheque));

                chequeRepository.save(cheque);
            }
        }

        Restaurant restaurant = cheque.getMenuItems().get(0).getRestaurant();
        BigDecimal serviceAsBigDecimal = BigDecimal.valueOf(restaurant.getService());
        BigDecimal grandTotal = cheque.getPriceAverage()
                .multiply(serviceAsBigDecimal.divide(BigDecimal.valueOf(100)))
                .add(cheque.getPriceAverage());

        ChequeResponse chequeResponse = ChequeResponse.builder()
                .id(cheque.getId())
                .restaurantName(restaurant.getName())
                .waiterFullName(cheque.getUser().getFirstName() + " " + cheque.getUser().getLastName())
                .menuName(cheque.getMenuItems().stream().map(MenuItem::getName).collect(Collectors.toList()))
                .priceAverage(cheque.getPriceAverage())
                .service(restaurant.getService())
                .grandTotal(grandTotal)
                .build();

        return chequeResponse;
    }


    @Override
    public SimpleResponse delete(Long id) {
        try {
            Cheque cheque = chequeRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Cheque with id: %s is not present", id)));
            List<MenuItem> menuItems = cheque.getMenuItems();
            for (MenuItem menuItem : menuItems) {
                menuItem.setCheques(null);
                cheque.setMenuItems(null);
            }
            chequeRepository.delete(cheque);
            return new SimpleResponse(HttpStatus.OK, "Cheque with id: " + id + " successfully deleted !");
        }catch (AccessDeniedException a){
            throw new AccessDeniedException("Access denied " + a.getMessage());
        }
    }
}
