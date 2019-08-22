package com.example.akshaysolanki.newnews;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {

    private Context context;
    private List<Article> articleList;

    public Adapter(Context context, List<Article> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cards, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        final Article article = articleList.get(i);
        viewHolder.txtTitle.setText(article.getTitle());
        viewHolder.txtDescription.setText(article.getDescription());

        if (article.getAuthor() == null)
            viewHolder.txtSource.setVisibility(View.GONE);
        else
            viewHolder.txtSource.setVisibility(View.VISIBLE);

        viewHolder.txtSource.setText(article.getAuthor());

        Glide.with(context).load(article.getUrlToImage()).into(viewHolder.img);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("article_detail_url", article.getUrl());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtDescription, txtSource;
        ImageView img;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            txtSource = (TextView) itemView.findViewById(R.id.txtSource);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }

}
