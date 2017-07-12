package org.comcast.ad.service;

import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import org.comcast.ad.database.DatabaseClass;
import org.comcast.ad.exception.DataNotFoundException;
import org.comcast.ad.exception.InvalidInputException;
import org.comcast.ad.model.CampaignModel;
import org.comcast.ad.model.PartnerAdModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AdServiceTest {

	public AdService adService;
	
	@Before
	public void setup() {
		DatabaseClass.campaignMap = new HashMap<>();
		adService = new AdService();
	}

	@Test
	public void testGetAllAds_RetrieveAllAds_NoCampaigns() {
		List<CampaignModel> campaignModelList = adService.getAllAds();
		Assert.assertEquals(0, campaignModelList.size());
	}
	
	@Test
	public void testGetAllAds_NoActiveCampaigns() throws Exception {
		PartnerAdModel partnerAdModel1 = new PartnerAdModel("1", 1, "TestContent");
		CampaignModel model1 = new CampaignModel(partnerAdModel1);
		PartnerAdModel partnerAdModel2 = new PartnerAdModel("2", 2, "Test2");
		CampaignModel model2 = new CampaignModel(partnerAdModel2);
		PartnerAdModel partnerAdModel3 = new PartnerAdModel("2", 2, "Test22");
		CampaignModel model3 = new CampaignModel(partnerAdModel3);
		Queue<CampaignModel> q1 = new LinkedList<>();
		q1.add(model1);
		DatabaseClass.campaignMap.put("1", q1);
		Queue<CampaignModel> q2 = new LinkedList<>();
		q2.add(model2);
		q2.add(model3);
		DatabaseClass.campaignMap.put("2", q2);
		
		TimeUnit.SECONDS.sleep(2);
		
		List<CampaignModel> campaignModelList = adService.getAllAds();
		Assert.assertEquals(0, campaignModelList.size());
	}
	
	@Test
	public void testGetAllAds_RetrieveAllAds() {
		PartnerAdModel partnerAdModel1 = new PartnerAdModel("1", 1, "TestContent");
		CampaignModel model1 = new CampaignModel(partnerAdModel1);
		PartnerAdModel partnerAdModel2 = new PartnerAdModel("2", 2, "Test2");
		CampaignModel model2 = new CampaignModel(partnerAdModel2);
		PartnerAdModel partnerAdModel3 = new PartnerAdModel("2", 2, "Test22");
		CampaignModel model3 = new CampaignModel(partnerAdModel3);
		Queue<CampaignModel> q1 = new LinkedList<>();
		q1.add(model1);
		DatabaseClass.campaignMap.put("1", q1);
		Queue<CampaignModel> q2 = new LinkedList<>();
		q2.add(model2);
		q2.add(model3);
		DatabaseClass.campaignMap.put("2", q2);
		
		List<CampaignModel> campaignModelList = adService.getAllAds();
		Assert.assertEquals(3, campaignModelList.size());
	}

	@Test
	public void testGetAllAdsByPartner_UserDoesNotExist() {
		try {
			adService.getAllAdsByPartner("1");
			fail("Expected a DataNotFoundException.");
		} catch (DataNotFoundException e) {
			Assert.assertEquals("Ad campaign with partner id [1] was not found.", e.getMessage());
		}
	}

	@Test
	public void testGetAllAdsByPartner_UserCampaignsHaveExpired() throws Exception {
		PartnerAdModel partnerAdModel1 = new PartnerAdModel("1", 1, "TestContent");
		CampaignModel model1 = new CampaignModel(partnerAdModel1);
		Queue<CampaignModel> q = new LinkedList<>();
		q.add(model1);
		DatabaseClass.campaignMap.put("1", q);
		
		TimeUnit.SECONDS.sleep(1);
		
		try {
			adService.getAllAdsByPartner("1");
			fail("Expected a DataNotFoundException.");
		} catch (DataNotFoundException e) {
			Assert.assertEquals("Ad campaign with partner id [1] does not contain any active campaigns.", e.getMessage());
		}
	}
	
	@Test
	public void testGetAllAdsByPartner_RetrieveAllCurrentCamapaignByUser() {
		PartnerAdModel partnerAdModel1 = new PartnerAdModel("1", 1, "TestContent");
		CampaignModel model1 = new CampaignModel(partnerAdModel1);
		PartnerAdModel partnerAdModel2 = new PartnerAdModel("2", 2, "Test2");
		CampaignModel model2 = new CampaignModel(partnerAdModel2);
		PartnerAdModel partnerAdModel3 = new PartnerAdModel("2", 2, "Test22");
		CampaignModel model3 = new CampaignModel(partnerAdModel3);
		Queue<CampaignModel> q1 = new LinkedList<>();
		q1.add(model1);
		DatabaseClass.campaignMap.put("1", q1);
		Queue<CampaignModel> q2 = new LinkedList<>();
		q2.add(model2);
		q2.add(model3);
		DatabaseClass.campaignMap.put("2", q2);
		
		List<CampaignModel> campaignModelList = adService.getAllAdsByPartner("2");
		Assert.assertEquals("2", campaignModelList.get(0).getPartnerId());
		Assert.assertTrue(LocalDateTime.now().isBefore(campaignModelList.get(0).getExpiration()));
		Assert.assertEquals("Test2", campaignModelList.get(0).getAdContent());
		
		Assert.assertEquals("2", campaignModelList.get(1).getPartnerId());
		Assert.assertTrue(LocalDateTime.now().isBefore(campaignModelList.get(1).getExpiration()));
		Assert.assertEquals("Test22", campaignModelList.get(1).getAdContent());
	}
	
	@Test
	public void testGetCurrentAdByPartnerId_UserDoesNotExist() throws Exception {
		try {
			adService.getCurrentAdByPartnerId("1");
			fail("Expected a DataNotFoundException.");
		} catch (DataNotFoundException e) {
			Assert.assertEquals("Ad campaign with partner id [1] was not found.", e.getMessage());
		}
	}
	
	@Test
	public void testGetCurrentAdByPartnerId_UserCampaignsHaveExpired() throws Exception {
		PartnerAdModel partnerAdModel1 = new PartnerAdModel("1", 1, "TestContent");
		CampaignModel model1 = new CampaignModel(partnerAdModel1);
		Queue<CampaignModel> q = new LinkedList<>();
		q.add(model1);
		DatabaseClass.campaignMap.put("1", q);
		
		TimeUnit.SECONDS.sleep(1);
		
		try {
			adService.getCurrentAdByPartnerId("1");
			fail("Expected a DataNotFoundException.");
		} catch (DataNotFoundException e) {
			Assert.assertEquals("Ad campaign with partner id [1] does not contain any active campaigns.", e.getMessage());
		}
	}
	
	@Test
	public void testGetCurrentAdByPartnerId_RetrieveCurrentAdCampaign() {
		PartnerAdModel partnerAdModel1 = new PartnerAdModel("1", 1, "TestContent");
		CampaignModel model1 = new CampaignModel(partnerAdModel1);
		Queue<CampaignModel> q = new LinkedList<>();
		q.add(model1);
		DatabaseClass.campaignMap.put("1", q);
		
		
		CampaignModel campaignModel = adService.getCurrentAdByPartnerId("1");
		Assert.assertEquals("1", campaignModel.getPartnerId());
		Assert.assertTrue(LocalDateTime.now().isBefore(campaignModel.getExpiration()));
		Assert.assertEquals("TestContent", campaignModel.getAdContent());
	}
	
	@Test
	public void testGetCurrentAdByPartnerId_RetrieveCurrentAdCampaignAfterFirstExpires() throws Exception {
		PartnerAdModel partnerAdModel1 = new PartnerAdModel("1", 1, "TestContent");
		CampaignModel model1 = new CampaignModel(partnerAdModel1);
		PartnerAdModel partnerAdModel2 = new PartnerAdModel("1", 2, "Test2");
		CampaignModel model2 = new CampaignModel(partnerAdModel2);
		Queue<CampaignModel> q = new LinkedList<>();
		q.add(model1);
		q.add(model2);
		DatabaseClass.campaignMap.put("1", q);
		
		TimeUnit.SECONDS.sleep(1);
		
		CampaignModel campaignModel = adService.getCurrentAdByPartnerId("1");
		Assert.assertEquals("1", campaignModel.getPartnerId());
		Assert.assertTrue(LocalDateTime.now().isBefore(campaignModel.getExpiration()));
		Assert.assertEquals("Test2", campaignModel.getAdContent());
	}
	
	@Test
	public void testGetCurrentAdByPartnerId() throws Exception {
		PartnerAdModel partnerAdModel1 = new PartnerAdModel("1", 1, "TestContent");
		CampaignModel model1 = new CampaignModel(partnerAdModel1);
		PartnerAdModel partnerAdModel2 = new PartnerAdModel("1", 2, "");
		CampaignModel model2 = new CampaignModel(partnerAdModel2);
		Queue<CampaignModel> q = new LinkedList<>();
		q.add(model1);
		q.add(model2);
		DatabaseClass.campaignMap.put("1", q);
		
		Assert.assertEquals("1", adService.getCurrentAdByPartnerId("1").getPartnerId());
		Assert.assertEquals("TestContent", adService.getCurrentAdByPartnerId("1").getAdContent());

		TimeUnit.SECONDS.sleep(1);
		
		Assert.assertEquals("1", adService.getCurrentAdByPartnerId("1").getPartnerId());
		Assert.assertEquals("", adService.getCurrentAdByPartnerId("1").getAdContent());

		TimeUnit.SECONDS.sleep(2);
		
		try {
			adService.getCurrentAdByPartnerId("1");
			fail("Expected a DataNotFoundException.");
		} catch (DataNotFoundException e) {
			Assert.assertEquals("Ad campaign with partner id [1] does not contain any active campaigns.", e.getMessage());
		}
	}

	@Test
	public void testAddAd_PartnerIdIsEmpty() {
		PartnerAdModel ad = new PartnerAdModel("", 1, "Content");
		
		try {
			adService.addAd(ad);
			fail("Expected an InvalidInputException.");
		} catch (InvalidInputException e) {
			Assert.assertEquals("The partner ID cannot be empty.", e.getMessage());
		}
	}
	
	@Test
	public void testAddAd_DurationIsInvalid() {
		PartnerAdModel ad = new PartnerAdModel("1", 0, "Content");
		
		try {
			adService.addAd(ad);
			fail("Expected an InvalidInputException.");
		} catch (InvalidInputException e) {
			Assert.assertEquals("The duration cannot be <= 0.", e.getMessage());
		}
	}
	
	@Test
	public void testAddAd_AdContentIsEmpty() {
		PartnerAdModel ad = new PartnerAdModel("1", 1, "");
		
		try {
			adService.addAd(ad);
			fail("Expected an InvalidInputException.");
		} catch (InvalidInputException e) {
			Assert.assertEquals("The ad content cannot be empty.", e.getMessage());
		}
	}
	
	@Test
	public void testAddAd_AddValid() {
		PartnerAdModel ad = new PartnerAdModel("1", 1, "Test");
		
		adService.addAd(ad);
		CampaignModel model = DatabaseClass.campaignMap.get("1").poll();
		Assert.assertEquals("1", model.getPartnerId());
		Assert.assertTrue(LocalDateTime.now().isBefore(model.getExpiration()));
		Assert.assertEquals("Test", model.getAdContent());
	}
	
	@Test
	public void testAddAd_AddToPartnerWithAd() {
		PartnerAdModel partnerAdModel1 = new PartnerAdModel("1", 1, "TestContent");
		CampaignModel model1 = new CampaignModel(partnerAdModel1);
		Queue<CampaignModel> q = new LinkedList<>();
		q.add(model1);
		DatabaseClass.campaignMap.put("1", q);
		
		PartnerAdModel ad1 = new PartnerAdModel("1", 1, "Test1");
		
		adService.addAd(ad1);
		CampaignModel model = DatabaseClass.campaignMap.get("1").poll();
		Assert.assertEquals("1", model.getPartnerId());
		Assert.assertTrue(LocalDateTime.now().isBefore(model.getExpiration()));
		Assert.assertEquals("TestContent", model.getAdContent());
		
		model = DatabaseClass.campaignMap.get("1").poll();
		Assert.assertEquals("1", model.getPartnerId());
		Assert.assertTrue(LocalDateTime.now().isBefore(model.getExpiration()));
		Assert.assertEquals("Test1", model.getAdContent());
	}
	
	@Test
	public void testAddAd_AddToNonEmptyDB() {
		PartnerAdModel partnerAdModel1 = new PartnerAdModel("1", 1, "TestContent");
		CampaignModel model1 = new CampaignModel(partnerAdModel1);
		Queue<CampaignModel> q = new LinkedList<>();
		q.add(model1);
		DatabaseClass.campaignMap.put("1", q);
		
		PartnerAdModel ad1 = new PartnerAdModel("2", 1, "Test2");
		
		adService.addAd(ad1);
		CampaignModel model = DatabaseClass.campaignMap.get("1").poll();
		Assert.assertEquals("1", model.getPartnerId());
		Assert.assertTrue(LocalDateTime.now().isBefore(model.getExpiration()));
		Assert.assertEquals("TestContent", model.getAdContent());
		
		model = DatabaseClass.campaignMap.get("2").poll();
		Assert.assertEquals("2", model.getPartnerId());
		Assert.assertTrue(LocalDateTime.now().isBefore(model.getExpiration()));
		Assert.assertEquals("Test2", model.getAdContent());
	}
}















