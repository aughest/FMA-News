package binusmaya.binus.ac.id.fmanews;

import java.util.List;

import binusmaya.binus.ac.id.fmanews.Models.NewsHeadlines;

public interface OnFetchDataListener<NewsApiResponse> {
    void onFetchData(List<NewsHeadlines> list, String message);
    void onError(String message);
}
