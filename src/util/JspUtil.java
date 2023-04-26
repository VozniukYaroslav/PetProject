package util;

public class JspUtil {

    private static final String JSP_PATH = "/WEB-INF/jsp/%s.jsp";

    public static String getJspPath(String path){
        return String.format(JSP_PATH,path);
    }
}
