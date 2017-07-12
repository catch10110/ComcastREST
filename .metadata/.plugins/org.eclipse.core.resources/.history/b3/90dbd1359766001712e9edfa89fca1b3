package org.comcast.ad.model;

import java.time.LocalDateTime;

public class CampaignModel {
	private String partnerId;
	private String adContent;
	private LocalDateTime expiration;
	
	public CampaignModel() {
		;
	}
	
	public CampaignModel(PartnerAdModel partnerAdModel) {
		super();
		this.partnerId = partnerAdModel.getPartnerId();
		this.adContent = partnerAdModel.getAdContent();
		this.expiration = LocalDateTime.now().plusSeconds(partnerAdModel.getDuration());
	}

	public String getAdContent() {
		return adContent;
	}
	public void setAdContent(String adContent) {
		this.adContent = adContent;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public LocalDateTime getExpiration() {
		return expiration;
	}

	public void setExpiration(LocalDateTime expiration) {
		this.expiration = expiration;
	}
}
