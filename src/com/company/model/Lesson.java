package com.company.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class Lesson implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    List<Pair<String,String>> wordList = new LinkedList<>();

    public Lesson() {}

    public void addWord(String wordDE, String wordPL) {
        wordList.add(new Pair<>(wordDE, wordPL));
    }

    public void removeWord(String wordDE, String wordPL) {
        wordList.remove(new Pair<>(wordDE, wordPL));
    }

    public List<Pair<String,String>> getWordList() {
        return wordList;
    }

    public void setWordList(Vector<Vector> wordList) {
        this.wordList.clear();
        for(Vector item: wordList) {
            this.wordList.add(new Pair<>((String)item.get(0), (String)item.get(1)));
        }
    }

    public String[][] getWords() {
        // Create a 2D String array with the same size as the list
        String[][] result = new String[wordList.size()][2];

        // Iterate over the list and populate the array
        for (int i = 0; i < wordList.size(); i++) {
            Pair<String, String> pair = wordList.get(i);
            result[i][0] = pair.getFirst();
            result[i][1] = pair.getSecond();
        }

        return result;
    }
}
