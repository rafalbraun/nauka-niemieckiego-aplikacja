package com.company.model;

import java.util.Collection;
import java.util.LinkedList;

public class Data {

    String filename;
    Collection<Lesson> lessons = new LinkedList<>();
    Lesson chosenLesson;

    public Data() {}

    public Collection<Lesson> getLessons() {
        return lessons;
    }

    public void createLesson(String lessonName) {
        lessons.add(new Lesson(lessonName));
    }

    public Lesson getChosenLesson() { return chosenLesson; }

    public void setChosenLesson(Lesson lesson) { this.chosenLesson = lesson; }

/*
    public void saveData() {
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write("...");
            writer.close();
            System.out.println("Success");
        } catch (IOException ex) {
            System.out.println("ERROR");
            ex.printStackTrace();
        }
    }

    public void readData() {
        try {
            File file = new File(filename);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                System.out.println(data);
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR");
            ex.printStackTrace();
        }
    }
*/


}
