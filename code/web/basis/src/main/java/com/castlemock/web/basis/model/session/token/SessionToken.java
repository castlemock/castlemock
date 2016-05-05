package com.castlemock.web.basis.model.session.token;

import com.castlemock.web.basis.model.session.token.repository.SessionTokenRepository;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * The session token represent the token which will be used to identify the user
 * if the user has chosen the "Remember me" option when logging in
 * @author Karl Dahlgren
 * @since 1.0
 * @see SessionTokenList
 * @see SessionTokenRepository
 */
@XmlRootElement
public class SessionToken {

    private String username;
    private String series;
    private String tokenValue;
    private Date date;

    /**
     * Default constructor for the Token.
     * It is required in order to marshal and unmarshal the token
     */
    public SessionToken(){

    }

    public SessionToken(PersistentRememberMeToken persistentRememberMeToken){
        this.username = persistentRememberMeToken.getUsername();
        this.series = persistentRememberMeToken.getSeries();
        this.tokenValue = persistentRememberMeToken.getTokenValue();
        this.date = persistentRememberMeToken.getDate();
    }

    @XmlElement
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @XmlElement
    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    @XmlElement
    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    @XmlElement
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

