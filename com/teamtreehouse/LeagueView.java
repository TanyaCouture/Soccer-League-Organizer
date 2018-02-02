package com.teamtreehouse;

import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;
import com.teamtreehouse.model.Teams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class LeagueView {

  private BufferedReader mReader;
  private int count;
  private int index;
  private int teamPlayerIndex;
  private List<Player> playersLists;
  private Player[] players;
  private Player player;
  private Set<Player> playersSets;
  
  private Map<String, String> mMenu;
  private Team mTeam;
  private Teams mTeams;
  
  public LeagueView(Player[] players){
    this.players = players;

    mTeam = new Team();
    mTeams = new Teams();
    
    mReader = new BufferedReader(new InputStreamReader(System.in));
    mMenu = new HashMap<String, String>();
    mMenu.put("Create","Create a team");
    mMenu.put("Add", "Add a player to a team");
    mMenu.put("Remove", "Remove a player from a team");
    mMenu.put("Report", "View a report of a team by height");
    mMenu.put("Balance", "View the League Balance Report");
    mMenu.put("Roster", "View roster");
    mMenu.put("Quit", "Exits the program");
  }
  
  private String promptAction() throws IOException {
    for(Map.Entry<String, String> option: mMenu.entrySet()){
      System.out.printf("%s - %s %n", option.getKey(), option.getValue());
    }
    System.out.print("Select an option: ");
    String choice = mReader.readLine();
    return choice.trim().toLowerCase();
  }
  
  private Team promptNewTeam() throws IOException{
    System.out.print("What is the team name: ");
    String teamName = mReader.readLine();
    System.out.print("What is the coach name: ");
    String coachName = mReader.readLine();
    return new Team(teamName, coachName);
  }
  
  public void run(){
    String choice = "";
    do {
      try {
        choice = promptAction();
        switch(choice){
          case "create":
              // if number of teams is > than # of players, give message and exit create
            if(mTeams.getTeamCount() >= players.length){
              System.out.printf("Number of teams cannot exceed the number of players. %n");
              break;
            }   
            mTeam = promptNewTeam();
            mTeams.addTeam(mTeam);
            System.out.printf("The team %s has been created with %s as coach. %n%n", mTeam.getTeamName(), mTeam.getCoachName());
            break;
          case "add":
            // print list of teams and user chooses team
            mTeam = mTeams.getTeamList();
            // prints players and selects user choice
            Arrays.sort(players);
            if(playersLists == null){
              playersLists = new ArrayList<Player>(Arrays.asList(players));
            }
            index = promptPlayer(playersLists);
            mTeam.addPlayer(playersLists.get(index));
            System.out.printf("%s %s has been added to %s %n%n", playersLists.get(index).getFirstName(), playersLists.get(index).getLastName(), mTeam.getTeamName());
            // remove player from list playersLists
            playersLists.remove(index);
            break;
          case "remove":
            // print list of mTeams, user chooses team
            mTeam = mTeams.getTeamList();            
            // get players on mTeam
            Player playerToRemove = promptTeamPlayer(mTeam);
            mTeam.removePlayer(playerToRemove);
            // Add player back to playersLists
            playersLists.add(playerToRemove);
            Collections.sort(playersLists);
            System.out.printf("%s %s has been removed from %s %n%n", playerToRemove.getFirstName(), playerToRemove.getLastName(), mTeam.getTeamName());
            break;
          case "report":
            // choose team
            mTeam = mTeams.getTeamList();
            // new method to get players on team
            teamHeight(mTeam);
            // print heights by ranges            
            break;
          case "balance":
            // experienced vs inexperienced
            mTeam = mTeams.getTeamList();
            teamExperience(mTeam);
            break;
          case "roster":
            getRoster(mTeams);
            break;
          case "quit":
            break;
          default:
            System.out.printf("Unknown choice: '%s'. Try again %n%n%n", choice);          
        }
      } catch(IOException ioe){
        System.out.println("Problem with input");
        ioe.printStackTrace();
        }
     } while(!choice.equals("quit"));  
   }
  
  private int promptPlayer(List<Player> playersLists) throws IOException {
    // prints list of available players
    count = 1;
    
    System.out.println("List of players by height: ");
    
    // prints each name and each height in following format: Matt Gill (40 inches - inexperienced)
    for(Player playerList : playersLists){
      boolean experience = playerList.isPreviousExperience();
      
      System.out.println(count + ".) "+playerList.getFirstName() + " " + playerList.getLastName() + "(" + playerList.getHeightInInches() + " inches - " + translateExperience(experience) + ")");
      
      count++;
    }
    System.out.printf("Choose player number for team %s: %n", mTeam.getTeamName());
    String newPlayerAsString = mReader.readLine();
    int newPlayer = Integer.parseInt(newPlayerAsString.trim());
    System.out.printf("%n");
    return newPlayer - 1;
  }
  
  // list players on current team
  public void getListOfPlayers(Team mTeam) throws IOException{
      count = 1;
    Set<Player> teamPlayers = mTeam.getPlayers();
    
    for(Player player: teamPlayers){
      boolean experience = player.isPreviousExperience();
      
      System.out.println(count + ".) "+player.getFirstName() + " " + player.getLastName() + "(" + player.getHeightInInches() + " inches - " + translateExperience(experience) + ")");
      
      count++;
    }
    System.out.printf("%n");
  }
  
  // choose player for removal from team
  public Player promptTeamPlayer(Team mTeam) throws IOException{
    Set<Player> playersSets = mTeam.getPlayers();   
    getListOfPlayers(mTeam);
    
    System.out.printf("Choose player for removal from team: %n");
    String newPlayerAsString = mReader.readLine();
    int newPlayer = Integer.parseInt(newPlayerAsString.trim());
    newPlayer = newPlayer - 1;
    
    return new ArrayList<>(playersSets).get(newPlayer);
  }
  
    public String translateExperience(boolean experience){
    if(experience == true){
      return "experienced";
    }
    else{
      return "inexperienced";
    }
  }
    
    public void teamHeight(Team mTeam){
      // get list of players
      // iterate through players, if height between this and this, put in group 1,2, or 3
      // print group 1, then 2 then 3
      Set<Player> playersSets = mTeam.getPlayers();
      Map<Integer, String> teamHeight1=new HashMap<>();
      Map<Integer, String> teamHeight2=new HashMap<>();
      Map<Integer, String> teamHeight3=new HashMap<>();
      
      int count1 = 0;
      int count2 = 0;
      int count3 = 0;
      
      for(Player playerSet: playersSets){
        if(playerSet.getHeightInInches()>=35 && playerSet.getHeightInInches()<=40){
          count1++;
          teamHeight1.put(count1, playerSet.getName());
        }
        if(playerSet.getHeightInInches()>=41 && playerSet.getHeightInInches()<=46){
          count2++;
          teamHeight2.put(count2, playerSet.getName());
        }
        if(playerSet.getHeightInInches()>=47 && playerSet.getHeightInInches()<=50){
          count3++;
          teamHeight3.put(count3, playerSet.getName());
        }
      }
      
      Collection<String> teamHeight1Values = teamHeight1.values();
      System.out.printf("35-40 inches: %n");
      for(String playerString1: teamHeight1Values){
        System.out.printf(playerString1 + "%n");
      }
      Collection<String> teamHeight2Values = teamHeight2.values();
      System.out.printf("%n41-46 inches: %n");
      for(String playerString2: teamHeight2Values){
        System.out.printf(playerString2 + "%n");
      }
      Collection<String> teamHeight3Values = teamHeight3.values();
      System.out.printf("%n47-50 inches: %n");
      for(String playerString3: teamHeight3Values){
        System.out.printf(playerString3 + "%n");
      }
      
        System.out.printf("%n%s are 35-40 inches %n", count1);
        System.out.printf("%s are 41-46 inches %n", count2);
        System.out.printf("%s are 47-50 inches %n %n", count3);      
    }
  
    public void teamExperience(Team mTeam){
      Set<Player> playersSets = mTeam.getPlayers();
      Map<Integer, String> experienced=new HashMap<>();
      Map<Integer, String> inexperienced=new HashMap<>();
      int countExperienced = 0;
      int countInexperienced = 0;
      
      for(Player playerSet: playersSets){
        if (playerSet.isPreviousExperience()==true){
          countExperienced++;
          experienced.put(countExperienced, playerSet.getName());
        }
        if(playerSet.isPreviousExperience()==false){
          countInexperienced++;
          inexperienced.put(countInexperienced, playerSet.getName());
        }
      }
      
      Collection<String> experiencedNames = experienced.values();
      System.out.printf("Experienced: %n");
      for(String experiencedName : experiencedNames){
        System.out.printf(experiencedName + "%n");
      }
      System.out.printf("%n%s players are experienced.%n%n", countExperienced);
      
      Collection<String> inexperiencedNames = inexperienced.values();
      for(String inexperiencedName: inexperiencedNames){
        System.out.printf(inexperiencedName + "%n");
      }
      System.out.printf("%n%s players are inexperienced.%n%n", countInexperienced);
      //calculate percentage of experienced
      int total = countExperienced+countInexperienced;
      float percentageExperienced = (countExperienced*100.0f/total);
      System.out.printf(Math.round(percentageExperienced) + "%% of the players are experienced.%n%n", percentageExperienced);
    }
  
   public void getRoster (Teams mTeams) throws IOException{
      //iterate through all teams
      // add players from each team to own ArrayList
      // print each player
      Set<Player> playersSets;
     
      for(Team mTeam: mTeams.getTeams()){
        System.out.printf("Team %s coached by %s - ", mTeam.getTeamName(), mTeam.getCoachName());
        
        playersSets = mTeam.getPlayers();
        System.out.printf(playersSets + " %n");
        
      }
   } 
}