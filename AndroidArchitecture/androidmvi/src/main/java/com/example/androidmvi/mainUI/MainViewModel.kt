package com.example.androidmvi.mainUI

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidmvi.dependency_injection.DI
import com.example.androidmvi.mainUI.mvi.ActionProcessorHolder
import com.example.androidmvi.mainUI.mvi.MyAction
import com.example.androidmvi.mainUI.mvi.MyIntent
import com.example.androidmvi.mainUI.mvi.MyResult
import com.example.androidmvi.repository.model.UserModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.ReplaySubject
import timber.log.Timber


class MainViewModel : ViewModel() {

    val mainData = MutableLiveData<List<UserModel>>()
    private val disposables = CompositeDisposable()

    // that variable is RxJava Replay which work like a bridge between UI components
    // like buttons and business logic
    private val userIntent = ReplaySubject.create<MyIntent>()

    // Keeps instances to Processors which execute logic based on Actions
    private val actionProcessorHolder = DI.getActionProcessorHolder()

    /**
     * The Reducer is where [LauncherViewState] is created.
     * It takes the last cached [LauncherViewState], the latest [LauncherResult] and
     * creates a new [LauncherViewState] by only updating the related fields.
     * This is basically like a big switch statement of all possible types for the [LauncherResult]
     */
    private val reducer = BiFunction { previousState: UIModel, result: MyResult ->
        when (result) {
            is MyResult.SyncBackendResult -> when (result) {
                is MyResult.SyncBackendResult.Success -> previousState.copy(userModels = result.users)
                is MyResult.SyncBackendResult.Failure -> previousState.copy(error = result.error)
            }
            else -> {
                throw IllegalStateException("Some error")
            }
        }
    }

    fun refreshUsers() {
        userIntent.onNext(MyIntent.InitialIntent)
    }

    init {
        bindDisposable()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    /**
     * It takes User Intent [LauncherIntent], converts it into [LauncherAction],
     * then sends the action to [ActionProcessorHolder]
     * which transfer the action to corresponding processor for execution.
     * A Processor returns [LauncherResult] which sends into reducer [BiFunction], Reducer creates an instance
     * of [LauncherViewState]. Later [LauncherViewState] binds with [MutableLiveData]
     */
    private fun buildMVIArchitecture(): Disposable {
        Timber.i("buildMVIArchitecture")
        // get user intents
        return userIntent
            // convert user Intents to Actions
            .map { it -> toAction(it) }
            // sends Actions to Processor for execution
            .compose(actionProcessorHolder.actionProcessor)
            // Cache each state and pass it to the reducer to create a new state from
            // the previous cached one and the latest Result emitted from the action processor.
            // The Scan operator is used here for the caching.
            .scan(UIModel.idle(), reducer) // initial data, empty view
            // When a reducer just emits previousState, there's no reason to call initLiveData. In fact,
            // redrawing the UI in cases like this can cause junk (e.g. messing up snackbar animations
            // by showing the same snackbar twice in rapid succession).
            .distinctUntilChanged()
            // Emit the last one event of the stream on subscription
            // Useful when a View rebinds to the ViewModel after rotation.
            .replay(1)
            // Create the stream on creation without waiting for anyone to subscribe
            // This allows the stream to stay alive even when the UI disconnects and
            // match the stream's lifecycle to the ViewModel's one.
            .autoConnect(0)
            // send Data to UI
            .subscribe { it -> initLiveData(it) }
    }

    /**
     * Binds data from [UIModel] to Android [MutableLiveData]
     */
    private fun initLiveData(data: UIModel) {
        Timber.i("initLiveData data $data")
        // prevent duplication
        if (mainData.value != data.userModels) {
            // set UI State object
            mainData.value = data.userModels

        }

    }

    /**
     * Translate an [MyIntent] to an [MyAction].
     * Used to decouple the UI and the business logic to allow easy testings and reusability.
     */
    private fun toAction(intent: MyIntent): MyAction {
        Timber.i("toAction $intent")
        return when (intent) {
            // Any inApp Intent -> corespondent Action type
            is MyIntent.InitialIntent -> MyAction.SyncBackendAction(123)
        }
    }

    private fun bindDisposable() {
        Timber.i("bindDisposable")
        disposables.add(buildMVIArchitecture())
    }

}