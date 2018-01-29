/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demusic.models;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author gennaro
 */
public class PartyManager {
    //Mappa partyId(uriPLaylist)-partyBean
    private final Map<String,Party> activeParty = new HashMap<String,Party>();


    private static final PartyManager instance = new PartyManager();
   
    //private constructor to avoid client applications to use constructor
    private PartyManager(){}

    public static  PartyManager getPartyManager(){
        return instance;
    }
    
    public synchronized void addParty(Party x){
        this.activeParty.put(x.getId(), x);
    }
    
    public synchronized Party getParty(String id){                 
        return this.activeParty.get(id);
    } 
    
    public Map<String, Party> getActiveParty() {
        return activeParty;
    }    
}
