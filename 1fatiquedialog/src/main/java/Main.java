import java.util.Scanner;

public class Main {

    final static String ROBOT_NAME = "Robot";
    static String previousPhrase = "";

    public static String[] answers = {
            "I`m not sure",
            "I don't remember",
            "Who knows...",
            "Well...it's hard to say",
            "Sorry, but let's change theme. I don`t like this one",
            "At least I think so",
            "Exactly",
            "That's for sure",
            "I agree with you.",
            "I will not answer that question"
    };


    public static String[] generalQuestions = {
            "So, whas'up?",
            "How are you doing?",
            "What do you mean?",
            "Have you ever play basketball?",
            "Where you travel?",
            "Do you like your city?",
            "Do you really think so?",
            "What do you think about that?",
            "Where you wanna work?",
            "Is it true?",
            "What dreams you have, when you was small?",
            "You know something new about Intelligent Systems?",
            "You know something new about GameDev?",
            "So, what`s now?",
            "Tell me something more?"
    };

    private static String answerGeneral(String token) {
        double rand = Math.random();

        if (rand > 0.5) {
            return "Yes, I " + token;
        } else {
            return "No, I " + token + " not";
        }
    }

    public static String[] generalAnswer = {
            "Yes, I ",
            "Nope, I do not "
    };

    public static String[] aboutMeAnswer = {
            "I'am fine, don't worry about me"
    };

    public static String[] whenAnswers = {
            "I don't remember exact time...",
            "Around the evening",
            "At 12 a.m.",
    };

    //    public static String[] yesNoQuestions = {
//            "did",
//            "do",
//            "will",
//            "have",
//            "had",
//            "would"
//    };
    public static String[] whyAnswers = {
            "I really don`t know. Maybe, it`s magic.",
            "I don't know exactly, but what do you think?",
            "God knows.",
            "Man, so hard questions, maybe something else?",
            "I don't wanna answer you"
    };


    public static void Say(String name, String sentence) {
        System.out.println(name + ": " + sentence);
        previousPhrase = sentence;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Say(ROBOT_NAME, "Hello, my name is Alex. How is it going?");
        String message = "";

        do {
            System.out.print("User: ");
            message = input.nextLine();
            message = message.toLowerCase();
            if (message.contains("bye")){
                break;
            }
            if (message.contains("?")) {
                if (message.startsWith("do") || message.startsWith("did") || message.startsWith("will") ||
                        message.startsWith("would") || message.startsWith("have") || message.startsWith("had")) {
                    Say(ROBOT_NAME, answerGeneral(message.split(" ")[0]));
                } else if (message.contains("how")) {
                    if (message.contains("you")) {
                        Say(ROBOT_NAME, getRandomPhraseFrom(aboutMeAnswer));
                    } else {
                        Say(ROBOT_NAME, getRandomPhraseFrom(whyAnswers));
                    }
                } else if (message.contains("why")) {
                    Say(ROBOT_NAME, getRandomPhraseFrom(whyAnswers));
                } else if (message.contains("when")) {
                    Say(ROBOT_NAME, getRandomPhraseFrom(whenAnswers));
                } else {
                    Say(ROBOT_NAME, getRandomPhraseFrom(answers));
                }
            } else {
                askGeneralQuestion();
            }
        }while (true);
        System.out.println("Ciao");
    }


    private static void askGeneralQuestion() {
        Say(ROBOT_NAME, getRandomPhraseFrom(generalQuestions));
    }

    private static String returnAnswerByKeywords(String[] keyWords) {
        return "";
    }

    private static String getRandomPhraseFrom(String[] phrases) {
        String phrase;
        do {
            phrase = phrases[(int) (Math.random() * phrases.length)];
        } while (phrase.equals(previousPhrase));

        return phrase;
    }
}
