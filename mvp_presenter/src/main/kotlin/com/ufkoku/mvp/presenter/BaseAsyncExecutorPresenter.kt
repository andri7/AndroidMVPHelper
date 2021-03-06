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

package com.ufkoku.mvp.presenter

import com.ufkoku.mvp_base.presenter.IAsyncPresenter
import com.ufkoku.mvp_base.view.IMvpView
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor

abstract class BaseAsyncExecutorPresenter<T : IMvpView> : BaseAsyncPresenter<T>(), IAsyncPresenter<T> {

    protected var executor: ThreadPoolExecutor? = null

    override fun onAttachView(view: T) {
        if (executor == null) {
            executor = createExecutor()
            if (useSaveThreadFactory()) {
                if (executor!!.threadFactory !is SaveThreadFactory) {
                    executor!!.threadFactory = SaveThreadFactory()
                }
            }
        }

        super.onAttachView(view)
    }

    override fun cancel() {
        if (executor != null) {
            executor!!.shutdownNow()
            executor = null
        }

        super.cancel()
    }

    protected abstract fun createExecutor(): ThreadPoolExecutor

    protected fun useSaveThreadFactory(): Boolean = true

    protected class SaveThreadFactory : ThreadFactory {

        private val delegate = Executors.defaultThreadFactory();

        override fun newThread(r: Runnable?): Thread {
            val thread = delegate.newThread(r)
            thread.uncaughtExceptionHandler = Thread.UncaughtExceptionHandler { _, _ -> /*ignored*/ }
            return thread
        }

    }

}
