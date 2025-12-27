package com.exilant.tfw.pojo.def;

import java.util.List;

import com.exilant.tfw.pojo.SchedulerLaneDetails;
import com.exilant.tfw.pojo.output.LaneResults;

/**
 * For testing and to run TFW without app container and in one jvm
 * */
public interface LaneListener {

	List<LaneResults> process(SchedulerLaneDetails schedulerLaneDetails);

	void masterPlanStart(RunResult runr, SchedulerLaneDetails schedulerLaneDetails);
}
