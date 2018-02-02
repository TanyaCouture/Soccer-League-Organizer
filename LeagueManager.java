import com.teamtreehouse.LeagueView;
import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;
import com.teamtreehouse.model.Teams;

public class LeagueManager {

  public static void main(String[] args) {
    Player[] players = Players.load();
    System.out.printf("There are currently %d registered players.%n%n", players.length);

    LeagueView view = new LeagueView(players);
    view.run();
  }

}
