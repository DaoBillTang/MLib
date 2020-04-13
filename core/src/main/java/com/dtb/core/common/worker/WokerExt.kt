package com.dtb.core.common.worker

import android.content.Context
import android.net.Uri
import androidx.work.*
import java.util.concurrent.TimeUnit


/**
 * project frame
 * Created by dtb on 2020/4/12
 * email: 1750352866@qq.com

 * @author: dtb
 * *
 * @version: 1.0
 * *
 * @description:
 *  workerClass :ListenableWorker,
 */


class WorkContext() {
    /**
     * 如果有尚未完成的前提性工作，则工作处于 BLOCKED State。
     * 如果工作能够在满足 Constraints 和时机条件后立即运行，则被视为处于 ENQUEUED 状态。
     * 当工作器在活跃地执行时，其处于 RUNNING State。
     * 如果工作器返回 Result.success()，则被视为处于 SUCCEEDED 状态。这是一种终止 State；只有 OneTimeWorkRequest 可以进入这种 State。
     * 相反，如果工作器返回 Result.failure()，则被视为处于 FAILED 状态。这也是一个终止 State；
     * 只有 OneTimeWorkRequest 可以进入这种 State。所有依赖工作也会被标记为 FAILED，并且不会运行。
     * 当您明确取消尚未终止的 WorkRequest 时，它会进入 CANCELLED State。所有依赖工作也会被标记为 CANCELLED，并且不会运行。
     */
    var workerClass: Class<out ListenableWorker?>? = null

    var context: Context? = null
    var repeatInterval: Long = 15
    var repeatIntervalTimeUnit: TimeUnit = TimeUnit.MINUTES
    var inputData: Data = Data.EMPTY
    var keepResultsForAtLeast: Pair<Long, TimeUnit>? = null // 设定保持结果的 最短时间
    var initialDelay: Pair<Long, TimeUnit>? = null // 设定 初始启动的延时

    /**
     * 如果您需要让 WorkManager 重新尝试执行您的任务，可以从工作器返回 Result.retry()。

    然后，系统会根据默认的退避延迟时间和政策重新调度您的工作。退避延迟时间指定重试工作前的最短等待时间。
    退避政策定义了在后续重试的尝试过程中，退避延迟时间随时间以怎样的方式增长；默认情况下按 EXPONENTIAL 延长。

    这个方法是设置重试机制的时间策略，有线性和指数增长两种方式。这个方法与jobFinished(params, true)有关。
     */
    var backoffCriteria: Triple<BackoffPolicy, Long, TimeUnit>? = null

    // tag 再 build  里面是 set 的 形式，但是在这个地方，暂时定义为 一个string
    var tag: String? = null


    private var mConstraints: Constraints = Constraints.NONE

    fun constraints(init: ConstraintsContext.() -> Unit) {
        mConstraints = ConstraintsContext().apply(init).build()
    }

    // 测试的时候用于 延迟启动的方案
    var testPeriodStartTime: Pair<Long, TimeUnit>? = null

    //runAttemptCount test only
    var testInitialRunAttemptCount: Int? = null

    fun build() {
        val builder = PeriodicWorkRequest.Builder(
            workerClass!!,
            repeatInterval,
            repeatIntervalTimeUnit
        ).setInputData(inputData)
            .setConstraints(mConstraints)

        keepResultsForAtLeast?.apply {
            builder.keepResultsForAtLeast(this.first, this.second)
        }

        initialDelay?.apply {
            builder.setInitialDelay(this.first, this.second)
        }
        backoffCriteria?.apply {
            builder.setBackoffCriteria(first, second, third)
        }
        tag?.apply {
            builder.addTag(this)
        }

        val request = builder.build()

        val manager = if (context != null) {
            WorkManager.getInstance(context!!)
        } else {
            WorkManager.getInstance()
        }
        manager.enqueue(request)
    }


    class ConstraintsContext {
        /**
         * 需要注意的是setRequiredNetworkType, setRequiresCharging 和
         * setRequiresDeviceIdle这几个方法可能会使得你的任务无法执行，
         * 除非调用setOverrideDeadline(long time)设置了最大延迟时间，
         * 使得你的任务在为满足条件的情况下也会被执行

         */
        val context = Constraints.Builder()
        var requiresCharging = false  //这个方法告诉你的应用，只有当设备在充电时这个任务才会被执行。
            set(value) {
                field = value
                context.setRequiresCharging(field)
            }
        var requiresDeviceIdle = false // 这个方法告诉你的任务只有当用户没有在使用该设备且有一段时间没有使用时才会启动该任务。
            set(value) {
                field = value
                context.setRequiresDeviceIdle(field)
            }
        var requiredNetworkType = NetworkType.NOT_REQUIRED
            set(value) {
                field = value
                context.setRequiredNetworkType(value)
            }
        var requiresBatteryNotLow = false  //是否要求不是低电量
            set(value) {
                field = value
                context.setRequiresBatteryNotLow(field)
            }
        var requiresStorageNotLow = false   // 是否要求不是低 存储
            set(value) {
                field = value
                context.setRequiresStorageNotLow(field)
            }

        // Same defaults as JobInfo
        //设置从content变化到任务被执行中间的延迟。如果在延迟期间content发生了变化，延迟会重新计算
        var triggerContentUpdateDelay: Pair<Long, TimeUnit>? = null
            set(value) {
                value?.apply {
                    field = value
                    context.setTriggerContentUpdateDelay(value.first, value.second)
                }
            }

        // 最大延迟 sdk>=24
        var triggerContentMaxDelay: Pair<Long, TimeUnit>? = null
            set(value) {
                value?.apply {
                    field = value
                    context.setTriggerContentMaxDelay(value.first, value.second)
                }
            }

        /**
         * 该Uri将利用ContentObserver来监控一个Content Uri，
         * 当且仅当其发生变化时将触发任务的执行。
         * 为了持续监控content的变化，你需要在最近的任务触发后再调度一个新的任务
         * （需要注意的是触发器URI不能与setPeriodic(long)或setPersisted(boolean)组合使用。
         * 要持续监控内容更改，需要在完成JobService处理最近的更改之前，调度新的JobInfo，观察相同的URI。
         * 因为设置此属性与定期或持久化Job不兼容，这样做会在调用build()时抛出IllegalArgumentException异常。)
         */
        var mContentUriTriggers: Pair<Uri, Boolean>? = null
            set(value) {
                value?.apply {
                    context.addContentUriTrigger(value.first, value.second)
                }
                field = value
            }

        fun build(): Constraints {
            return context.build()
        }
    }
}

class WorkerTest(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        return Result.failure()
    }
}

fun createWork(ctx: Context) {
//    DSL 方案才行

//    WorkManager.getInstance(ctx).enqueue(request);
}

fun createWork(init: WorkContext.() -> Unit) {
    val c = WorkContext().apply(init)

}

fun t1() {

    createWork {
        constraints {
        }
    }
}