
public class Main extends javafx.application.Application {
    public static void funcTestOFCaughtException(javafx.stage.Stage primary) {
    }

    public static void main(String[] args) throws Throwable {
        try {
            try {
                DatabaseMnm.init();
                DatabaseMnm.tempSeeDatabaseLamo();
            } catch (java.sql.SQLException e1) {
                throw e1;
            }
            MIDIPlayer.main();
            MIDIPlayer.play();
            launch(args);
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
        }
    }

    @Override
    public void start(javafx.stage.Stage primaryStage) {
        try {
            MainAlt1.primaryApplication = this;
            MainAlt1.primaryStage = primaryStage;
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            com.github.saacsos.FXRouter.bind(this, primaryStage,
                    "ระบบหลังบ้านบริการซื้อขายเครื่องซักผ้าอุตสาหกรรมมือสอง", 800, 600);
            com.github.saacsos.FXRouter.when("main", "res/Main.fxml");
            com.github.saacsos.FXRouter.when("test", "res/Test.fxml");
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

    @Override
    public void stop() {
        try {
            MainAlt1.primaryStage.hide();
            MIDIPlayer.stop();
            javafx.application.Platform.exit();
            // in case it don't shutdown lamo
            try {
                Thread.sleep(1000 * 30);
            } catch (InterruptedException e3) {
                // then force shutdown,so do nothing here
            }
            System.exit(0);
        } catch (Throwable e) {
            try {
                MyExceptionHandling.handleFatalException(e);
            } catch (Throwable e0) {
            } // we cannot throw out of this function due to overriden
        }
    }

    @javafx.fxml.FXML
    public static void tempSwitchToTestPage() throws java.io.IOException, Throwable {
        try {
            try {
                com.github.saacsos.FXRouter.goTo("test");
            } catch (java.io.IOException e) {
                throw e;
            }
            tempChangeCSS("Test");
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
        }
    }

    @javafx.fxml.FXML
    public static void tempChangeCSS(String cssName) throws Throwable {
        try {
            javafx.scene.Scene temp = MainAlt1.primaryStage.getScene();
            temp.getStylesheets().clear();
            temp.getStylesheets().add(Main.class.getResource("res/" + cssName + ".css").toExternalForm());
            temp.getRoot().applyCss();
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
        }
    }

    public static javafx.stage.Stage tempGetPrimaryStage() throws Throwable {
        javafx.stage.Stage retVal = null;
        try {
            retVal = MainAlt1.primaryStage;
        } catch (Throwable e) {
            MyExceptionHandling.handleFatalException(e);
        }
        return retVal;
    }

}

class MainAlt1 {
    public static javafx.application.Application primaryApplication = null;
    public static javafx.stage.Stage primaryStage = null;
}