package com.example.tictactoe;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class TicTacToe extends Application {

    private Button[][] buttons = new Button[3][3];
    private Label p1_score;
    private Label p2_score;
    private Label p1_name;
    private Label p2_name;
    private Label Turn = new Label("X");
    private boolean p1turn = true;

    private BorderPane main_panel(){
        BorderPane root = new BorderPane();

        //Making top
        HBox header = new HBox(20);
        Label title = new Label("Tic Tac Toe");
        title.setStyle("-fx-font-size : 24pt; -fx-font-weight : bold; -fx-font-alignment : centre;");
        Label turn_text = new Label("Turn :");
        turn_text.setStyle("-fx-font-size : 12pt; -fx-font-weight : bold; -fx-font-alignment : centre;");
        Turn.setStyle("-fx-font-size : 12pt; -fx-font-weight : bold; -fx-font-alignment : centre;");
        header.getChildren().addAll(title, turn_text, Turn);
        header.setAlignment(Pos.CENTER);
        header.setBackground(Background.fill(Color.LIGHTBLUE));
        root.setTop(header);

        //Making 3x3 grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setBackground(Background.fill(Color.DARKBLUE));
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                Button button = new Button();
                button.setPrefSize(100, 100);
                button.setStyle("-fx-font-size : 30pt; -fx-font-weight : bold;");
                button.setOnAction(actionEvent -> {
                    play(button);
                });
                buttons[i][j] = button;
                grid.add(button, i, j);
            }
        }
        root.setCenter(grid);

        //Making ScoreBoard
        String p1 = "Player1";
        String p2 = "Player2";
        int s1 = 0;
        int s2 = 0;
        p1_name = new Label(p1);
        p1_name.setStyle("-fx-font-size : 15pt; -fx-font-weight : bold;");
        p2_name = new Label(p2);
        p2_name.setStyle("-fx-font-size : 15pt; -fx-font-weight : bold;");
        p1_score = new Label(Integer.toString(s1));
        p1_score.setStyle("-fx-font-size : 15pt; -fx-font-weight : bold;");
        p2_score = new Label(Integer.toString(s2));
        p2_score.setStyle("-fx-font-size : 15pt; -fx-font-weight : bold;");

        HBox score_board = new HBox(20);
        score_board.getChildren().addAll(p1_name, p1_score, new Label(":"), p2_score, p2_name);
        score_board.setAlignment(Pos.CENTER);
        score_board.setBackground(Background.fill(Color.GREENYELLOW));
        root.setBottom(score_board);

        return root;
    }

    private void play(Button button){
        int i = 0;
        int j = 0;
        for(int r=0; r<3; r++){
            for(int c=0; c<3; c++){
                if(buttons[r][c] == button){
                    i = r;
                    j = c;
                    break;
                }
            }
        }
        if(button.getText().equals("")){
            if(p1turn){
                Turn.setText("O");
                button.setText("X");
                checker(i, j);
            } else{
                Turn.setText("X");
                button.setText("O");
                checker(i, j);
            }
            p1turn = !p1turn;
        }
    }

    private void checker(int i, int j){

        String chk = buttons[i][j].getText();

        //checking row
        if(buttons[i][0].getText().equals(buttons[i][1].getText()) && buttons[i][1].getText().equals(buttons[i][2].getText())) roundClose(chk);

        //checking col
        else if(buttons[0][j].getText().equals(buttons[1][j].getText()) && buttons[1][j].getText().equals(buttons[2][j].getText())) roundClose(chk);

        //checking diagonal
        else if(buttons[0][0].getText().equals(chk) && buttons[1][1].getText().equals(chk) && buttons[2][2].getText().equals(chk)) roundClose(chk);
        else if(buttons[2][0].getText().equals(chk) && buttons[1][1].getText().equals(chk) && buttons[0][2].getText().equals(chk)) roundClose(chk);
        else{
            for(int r=0; r<3; r++){
                for(int c=0; c<3; c++)
                    if(buttons[r][c].getText().equals("")) return;
            }
            reset();
        }
    }

    private void roundClose(String winner){

        if(winner.equals("X")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(p1_name.getText()+" Wins !");
            alert.showAndWait();
            int score = Integer.parseInt(p1_score.getText());
            score++;
            p1_score.setText(Integer.toString(score));
        } else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(p2_name.getText()+" Wins !");
            alert.showAndWait();
            int score = Integer.parseInt(p2_score.getText());
            score++;
            p2_score.setText(Integer.toString(score));
        }
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                buttons[i][j].setText("");
            }
        }
    }

    private void reset(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Draw !");
        alert.showAndWait();
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++)
                buttons[i][j].setText("");
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(main_panel());
        stage.setTitle("Tic Tac Toe");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}