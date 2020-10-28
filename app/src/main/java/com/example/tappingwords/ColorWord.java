package com.example.tappingwords;

import java.util.ArrayList;
import java.util.Random;

public class ColorWord {
    public ArrayList<String> colorList;
    public ArrayList<String> wordList;

    public ArrayList<String> getColorList() {
        return colorList;
    }

    public void setColorList(ArrayList<String> colorList) {
        this.colorList = colorList;
    }

    public ArrayList<String> getWordList() {
        return wordList;
    }

    public void setWordList(ArrayList<String> wordList) {
        this.wordList = wordList;
    }

    public String generateColor() {
        Random random = new Random();
        return colorList.get(random.nextInt(colorList.size()));
    }

    public String generateWord() {
        Random random = new Random();
        return wordList.get(random.nextInt(wordList.size()));
    }
}
