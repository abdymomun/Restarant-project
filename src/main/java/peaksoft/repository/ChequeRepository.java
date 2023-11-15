package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.dto.dtoCheque.ChequeResponse;
import peaksoft.entity.Cheque;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChequeRepository extends JpaRepository<Cheque, Long> {
    @Query("SELECT SUM(c.priceAverage) " +
            "FROM Cheque c " +
            "JOIN c.user u " +
            "WHERE u.id = :userId AND c.createdAt = :createDate " +
            "GROUP BY u.id")
    Double getChequeAverageSum(@Param("userId") Long userId, @Param("createDate") LocalDate createDate);
    @Query("SELECT r.name ,  CONCAT(u.firstName, ' ', u.lastName) as waiterFullName, " +
            "mi.name, c.priceAverage, r.service,  " +
            "(SUM(mi.price) + r.service + c.priceAverage)  " +
            "FROM Cheque c " +
            "JOIN c.user u " +
            "JOIN c.menuItems mi " +
            "JOIN mi.restaurant r " +
            "GROUP BY r.name, c.priceAverage, mi.name, r.service, waiterFullName")
    List<ChequeResponse> getAllChequeResponses();


}