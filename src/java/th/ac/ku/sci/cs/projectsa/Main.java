package th.ac.ku.sci.cs.projectsa;

import th.ac.ku.sci.cs.projectsa.uictrl.*;
import th.ac.ku.sci.cs.projectsa.*;

public class Main extends javafx.application.Application {
    public static String[] args = null;
    public static java.util.concurrent.ExecutorService exitThread = java.util.concurrent.Executors
            .newSingleThreadExecutor();

    public static void funcTestOFCaughtException() {
    }

    // entire exception handling info: mode=fatal
    public static void main(String[] args) throws Throwable {
        boolean isFriendlyException = false;
        try {
            Main.args = args;
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
                System.out.println(
                        Main.clReportHeader(null, "DEVTEST") + "this is test of create our db and print them lamo:");
                System.out.println(Main.clReportHeader(null, "DEVTEST") + "<ZONE START>");
                DatabaseMnm.demo_printOurInitTableLAMO();
                System.out.println(Main.clReportHeader(null, "DEVTEST") + "<ZONE END>");
            } catch (java.sql.SQLException e1) {
                isFriendlyException = true;
                MyExceptionHandling.handleFatalException(e1, true, new String[] {
                        Main.clReportHeader("MainApp|DatabaseMnm", "FATAL"), null,
                        "ข้อผิดพลาดร้ายแรงของโปรแกรม (เมื่อ " + Main.getISODateTimeString() + " | ตรงส่วนของ \""
                                + "MainApp|DatabaseMnm" + "\")",
                        "<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของระบบฐานข้อมูลแบบ SQL ซึ่งทำงานไม่ถูกต้องตามที่คาดหวังไว้<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดเช็คความถูกต้องของไฟล์โปรแกรมและข้อมูลและเช็คว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>" });
                throw e1;
            } catch (java.io.IOException e1) {
                // TODO: (ทำทีหลังก็ได้ แต่ทำก่อนเพิ่ม MyExceptionHandling.handleFatalException
                // ใน overload แบบนี้) เรียบเรียงโค้ดๆๆให้ใช้ง่าย
                isFriendlyException = true;
                MyExceptionHandling.handleFatalException(e1, true, new String[] {
                        Main.clReportHeader("MainApp|DatabaseMnm", "FATAL"), null,
                        "ข้อผิดพลาดร้ายแรงของโปรแกรม (เมื่อ " + Main.getISODateTimeString() + " | ตรงส่วนของ \""
                                + "MainApp|DatabaseMnm" + "\")",
                        "<html>โปรแกรมเกิดข้อผิดพลาดร้ายแรง โดยเป็นปัญหาของการเข้าถึงไฟล์ระบบฐานข้อมูลฯ<br/>โดยสาเหตุอาจจะมาจากฝั่งของผู้ใช้หรือของบั๊กโปรแกรม โปรดเช็คความถูกต้องของไฟล์โปรแกรมและข้อมูลและเช็คว่าโปรแกรมสามารถเข้าถึงไฟล์ได้อย่างถูกต้อง<br/>โดยข้อมูลของปัญหาได้ถูกระบุไว้ด้านล่างนี้:</html>" });
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
                    "ระบบหลังบ้านบริการซื้อขายเครื่องซักผ้าอุตสาหกรรมมือสอง", 800, 600);
            // do not put leading slash for jarfile resource for this line of code
            // TODO: [UI] Adding CTRL / Change FXML version idk / CSS settings
            // TODO: [UI] ตั้งภาษา ไม่ยากๆ
            for (String pageName : new String[] {"homepage","login","add_item","buy_history","check_items","create_customer","customer_data","customer_list","money_accounting","quotation","sell_history","warehouse"}) {
                com.github.saacsos.FXRouter.when(pageName, "resources/"+pageName+"_pre.fxml");
            }
            try

            {
                com.github.saacsos.FXRouter.goTo("login");
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
    public static String clReportHeader(String scope, String cate) {
        if (scope == null) {
            scope = "MainApp";
        }
        if (cate == null) {
            cate = "INFO";
        }
        return "[" + getISODateTimeString() + "|" + scope + "|" + cate + "] ";
    }

    // entire exception handling info: mode=no
    public static String getISODateTimeString() {
        return java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    // entire exception handling info: mode=no
    public static void switchToSpecificPagename(String pageName) throws java.io.IOException {
        try {
            com.github.saacsos.FXRouter.goTo(pageName);
        } catch (java.io.IOException e) {
            throw e;
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

}

class MainAlt1 {
    public static javafx.application.Application primaryApplication = null;
    public static javafx.stage.Stage primaryStage = null;
}