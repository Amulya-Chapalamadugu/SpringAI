package com.eazybytes.mcp_server.repository;



import com.eazybytes.mcp_server.domain.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundRepository extends JpaRepository<Refund, Long> {
}
