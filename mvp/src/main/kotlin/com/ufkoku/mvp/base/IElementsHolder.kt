/*
 * Copyright 2017 Ufkoku (https://github.com/Ufkoku/AndroidMVPHelper)
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

package com.ufkoku.mvp.base

import com.ufkoku.mvp_base.presenter.IPresenter
import com.ufkoku.mvp_base.view.IMvpView
import com.ufkoku.mvp_base.viewstate.IViewState

interface IElementsHolder<V : IMvpView, out P : IPresenter<V>, out VS : IViewState<V>> {

    /**
     * Return true if presenter must be retained accross fragment or activity recreations
     *
     * Note: it must be constant value
     * */
    fun retainPresenter(): Boolean = false

    /**
     * Return true if view state must be retained accross fragment or activity recreations
     *
     * Note: it must be constant value
     * */
    fun retainViewState(): Boolean = false

    /**
     * Return object that will be passed to presenter and view state as callback.
     *
     * Probably you will return 'this', or instance of wrap generated by mvp_view_wrap, or delegate.
     * */
    fun getMvpView(): V

    /**
     * Create and return new instance of view state
     * */
    fun createNewViewState(): VS

    /**
     * Create and return new instance of presenter
     * */
    fun createPresenter(): P

}