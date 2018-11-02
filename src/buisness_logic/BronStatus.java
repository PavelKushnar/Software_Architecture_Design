/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buisness_logic;

/**
 *
 * @author Pavel_User
 */
public enum BronStatus {
    New, WaitPay, Payed, WaitCashierPay, WaitAccessManager;

    public static String statusToStr(BronStatus type) {
        switch (type) {
            case New:
                return "New";
            case WaitPay:
                return "WaitPay";
            case Payed:
                return "Payed";
            case WaitCashierPay:
                return "WaitCashierPay";
            case WaitAccessManager:
                return "WaitAccessManager";
            default:
                return null;
        }
    }

    
    
     public static BronStatus strToStatus(String st) {
           switch (st) {
            case "New":
                return New;
            case "WaitPay":
                return WaitPay;
            case "Payed":
                return Payed;
            case "WaitCashierPay":
                return WaitCashierPay;
            case "WaitAccessManager":
                return WaitAccessManager;
            default:
                return null;
        }
     }
}
