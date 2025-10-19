package com.abhibus.Factory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class Reporter {

    static ExtentSparkReporter spark;
    public static ExtentTest test;
    public static ExtentReports extent;
    public static String ReportPath;
    public String testCaseName;
    public String testDescription;
    public String Category;
    public static String currentDateAndTime;
    public static String module;
    public String Author;
    public static String dbhost, dbUID, dbPwd, encryptDbPwd, appEnv;
    public static int totalCaseCounter = 0, totalPass = 0, totalFail = 0, totalPassPercentage = 0;

    public static void reportStep(String desc, String status, boolean bSnap) {
        try {
            if (status.equalsIgnoreCase("FAIL") || status.equalsIgnoreCase("PASS.WITHSNAP")) {
                String snapNumber = "100001";
                test.fail(desc);
                System.out.println("Failed : " + desc);
//                try {
//                    expAndErrorGetScreenshotAndText();
//                    snapNumber = takeBase64Snap();
//                } catch (Exception e) { e.printStackTrace(); }
//                desc = desc + test.addScreenCaptureFromBase64String(snapNumber);
            }

            if (status.equalsIgnoreCase("PASS")) {
                test.pass(desc);
                System.out.println("Passed : " + desc);
            }

            else if (status.equalsIgnoreCase("FAIL")) {
                test.fail(desc);
                String[] FailedDesc = desc.split("<img");
                System.out.println("FAILED ----- !!!! " + FailedDesc[0] + " !!!! -----");
                totalFail = totalFail + 1;
            }

            else if (status.equalsIgnoreCase("WARN")) {
                test.warning(desc);
            }

            else if (status.equalsIgnoreCase("INFO")) {
                test.info(desc);
                System.out.println("INFO : " + desc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String takeBase64Snap() {
        return "";
    }

    public static void expAndErrorGetScreenshotAndText() throws InterruptedException {
    }

    public static ExtentReports startResult() {
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH-mm-ss");
            Date today = Calendar.getInstance().getTime();
            currentDateAndTime = dateFormat.format(today);

        /*
        extent = new ExtentReports("./reports/Report_" + currentDateAndTime + ".html", true)
            .addSystemInfo("App_ENV", GenericFunctions.environment)
            .addSystemInfo("App_URL", GenericFunctions.url);
        */

            spark = new ExtentSparkReporter("./reports/Report_" + currentDateAndTime + ".html");
            extent = new ExtentReports();
            extent.attachReporter(spark);
            spark.config().setDocumentTitle("NPS Automation Report");
            extent.setSystemInfo("App_ENV", "LOCAL");
            extent.setSystemInfo("App_URL", "abhibus.com");
//            sendEmail(GenericFunctions.Recipients, "",
//                    " - NPG Automation Execution Started By User - " + System.getProperty("user.name") + " - "
//                            + java.util.Calendar.getInstance().getTime(),
//                    "Result will be shared once execution completes.\n\n Thanks! \n\n");
            // XMLCSVCreator();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return extent;
    }


    public static void reportStep(String desc, String status){
        reportStep(desc,status,true);
    }
    public static  ExtentTest startTestCase(String testCaseName,String testCaseDescription){
        totalCaseCounter +=1;
        System.out.println("Script Executed: "+ totalCaseCounter);
        test = extent.createTest(testCaseName,testCaseDescription);
//        test.assignCategory(module);
        test.assignAuthor("Ravi Teja");
        return test;
    }
    public static void endResult() throws FileNotFoundException, IOException {
        extent.flush();
    }
}
