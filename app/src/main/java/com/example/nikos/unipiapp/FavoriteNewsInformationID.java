package com.example.nikos.unipiapp;

import java.util.ArrayList;

public class FavoriteNewsInformationID {
    private ArrayList<FavoriteNewsInformation> FavoriteNewsInformation;

    public FavoriteNewsInformationID() {
    }

    public FavoriteNewsInformationID(ArrayList<com.example.nikos.unipiapp.FavoriteNewsInformation> favoriteNewsInformation) {
        FavoriteNewsInformation = favoriteNewsInformation;
    }

    public ArrayList<com.example.nikos.unipiapp.FavoriteNewsInformation> getFavoriteNewsInformation() {
        return FavoriteNewsInformation;
    }

    public void setFavoriteNewsInformation(ArrayList<com.example.nikos.unipiapp.FavoriteNewsInformation> favoriteNewsInformation) {
        FavoriteNewsInformation = favoriteNewsInformation;
    }
}
