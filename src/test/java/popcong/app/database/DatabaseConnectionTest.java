package popcong.app.database;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import popcong.app.BaseTest;

public class DatabaseConnectionTest extends BaseTest {

    @Autowired
    private EntityManager entityManager;

    /**
     * DB 연결 테스트
     */
    @Test
    void databaseConnectionTest() {
        Object result = entityManager
                .createNativeQuery("SELECT 'JPA Connection OK' as message")
                .getSingleResult();

        Assertions.assertNotNull(result);
        Assertions.assertEquals("JPA Connection OK", result);
        System.out.println("JPA Connection OK: " + result);
    }

    /**
     * DB 정보 확인 (DB 이름 + 버전)
     */
    @Test
    void databaseInfoTest() {
        Object dbName = entityManager
                .createNativeQuery("SELECT DATABASE() as current_db")
                .getSingleResult();

        Object version = entityManager
                .createNativeQuery("SELECT VERSION() as db_version")
                .getSingleResult();

        System.out.println("Current Database & version: " + dbName + version);

        Assertions.assertNotNull(dbName);
        Assertions.assertNotNull(version);
    }
}