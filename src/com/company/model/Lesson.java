package com.company.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Lesson implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    List<Pair<String,String>> wordList = new LinkedList<>();

    public Lesson() {}

    public void addWord(String word1, String word2) {
        wordList.add(new Pair<>(word1, word2));
    }

    public void removeWord(String word1, String word2) {
        wordList.remove(new Pair<>(word1, word2));
    }

    public List<Pair<String,String>> getWordList() {
        return wordList;
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
