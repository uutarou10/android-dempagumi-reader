package mof2.net.dempareader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by uutarou on 2017/07/20.
 */

public class ArticleListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater = null;
    protected ArrayList<Article> articleList = new ArrayList<>();

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public ArticleListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setArticleList(ArrayList<Article> articleList) {
        this.articleList = articleList;
    }

    public void add(Article article) {
        this.articleList.add(article);
    }

    @Override
    public int getCount() {
        return this.articleList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.articleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.articleList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.article_item, parent, false);
        ((TextView)convertView.findViewById(R.id.titleText)).setText(articleList.get(position).getTitle());
        ((TextView)convertView.findViewById(R.id.detailText)).setText(articleList.get(position).getDescription());
        return convertView;
    }
}
