package testparkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parkinglot.*;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class testCmdAdjustFee extends inputOctopusAlipayCredit {

    private CmdAdjustFee cmd;
    private Staff staff;

    @BeforeEach
    void setUp() {
        super.setUpIO();
        SmartParkingSystem.injectScannerForTest(new Scanner(""));
        staff = new Staff("S001", "Test Staff");
        cmd = new CmdAdjustFee();
    }

    @Test
    public void hourlyPlanConfirmedUpdatesRate() {
        double originalFee = HourlyBilling.getInstance().getFeeRate();
        
        Scanner testScanner = new Scanner("1\n10.5\nyes\n");
        SmartParkingSystem.injectScannerForTest(testScanner);
        
        cmd.execute("ADJUST_FEE", staff);
        System.err.println(getOutput());
         
        assertEquals(10.5, HourlyBilling.getInstance().getFeeRate());
        
        HourlyBilling.getInstance().setFeeRate(originalFee);
    }

    @Test
    public void dailyPlanConfirmedUpdatesRate() {
        double originalFee = DailyBilling.getInstance().getFeeRate();
        
        Scanner testScanner = new Scanner("2\n50.0\nyes\n");
        SmartParkingSystem.injectScannerForTest(testScanner);
        
        cmd.execute("ADJUST_FEE", staff);
        System.err.println(getOutput());
         
        assertEquals(50.0, DailyBilling.getInstance().getFeeRate());
      
        DailyBilling.getInstance().setFeeRate(originalFee);
    }

    @Test
    public void monthlyPlanConfirmedUpdatesRate() {
        double originalFee = MonthlyBilling.getInstance().getFeeRate();
        
        Scanner testScanner = new Scanner("3\n500.0\nyes\n");
        SmartParkingSystem.injectScannerForTest(testScanner);
        
        cmd.execute("ADJUST_FEE", staff);
        System.err.println(getOutput());
         
        assertEquals(500.0, MonthlyBilling.getInstance().getFeeRate());
        
        MonthlyBilling.getInstance().setFeeRate(originalFee);
    }

    @Test
    public void annualPlanConfirmedUpdatesRate() {
        double originalFee = AnnualBilling.getInstance().getFeeRate();
        
        Scanner testScanner = new Scanner("4\n5000.0\nyes\n");
        SmartParkingSystem.injectScannerForTest(testScanner);
        
        cmd.execute("ADJUST_FEE", staff);
        System.err.println(getOutput());
         
        assertEquals(5000.0, AnnualBilling.getInstance().getFeeRate());
        
        AnnualBilling.getInstance().setFeeRate(originalFee);
    }

    @Test
    public void hourlyPlanCancelledDoesNotUpdateRate() {
        double originalFee = HourlyBilling.getInstance().getFeeRate();
        
        Scanner testScanner = new Scanner("1\n15.0\nno\n");
        SmartParkingSystem.injectScannerForTest(testScanner);
        
        cmd.execute("ADJUST_FEE", staff);
        System.err.println(getOutput());
         
        assertEquals(originalFee, HourlyBilling.getInstance().getFeeRate());
    }

    @Test
    public void dailyPlanCancelledDoesNotUpdateRate() {
        double originalFee = DailyBilling.getInstance().getFeeRate();
        
        Scanner testScanner = new Scanner("2\n60.0\nno\n");
        SmartParkingSystem.injectScannerForTest(testScanner);
        
        cmd.execute("ADJUST_FEE", staff);
        System.err.println(getOutput());
         
        assertEquals(originalFee, DailyBilling.getInstance().getFeeRate());
    }

    @Test
    public void monthlyPlanCancelledDoesNotUpdateRate() {
        double originalFee = MonthlyBilling.getInstance().getFeeRate();
        
        Scanner testScanner = new Scanner("3\n600.0\nno\n");
        SmartParkingSystem.injectScannerForTest(testScanner);
        
        cmd.execute("ADJUST_FEE", staff);
        System.err.println(getOutput());
         
        assertEquals(originalFee, MonthlyBilling.getInstance().getFeeRate());
    }

    @Test
    public void annualPlanCancelledDoesNotUpdateRate() {
        double originalFee = AnnualBilling.getInstance().getFeeRate();
        
        Scanner testScanner = new Scanner("4\n6000.0\nno\n");
        SmartParkingSystem.injectScannerForTest(testScanner);
        
        cmd.execute("ADJUST_FEE", staff);
        System.err.println(getOutput());
         
        assertEquals(originalFee, AnnualBilling.getInstance().getFeeRate());
    }

    @Test
    public void exitOptionDoesNotUpdateRate() {
        double originalFee = HourlyBilling.getInstance().getFeeRate();
        
        Scanner testScanner = new Scanner("5\n");
        SmartParkingSystem.injectScannerForTest(testScanner);
        
        cmd.execute("ADJUST_FEE", staff);
        System.err.println(getOutput());
         
        assertEquals(originalFee, HourlyBilling.getInstance().getFeeRate());
    }

    @Test
    public void invalidOptionDoesNotUpdateRate() {
        double originalFee = HourlyBilling.getInstance().getFeeRate();
        
        Scanner testScanner = new Scanner("6\n");
        SmartParkingSystem.injectScannerForTest(testScanner);
        
        cmd.execute("ADJUST_FEE", staff);
        System.err.println(getOutput());
         
        assertEquals(originalFee, HourlyBilling.getInstance().getFeeRate());
    }
}
