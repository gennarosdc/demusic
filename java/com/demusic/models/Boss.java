/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demusic.models;

import com.wrapper.spotify.Api;
import com.wrapper.spotify.models.AuthorizationCodeCredentials;
import com.wrapper.spotify.models.SimplePlaylist;
import com.wrapper.spotify.models.User;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author gennaro
 */
public class Boss {
    private User userProfile;
    private AuthorizationCodeCredentials userAuthorizationCredential;
    private List<SimplePlaylist> playlists = new LinkedList<SimplePlaylist>();
    private Api api;

    public Api getApi() {
        return api;
    }

    public List<SimplePlaylist> getPlaylists() {
        return playlists;
    }
    
    public SimplePlaylist getPlaylistById(String id) {
        for(SimplePlaylist x: this.playlists){
            if (x.getId().equals(id)) return x;
        }
        
        return null;
    }    
    
    public void setUserProfile(User userProfile) {
        this.userProfile = userProfile;
    }

    public void setUserAuthorizationCredential(AuthorizationCodeCredentials userAuthorizationCredential) {
        this.userAuthorizationCredential = userAuthorizationCredential;
    }


    public User getUserProfile() {
        return userProfile;
    }

    public AuthorizationCodeCredentials getUserAuthorizationCredential() {
        return userAuthorizationCredential;
    }

    public void setPlaylists(List<SimplePlaylist> playlists) {
        this.playlists = playlists;
    }

    public void setApi(Api api) {
        this.api = api;
    }

    public void setOwenedPlaylists(List<SimplePlaylist> items) {
        for(SimplePlaylist item : items){
            if (item.getOwner().getId().equals(this.getUserProfile().getId())) {
                this.playlists.add(item);
            }
        }
    }
    
}
