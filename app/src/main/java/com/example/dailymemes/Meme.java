package com.example.dailymemes;

public class Meme {
    // stores meme url
    private String meme;
    //default constrator
    public Meme(){}
    //parametric constructor
    public Meme(String meme) {
        this.meme = meme;
    }
    //setter
    public void setMeme(String meme) {
        this.meme = meme;
    }
    //getter
    public String getMeme() {
        return meme;
    }
}
