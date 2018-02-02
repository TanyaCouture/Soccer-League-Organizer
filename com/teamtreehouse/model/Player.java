package com.teamtreehouse.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Player implements Comparable<Player>, Serializable {
  private static final long serialVersionUID = 1L;

  private String firstName;
  private String lastName;
  private int heightInInches;
  private boolean previousExperience;
  private Map<Integer, Player> teamHeight;

  public Player(String firstName, String lastName, int heightInInches, boolean previousExperience) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.heightInInches = heightInInches;
    this.previousExperience = previousExperience;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }
  
  public String getName(){
    return firstName + " " + lastName;
  }

  public int getHeightInInches() {
    return heightInInches;
  }

  public boolean isPreviousExperience() {
    return previousExperience;
  }
  
  @Override
  public int compareTo(Player other) {
    // We always want to sort by last name then first name
    // sort last name
    // if last names are the same, sort by the first name
    int compareName = this.lastName.toLowerCase().compareTo(other.lastName.toLowerCase());
    
    if(compareName == 0){
      compareName = this.firstName.toLowerCase().compareTo(other.firstName.toLowerCase());
    }
    return compareName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Player)) return false;

    Player player = (Player) o;

    if (heightInInches != player.heightInInches) return false;
    if (previousExperience != player.previousExperience) return false;
    if (!firstName.equals(player.firstName)) return false;
    return lastName.equals(player.lastName);

  }

  @Override
  public int hashCode() {
    int result = firstName.hashCode();
    result = 31 * result + lastName.hashCode();
    result = 31 * result + heightInInches;
    result = 31 * result + (previousExperience ? 1 : 0);
    return result;
  }
  
  @Override
  public String toString(){
    String experienceString;
    if(this.previousExperience == true){
      experienceString="experienced";
    }
    else{
      experienceString = "inexperienced";
    }
  return String.format(this.firstName + " " + this.lastName + "(" + this.heightInInches + " inches - " + experienceString + ")");
  }
}
