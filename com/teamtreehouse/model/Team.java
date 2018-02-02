package com.teamtreehouse.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class Team implements Comparable<Team>{

  private String teamName;
  private String coachName;
  private Set<Player> playersSets;
  
  public Team(String teamName, String coachName){
    this.teamName = teamName;
    this.coachName = coachName;
    
    playersSets = new TreeSet<Player>();
  }
  
  public Team(){
  }
  
  public void addPlayer(Player player){
    playersSets.add(player);
  }
  
  public String getTeamName(){
    return teamName;
  }
  
  public String getCoachName(){
    return coachName;
  }
  
  public Set getPlayers(){
    return playersSets;
  }
  
  public void removePlayer(Player player){
    playersSets.remove(player);
  }
  
  @Override
  public int compareTo(Team other){
    return (this.teamName.toLowerCase()).compareTo(other.teamName.toLowerCase());
  }  
}