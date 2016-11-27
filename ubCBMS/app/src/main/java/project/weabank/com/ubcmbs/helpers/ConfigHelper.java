package project.weabank.com.ubcmbs.helpers;

/**
 * Created by Kira on 11/27/2016.
 */

public class ConfigHelper {
    public static final String PACKAGE_NAME = "project.weabank.com.ubcmbs";

    /**
     * UNION BANK API URLS
     */
    public static final String URL_CHANNEL_ID = "BLUEMIX";
    public static final String URL_TRANSFER = "https://api.us.apiconnect.ibmcloud.com/ubpapi-dev/sb/api/RESTs/transfer";
    public static final String URL_PAYMENT = "https://api.us.apiconnect.ibmcloud.com/ubpapi-dev/sb/api/RESTs/payment";
    public static final String URL_ACCOUNT = "https://api.us.apiconnect.ibmcloud.com/ubpapi-dev/sb/api/RESTs/getAccount?account_no=";
    public static final String URL_LOCATOR = "https://api.us.apiconnect.ibmcloud.com/ubpapi-dev/sb/api/RESTs/getBranches";
    public static final String URL_LOANS = "https://api.us.apiconnect.ibmcloud.com/ubpapi-dev/sb/api/Loans/compute?principal=%s&interest=%s&noy=%s";

    public static final String BORED_ID = "x-ibm-client-id";
    public static final String BORED_CLIENT = "x-ibm-client-secret";

    public static final String BORED_CODER = "X4sQ4qE4hA3yP1pC3dI7bX1pD7xJ7gH0xP6xF6wA0gI2aQ5mU5";
    public static final String VOLDEMORT = "ef4e55aa-c6c4-4a5e-be01-0acd623cea05";

    public static final String ACCOUNT_KIRA = "101960772459";
    public static final String ACCOUNT_ERWIN = "100509108930";

}
