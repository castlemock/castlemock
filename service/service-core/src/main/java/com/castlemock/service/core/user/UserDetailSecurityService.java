/*
 * Copyright 2015 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castlemock.service.core.user;

import com.castlemock.model.core.ServiceProcessor;
import com.castlemock.model.core.user.Role;
import com.castlemock.model.core.user.Status;
import com.castlemock.model.core.user.User;
import com.castlemock.service.core.user.input.ReadUserByUsernameInput;
import com.castlemock.service.core.user.output.ReadUserByUsernameOutput;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The User Detail Security service is used upon authentication towards Castle Mock.
 * The class is responsible for locating users and match the incoming authentication credentials
 * towards the users.
 * @author Karl Dahlgren
 * @since 1.0
 * @see org.springframework.security.core.userdetails.User
 */
@Service("userDetailsService")
public class UserDetailSecurityService implements UserDetailsService {

    @Autowired
    private ServiceProcessor serviceProcessor;

    /**
     * Loads a user by the username
     * @param username The user detail that will be loaded should match the provided username
     * @return User details that match the provided username
     * @throws NullPointerException Throws NullPointerException if provided username is null
     * @throws IllegalArgumentException Throws IllegalArgumentException if the username is empty
     */
    @Override
    public UserDetails loadUserByUsername(final String username) {
        Preconditions.checkNotNull(username, "Username cannot be null");
        Preconditions.checkArgument(!username.isEmpty(), "Username cannot be empty");
        final ReadUserByUsernameInput readUserByUsernameInput = ReadUserByUsernameInput.builder()
                .username(username)
                .build();
        final ReadUserByUsernameOutput readUserByUsernameOutput = serviceProcessor.process(readUserByUsernameInput);
        final User user = readUserByUsernameOutput.getUser();
        Preconditions.checkNotNull(user, "Unable to find user");
        final List<GrantedAuthority> authorities = buildUserAuthority(user.getRole());
        return buildUserForAuthentication(user, authorities);

    }

    /**
     * Builds a user for the authentication
     * @param user The user dto
     * @param authorities A list of authorities
     * @return Returns a new user that contain the same username and password as the provided user dto
     * @throws NullPointerException Throws NullPointerException if provided username or authorities parameters are null
     */
    private org.springframework.security.core.userdetails.User buildUserForAuthentication(final User user, final List<GrantedAuthority> authorities) {
        Preconditions.checkNotNull(user, "Username cannot be null");
        Preconditions.checkNotNull(authorities, "Authorities cannot be null");
        boolean locked = user.getStatus().equals(Status.LOCKED);
        boolean inactive = user.getStatus().equals(Status.INACTIVE);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, !inactive, !locked, authorities);
    }

    /**
     * Build user authority
     * @param role The role which name will be used for the authority
     * @return Returns a list of granted authorities
     * @throws NullPointerException Throws NullPointerException if provided role parameter is null
     */
    private List<GrantedAuthority> buildUserAuthority(final Role role) {
        Preconditions.checkNotNull(role, "Role cannot be null");
        final List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role.name()));
        return grantedAuthorities;
    }

    /**
     * The method updates the current logged in user. This method is used when updating a new username to the logged in
     * user.
     * @param username The new name of the user that will be logged in
     * @see org.springframework.security.core.userdetails.User
     */
    public void updateCurrentLoggedInUser(final String username){
        final UserDetails userDetails = loadUserByUsername(username);
        final Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
