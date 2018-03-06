package minigames.util;

public class SimpleScoreBoard {

    /*private Scoreboard scoreboard;

    private String title;
    private Map<String, Integer> scores;
    private List<Team> teams;

    public SimpleScoreBoard(String title) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.title = title;
        this.scores = Maps.newLinkedHashMap();
        this.teams = Lists.newArrayList();
    }

    public void blankLine() {
        add(" ");
    }

    public void add(String text) {
        add(text, null);
    }

    public void add(String text, Integer score) {
        Preconditions.checkArgument(text.length() < 48, "text cannot be over 48 characters in length");
        text = fixDuplicates(text);
        scores.put(text, score);
    }

    private String fixDuplicates(String text) {
        while (scores.containsKey(text))
            text += "§r";
        if (text.length() > 48)
            text = text.substring(0, 47);
        return text;
    }

    private Map.Entry<Team, String> createTeam(String text) {
        String result = "";
        if (text.length() <= 16)
            return new AbstractMap.SimpleEntry<>(null, text);
        Team team = scoreboard.registerNewTeam("text-" + scoreboard.getTeams().size());
        Iterator<String> iterator = Splitter.fixedLength(16).split(text).iterator();
        team.setPrefix(iterator.next());
        result = iterator.next();
        if (text.length() > 32)
            team.setSuffix(iterator.next());
        teams.add(team);
        return new AbstractMap.SimpleEntry<>(team, result);
    }

    public void build() {
        Objective obj = scoreboard.registerNewObjective((title.length() > 16 ? title.substring(0, 15) : title), "dummy");
        obj.setDisplayName(title);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        int index = scores.size();

        for (Map.Entry<String, Integer> text : scores.entrySet()) {
            Map.Entry<Team, String> team = createTeam(text.getKey());
            Integer score = text.getValue() != null ? text.getValue() : index;
            OfflinePlayer player = Bukkit.getOfflinePlayer(team.getValue());
            if (team.getKey() != null)
                team.getKey().addPlayer(player);
            obj.getScore(player).setScore(score);
            index -= 1;
        }
    }

    public void reset() {
        title = null;
        scores.clear();
        for (Team t : teams)
            t.unregister();
        teams.clear();
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void send(Player... players) {
        for (Player p : players)
            p.setScoreboard(scoreboard);
    }*/

}