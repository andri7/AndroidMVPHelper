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


package com.ufkoku.mvp.presenter

import com.ufkoku.mvp_base.presenter.IAsyncPresenter
import com.ufkoku.mvp_base.view.IMvpView
import java.util.*

open class BaseAsyncPresenter<T : IMvpView> : BasePresenter<T>(), IAsyncPresenter<T> {

    companion object {
        val TASK_ADDED = 0
        val TASK_FINISHED = 1
    }

    /**
     * Variable is used in method waitForViewIfNeeded().
     * */
    protected val lockObject = Object()

    override var view: T?
        get() {
            synchronized(lockObject) {
                return super.view
            }
        }
        set(value) {
            synchronized(lockObject) {
                super.view = value
            }
        }

    private var taskStatusListener: IAsyncPresenter.ITaskListener? = null
        get() {
            synchronized(lockObject) {
                return field
            }
        }
        set(value) {
            synchronized(lockObject) {
                field = value
            }
        }

    private val runningTasks: MutableList<Int> = Collections.synchronizedList(LinkedList())

    override fun onAttachView(view: T) {
        super.onAttachView(view)

        if (view is IAsyncPresenter.ITaskListener) {
            taskStatusListener = view
        }

        notifyLockObject()
    }

    override fun onDetachView() {
        super.onDetachView()
        taskStatusListener = null
    }

    override fun cancel() {
        runningTasks.clear()
        notifyLockObject()
    }

    private fun notifyLockObject() {
        synchronized(lockObject) {
            try {
                lockObject.notifyAll()
            } catch (ignored: IllegalMonitorStateException) {

            }
        }
    }

    /**
     * Call this method, before populating result (from worker thread).
     * It will wait for view attach, via calling wait() on lockObject.
     * lockObject.notifyAll() will be called after onAttachView().
     *
     * @return view, attached to presenter
     *
     * @throws RuntimeException with cause InterruptedException, if thread was interrupted
     * */
    fun waitForViewIfNeeded(): T {
        synchronized(lockObject) {
            if (view == null) {
                try {
                    lockObject.wait()
                } catch (e: InterruptedException) {
                    throw RuntimeException(e)
                }
            }
            return view!!
        }
    }

    /**
     * Adds task id to list, notifies attached view if it is possible
     * */
    protected fun notifyTaskAdded(task: Int) {
        runningTasks.add(task)
        taskStatusListener?.onTaskStatusChanged(task, TASK_ADDED)
    }

    /**
     * Removes task id from list, notifies attached view if it is possible
     * */
    protected fun notifyTaskFinished(task: Int) {
        runningTasks.remove(task)
        taskStatusListener?.onTaskStatusChanged(task, TASK_FINISHED)
    }

    fun isTaskRunning(task: Int): Boolean {
        return runningTasks.contains(task)
    }

    fun isAnyOfTasksRunning(vararg tasks: Int): Boolean {
        return tasks.any { isTaskRunning(it) }
    }

    fun hasRunningTasks(): Boolean {
        return runningTasks.size > 0
    }

}