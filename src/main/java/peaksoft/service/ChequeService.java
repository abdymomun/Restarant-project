package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoCheque.ChequeRequest;
import peaksoft.dto.dtoCheque.ChequeResponse;

import java.time.LocalDate;
import java.util.List;

public interface ChequeService {
    SimpleResponse save(ChequeRequest chequeRequest);

    List<ChequeResponse> getAll();

    ChequeResponse getById(Long id);
    Double getChequeAverageSum(Long id, LocalDate date);


    ChequeResponse update(Long id, ChequeRequest chequeRequest);

    SimpleResponse delete(Long id);
}
