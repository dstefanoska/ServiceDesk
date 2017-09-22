package com.daka.servicedesk.jobs;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * Created by Dana on 22-Sep-17.
 */

public class DakaJobCreator implements JobCreator{
    @Override
    public Job create(String tag) {
        switch (tag) {
            default:
                return null;
        }
    }
}
