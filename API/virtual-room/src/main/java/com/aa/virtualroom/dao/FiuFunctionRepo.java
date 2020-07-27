package com.aa.virtualroom.dao;

import com.aa.virtualroom.model.FunctionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FiuFunctionRepo extends JpaRepository<FunctionDetails, UUID> {

    @Query("select j from FunctionDetails j where j.functionId = ?1")
    public Optional<FunctionDetails> getFunctionById(@Param("functionId") UUID functionId);

    @Query("select j from FunctionDetails j where j.fiuId = ?1")
    public Optional<List<FunctionDetails>> getFunctionByFiuId(@Param("fiuId") UUID fiuId);

}
