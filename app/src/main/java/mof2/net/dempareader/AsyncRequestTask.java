package mof2.net.dempareader;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by uutarou on 2017/07/20.
 */

public class AsyncRequestTask extends AsyncTask<Integer, Integer, ArrayList<Article>> {
    ArticleListAdapter adapter;
    ArrayList<Article> articleList;

    public AsyncRequestTask(ArticleListAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected ArrayList<Article> doInBackground(Integer... integers) {
        RssParser parser = new RssParser("http://dempagumi.dearstage.com/rss");
        return parser.parseRss();
    }

    @Override
    protected void onPostExecute(ArrayList<Article> articleList) {
        for (Article article : articleList) {
            adapter.add(article);
        }
        adapter.notifyDataSetChanged();
    }
}
