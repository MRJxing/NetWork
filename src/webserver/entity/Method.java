package entity;

import java.util.ArrayList;

/**
 * @author MR.J
 */
public class Method {
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
    public static final String UNRECOGNIZED = "UNRECOGNIZED";

    public static final ArrayList<String> methods = new ArrayList<String>();

    // static block to initialize the static member;
    static {
        methods.add(GET);
        methods.add(POST);
        methods.add(PUT);
        methods.add(DELETE);
    }
}