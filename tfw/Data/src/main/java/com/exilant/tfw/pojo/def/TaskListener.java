package com.exilant.tfw.pojo.def;

import com.exilant.tfw.pojo.SchedulerLaneDetails;

public interface TaskListener {

	void completed(SchedulerLaneDetails schedulerLaneDetails);
}
