package com.example.project;

public class Player implements Comparable
{
    private String username;
    private int wins;
    private int draws;
    private int loses;

    public Player(String username, int wins, int draws, int loses)
    {
        this.username = username;
        this.wins = wins;
        this.draws = draws;
        this.loses = loses;
    }

    @Override
    public int compareTo(Object o)
    {
        Player p = (Player) o;
        int winsCompareResult = Integer.compare(p.wins, this.wins);
        if (winsCompareResult == 0)
        {
            int drawsCompareResult = Integer.compare(p.draws, this.draws);
            if (drawsCompareResult == 0)
            {
                int losesCompareResult = Integer.compare(p.loses, this.loses);
                if (losesCompareResult == 0)
                {
                    return this.username.compareTo(p.getUsername());
                }
                return -1 * losesCompareResult;
            }
            return drawsCompareResult;
        }
        return winsCompareResult;
    }

    public String getUsername() {
        return username;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws()
    {
        return draws;
    }

    public int getLoses() {
        return loses;
    }
}
