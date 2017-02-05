package br.ufscar.dc.cc2.muzean;

import java.util.Scanner;

/**
 *
 * @author petri
 */
public class IOUtil {

    public static String convertStreamToString(java.io.InputStream is) {
        Scanner s = new Scanner(is, "UTF-8").useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static String getResourceAsString(String resource_path) {
        // Source: http://stackoverflow.com/a/18897411 + http://stackoverflow.com/a/5445161/6278885
        return convertStreamToString(Gerador.class.getResourceAsStream(resource_path));
    }
}
