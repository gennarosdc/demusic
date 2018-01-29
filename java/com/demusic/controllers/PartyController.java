package com.demusic.controllers;

import com.demusic.exceptions.CustomException;
import com.demusic.models.Boss;
import com.demusic.models.Party;
import com.demusic.models.PartyManager;
import com.demusic.models.Votebox;
import com.demusic.models.Votebox.Vote;
import com.wrapper.spotify.Api;
import com.wrapper.spotify.exceptions.WebApiException;
import com.wrapper.spotify.methods.UserCurrentlyPlayingRequest;
import com.wrapper.spotify.models.Page;
import com.wrapper.spotify.models.PlayingTrack;
import com.wrapper.spotify.models.PlaylistTrack;
import com.wrapper.spotify.models.SimplePlaylist;
import com.wrapper.spotify.models.Track;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author gennaro
 */
@WebServlet(name = "PartyController", urlPatterns = {"/PartyController"})
public class PartyController extends HttpServlet {
   
    private final PartyManager partyManager = PartyManager.getPartyManager();
    private final Logger log = LogManager.getLogger(PartyController.class);
    private static final String ERROR_PAGE = "/errorPage.jsp";    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ERR_MESSAGE = "";
        Boss boss =(Boss) request.getSession().getAttribute("currentBoss");
        
