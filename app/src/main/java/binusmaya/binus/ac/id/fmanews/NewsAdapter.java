package binusmaya.binus.ac.id.fmanews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import binusmaya.binus.ac.id.fmanews.Models.Article;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

    private Context context;
    private List<Article> newsHeadlines;

    public NewsAdapter(Context context, List<Article> newsHeadlines) {
        this.context = context;
        this.newsHeadlines = newsHeadlines;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
        holder.text_title.setText(newsHeadlines.get(position).getTitle());
        holder.text_source.setText(newsHeadlines.get(position).getSource().getName());

        if (newsHeadlines.get(position).getUrlToImage()!=null){
            Picasso.get().load(newsHeadlines.get(position).getUrlToImage()).into(holder.img_headline);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("data",newsHeadlines.get(position));

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsHeadlines.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView text_title, text_source;
        ImageView img_headline;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            text_title = itemView.findViewById(R.id.text_title);
            text_source = itemView.findViewById(R.id.text_source);
            img_headline = itemView.findViewById(R.id.img_headline);
            cardView = itemView.findViewById(R.id.main_container);
        }
    }
}
