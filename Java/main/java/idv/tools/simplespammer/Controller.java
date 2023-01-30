package java.simplespammer;

import java.io.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.robot.Robot;

public class Controller {
    // 메인 팬
    @FXML
    private AnchorPane root;

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

    // 하나인 버튼을 믿나이다.
    @FXML
    private Button btnExplosion;

    Robot robot = new Robot();
    Process python = null;

    public void ShowMousePosition(KeyEvent event) {
        String[] out = null;
        ProcessBuilder pb = null;
        BufferedReader rdr = null;

        if (event.getCode() == KeyCode.CONTROL) {
            pb = new ProcessBuilder(System.getProperty("user.dir") + "\\position.exe");

            try{
                python = pb.start();
                python.waitFor();

                rdr = new BufferedReader(new InputStreamReader(python.getInputStream()));
                out = rdr.readLine().split("-");
                rdr.close();
            }
            catch(Exception e){}
            finally {
                python.destroy();
                python = null;
            }

            txtTgtXPos.setText(out[0]);
            txtTgtYPos.setText(out[1]);
        }
    }

    public void Explosion() {
        int tgtX, tgtY;
        int frequencyMil;
        int timerMinMil;
        boolean isFirst = true;

        ProcessBuilder pb;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        String txtAreaContent = txtArea.getText();
        String unprocessedXPos = txtTgtXPos.getText();
        String unprocessedYPos = txtTgtYPos.getText();
        String unprocessedFrequency = txtFrequency.getText();
        String unprocessedTimerMin = txtTimerMin.getText();

        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();


        if (python != null) {
            if (python.isAlive()) {
                alert.setContentText("아직 매크로 프로세스 종료 안 됐음.");
                alert.show();
                return;
            }
        }

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
            tgtX = Integer.parseInt(unprocessedXPos);
            tgtY = Integer.parseInt(unprocessedYPos);

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

        try {
            pb = new ProcessBuilder(System.getProperty("user.dir") + "\\robot.exe", Integer.toString(tgtX), Integer.toString(tgtY), "3000", Integer.toString(frequencyMil), Integer.toString(timerMinMil));
            python = pb.start();
        } catch (IOException e) {
            alert.setContentText(e.toString());
            alert.show();
        }
    }
}