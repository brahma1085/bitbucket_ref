package com.sssoft.isatt.data.pojo.def;

import java.util.List;

import com.sssoft.isatt.data.pojo.SchedulerLaneDetails;
import com.sssoft.isatt.data.pojo.output.LaneResults;

/**
 * For testing and to run TFW without app container and in one jvm
 * */
public interface LaneListener {

	List<LaneResults> process(SchedulerLaneDetails schedulerLaneDetails);

	void masterPlanStart(RunResult runr, SchedulerLaneDetails schedulerLaneDetails);
}
