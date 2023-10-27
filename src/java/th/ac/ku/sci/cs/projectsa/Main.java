package th.ac.ku.sci.cs.projectsa;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;

public class Main extends javafx.application.Application {
    public static String[] args =null;
    public static void funcTestOFCaughtException() {
    }

    // TODO: (OUTSIDE OF CODE) ศึกษา sqlite datatype ละเอัยดหน่อยๆ + S T R I C T
    // entire exception handling info: mode=fatal
    public static void main(String[] args) throws Throwable {
        try {
            Main.args=args;
            boolean doMIDIPlayer =true;
            for (String arg : args) {
                if (arg.equals("-MiscFunFlag+muteMIDI")||arg.equals("--MiscFunFlag+muteMIDI")) {doMIDIPlayer=false;break;}
            }
            if (doMIDIPlayer) {
                // funny stuff, lazy-exception-handling is done in that function 
                th.ac.ku.sci.cs.projectsa.fun.MIDIPlayer.main();
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


    // TODO: entire exception handling info: mode=fatal
    public static void shutdown() {
        // TODO:
    }
    
    // entire exception handling info: mode=fatal
    @Override
    public void start(javafx.stage.Stage primaryStage) {
        try {
            // TODO: (SHUTDOWN PART) exception handling (only about to add try-catch inside parenthesis of runnable) + also move to main()
            Runtime.getRuntime().addShutdownHook(new Thread(()->{
                this.stop();
            }));
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
    // TODO: (SHUTDOWN PART) move code to static method
    // TODO: (SHUTDOWN PART) do not let exception of 1 break another actions
    @Override
    public void stop() {
        try {
            MainAlt1.primaryStage.hide();
            // funny stuff, lazy-exception-handling is done in that function 
            th.ac.ku.sci.cs.projectsa.fun.MIDIPlayer.shutdown();
            if (MyExceptionHandling.isFatal) {}
            else {
                javafx.application.Platform.exit();
                // in case user want something extraordinary, เราจัดให้ ๆ
                // WARNING: do not run this arg to commandLine in production, this would skip any (Java exception handling / JVM shutdown hook/cleanup), results in ongoing-cleaning data being lossed, or even corrupts database file. 
                for (String arg : args) {
                if (arg.equals("-MiscFunFlag+crashOnPostExit")||arg.equals("--MiscFunFlag+crashOnPostExit")) {
                    th.ac.ku.sci.cs.projectsa.fun.UnsafeStuff.crashJVMLamo();
                }
                }
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