        if(request.getParameter("action").equals("start")){
            try{
               
                SimplePlaylist playlist = boss.getPlaylistById(request.getParameter("playlistId"));
                log.trace("Check if there is the right playback to play");
                PlayingTrack currentlyPlayingTrack = boss.getApi().getUserCurrentlyPlayingTrack().build().get();
                if( currentlyPlayingTrack.getTrack() == null ) {
                    throw new Exception("Playback not found. Check if there is the right playback on your spotify client app.");
                }else{
                    //if (! isInPartyPlaylist(currentlyPlayingTrack.getTrack())) {
                    //    throw new Exception("Wrong Playback. Be sure that playback track is in your party playlist tracks.");
                    //}
                } 
                
                log.trace("Start party with playlist " + request.getParameter("playlistId"));
                //Play/Resume playlist
                boss.getApi().getUserPlay().context(playlist.getUri()).build().get();

                //spotify uri della playlist da fare play, preso direttamente da client spotify 

                Page<PlaylistTrack> playlistPage = boss.getApi().getPlaylistTracks(boss.getUserProfile().getId(), request.getParameter("playlistId")).build().get();        
                List<PlaylistTrack> playlistTracks = playlistPage.getItems();
               
                
                
                List<Track> tracksList = new LinkedList<Track>();
                Map<Integer,String> tracksOrder = new HashMap<Integer,String>();
                //La prima traccia in playlist parte da zero
                int cont = 0;
                for(PlaylistTrack x: playlistTracks){
                    //Howlin' For You - 1gBGDhwhekrKYy7gqULf0t - spotify:track:1gBGDhwhekrKYy7gqULf0t
                    //System.out.println(x.getTrack().getName() + " - "+ x.getTrack().getId() + " - "+ x.getTrack().getUri() );
                    tracksList.add(x.getTrack());
                    tracksOrder.put(cont, x.getTrack().getId());
                    cont++;
                    
                }
                
                //Si puo ottimizzare costruttore
                Party party = new Party(playlist.getUri());
                //Default 1 minute
                party.setReorderTimer(1);
                party.setTracksList(tracksList);
                party.setTracksOrder(tracksOrder);
                party.setBoss(boss);
                party.setPlaylistId(request.getParameter("playlistId"));
                
                
                partyManager.addParty(party);
                request.getSession().setAttribute("currentParty", party);
                System.out.println("Party Started! Join in using code: " + playlist.getUri());
                request.getRequestDispatcher("/bossDashboard.jsp").forward(request, response);
                          
 
            }catch(WebApiException e){                  
                  ERR_MESSAGE = "Error replies from the Web API: "+ e.getMessage();
                  e.printStackTrace();
            }catch(IOException e){    
                  ERR_MESSAGE = "Error replies from networking issue: "+ e.getMessage();
                  e.printStackTrace();
            }catch(Exception e){
                  ERR_MESSAGE = "Error to start party: " + e.getMessage();
                  e.printStackTrace();                  
            }finally{
                if(!ERR_MESSAGE.equals("")){
                    request.setAttribute("ERR_MESSAGE", ERR_MESSAGE);
                    request.getRequestDispatcher(ERROR_PAGE).forward(request, response);  
                    return;
                }
            } 
            

        }else if(request.getParameter("action").equals("reorder")){
            
            //move the last
            try{
                log.debug("Sto per prelevare currentParty ");
                Party party =(Party) request.getSession().getAttribute("currentParty");               
                log.debug("Prelevato currentParty "+party.getId() + " Sto per rioridinare");
                this.reorderParty(party);
                log.debug("Riordino effettuato");
                this.updateTracksOrder(party);
                this.resetVotebox(party);
                
                //La chiamata a start/resume playback non fa una diretta selezione sulla canzone(generando un nuovo flusso esecutivo)
                //di conseguenza l`ordine cambia, ma il flusso esecutivo rimane quello di default


             //api.setAccessToken(boss.getUserAuthorizationCredential().getAccessToken());
            //api.reorderTracksInPlaylist(boss.getUserProfile().getId(), request.getParameter("playlistId"), 6, 0).build().get();     
            
            
            }catch(CustomException e){
                    request.setAttribute("ERR_MESSAGE", e.getMessage());
                    request.getRequestDispatcher(ERROR_PAGE).forward(request, response);  
                    return;
            }            
        }else if(request.getParameter("action").equals("changeReorderTimer")){
            
        }else if(request.getParameter("action").equals("joinParty")){
            Party party = partyManager.getParty(request.getParameter("partyId"));
            if(party!=null){
                request.setAttribute("party", party);
                request.getRequestDispatcher("/party.jsp").forward(request, response);
            }else{
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        }else if(request.getParameter("action").equals("vote")){       
            try{
                  log.debug("Voto trackId "+request.getParameter("trackId")+" party con id "+ request.getParameter("partyId"));
                  Party party = partyManager.getParty(request.getParameter("partyId"));
                  log.debug("Party prelevato per voto = " + party.getId());
                  party.getVotebox().addVote(request.getParameter("trackId"));
                  log.debug("Voto aggiunto");

            }catch(NullPointerException e){    
                  ERR_MESSAGE = "Party not found "+ e.getMessage();
                  e.printStackTrace();
            }catch(Exception e){
                  ERR_MESSAGE = "Error to vote" + e.getMessage();
                  e.printStackTrace();
            }finally{
                if(!ERR_MESSAGE.equals("")){
                    request.setAttribute("ERR_MESSAGE", ERR_MESSAGE);
                    request.getRequestDispatcher(ERROR_PAGE).forward(request, response);  
                    return;
                }
            } 


        }
        
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
    private void updateTracksOrder(Party party) throws CustomException{
        try{       
            Page<PlaylistTrack> playlistPage = party.getBoss().getApi().getPlaylistTracks(party.getBoss().getUserProfile().getId(), party.getPlaylistId()).build().get();        
            List<PlaylistTrack> playlistTracks = playlistPage.getItems();
            Map<Integer,String> newTracksOrder = new HashMap<Integer,String>();
            //La prima traccia in playlist parte da zero
            int cont = 0;            
            for(PlaylistTrack x: playlistTracks){                
                newTracksOrder.put(cont, x.getTrack().getId());
                cont++;
            }            
            party.setTracksOrder(newTracksOrder);
          
        }catch(Exception e){
            throw new CustomException(e.getMessage());
        }
    }
    
    private void resetVotebox(Party party) throws CustomException{
        try{       
          party.getVotebox().getVotes().clear();         
        }catch(Exception e){
            throw new CustomException(e.getMessage());
        }
    }    

    private void reorderParty(Party party) throws CustomException {
        try{
            log.debug("Riordino party: entro");
            TreeSet<Vote> orderedVotes = new TreeSet<Vote>();
            for (Vote vote: party.getVotebox().getVotes().values()) {
                log.debug("Trovato voto " + vote);
                orderedVotes.add(vote);
            }
            log.debug("Riordino party: voti ordinati");
            PlayingTrack currentlyPlaying = party.getBoss().getApi().getUserCurrentlyPlayingTrack().build().get();
            Integer currentlyPosition = party.findPosition(currentlyPlaying.getTrack().getId());
            log.debug("Riordino party: ottenuta currently playing track che nel party è in posizione " + currentlyPosition);
            int cont = 1;
            log.debug("userId:"+party.getBoss().getUserProfile().getId() +"  playlistId:"+ party.getPlaylistId());
            Iterator<Vote> i = orderedVotes.descendingIterator();
            while(i.hasNext()){
                Vote vote = i.next();
                log.debug("Riordino party: preparo riordino traccia "+ vote.toString() +" currentPos " + party.findPosition(vote.getTrackId()) +" targetPos " + ( (currentlyPosition + cont)%(party.getTracksList().size()+1)));
                party.getBoss().getApi().reorderTracksInPlaylist(party.getBoss().getUserProfile().getId(), party.getPlaylistId(), party.findPosition(vote.getTrackId()), (currentlyPosition + cont)%(party.getTracksList().size()+1) ).build().get();
                //Verifica se è necessario:
                this.updateTracksOrder(party);
                cont++;                
            }
            log.debug("Riordino party: fine riordino");
            
        }catch(Exception e ){
            e.printStackTrace();
            throw new CustomException(e.getMessage());
        }
       
    }
}
