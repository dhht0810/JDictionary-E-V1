package controller.QuizGame;

import com.example.jdictionaryev1.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import module.SQLite.SQLiteConnection;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class QuizGameController extends SQLiteConnection {
    @FXML
    public Stage stage;
    @FXML
    public Scene scene;
    public Parent root;
    @FXML
    public Label question;

    @FXML
    public Button opt1;
    public Button opt2;
    public Button opt3;
    public Button opt4;
   public Button outGame;


    static int counter = 0;
    static int correct = 0;
    static int wrong = 0;
    static Boolean[] asked = new Boolean[11];

    int getRandom() {
        Random rand = new Random();
        int ans = rand.nextInt(1,11);
        while (asked[ans] && Arrays.asList(asked).contains(false)) {
            ans = rand.nextInt(1,11);
        }
        return ans;
    }

    int randomId;
    @FXML
    private void initialize() {
        Arrays.fill(asked,false);
        loadQuestions();
    }



    private void loadQuestions() {
        SQLiteConnection sqLiteConnection1 = new SQLiteConnection();
        sqLiteConnection1.setConnection(dbName);
        try {
            String query = "SELECT id, description, choice1, choice2, choice3, choice4 FROM question WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            randomId = getRandom();
            asked[randomId] = true;
            preparedStatement.setInt(1, randomId); // Assuming your questions start from 1
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                question.setText(resultSet.getString("description"));
                opt1.setText(resultSet.getString("choice1"));
                opt2.setText(resultSet.getString("choice2"));
                opt3.setText(resultSet.getString("choice3"));
                opt4.setText(resultSet.getString("choice4"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkAnswer(String answer) {
        try {
            SQLiteConnection sqLiteConnection2 = new SQLiteConnection();
            sqLiteConnection2.setConnection(dbName);
            String query = "SELECT answer FROM question WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, randomId); // Assuming your questions start from 1
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String correctAnswer = resultSet.getString("answer");
                return answer.equals(correctAnswer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Default to false in case of any error
    }

   /* private void loadQuestions() {

        if (counter == 0) { //Question 1
            question.setText("1. How many consonants are there in the English alphabet?");
            opt1.setText("19");
            opt2.setText("20");
            opt3.setText("21");
            opt4.setText("22");
        }
        if (counter == 1) { //Question 2
            question.setText("2. Who invented the Light bulb?");
            opt1.setText("Thomas Alva Edison");
            opt2.setText("Alexander Fleming");
            opt3.setText("Charles Babbage");
            opt4.setText("Albert Einstein");
        }
        if (counter == 2) { //Question 3
            question.setText("3. In the Solar System, farthest planet from the Sun is");
            opt1.setText("Jupiter");
            opt2.setText("Saturn");
            opt3.setText("Uranus");
            opt4.setText("Neptune");
        }
        if (counter == 3) { //Question 4
            question.setText("4. Largest moon in the Solar System?");
            opt1.setText("Titan");
            opt2.setText("Ganymede");
            opt3.setText("Moon");
            opt4.setText("Europa");
        }
        if (counter == 4) {//Question 5
            question.setText("5. Which of these is 'not' a property of metal?");
            opt1.setText("Good Conduction");
            opt2.setText("Malleable");
            opt3.setText("Non Ductile");
            opt4.setText("Sonourous");
        }
        if (counter == 5) { //Question 6
            question.setText("6. Who discovered Pasteurisation?");
            opt1.setText("Alexander Fleming");
            opt2.setText("Louis Pasteur");
            opt3.setText("Simon Pasteur");
            opt4.setText("William Pasteur");
        }
        if (counter == 6) { //Question 7
            question.setText("7. Hydrochloric acid (HCl) is produced by ?");
            opt1.setText("Small Intestine");
            opt2.setText("Liver");
            opt3.setText("Oesophagus");
            opt4.setText("Stomach");
        }
        if (counter == 7) { //Question 8
            question.setText("8. The fastest animal in the world is ?");
            opt1.setText("Lion");
            opt2.setText("Blackbuck");
            opt3.setText("Cheetah");
            opt4.setText("Quarter Horse");
        }
        if (counter == 8) { //Question 9
            question.setText("9. Complementary colour of Red is ?");
            opt1.setText("Blue");
            opt2.setText("Green");
            opt3.setText("Yellow");
            opt4.setText("Pink");
        }
        if (counter == 9) { //Question 10
            question.setText("10. World Environment Day is on ?");
            opt1.setText("5th June");
            opt2.setText("5th July");
            opt3.setText("15th June");
            opt4.setText("25th June");
        }

    }
    boolean checkAnswer(String answer) {

        if (counter == 0) {
            if (answer.equals("21")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 1) {
            if (answer.equals("Thomas Alva Edison")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 2) {
            if (answer.equals("Neptune")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 3) {
            if (answer.equals("Ganymede")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 4) {
            if (answer.equals("Non Ductile")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 5) {
            if (answer.equals("Louis Pasteur")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 6) {
            if (answer.equals("Stomach")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 7) {
            if (answer.equals("Cheetah")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 8) {
            if (answer.equals("Green")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 9) {
            if (answer.equals("5th June")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }*/

    @FXML
    public void opt1clicked(ActionEvent event) {
        checkAnswer(opt1.getText().toString());
        if (checkAnswer(opt1.getText().toString())) {
            correct = correct + 1;
        } else {
            wrong = wrong + 1;
        }
        if (counter == 9) {
            try {
//                System.out.println(correct);
//                Stage thisstage = (Stage) ((Button) event.getSource()).getScene().getWindow();
//                thisstage.close();
//                FXMLLoader quiz = new FXMLLoader(getClass().getResource("result.fxml"));
//                Scene quizscene = new Scene(quiz.load());
//                quizscene.setFill(Color.TRANSPARENT);
//                Stage quizstage = new Stage();
//                quizstage.setScene(quizscene);
//                quizstage.initStyle(StageStyle.TRANSPARENT);
//                quizstage.show();
                counter = 0; // reset game
                HelloApplication helloApplication = new HelloApplication();
                helloApplication.changeScreen("Result.fxml",700,700);
            } catch(IOException e) {
                e.printStackTrace();
            }
        } else {
            counter++;
            loadQuestions();
        }

    }
    @FXML
    public void opt2clicked(ActionEvent event) {
        checkAnswer(opt2.getText().toString());
        if (checkAnswer(opt2.getText().toString())) {
            correct = correct + 1;
        } else {
            wrong = wrong + 1;
        }
        if (counter == 9) {
            try {
                //                System.out.println(correct);
//                Stage thisstage = (Stage) ((Button) event.getSource()).getScene().getWindow();
//                thisstage.close();
//                FXMLLoader quiz = new FXMLLoader(getClass().getResource("result.fxml"));
//                Scene quizscene = new Scene(quiz.load());
//                quizscene.setFill(Color.TRANSPARENT);
//                Stage quizstage = new Stage();
//                quizstage.setScene(quizscene);
//                quizstage.initStyle(StageStyle.TRANSPARENT);
//                quizstage.show();
                counter = 0; // reset game
                HelloApplication helloApplication = new HelloApplication();
                helloApplication.changeScreen("Result.fxml",700,700);
            } catch(IOException e) {
                e.printStackTrace();
            }
        } else {
            counter++;
            loadQuestions();
        }
    }

    @FXML
    public void opt3clicked(ActionEvent event) {
        checkAnswer(opt3.getText().toString());
        if (checkAnswer(opt3.getText().toString())) {
            correct = correct + 1;
        } else {
            wrong = wrong + 1;
        }
        if (counter == 9) {
            try {
//                System.out.println(correct);
//                Stage thisstage = (Stage) ((Button) event.getSource()).getScene().getWindow();
//                thisstage.close();
//                FXMLLoader quiz = new FXMLLoader(getClass().getResource("result.fxml"));
//                Scene quizscene = new Scene(quiz.load());
//                quizscene.setFill(Color.TRANSPARENT);
//                Stage quizstage = new Stage();
//                quizstage.setScene(quizscene);
//                quizstage.initStyle(StageStyle.TRANSPARENT);
//                quizstage.show();
                counter = 0; // reset game
                HelloApplication helloApplication = new HelloApplication();
                helloApplication.changeScreen("Result.fxml",700,700);
            } catch(IOException e) {
                e.printStackTrace();
            }
        } else {
            counter++;
            loadQuestions();
        }
    }

    @FXML
    public void opt4clicked(ActionEvent event) {
        checkAnswer(opt4.getText().toString());
        if (checkAnswer(opt4.getText().toString())) {
            correct = correct + 1;
        } else {
            wrong = wrong + 1;
        }
        if (counter == 9) {
            try {
                //                System.out.println(correct);
//                Stage thisstage = (Stage) ((Button) event.getSource()).getScene().getWindow();
//                thisstage.close();
//                FXMLLoader quiz = new FXMLLoader(getClass().getResource("result.fxml"));
//                Scene quizscene = new Scene(quiz.load());
//                quizscene.setFill(Color.TRANSPARENT);
//                Stage quizstage = new Stage();
//                quizstage.setScene(quizscene);
//                quizstage.initStyle(StageStyle.TRANSPARENT);
//                quizstage.show();
                counter = 0; // reset game
                HelloApplication helloApplication = new HelloApplication();
                helloApplication.changeScreen("Result.fxml",700,700);
            } catch(IOException e) {
                e.printStackTrace();
            }
        } else {
            counter++;
            loadQuestions();
        }
    }

    //-------------out game------------------
    public void returnApp(ActionEvent event) throws IOException {
        //xóa dữ liệu game
        counter = 0;
        correct = 0;
        wrong = 0;
        randomId = 0;
        Arrays.fill(asked,false);
        //chuyển về màn hình app
        HelloApplication helloApplication = new HelloApplication();
        helloApplication.changeScreen("Game.fxml",840,540);
    }



}
