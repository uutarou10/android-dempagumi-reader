package mof2.net.dempareader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by uutarou on 2017/07/20.
 */

public class RssParser {
    private URL url;

    public RssParser(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /***
     * urlからXMLを取得し返すメソッド
     * @return XML
     */
    public String getXml() {
        StringBuffer sb = new StringBuffer();
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                inputStream.close();
                inputReader.close();
                bufferedReader.close();
            }

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public ArrayList<Article> parseRss() {
        ArrayList<Article> articleArrayList = new ArrayList<>();
        String rss = getXml();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(rss));
            int eventType = xpp.getEventType();
            Article article = null;
            boolean isInItemTag = false;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("item")) {
                    isInItemTag = true;
                    article = new Article();
                }

                if (isInItemTag) {
                    if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("title")) {
                        eventType = xpp.next();
                        article.setTitle(xpp.getText());
                    }

                    if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("description")) {
                        eventType = xpp.next();
                        article.setDescription(xpp.getText());
                    }
                    if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("link")) {
                        eventType = xpp.next();
                        article.setLink(xpp.getText());
                    }

                    if (eventType == XmlPullParser.END_TAG && xpp.getName().equals("item")) {
                        isInItemTag = false;
                        articleArrayList.add(article);
                    }
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return articleArrayList;
    }
}
