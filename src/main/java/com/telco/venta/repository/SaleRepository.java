package com.telco.venta.repository;

import com.telco.venta.entity.Sale;
import com.telco.venta.enums.SaleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    Optional<Sale> findByCodigoLlamada(String codigoLlamada);

    List<Sale> findByAgenteId(Long agenteId);

    List<Sale> findByEstado(SaleStatus estado);

    @Query("SELECT s FROM Sale s WHERE s.agenteId = :agenteId AND s.estado = :estado")
    List<Sale> findByAgenteIdAndEstado(@Param("agenteId") Long agenteId, @Param("estado") SaleStatus estado);

    @Query("SELECT s FROM Sale s WHERE s.agenteId = :agenteId AND s.fechaRegistro BETWEEN :inicio AND :fin")
    List<Sale> findByAgenteIdAndDateRange(@Param("agenteId") Long agenteId, @Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);

    @Query("SELECT s FROM Sale s WHERE s.agenteId = :agenteId AND s.estado = :estado AND s.fechaRegistro BETWEEN :inicio AND :fin")
    List<Sale> findByAgenteIdEstadoAndDateRange(@Param("agenteId") Long agenteId, @Param("estado") SaleStatus estado, @Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);

    @Query("SELECT s FROM Sale s WHERE s.agenteId IN (SELECT u.id FROM User u WHERE u.supervisorId = :supervisorId) AND s.estado = :estado")
    List<Sale> findBySupervisorIdAndEstado(@Param("supervisorId") Long supervisorId, @Param("estado") SaleStatus estado);

    @Query("SELECT s FROM Sale s WHERE s.agenteId IN (SELECT u.id FROM User u WHERE u.supervisorId = :supervisorId)")
    List<Sale> findBySupervisorId(@Param("supervisorId") Long supervisorId);

    @Query("SELECT s FROM Sale s WHERE s.agenteId IN (SELECT u.id FROM User u WHERE u.supervisorId = :supervisorId) AND s.agenteId = :agenteId")
    List<Sale> findBySupervisorIdAndAgenteId(@Param("supervisorId") Long supervisorId, @Param("agenteId") Long agenteId);

    @Query("SELECT s FROM Sale s WHERE s.agenteId IN (SELECT u.id FROM User u WHERE u.supervisorId = :supervisorId) " +
            "AND s.fechaRegistro BETWEEN :inicio AND :fin")
    List<Sale> findBySupervisorIdAndDateRange(@Param("supervisorId") Long supervisorId, @Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);

    @Query("SELECT s FROM Sale s WHERE s.agenteId IN (SELECT u.id FROM User u WHERE u.supervisorId = :supervisorId) " +
            "AND s.estado = :estado AND s.fechaRegistro BETWEEN :inicio AND :fin")
    List<Sale> findBySupervisorIdEstadoAndDateRange(@Param("supervisorId") Long supervisorId,
                                                    @Param("estado") SaleStatus estado,
                                                    @Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);

    @Query("SELECT s FROM Sale s WHERE s.agenteId IN (SELECT u.id FROM User u WHERE u.supervisorId = :supervisorId) " +
            "AND s.agenteId = :agenteId AND s.fechaRegistro BETWEEN :inicio AND :fin")
    List<Sale> findBySupervisorIdAgenteIdAndDateRange(@Param("supervisorId") Long supervisorId,
                                                      @Param("agenteId") Long agenteId,
                                                      @Param("inicio") LocalDate inicio,
                                                      @Param("fin") LocalDate fin);

    //reportes
    @Query("SELECT s FROM Sale s WHERE s.fechaRegistro BETWEEN :inicio AND :fin")
    List<Sale> findByDateRange(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);


}
