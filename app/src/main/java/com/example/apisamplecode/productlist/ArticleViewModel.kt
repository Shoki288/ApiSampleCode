package com.example.apisamplecode.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apisamplecode.productlist.viewmodels.Article
import com.example.apisamplecode.productlist.viewmodels.ArticleListRepository
import com.example.apisamplecode.vo.Resource
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ArticleViewModel(private val repository: ArticleListRepository): ViewModel() {
    private val disposable: CompositeDisposable = CompositeDisposable()

    private val _searchResult: MutableLiveData<Resource<List<Article>>> = MutableLiveData()
    val searchResult: LiveData<Resource<List<Article>>> = _searchResult

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun searchRequest(keyword: String) {
        repository.fetchArticles(keyword, 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Article>> {
                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }
                override fun onSuccess(t: List<Article>) {
                    _searchResult.postValue(Resource.success(t))
                }

                override fun onError(e: Throwable) {
                    _searchResult.postValue(Resource.error(null))
                }
            })
    }
}

class ArticleViewModelFactory(private val repo: ArticleListRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleViewModel::class.java)) {
            return ArticleViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
