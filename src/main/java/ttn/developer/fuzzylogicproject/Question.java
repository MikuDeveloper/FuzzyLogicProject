package ttn.developer.fuzzylogicproject;

import java.util.HashMap;

public record Question(int id, String question, HashMap<String, Integer> answers) {  }
