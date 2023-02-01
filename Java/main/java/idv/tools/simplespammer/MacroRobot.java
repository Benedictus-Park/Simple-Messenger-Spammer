package idv.tools.simplespammer;

import java.lang.*;
import java.util.Date;

import javafx.scene.robot.Robot;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;

public class MacroRobot implements Runnable{
    Robot robot;
    int tgtX, tgtY;
    long timerMinMil;
    long frequencyMil;
    String enableShowMousePosition;

    public MacroRobot(Robot robot, int tgtX, int tgtY, long timerMinMil, long frequencyMil, String enableShowMousePosition){
        this.robot = robot;
        this.tgtX = tgtX;
        this.tgtY = tgtY;
        this.timerMinMil = timerMinMil;
        this.frequencyMil = frequencyMil;
        this.enableShowMousePosition = enableShowMousePosition;
    }

    @Override
    public void run(){
        Thread thread = new Thread(() -> {
            int curX, curY;
            long start_timestamp;
            Date date = new Date();

            enableShowMousePosition = "";
            start_timestamp = date.getTime();

            try{
                Thread.sleep(3000);
            }
            catch (InterruptedException e) {};

            robot.mouseMove(tgtX, tgtY);
            tgtX = (int)robot.getMouseX();
            tgtY = (int)robot.getMouseY();

            while(true){
                curX = (int)robot.getMouseX();
                curY = (int)robot.getMouseY();

                if(tgtX != curX || tgtY != curY){
                    break;
                }
                else if(timerMinMil != 0 && date.getTime() - start_timestamp >= timerMinMil){
                    break;
                }

                robot.mouseClick(MouseButton.PRIMARY);
                robot.keyPress(KeyCode.CONTROL);
                robot.keyType(KeyCode.V);
                robot.keyRelease(KeyCode.CONTROL);
                robot.keyType(KeyCode.ENTER);

                try{
                    Thread.sleep(frequencyMil);
                }
                catch (InterruptedException e) {}
            }
            enableShowMousePosition = null;
        });

        thread.run();
    }
}
