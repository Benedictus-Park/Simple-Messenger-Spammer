package idv.tools.simplespammer;

import javafx.fxml.FXML;
import javafx.scene.input.*;
import javafx.scene.control.*;
import javafx.scene.robot.Robot;
import javafx.application.Platform;

public class Controller {
    // 도배할 내용
    @FXML
    private TextArea txtArea;

    // 메시지 입력 창 좌표 표시
    @FXML
    private TextField txtTgtXPos;
    @FXML
    private TextField txtTgtYPos;

    // 도배 주기 입력
    @FXML
    private TextField txtFrequency;

    // 타이머 설정?
    @FXML
    private TextField txtTimerMin;

    String enableShowMousePosition = null;
    Robot robot = new Robot();

    public void ShowMousePosition(KeyEvent event) {
        if (event.getCode() == KeyCode.CONTROL && enableShowMousePosition == null) {
            txtTgtXPos.setText(Double.toString(robot.getMouseX()));
            txtTgtYPos.setText(Double.toString(robot.getMouseY()));
        }
    }

    public void Explosion() {
        int timerMinMil;
        int frequencyMil;
        double tgtX, tgtY;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        String txtAreaContent = txtArea.getText();
        String unprocessedXPos = txtTgtXPos.getText();
        String unprocessedYPos = txtTgtYPos.getText();
        String unprocessedFrequency = txtFrequency.getText();
        String unprocessedTimerMin = txtTimerMin.getText();

        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();

        if (txtAreaContent.isBlank()) {
            alert.setContentText("님 도배할 내용 입력 안 함?");
            alert.show();
            return;
        } else if (unprocessedXPos.isBlank() || unprocessedYPos.isBlank()) {
            alert.setContentText("아마 좌표 설정 깜빡한듯");
            alert.show();
            return;
        } else if (unprocessedFrequency.isBlank()) {
            alert.setContentText("선생님 도배 빈도를 빼먹으면 뭘 해드릴 수가 없어요 ㅋㅋㅋ");
            alert.show();
            return;
        }

        try {
            tgtX = Double.parseDouble(unprocessedXPos);
            tgtY = Double.parseDouble(unprocessedYPos);

            frequencyMil = Integer.parseInt(unprocessedFrequency);
            timerMinMil = 0;
            if (!unprocessedTimerMin.isEmpty()) {
                timerMinMil = Integer.parseInt(unprocessedTimerMin) * 1000 * 60;
            }
        } catch (NumberFormatException e) {
            alert.setContentText("님 아무래도 숫자가 들어가야 하는 칸에 문자가 들어간 것 같은데?");
            alert.show();
            return;
        }

        content.putString(txtAreaContent);
        clipboard.setContent(content);

        Platform.runLater(new MacroRobot(robot, (int)tgtX, (int)tgtY, timerMinMil, frequencyMil, enableShowMousePosition));
    }
}

// Copyright 2023. Benedictus Park(benedictuspark1220@gmail.com) all rights reserved.