package com.castlemock.repository.core.mongodb.token;

import com.castlemock.repository.Profiles;
import com.castlemock.repository.token.SessionTokenRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@DataMongoTest
@RunWith(SpringRunner.class)
@ActiveProfiles(Profiles.MONGODB)
public class SessionTokenMongoRepositoryTest {

    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private SessionTokenRepository sessionTokenRepository;

    @Test
    public void testCreateNewToken_CreatesAToken() {
        String series = "1uyasddf32";
        PersistentRememberMeToken rememberMeToken = new PersistentRememberMeToken("abdullah", series,
                "134xsdf32", new Date());
        sessionTokenRepository.createNewToken(rememberMeToken);

        PersistentRememberMeToken rememberMeTokenFromDb = sessionTokenRepository.getTokenForSeries(series);

        assertThat(rememberMeTokenFromDb).usingRecursiveComparison().isEqualTo(rememberMeToken);
    }

    @Test
    public void testCreateNewToken_AlreadyExists_ThrowsException() {
        String series = "1uyasddf32";
        PersistentRememberMeToken rememberMeToken = new PersistentRememberMeToken("abdullah", series,
                "134xsdf32", new Date());
        sessionTokenRepository.createNewToken(rememberMeToken);

        try {
            sessionTokenRepository.createNewToken(rememberMeToken);
            fail("should throws exception");
        } catch (DataIntegrityViolationException ex) {
            // Keeping the same message format of the file-based implementation for full compliance.
            assertThat(ex.getMessage()).isEqualTo("Series Id '1uyasddf32' already exists!");
        }
    }

    @Test
    public void testUpdateBySeries() {
        String series = "someSeries";
        PersistentRememberMeToken rememberMeToken = new PersistentRememberMeToken("abdullah", series,
                "oldToken", new Date());
        sessionTokenRepository.createNewToken(rememberMeToken);

        sessionTokenRepository.updateToken(series, "newToken", Date.from(Instant.parse("2010-01-01T10:00:00z")));

        PersistentRememberMeToken tokenFromDb = sessionTokenRepository.getTokenForSeries(series);
        assertThat(tokenFromDb.getTokenValue()).isEqualTo("newToken");
        // Note, the `lastUsed` parameter to updateToken is ignored, and a `new Date()`
        // object is used instead, to be fully compliant with current file-based implementation.
        assertThat(tokenFromDb.getDate()).isEqualToIgnoringMinutes(new Date());
    }

    @Test
    public void testUpdateByUsername() {
        String series = "someSeries";
        PersistentRememberMeToken rememberMeToken = new PersistentRememberMeToken("abdullah", series,
                "oldToken", new Date());
        sessionTokenRepository.createNewToken(rememberMeToken);

        sessionTokenRepository.updateToken("abdullah", "farida");

        PersistentRememberMeToken tokenFromDb = sessionTokenRepository.getTokenForSeries(series);

        assertThat(tokenFromDb.getUsername()).isEqualTo("farida");
        assertThat(tokenFromDb).usingRecursiveComparison().ignoringFields("username").isEqualTo(rememberMeToken);
    }

    @Test
    public void testRemoveUserTokens() {
        sessionTokenRepository.createNewToken(new PersistentRememberMeToken("abdullah", "s1", "t1", new Date()));
        sessionTokenRepository.createNewToken(new PersistentRememberMeToken("abdullah", "s2", "t2", new Date()));
        sessionTokenRepository.createNewToken(new PersistentRememberMeToken("farida", "s3", "t3", new Date()));
        sessionTokenRepository.createNewToken(new PersistentRememberMeToken("abdullah", "s4", "t4", new Date()));
        sessionTokenRepository.createNewToken(new PersistentRememberMeToken("abdullah", "s5", "t5", new Date()));

        sessionTokenRepository.removeUserTokens("abdullah");

        long sessionToken = mongoOperations.count(new Query(), "sessionToken");
        assertThat(sessionToken).isEqualTo(1);
    }


    @Test
    // concurrency already handled by mongo (using the @Id that makes the field unique)
    public void testCreateNewToken_Concurrently_IsGrantedByMongodb() throws Exception {
        CountDownLatch lock = new CountDownLatch(2);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger exceptionCount = new AtomicInteger();

        executorService.submit(() -> createTokenJob(lock, successCount, exceptionCount, "abdullah"));
        executorService.submit(() -> createTokenJob(lock, successCount, exceptionCount, "farida"));
        executorService.shutdown();

        lock.await(2, TimeUnit.SECONDS);    // just in case, but it should never waits this long duration

        long sessionToken = mongoOperations.count(new Query(), "sessionToken");
        assertThat(sessionToken).isEqualTo(1);
        assertThat(successCount.get()).isEqualTo(1);
        assertThat(exceptionCount.get()).isEqualTo(1);
    }

    @After
    public void after() {
        mongoOperations.dropCollection("sessionToken");
    }

    // -- private utility
    private void createTokenJob(CountDownLatch lock, AtomicInteger successCount, AtomicInteger exceptionCount, String username) {
        try {
            sessionTokenRepository.createNewToken(new PersistentRememberMeToken(username, "s1", "t2", new Date()));
            successCount.incrementAndGet();
        } catch (DataIntegrityViolationException ex) {
            exceptionCount.incrementAndGet();
        } finally {
            lock.countDown();
        }
    }
}