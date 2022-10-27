package com.example.androidrecape.schedulers

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

class MyJobService : JobService() {

    private var isJobCancelled = false
    override fun onStartJob(params: JobParameters?): Boolean {
        //This is also work on main thread of application
        //you have to create your own background thread
        doBackgroundWork(params)
       return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        isJobCancelled = true
        Log.v(TAG,"Job cancelled")
       return false
    }

    private fun doBackgroundWork(params: JobParameters?){
        Log.v(TAG,"Job Started")
        object :Thread(){
            override fun run() {
                super.run()

                for (i in 10 downTo 0){
                    //explicity close this running thread
                    if(isJobCancelled){
                        return
                    }
                    try {
                        Log.v(TAG,"Job work at $i")
                        sleep(1000)
                    }catch (ex :Exception){
                        Log.v(TAG,"error while running job")
                    }
                    jobFinished(params,false)
                }
            }
        }.start()
    }

    companion object{
         const val TAG = "MyJobService"
         const val JOB_ID = 31
    }
}