package com.codearms.maoqiqi.one.music.presenter;

import com.codearms.maoqiqi.base.BaseObserver;
import com.codearms.maoqiqi.one.data.bean.MusicAlbumBean;
import com.codearms.maoqiqi.one.data.bean.MusicArtistBean;
import com.codearms.maoqiqi.one.data.bean.MusicSongBean;
import com.codearms.maoqiqi.one.data.source.OneRepository;
import com.codearms.maoqiqi.one.music.presenter.contract.MusicListContract;
import com.codearms.maoqiqi.one.utils.MediaLoader;
import com.codearms.maoqiqi.one.utils.SortOrder;
import com.codearms.maoqiqi.rx.RxPresenterImpl;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MusicListPresenter extends RxPresenterImpl<MusicListContract.View> implements MusicListContract.Presenter {

    private OneRepository repository;

    public MusicListPresenter(MusicListContract.View view) {
        super(view);
        this.repository = OneRepository.getInstance();
    }

    @Override
    public void getSongList(Long artistId, long albumId, String folderPath, String sortOrder) {
        addSubscribe(repository.getSongList(artistId, albumId, folderPath, sortOrder)
                .subscribeWith(new BaseObserver<List<MusicSongBean>>(view) {
                    @Override
                    public void onNext(List<MusicSongBean> musicSongBeans) {
                        if (isActive()) view.setSongList(musicSongBeans);
                    }
                }));
    }

    @Override
    public void getArtistList(String sortOrder) {
        addSubscribe(repository.getArtistList(sortOrder)
                .subscribeWith(new BaseObserver<List<MusicArtistBean>>(view) {
                    @Override
                    public void onNext(List<MusicArtistBean> musicArtistBeans) {
                        if (isActive()) view.setArtistList(musicArtistBeans);
                    }
                }));
    }

    @Override
    public void getAlbumList(String sortOrder) {
        addSubscribe(repository.getAlbumList(sortOrder)
                .subscribeWith(new BaseObserver<List<MusicAlbumBean>>(view) {
                    @Override
                    public void onNext(List<MusicAlbumBean> musicAlbumBeans) {
                        if (isActive()) view.setAlbumList(musicAlbumBeans);
                    }
                }));
    }

    @Override
    public void getFolderList() {
        addSubscribe(repository.getFolderList()
                .subscribeWith(new BaseObserver<List<String>>(view) {
                    @Override
                    public void onNext(List<String> strings) {
                        getCount(strings);
                    }
                }));
    }

    private void getCount(List<String> folderList) {
        Observable.fromIterable(folderList)
                .map(s -> MediaLoader.getSongBeanList(0, 0, s, SortOrder.SongSortOrder.SONG_A_Z).size()).toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Integer> integers) {
                        if (view == null || !view.isActive()) return;
                        view.setFolderList(folderList, integers);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}