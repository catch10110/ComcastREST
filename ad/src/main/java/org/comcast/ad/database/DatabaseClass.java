package org.comcast.ad.database;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import org.comcast.ad.model.CampaignModel;

/**
 * This is an in memory database mock for the demonstration of this project.
 * Everything is static so any method can access it like a real DB.
 * This is not thread safe.
 */

public class DatabaseClass {

	public static Map<String, Queue<CampaignModel>> campaignMap = new HashMap<>();

	public static Map<String, Queue<CampaignModel>> getCampaignMap() {
		return campaignMap;
	}
	
	public static void ResetDB() {
		campaignMap = new HashMap<>();
	}
}