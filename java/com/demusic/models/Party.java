/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demusic.models;

import com.demusic.exceptions.CustomException;
import com.wrapper.spotify.models.Track;
import java.sql.Time;
import java.util.List;
import java.util.Map;

/**
 *
 * @author gennaro
 */
public class Party {
    //Id del party corrisponde a uri della playlist
    private String id;
    private List<Track> tracksList;
    private Map<Integer,String> tracksOrder;
    private Time startedAt;
    private Integer reorderTimer;
    //private boolean isRunning;
    private Votebox votebox = new Votebox();
    private Boss boss;
    private String playlistId;

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }
    public Votebox getVotebox() {
        return votebox;
    }

    public void setVotebox(Votebox votebox) {
        this.votebox = votebox;
    }

    public Boss getBoss() {
        return boss;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }
    
    public Party (String id){
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Track> getTracksList() {
        return tracksList;
    }

    public void setTracksList(List<Track> tracksList) {
        this.tracksList = tracksList;
    }

    public Map<Integer, String> getTracksOrder() {
        return tracksOrder;
    }

    public void setTracksOrder(Map<Integer, String> tracksOrder) {
        this.tracksOrder = tracksOrder;
    }

    public Time getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Time startedAt) {
        this.startedAt = startedAt;
    }

    public Integer getReorderTimer() {
        return reorderTimer;
    }

    public void setReorderTimer(Integer reorderTimer) {
        this.reorderTimer = reorderTimer;
    }

    public Integer findPosition(String id) throws CustomException{
        for (Map.Entry<Integer, String> entry: this.tracksOrder.entrySet()){
            if (entry.getValue().equals(id)) return entry.getKey();
            
        } 
           
    throw new CustomException("FindPosition: traccia non trovata");
    }


    

}
