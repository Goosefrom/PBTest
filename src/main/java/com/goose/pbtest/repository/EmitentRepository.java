package com.goose.pbtest.repository;

import com.goose.pbtest.model.Emitent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmitentRepository extends JpaRepository<Emitent, Long>, JpaSpecificationExecutor<Emitent> {

    @Query("SELECT e FROM Emitent e WHERE :cardNumber BETWEEN e.minRange AND e.maxRange")
    Optional<Emitent> findByCardInRange(@Param("cardNumber") String cardNumber);

    @Query("SELECT e.id FROM Emitent e")
    List<Long> findAllId();

    @Modifying
    @Query("DELETE FROM Emitent e WHERE e.id IN :ids")
    void deleteAllById(@Param("ids") List<Long> ids);
}
