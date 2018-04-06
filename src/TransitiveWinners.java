import java.io.File;
import java.io.IOException;
import java.util.*;

public class TransitiveWinners {
    private Scanner key;
    private Set<String> teamStrings = new HashSet<>();
    private Team[] teamList = new Team[1362];
    private Set<Team> transitiveWinners = new HashSet<>();

    public static void main(String[] args) throws IOException {
        new TransitiveWinners();
    }

    private TransitiveWinners() throws IOException {
        key = new Scanner(new File("info.txt"));
        String line, winner, loser;
        for (int i = 0; i < 16445; i++) {
            line = key.nextLine();
            winner = line.substring(12, 36).trim();
            loser = line.substring(41, 65).trim();
            teamStrings.add(winner);
            teamStrings.add(loser);
        }
        System.out.println(teamStrings.size());

        int iterator = 0;
        for (String s : teamStrings) {
            teamList[iterator] = new Team(s);
            iterator++;
        }

        key = new Scanner(new File("info.txt"));
        Team better, lesser;
        for (int i = 0; i < 16445; i++) {
            line = key.nextLine();
            //System.out.println(line);
            winner = line.substring(12, 36).trim();
            loser = line.substring(41, 65).trim();

            better = getTeam(winner);
            lesser = getTeam(loser);
            lesser.beatMe.add(better);
        }

        getWinners(getTeam("Villanova"));
        System.out.println(transitiveWinners.size());
    }

    private void getWinners(Team winner) {
        transitiveWinners.add(winner);
        for (Team t : winner.beatMe) {
            if (!transitiveWinners.contains(t))
                getWinners(t);
        }
    }

    private Team getTeam(String s) {
        for (Team t : teamList) {
            if (t.name.equals(s))
                return t;
        }
        return null;
    }

    private class Team {
        final String name;
        final Set<Team> beatMe = new HashSet<>();

        Team(String n) {
            name = n;
        }

    }

}
