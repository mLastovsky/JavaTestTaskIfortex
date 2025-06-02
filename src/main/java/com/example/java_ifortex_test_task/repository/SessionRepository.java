package com.example.java_ifortex_test_task.repository;

import com.example.java_ifortex_test_task.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query(value = """
            SELECT * FROM sessions
            WHERE device_type = 2
            ORDER BY started_at_utc
            LIMIT 1
            """,
            nativeQuery = true)
    Session getFirstDesktopSession();

    @Query(value = """
            SELECT s.* FROM sessions s
            JOIN users u ON s.user_id = u.id
            WHERE u.deleted = false
              AND s.ended_at_utc < '2025-01-01T00:00:00'
            ORDER BY s.started_at_utc DESC
            """,
            nativeQuery = true)
    List<Session> getSessionsFromActiveUsersEndedBefore2025();

}
