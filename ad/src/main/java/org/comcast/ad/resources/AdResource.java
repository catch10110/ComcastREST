package org.comcast.ad.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.comcast.ad.model.CampaignModel;
import org.comcast.ad.model.PartnerAdModel;
import org.comcast.ad.service.AdService;

@Path("/ad")
public class AdResource {
	
	AdService adService = new AdService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CampaignModel> getAllAds() {
		return adService.getAllAds();
	}
	
	@GET
	@Path("/{partnerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CampaignModel> getCurrentCampaignById(@PathParam("partnerId") String partnerId, @QueryParam("all") boolean all) {
		if (all) {
			return adService.getAllAdsByPartner(partnerId);
		}
		
		List<CampaignModel> list = new ArrayList<CampaignModel>();
		list.add(adService.getCurrentAdByPartnerId(partnerId));
		return list;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAd(PartnerAdModel ad) {
		adService.addAd(ad);
		return Response.status(Status.CREATED).build();
	}
}
