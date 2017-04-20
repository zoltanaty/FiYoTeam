package com.fiyoteam.utils;

import java.util.Comparator;

import com.fiyoteam.model.Project;

public class ProjectDateSorter implements Comparator<Project>{

	@Override
	public int compare(Project one, Project another) {
		return another.getCreatedAt().compareTo(one.getCreatedAt());
	}

}
