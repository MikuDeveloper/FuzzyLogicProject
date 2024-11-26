package ttn.developer.fuzzylogicproject;

import net.sourceforge.jFuzzyLogic.FIS;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class FuzzyLogicModel {
    private final ArrayList<Question> questions;
    private final Iterator<Question> iterator;

    HashMap<String, Integer> answerToQuestion1 = new HashMap<>();

    HashMap<String, Integer> answerToQuestion2 = new HashMap<>();
    HashMap<String, Integer> answerToQuestion3 = new HashMap<>();
    HashMap<String, Integer> answerToQuestion4 = new HashMap<>();
    HashMap<String, Integer> answerToQuestion5 = new HashMap<>();
    HashMap<String, Integer> answerToQuestion6 = new HashMap<>();
    HashMap<String, Integer> answerToQuestion7 = new HashMap<>();
    HashMap<String, Integer> answerToQuestion8 = new HashMap<>();
    HashMap<String, Integer> answerToQuestion9 = new HashMap<>();
    HashMap<String, Integer> answerToQuestion10 = new HashMap<>();
    HashMap<String, Integer> answerToQuestion11 = new HashMap<>();

    public FuzzyLogicModel() {
        questions = new ArrayList<>();
        fillAnswers();
        setQuestions();
        iterator = questions.iterator();
    }

    public int getAnswerValue(int id, String answer) {
        return this.questions
            .get(id)
            .answers()
            .get(answer);
    }

    public Question nextQuestion() {
        return iterator.hasNext() ? iterator.next() : null;
    }

    public boolean hasNextQuestion() {
        return iterator.hasNext();
    }

    private void fillAnswers() {
        answerToQuestion1.put("Sí, claro", 0);
        answerToQuestion1.put("Por supuesto", 0);
        answerToQuestion1.put("Adelante", 0);

        answerToQuestion2.put("Sin empleo fijo", 0);
        answerToQuestion2.put("Autónomo/Empresario", 50);
        answerToQuestion2.put("Empleado", 75);

        answerToQuestion3.put("Menos de 6 meses", 0);
        answerToQuestion3.put("Entre 6 meses y dos años", 50);
        answerToQuestion3.put("Más de 2 años", 75);

        answerToQuestion4.put("Menos de $8,000", 25);
        answerToQuestion4.put("Entre $8,000 y $15,000", 50);
        answerToQuestion4.put("Más de $15,000", 75);

        answerToQuestion5.put("Sí, es malo", 0);
        answerToQuestion5.put("No tengo historial", 30);
        answerToQuestion5.put("Sí, es bueno", 90);

        answerToQuestion6.put("Más de $10,000", 25);
        answerToQuestion6.put("Entre $5,000 y $10,000", 50);
        answerToQuestion6.put("Menos de $5,000", 75);

        answerToQuestion7.put("Emergencias", 25);
        answerToQuestion7.put("Negocio", 50);
        answerToQuestion7.put("Casa o auto", 75);

        answerToQuestion8.put("No tengo garantías", 25);
        answerToQuestion8.put("Ahorros/Inversiones", 50);
        answerToQuestion8.put("Propiedades/Pertenencias", 75);

        answerToQuestion9.put("Menos del 10%", 25);
        answerToQuestion9.put("Entre el 10% y el 20%", 50);
        answerToQuestion9.put("Más del 20%", 75);

        answerToQuestion10.put("Ninguno", 80);
        answerToQuestion10.put("Uno", 45);
        answerToQuestion10.put("Dos o más", 20);

        answerToQuestion11.put("No tengo ahorros", 0);
        answerToQuestion11.put("Entre $5,000 y $20,000", 45);
        answerToQuestion11.put("Más de $20,000", 80);
    }

    private void setQuestions() {
        questions.add(new Question(1, "¿Está listo para comenzar?", answerToQuestion1));

        questions.add(new Question(2, "¿En qué trabajas o cuál es tu ocupación", answerToQuestion2));
        questions.add(new Question(3, "¿Cuál es tu antigüedad laboral actual en años?", answerToQuestion3));
        questions.add(new Question(4, "¿Cuáles son tus ingresos mensuales netos?", answerToQuestion4));
        questions.add(new Question(5, "¿Tienes historial crediticio?", answerToQuestion5));
        questions.add(new Question(6, "¿Tienes alguna deuda pendiente?", answerToQuestion6));
        questions.add(new Question(7, "¿Cuál es el propósito del crédito?", answerToQuestion7));
        questions.add(new Question(8, "¿Puedes ofrecer alguna garantía para el crédito?", answerToQuestion8));
        questions.add(new Question(9, "¿Qué porcentaje de tus ingresos puedes destinar al pago?", answerToQuestion9));
        questions.add(new Question(10, "¿Tienes dependientes económicos?", answerToQuestion10));
        questions.add(new Question(11, "¿Tienes ahorros actualmente?", answerToQuestion11));
    }

    public static int evaluate(int score, int incomeAmount) {
        InputStream filename;
        try {
            filename = Objects.requireNonNull(FuzzyLogicModel.class.getResource("credit.fcl")).openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FIS fis = FIS.load(filename,true);

        if (fis == null) {
            System.out.println("No se pudo cargar el archivo FCL.");
            return -1;
        }

        fis.setVariable("puntuacion_credito", score);
        fis.setVariable("ingreso_mensual", incomeAmount);

        fis.evaluate();
        return (int) Math.floor(fis.getVariable("credito_otorgado").getValue());
    }
}
