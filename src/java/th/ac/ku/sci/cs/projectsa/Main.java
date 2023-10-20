package th.ac.ku.sci.cs.projectsa;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;

public class Main extends javafx.application.Application {
    public static void funcTestOFCaughtException(javafx.stage.Stage primary) {
    }


    // entire exception handling info: mode=fatal
    public static void main(String[] args) throws Throwable {
        try {
            MIDIPlayer.main();
            MIDIPlayer.play();
            try {
                DatabaseMnm.init();
                System.out.println("[USER] this is test of see saved database:");
                DatabaseMnm.tempSeeDatabaseLamo();
                System.out.println("[USER] <END>");
                System.out.println("[USER] and this is test of create my datastr of sqltable, and print them lamo:");
                DatabaseMnm.tempCreateAndSeeMySQLTableDataStr();
                System.out.println("[USER] <END>");
            } catch (java.sql.SQLException e1) {
                throw e1;
            } catch (MyExceptionHandling.UserException e1) {
                throw e1;
            }
            launch(args);
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
        }
    }

    // entire exception handling info: mode=fatal
    @Override
    public void start(javafx.stage.Stage primaryStage) {
        try {
            MainAlt1.primaryApplication = this;
            MainAlt1.primaryStage = primaryStage;
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            com.github.saacsos.FXRouter.bind(this, primaryStage,
                    "ระบบหลังบ้านบริการซื้อขายเครื่องซักผ้าอุตสาหกรรมมือสอง", 800, 600);
            // do not put leading slash for jarfile resource for this line of code
            com.github.saacsos.FXRouter.when("main", "resources/Main.fxml");
            // do not put leading slash for jarfile resource for this line of code
            com.github.saacsos.FXRouter.when("test", "resources/Test.fxml");
            try

            {
                com.github.saacsos.FXRouter.goTo("main");
            } catch (java.io.IOException e) {
                throw e;
            }
            java.awt.Toolkit.getDefaultToolkit().beep(); // เสียงเพื่อสิริมงคล55
        } catch (Throwable e) {
            try {
                MyExceptionHandling.handleFatalException(e);
            } catch (Throwable e0) {
            } // we cannot throw out of this function due to override
        }
    }

    // entire exception handling info: mode=fatal
    @Override
    public void stop() {
        try {
            MainAlt1.primaryStage.hide();
            MIDIPlayer.stop();
            javafx.application.Platform.exit();
            // (DISABLED) in case it don't shutdown lamo
            // try {
            // Thread.sleep(1000 * 30);
            // } catch (InterruptedException e3) {
            // // then force shutdown,so do nothing here
            // }
            // System.exit(0);
        } catch (Throwable e) {
            try {
                MyExceptionHandling.handleFatalException(e);
            } catch (Throwable e0) {
            } // we cannot throw out of this function due to overriden
        }
    }

    // entire exception handling info: mode=no
    public static void tempSwitchToTestPage() throws java.io.IOException {
        try {
            com.github.saacsos.FXRouter.goTo("test");
        } catch (java.io.IOException e) {
            throw e;
        }
        tempChangeCSS("Test");
    }

    // entire exception handling info: mode=no
    public static void tempChangeCSS(String cssName) {

        javafx.scene.Scene temp = MainAlt1.primaryStage.getScene();
        temp.getStylesheets().clear();
        temp.getStylesheets().add(Main.class.getResource("/resources/" + cssName + ".css").toExternalForm());
        temp.getRoot().applyCss();

    }

    // entire exception handling info: mode=no
    public static javafx.stage.Stage tempGetPrimaryStage() {
        javafx.stage.Stage retVal = null;
        retVal = MainAlt1.primaryStage;
        return retVal;
    }

}

class MainAlt1 {
    public static javafx.application.Application primaryApplication = null;
    public static javafx.stage.Stage primaryStage = null;
}