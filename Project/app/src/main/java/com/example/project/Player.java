package com.example.project;

public class Player
{
    private String username;
    private String wins;
    private String draws;
    private String loses;

    public Player(String username, String wins, String draws, String loses)
    {
        this.username = username;
        this.wins = wins;
        this.draws = draws;
        this.loses = loses;
    }

    public String getUsername() {
        return username;
    }

    public String getWins() {
        return wins;
    }

    public String getDraws() {
        return draws;
    }

    public String getLoses() {
        return loses;
    }
}
