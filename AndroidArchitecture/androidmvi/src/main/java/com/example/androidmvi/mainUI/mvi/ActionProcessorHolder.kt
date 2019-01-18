package com.example.androidmvi.mainUI.mvi

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ActionProcessorHolder(private val processFetchUsers: ProcessFetchUsers) {


    // An observable for fetch License
    // it uses a singleton instance of ProcessSyncLicense and parse response base on result
    // Success or Failure
    private val fetchUserProcessor =
        ObservableTransformer<MyAction.SyncBackendAction, MyResult.SyncBackendResult> { actions ->
            actions.flatMap { action ->
                processFetchUsers.fetchUsers()
                    // Wrap returned data into an immutable object
                    .map { users -> MyResult.SyncBackendResult.Success(users) }
                    .cast(MyResult.SyncBackendResult::class.java)
                    // Wrap any error into an immutable object and pass it down the stream
                    // without crashing.
                    // Because errors are data and hence, should just be part of the stream.
                    .onErrorReturn(MyResult.SyncBackendResult::Failure)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }

    private val doSomethingProcessor =
        ObservableTransformer<MyAction.SyncBackendAction2, MyResult.SyncBackendResult2> { actions ->
            actions.flatMap { action ->
                processFetchUsers.fetchUsers()
                    // Wrap returned data into an immutable object
                    .map { users -> MyResult.SyncBackendResult2.Success(users) }
                    .cast(MyResult.SyncBackendResult2::class.java)
                    // Wrap any error into an immutable object and pass it down the stream
                    // without crashing.
                    // Because errors are data and hence, should just be part of the stream.
                    .onErrorReturn(MyResult.SyncBackendResult2::Failure)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }

    /**
     * Splits the [Observable] to match each type of [MyAction] to
     * its corresponding business logic processor. Each processor takes a defined [MyAction],
     * returns a defined [MyResult]
     * The global actionProcessor then merges all [Observable] back to
     * one unique [Observable].
     *
     *
     * The splitting is done using [Observable.publish] which allows almost anything
     * on the passed [Observable] as long as one and only one [Observable] is returned.
     *
     *
     * An security layer is also added for unhandled [MyAction] to allow early crash
     * at runtime to easy the maintenance.
     */
    internal val actionProcessor =
        ObservableTransformer<MyAction, MyResult> { actions ->
            Timber.i("actionProcessor $actions")
            actions.publish { shared ->
                Observable.merge(
                    listOf(
                        shared.ofType(MyAction.SyncBackendAction::class.java).compose(fetchUserProcessor),
                        shared.ofType(MyAction.SyncBackendAction2::class.java).compose(doSomethingProcessor)
                    )
                )
            }
        }
}