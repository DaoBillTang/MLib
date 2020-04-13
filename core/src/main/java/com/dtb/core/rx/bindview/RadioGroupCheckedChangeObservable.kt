package com.dtb.core.rx.bindview

import android.widget.RadioGroup
import androidx.annotation.CheckResult
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable


/**
 * @author: dtb
 * @email: wendaoyuqin1@gmail.com
 * @createTime: 19-3-12上午10:28
 */


@CheckResult
fun RadioGroup.checkedChanges(): RadioGroupCheckedChangeObservable {
    return RadioGroupCheckedChangeObservable(this)
}

class RadioGroupCheckedChangeObservable internal constructor(private val view: RadioGroup)
    : InitialValueObservable<Int>() {

    override val initialValue get() = view.checkedRadioButtonId

    override fun subscribeListener(observer: Observer<in Int>) {
        if (!Preconditions.checkMainThread(observer)) {
            return
        }
        val listener = Listener(view, observer)
        observer.onSubscribe(listener)
        view.setOnCheckedChangeListener(listener)
    }

    private class Listener(
            private val view: RadioGroup,
            private val observer: Observer<in Int>
    ) : MainThreadDisposable(), RadioGroup.OnCheckedChangeListener {

        private var lastChecked = -1

        override fun onCheckedChanged(radioGroup: RadioGroup, checkedId: Int) {
            if (!isDisposed && checkedId != lastChecked) {
                lastChecked = checkedId
                observer.onNext(checkedId)
            }
        }

        override fun onDispose() {
            view.setOnCheckedChangeListener(null)
        }
    }
}