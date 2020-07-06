package com.castlemock.repository.core.dynamodb.token;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.castlemock.repository.Profiles;
import com.castlemock.repository.token.SessionTokenRepository;
import lombok.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.annotation.Id;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * The session token repository is responsible for managing all the ongoing sessions and their corresponding
 * tokens. The repository is also responsible for providing the functionality to both save and load all the
 * tokens to and from dynamodb.
 *
 * @author Tiago Santos
 * @see SessionToken
 * @since 1.51
 */
@Profile(Profiles.DYNAMODB)
@Component("tokenRepository")
public class SessionTokenDynamoRepository implements SessionTokenRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionTokenDynamoRepository.class);

    protected DynamoDBMapper dynamoDBMapper;

    protected DynamoDBMapperConfig dynamoDBMapperConfig;

    protected AmazonDynamoDB amazonDynamoDB;

    @Autowired
    public SessionTokenDynamoRepository(AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig dynamoDBMapperConfig) {
        this.amazonDynamoDB = amazonDynamoDB;
        this.dynamoDBMapperConfig = dynamoDBMapperConfig;
        this.dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB, dynamoDBMapperConfig);
    }

    public SessionTokenDynamoRepository(AmazonDynamoDB amazonDynamoDB) {
        this(amazonDynamoDB, null);
    }

    /**
     * The initialize method is responsible for initiating the token repository and load
     * all the stored tokens
     */
    public void initialize() {
        createTableIfNotExists();
    }

    protected void createTableIfNotExists() {
        CreateTableRequest createTableRequest = dynamoDBMapper.generateCreateTableRequest(SessionToken.class)
                .withProvisionedThroughput(new ProvisionedThroughput(10L, 10L));
        TableUtils.createTableIfNotExists(amazonDynamoDB, createTableRequest);
    }

    /**
     * The method provides the functionality to store a new token in the token repository
     *
     * @param token The token that will be stored in the token repository
     */
    @Override
    public synchronized void createNewToken(PersistentRememberMeToken token) {
        try {
            dynamoDBMapper.save(new SessionToken(token), new DynamoDBSaveExpression()
                    .withExpectedEntry("series", new ExpectedAttributeValue(false)));
        } catch (ConditionalCheckFailedException ex) {
            LOGGER.error(ex.getMessage());
            throw new DataIntegrityViolationException("Series Id '" + token.getSeries() + "' already exists!");
        }
    }

    /**
     * Updates a specific token with a new values to a specific token
     *
     * @param series     The token that will be updated
     * @param tokenValue The new token value
     * @param lastUsed   Date for when it was last used
     */
    @Override
    public synchronized void updateToken(String series, String tokenValue, Date lastUsed) {

        SessionToken current = Optional.ofNullable(dynamoDBMapper.load(SessionToken.class, series)).orElseThrow(
                () -> new IllegalArgumentException("cannot find SessionToken with series value: " + series)
        );

        current.tokenValue = tokenValue;
        current.date = new Date();

        dynamoDBMapper.save(current);
    }

    /**
     * The method provides the functionality to update the token with a new username. The token
     * will be identified with the old username and upon found, the username will be updated to the
     * new provided username value
     *
     * @param oldUsername The old username. It is used to identify the token
     * @param newUsername The new username. It will replace the old username
     */
    public synchronized void updateToken(String oldUsername, String newUsername) {

        List<SessionToken> tokens = dynamoDBMapper.scan(SessionToken.class,
                getAttributeScan("usernameLower", oldUsername.toLowerCase(), ComparisonOperator.EQ))
                .stream()
                .map(x -> x.toBuilder()
                        .username(newUsername)
                        .usernameLower(newUsername.toLowerCase())
                        .build()
                ).collect(Collectors.toList());

        dynamoDBMapper.batchSave(tokens);
//        SessionToken current = dynamoDBMapper.scan(SessionToken.class, getUsernameQuery(oldUsername))
//                .stream().findFirst().orElseThrow(() ->
//                        new IllegalArgumentException("cannot find SessionToken with username value: " + oldUsername));
//
//        current.username = newUsername;
//        dynamoDBMapper.save(current);

    }

    /**
     * Get a specific token for a series
     *
     * @param seriesId The id of the series that the token belongs to
     * @return Token that matches the provided series id. Null will be returned if no token matches
     * the provided series id
     */
    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return dynamoDBMapper.scan(SessionToken.class, getSeriesQuery(seriesId))
                .stream().findFirst().map(SessionToken::toPersistentRememberMeToken).orElse(null);
    }

    /**
     * Remove a user token from the repository
     *
     * @param username The token that matches this user name will be removed
     */
    @Override
    public void removeUserTokens(String username) {
        List<SessionToken> list = dynamoDBMapper.scan(SessionToken.class, getUsernameQuery(username));
        dynamoDBMapper.batchDelete(list);
    }

    /**
     * The session token represent the token which will be used to identify the user
     * if the user has chosen the "Remember me" option when logging in
     */
    @DynamoDBTable(tableName = "sessionToken")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(toBuilder = true)
    public static class SessionToken {

        @DynamoDBAttribute(attributeName = "username")
        private String username;
        @Id
        @DynamoDBHashKey(attributeName = "series")
        private String series;
        @DynamoDBAttribute(attributeName = "tokenValue")
        private String tokenValue;
        @DynamoDBAttribute(attributeName = "date")
        private Date date;

        @DynamoDBAttribute(attributeName = "usernameLower")
        private String usernameLower;

        public SessionToken(PersistentRememberMeToken persistentRememberMeToken) {
            this.username = persistentRememberMeToken.getUsername();
            this.series = persistentRememberMeToken.getSeries();
            this.tokenValue = persistentRememberMeToken.getTokenValue();
            this.date = persistentRememberMeToken.getDate();
            this.usernameLower = this.username == null ? null : this.username.toLowerCase();
        }

        public PersistentRememberMeToken toPersistentRememberMeToken() {
            return new PersistentRememberMeToken(this.username, this.series, this.tokenValue, this.date);
        }
    }

    protected DynamoDBScanExpression getAttributeScan(
            String attributeName, String value, ComparisonOperator comparisonOperator
    ) {
        return new DynamoDBScanExpression().withFilterConditionEntry(attributeName,
                new Condition().withComparisonOperator(comparisonOperator)
                        .withAttributeValueList(new AttributeValue(value)));
    }

    private DynamoDBScanExpression getSeriesQuery(String series) {
        return getAttributeScan("series", series, ComparisonOperator.EQ);
    }

    private DynamoDBScanExpression getUsernameQuery(String username) {
        return getAttributeScan("username", username, ComparisonOperator.EQ);
    }
}
