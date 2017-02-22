package com.ambeyindustry.pokedox;

public class Pokemon {
    private String pRESOURCE_URI;
    private String pIMAGE_URI;
    private String pNAME;
    private int pHP;
    private int pEXP;
    private String pTYPES[];
    private int pHEIGHT;
    private int pWEIGHT;
    private int pATTACK;
    private int pDEFENSE;
    private int pSPEED;
    private String pMOVES[];
    private String pABILITIES[];
    private Pokemon pEVOLUTIONS[];

    public Pokemon(String uri, String name) {
        pRESOURCE_URI = uri;
        String str[] = uri.split("/");
        pIMAGE_URI = "http://pokeapi.co/media/img/" + str[str.length - 1] + ".png";
        pNAME = name;
    }

    public Pokemon(String uri, String name, int hp, int exp, String types[], int height, int weight,
                   int attack, int defense, int speed, String moves[], String abilities[], Pokemon evolutions[]) {
        pRESOURCE_URI = uri;
        String str[] = uri.split("/");
        pIMAGE_URI = "http://pokeapi.co/media/img/" + str[str.length - 1] + ".png";
        pNAME = name;
        pHP = hp;
        pEXP = exp;
        pTYPES = types;
        pHEIGHT = height;
        pWEIGHT = weight;
        pATTACK = attack;
        pDEFENSE = defense;
        pSPEED = speed;
        pMOVES = moves;
        pABILITIES = abilities;
        pEVOLUTIONS = evolutions;
    }

    public String getName() {
        return pNAME;
    }

    public int getHp() {
        return pHP;
    }

    public int getExp() {
        return pEXP;
    }

    public String[] getType() {
        return pTYPES;
    }

    public int getHeight() {
        return pHEIGHT;
    }

    public int getWeight() {
        return pWEIGHT;
    }

    public int getAttack() {
        return pATTACK;
    }

    public int getDefense() {
        return pDEFENSE;
    }

    public int getSpeed() {
        return pSPEED;
    }

    public String[] getMoves() {
        return pMOVES;
    }

    public String[] getAbilities() {
        return pABILITIES;
    }

    public Pokemon[] getEvolutions() {
        return pEVOLUTIONS;
    }

    public String getImageURI() {
        return pIMAGE_URI;
    }
}
