package com.fortmocks.web.basis.model.session.token;

import com.fortmocks.web.basis.model.session.token.repository.SessionTokenRepository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.LinkedList;
import java.util.List;

/**
 * The SessionTokenList is a container class for all the tokens which will be stored on
 * the local file system.
 * @author Karl Dahlgren
 * @since 1.0
 * @see SessionToken
 * @see SessionTokenRepository
 */
@XmlRootElement(name = "tokens")
@XmlSeeAlso({SessionToken.class})
public class SessionTokenList extends LinkedList<SessionToken> {

    @XmlElement(name = "token")
    public List<SessionToken> getSessionTokens() {
        return this;
    }
}
