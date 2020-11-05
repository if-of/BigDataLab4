package com.donets.util;

import org.apache.commons.lang3.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;


public class UrlUtils {

    public static String getRootUrl(String rawUrl) {
        String scheme = rawUrl.startsWith("https://")
                ? "https://"
                : "http://";

        String rootUrl = removeSchema(rawUrl);
        rootUrl = trimParametersAndTrailingSlash(rootUrl);
        rootUrl = rootUrl.split("/", 2)[0];
        return scheme + rootUrl;
    }

    public static String removeSchema(String rawUrl) {
        return rawUrl.replaceAll("http://|https://", "");
    }

    public static String trimParametersAndTrailingSlash(String rawUrl) {
        String url = rawUrl.split("#", 2)[0];
        url = url.split("\\?", 2)[0];
        url = StringUtils.stripEnd(url, "/");
        return url;
    }

    public static String getAbsoluteUrl(String baseUrlString, String urlString) {
        try {
            if (urlString == null || urlString.trim().length() == 0) {
                urlString = "";
            }
            URL baseUrl = new URL(baseUrlString);
            URL url = new URL(baseUrl, urlString);

            urlString = url.toString().replaceAll("\\\\+", "/");
            url = new URL(urlString);
            String uri = url.getPath();
            String uriString = uri.replaceAll("/+", "/");
            urlString = url.toString().replaceAll(uri, uriString);
            int index = urlString.indexOf("/..");
            if (index < 0) {
                return urlString;
            }
            String urlStringLeft = urlString.substring(0, index) + "/";
            String urlStringRight = urlString.substring(index + 1);
            return getAbsoluteUrl(urlStringLeft, urlStringRight);

        } catch (MalformedURLException e) {
            return "";
        }
    }

    public static boolean urlBelongsToRootUrl(String rawUrl, String rootUrl) {
        boolean startWith = rawUrl.startsWith(rootUrl);
        if (startWith) {
            if (rawUrl.length() <= rootUrl.length()) {
                return true;
            }

            String delimeter = rawUrl.substring(rootUrl.length(), rootUrl.length() + 1);
            if (delimeter.equals("/") || delimeter.equals("#") || delimeter.equals("?")) {
                return true;
            }
        }
        return false;
    }
}
