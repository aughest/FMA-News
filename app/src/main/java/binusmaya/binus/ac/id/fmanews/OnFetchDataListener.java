package binusmaya.binus.ac.id.fmanews;

import java.util.List;

import binusmaya.binus.ac.id.fmanews.Models.Article;

public interface OnFetchDataListener<NewsApiResponse> {
    void onFetchData(List<Article> list, String message);
    void onError(String message);
}
