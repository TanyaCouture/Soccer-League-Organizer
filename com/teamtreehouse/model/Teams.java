package com.teamtreehouse.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

// team names should be added to a treeset

public class Teams{
  private BufferedReader mReader;
  
  private List<Team> mTeams;
  
  public Teams(){
    mReader = new BufferedReader(new InputStreamReader(System.in));
    
    mTeams = new ArrayList<Team>();
    
    
  }
  
  public void addTeam(Team team){
    mTeams.add(team);
  }
  
  public List<Team> getTeams(){
    return mTeams;
  }
  
  public int getTeamCount(){
    return mTeams.size();  
  }
  
  public Team getTeamList() throws IOException{
    // creates new alphabetically ordered ArrayList of teams
    Collections.sort(mTeams);
    int count = 1;
    System.out.printf("Teams: %n");
    for(Team mTeam: mTeams){
      System.out.printf(count + ".) " +" Team "+ "%s" + " with coach %s %n", mTeam.getTeamName(), mTeam.getCoachName());
      count++;
    }
    
    System.out.printf("Please choose the team number: ");
    String newTeamAsString = mReader.readLine();
    int newTeam = Integer.parseInt(newTeamAsString.trim());
    System.out.printf("%n");
    return mTeams.get(newTeam - 1);
  }
}