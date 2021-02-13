package com.its.httpserver.models;

import java.io.Serializable;
import java.util.Date;

public class CreateControllerRequestModel implements Serializable {
	private String controllerPath;
	private String dtEventDatetime;

	public String getControllerPath() {
		return controllerPath;
	}

	public void setControllerPath(String controllerPath) {
		this.controllerPath = controllerPath;
	}

	public String getDtEventDatetime() {
		return dtEventDatetime;
	}

	public void setDtEventDatetime(String dtEventDatetime) {
		this.dtEventDatetime = dtEventDatetime;
	}
}
