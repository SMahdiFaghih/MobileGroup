package com.example.project;

public class Player
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
