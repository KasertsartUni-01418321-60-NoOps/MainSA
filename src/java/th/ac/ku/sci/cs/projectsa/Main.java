package th.ac.ku.sci.cs.projectsa;

import th.ac.ku.sci.cs.projectsa.uictrl.*;

import java.io.File;

import th.ac.ku.sci.cs.projectsa.*;

public class Main extends javafx.application.Application {
    public static String[] args = null;
    public static java.util.Map<String,Object> globalVar = new java.util.HashMap<>();
    public static java.util.concurrent.ExecutorService exitThread = java.util.concurrent.Executors
            .newSingleThreadExecutor();

    public static void funcTestOFCaughtException() {
    }

    // entire exception handling info: mode=fatal
    public static void main(String[] args) throws java.io.UnsupportedEncodingException,java.sql.SQLException,java.io.IOException,java.security.NoSuchAlgorithmException {
        boolean isFriendlyException = false;
        try {
            String jarParentDirectoryPath = (new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath())).getParentFile().getAbsolutePath();
            System.setProperty("user.dir", jarParentDirectoryPath);
            System.setProperty("file.encoding", "UTF-8");
            try {
                System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
            } catch (java.io.UnsupportedEncodingException e) {
                throw e;
            }
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    javafx.application.Platform.exit();
                    // funny stuff, lazy-exception-handling is done in that function
                    th.ac.ku.sci.cs.projectsa.fun.MIDIPlayer.shutdown();
                    if (MyExceptionHandling.isFatal) {
                    } else {
                        // in case user want something extraordinary, เราจัดให้ ๆ
                        // WARNING: do not run this arg to commandLine in production, this would skip
                        // any (Java exception handling / JVM shutdown hook/cleanup), results in
                        // ongoing-cleaning data being lossed, or even corrupts database file.
                        for (String arg : args) {
                            if (arg.equals("-MiscFunFlag+crashOnPostExit")
                                    || arg.equals("--MiscFunFlag+crashOnPostExit")) {
                                th.ac.ku.sci.cs.projectsa.fun.UnsafeStuff.crashJVMLamo();
                            }
                        }
                    }
                    Main.exitThread.shutdownNow();
                } catch (Throwable e) {
                    MyExceptionHandling.handleFatalExitException(e, "MainApp|ShutdownSystem|ProgramMainHook");
                }
            }));
            java.util.Locale.setDefault(new java.util.Locale("th", "TH"));
            Main.args = args;
            boolean doMIDIPlayer = true;
            for (String arg : args) {
                if (arg.equals("-MiscFunFlag+muteMIDI") || arg.equals("--MiscFunFlag+muteMIDI")) {
                    doMIDIPlayer = false;
                    break;
                }
            }
            if (doMIDIPlayer) {
                // funny stuff, lazy-exception-handling is done in that function
                th.ac.ku.sci.cs.projectsa.fun.MIDIPlayer.main();
            }
            try {
                DatabaseMnm.mainDbInit();
            } catch (java.sql.SQLException e1) {
                isFriendlyException = true;
                MyExceptionHandling.handleFatalException_simplev1(e1, true,"MainApp|DatabaseMnm", null,null,"<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดเช็คความถูกต้องของไฟล์โปรแกรมและข้อมูลและเช็คว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>" );
                throw e1;
            } catch (java.io.IOException e1) {
                isFriendlyException = true;
                MyExceptionHandling.handleFatalException_simplev1(e1, true,"MainApp|DatabaseMnm", null,null,"<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของการเข้าถึงไฟล์ระบบฐานข้อมูลฯ<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดเช็คความถูกต้องของไฟล์โปรแกรมและข้อมูลและเช็คว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>" );
                throw e1;
            }
            launch(args);
        } catch (Throwable e) {
            if (!isFriendlyException) {
                MyExceptionHandling.handleFatalException(e);
            }
            throw e;
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
                    "ระบบหลังบ้านบริการซื้อขายเครื่องซักผ้าอุตสาหกรรมมือสอง", 800,600);
            for (String pageName : new String[] { "homepage", "login", "add_item", "buy_from_vender","buy_history","check_items",
                    "create_customer", "customer_data", "customer_list", "money_accounting", "quotation",
                    "sell_history", "warehouse" ,"buy_data"}) {
                // do not put leading slash for jarfile resource for this line of code
                com.github.saacsos.FXRouter.when(pageName, "resources/" + pageName + "_pre.fxml");
            }
            try

            {
                com.github.saacsos.FXRouter.goTo("login");
                // TODO: EASY [DEBUG START]
                try {com.github.saacsos.FXRouter.goTo(args[0]);}
                catch (Exception e) {com.github.saacsos.FXRouter.goTo("login");}
                // [DEBUG END]
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
            // in case of fatal
            if (MyExceptionHandling.isFatal) {
            } else {
                Main.exitThread.submit(() -> {
                    System.exit(0);
                });
            }
        } catch (Throwable e) {
            // due to this method is called by MyExceptionHandling.handleFatalException, so
            // if that happens, then we ignore it due to guideline that specified in that
            // file of MyExceptionHandling
            if (MyExceptionHandling.isFatal) {
            } else {
                try {
                    MyExceptionHandling.handleFatalException(e);
                } catch (Throwable e0) {
                } // we cannot throw out of this function due to overriden
            }
        }
    }

    
    // entire exception handling info: mode=no
    public static javafx.application.Application getPrimaryApp() {
        javafx.application.Application retVal = null;
        retVal = MainAlt1.primaryApplication;
        return retVal;
    }

    // entire exception handling info: mode=no
    public static javafx.stage.Stage getPrimaryStage() {
        javafx.stage.Stage retVal = null;
        retVal = MainAlt1.primaryStage;
        return retVal;
    }

    // entire exception handling info: mode=no
    public static String clReportHeader(String scope, String cate) {
        if (scope == null) {
            scope = "MainApp";
        }
        if (cate == null) {
            cate = "INFO";
        }
        return "[" + Misc.getISODateTimeString() + "|" + scope + "|" + cate + "] ";
    }

    // entire exception handling info: mode=no
    public static void switchToSpecificPagename(String pageName, Object obj) throws java.io.IOException {
        try {
            // TODO: [DEBUG START]
            System.out.println(pageName);
            // [DEBUG END]
            com.github.saacsos.FXRouter.goTo(pageName, obj);
        } catch (java.io.IOException e) {
            throw e;
        }
    }

    // entire exception handling info: mode=no
    public static void switchToSpecificPagename(String pageName) throws java.io.IOException {
        try {
            // TODO: [DEBUG START]
            System.out.println(pageName);
            // [DEBUG END]
            com.github.saacsos.FXRouter.goTo(pageName);
        } catch (java.io.IOException e) {
            throw e;
        }
    }

    // entire exception handling info: mode=no
    public static void showAlertBox(javafx.stage.Stage stage, javafx.scene.control.Alert.AlertType alertType, String wintitle, String title, String desc, boolean waitForClose) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(alertType);
        alert.initOwner(stage);
        alert.initModality(javafx.stage.Modality.WINDOW_MODAL);
        alert.setTitle(wintitle);
        alert.setHeaderText(title);
        alert.setContentText(desc);
        if (waitForClose) {alert.showAndWait();}
        else {alert.show();}
    }


}

class MainAlt1 {
    public static javafx.application.Application primaryApplication = null;
    public static javafx.stage.Stage primaryStage = null;
}