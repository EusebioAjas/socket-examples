package com.company;


import com.google.gson.Gson;

import java.io.*;

public class Main {

  static class Student {
    String name;
    int age;

    public Student(String name, int age) {
      this.name = name;
      this.age = age;
    }

    @Override
    public String toString() {
      return "name: " + name + " age: " + age;
    }
  }

  public static void main(String[] args) {
    Student st = new Student("Mike", 26);
    Gson gson = new Gson();
    String json = gson.toJson(st);

    //Write JSON to file
    try {
      FileWriter fileWriter = new FileWriter("src/com/company/student.json");
      fileWriter.write(json);
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println(json);
    /*Gson gson = new Gson();
    String jsonFile = "src/com/company/student.json";
    try (BufferedReader br = new BufferedReader(new FileReader(jsonFile))) {
      Student student = gson.fromJson(br, Student.class);
      System.out.println(student);
    } catch (IOException e) {
      e.printStackTrace();
    }*/

  }


}
