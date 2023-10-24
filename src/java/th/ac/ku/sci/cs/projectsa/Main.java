package th.ac.ku.sci.cs.projectsa;

// TODO: mangage this lamo
import th.ac.ku.sci.cs.projectsa.uictrl.*;
import javafx.scene.chart.PieChart.Data;
import th.ac.ku.sci.cs.projectsa.*;
import th.ac.ku.sci.cs.projectsa.fun.MIDIPlayer;

public class Main extends javafx.application.Application {
    public static String[] args =null;
    public static void funcTestOFCaughtException() {
    }


    // entire exception handling info: mode=fatal
    public static void main(String[] args) throws Throwable {
        try {
            Main.args=args;
            boolean doMIDIPlayer =true;
            for (String arg : args) {
                if (arg.equals("-mute-midi")||arg.equals("--mute-midi")) {doMIDIPlayer=false;break;}
            }
            if (doMIDIPlayer) {
                // funny stuff, lazy-exception-handling is done in that function 
                MIDIPlayer.main();
            }
            try {
                DatabaseMnm.mainDbInit();
                System.out.println(Main.clReportHeader(null, "DEVTEST")+"this is test of create our db and print them lamo:");
                System.out.println(Main.clReportHeader(null, "DEVTEST")+"<ZONE START>");
                DatabaseMnm.demo_printOurInitTableLAMO();
                System.out.println(Main.clReportHeader(null, "DEVTEST")+"<ZONE END>");
            } catch (java.sql.SQLException e1) {
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
            // funny stuff, lazy-exception-handling is done in that function 
            MIDIPlayer.shutdown();
            if (MyExceptionHandling.isFatal) {}
            else {
                javafx.application.Platform.exit();
                System.exit(0);
            }
        } catch (Throwable e) {
            // due to this method is called by MyExceptionHandling.handleFatalException, so if that happens, then we ignore it due to guideline that specified in that file of MyExceptionHandling
            if (MyExceptionHandling.isFatal) {}
            else {
                try {
                    MyExceptionHandling.handleFatalException(e);
                } catch (Throwable e0) {
                } // we cannot throw out of this function due to overriden
            }
        }
    }

    // entire exception handling info: mode=no
    public static String clReportHeader(String scope,String cate) {
        if (scope==null) {scope="MainApp";}
        if (cate==null) {cate="INFO";}
        return "["+getISODateTimeString()+"|"+scope+"|"+cate+"] ";
    }

// entire exception handling info: mode=no
public static String getISODateTimeString() {
    return java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME);
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