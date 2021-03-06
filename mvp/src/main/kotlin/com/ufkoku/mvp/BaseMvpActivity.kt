/*
 * Copyright 2016 Ufkoku (https://github.com/Ufkoku/AndroidMVPHelper)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ufkoku.mvp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.ufkoku.mvp.base.IMvpActivity
import com.ufkoku.mvp.delegate.controller.ActivityDelegate
import com.ufkoku.mvp.delegate.observable.ActivityLifecycleObservable
import com.ufkoku.mvp.utils.NullerUtil
import com.ufkoku.mvp.utils.NullerUtil.nullAllFields
import com.ufkoku.mvp.utils.view_injection.ViewInjector
import com.ufkoku.mvp_base.view.lifecycle.ILifecycleObservable
import com.ufkoku.mvp_base.presenter.IPresenter
import com.ufkoku.mvp_base.view.IMvpView
import com.ufkoku.mvp_base.viewstate.IViewState

abstract class BaseMvpActivity<V : IMvpView, P : IPresenter<V>, VS : IViewState<V>> : AppCompatActivity(), IMvpActivity<V, P, VS> {

    private val delegate: ActivityDelegate<BaseMvpActivity<V, P, VS>, V, P, VS> = ActivityDelegate(this)

    private val lifecycleDelegate: ActivityLifecycleObservable = ActivityLifecycleObservable()

    protected val presenter: P?
        get() {
            return delegate.presenter
        }

    protected val viewState: VS?
        get() {
            return delegate.viewState
        }

    //-----------------------------------------------------------------------------------------//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delegate.onCreate(savedInstanceState)
        lifecycleDelegate.onCreate(this, savedInstanceState)
    }

    override fun createView() {
        if (ViewInjector.checkAnnotation(this)) {
            val view = ViewInjector.injectViews(this, this, BaseMvpActivity::class.java, null)
            setContentView(view)
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleDelegate.onStart(this)
    }

    override fun onResume() {
        super.onResume()
        lifecycleDelegate.onResume(this)
    }

    override fun onPause() {
        lifecycleDelegate.onPause(this)
        super.onPause()
    }

    override fun onStop() {
        lifecycleDelegate.onStop(this)
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        delegate.onSaveInstanceState(outState)
        lifecycleDelegate.onSaveInstance(this, outState)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        lifecycleDelegate.onDestroy(this)
        delegate.onDestroy()
        super.onDestroy()
        if (nullViews()) {
            this.nullAllFields(View::class.java, BaseMvpActivity::class.java)
        }
    }

    //-----------------------------------------------------------------------------------------//

    override fun subscribe(observer: Any) {
        lifecycleDelegate.subscribe(observer)
    }

    override fun unsubscribe(observer: Any) {
        lifecycleDelegate.unsubscribe(observer)
    }

}
