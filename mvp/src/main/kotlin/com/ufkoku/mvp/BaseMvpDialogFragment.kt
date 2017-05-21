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

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.View
import com.ufkoku.mvp.delegate.FragmentDelegate
import com.ufkoku.mvp.base.IElementsHolder
import com.ufkoku.mvp_base.presenter.IPresenter
import com.ufkoku.mvp.base.IMvpFragment
import com.ufkoku.mvp_base.view.IMvpView
import com.ufkoku.mvp_base.viewstate.IViewState

@SuppressLint("LongLogTag")
abstract class BaseMvpDialogFragment<V : IMvpView, P : IPresenter<V>, VS : IViewState<V>> : DialogFragment(), IMvpFragment<V, P, VS> {

    companion object {
        private val TAG = "BaseSavableDialogFragment"
    }

    private val delegate: FragmentDelegate<BaseMvpDialogFragment<V, P, VS>, V, P, VS> = FragmentDelegate(this)

    protected val presenter: P?
        get() {
            return delegate.presenter
        }

    protected val viewState: VS?
        get() {
            return delegate.viewState
        }

    //---------------------------------------------------------------------------------------//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delegate.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        delegate.onViewCreated(view, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        delegate.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        delegate.onDestroyView()
        super.onDestroyView()
    }

    override fun onDestroy() {
        delegate.onDestroy()
        super.onDestroy()
    }

}