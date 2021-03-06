package com.ufkoku.demo_app.ui.activity.static_list;

import com.ufkoku.demo_app.ui.base.presenter.StaticListPresenter;
import com.ufkoku.demo_app.ui.base.view.IDataView;
import com.ufkoku.mvp.view.wrap.Wrap;
import com.ufkoku.mvp_base.view.IMvpView;

@Wrap
public interface IStaticListActivity extends IMvpView, IDataView, StaticListPresenter.PresenterListener {

}
