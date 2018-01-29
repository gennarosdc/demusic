/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demusic.models;

import java.util.HashMap;

/**
 *
 * @author gennaro
 */
public class BossesManager {
    private final HashMap<String,Boss> activeBosses = new HashMap<String,Boss>();
    private static final BossesManager instance = new BossesManager();
   
    //private constructor to avoid client applications to use constructor
    private BossesManager(){}

    public static BossesManager getBossesManager(){
        return instance;
    }
    
    public synchronized void addBoss(Boss x){
        this.activeBosses.put(x.getUserProfile().getId(), x);
    }
    
    public synchronized Boss getBoss(String id){
        return this.activeBosses.get(id);
    }
}
