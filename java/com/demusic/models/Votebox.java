package com.demusic.models;

import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author gennaro
 */
public class Votebox {
    
   // private Set<Vote> votes = new TreeSet<>(); // soluzione O(2nlog(n))
    private Map<String,Vote> votes = new HashMap<>(); //soluzione O(nlog(n)) 
    public Map<String,Vote> getVotes() {
        return votes;
    }
    
    /*
    public Map<String, Integer> getVotes() {
        return votes;
    }

    public void setVotes(Map<String, Integer> votes) {
        this.votes = votes;
    }
    
    
     See /myTreeMultimap.
    */
    public synchronized void addVote(String trackId){
        
        Vote oldVote = votes.get(trackId);
        if (oldVote != null){
            oldVote.setVoteHit(oldVote.getVoteHit() + 1);
        }else{
            votes.put(trackId, new Vote(trackId));
        }

    }

    public class Vote implements Comparable{
        String trackId;
        Integer voteHit;

        public Vote(String key, Integer value) {
            this.trackId = key;
            this.voteHit = value;
        }
        public Vote(String key) {
            this.trackId = key;
            this.voteHit = 1;
        }
        public String getTrackId() {
            return trackId;
        }

        public void setTrackId(String trackId) {
            this.trackId = trackId;
        }

        public Integer getVoteHit() {
            return voteHit;
        }

        public void setVoteHit(Integer voteHit) {
            this.voteHit = voteHit;
        }

        
        @Override
        public int compareTo(Object o) {
            if ( ! (o instanceof Vote)) throw new ClassCastException();
            Vote x = (Vote) o;
            return this.voteHit.compareTo(x.getVoteHit());
        }


        @Override
        public String toString(){
            return "[" + this.trackId + "-"+ this.voteHit + "]";
        }
    }
    

}
