package com.fiyoteam.rest;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiyoteam.model.Rating;
import com.fiyoteam.model.User;
import com.fiyoteam.model.DTO.RatingDTO;
import com.fiyoteam.persistence.Entitymanager;
import com.sun.jersey.spi.container.ResourceFilters;

@Path("/rating")
public class RatingService {
	private static final Logger log = LoggerFactory.getLogger(RatingService.class);

	@GET
	@Path("/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response getRatingForUser(@PathParam("userid") Integer userId){
		EntityManager em = Entitymanager.getEntityManagerInstance();
		User user = em.find(User.class, userId);
		Query query = em.createQuery("FROM Rating r WHERE r.voted = :user");
		query.setParameter("user", user);

		@SuppressWarnings("unchecked")
		ArrayList<Rating> resultList = (ArrayList<Rating>) query.getResultList();

		RatingDTO ratingDTO = new RatingDTO();
		
		if (resultList.size() > 0) {
			Double totalSumOfRatings = 0.0;
			int nrOf5 = 0;
			int nrOf4 = 0;
			int nrOf3 = 0;
			int nrOf2 = 0;
			int nrOf1 = 0;

			for (Rating rating : resultList) {
				totalSumOfRatings += rating.getRating();

				switch (rating.getRating()) {
				case 5:
					nrOf5++;
					break;

				case 4:
					nrOf4++;
					break;

				case 3:
					nrOf3++;
					break;

				case 2:
					nrOf2++;
					break;

				case 1:
					nrOf1++;
					break;
				}
			}

			ratingDTO.setVoted(user.getId());
			ratingDTO.setAvgRating(totalSumOfRatings / resultList.size());
			ratingDTO.setPercentage1Star((nrOf1 * 100) / resultList.size());
			ratingDTO.setPercentage2Star((nrOf2 * 100) / resultList.size());
			ratingDTO.setPercentage3Star((nrOf3 * 100) / resultList.size());
			ratingDTO.setPercentage4Star((nrOf4 * 100) / resultList.size());
			ratingDTO.setPercentage5Star((nrOf5 * 100) / resultList.size());

			log.info("Returned the rating of user: " + user.getId());

		}else{
			ratingDTO.setVoted(user.getId());
			ratingDTO.setAvgRating(0.0);
			ratingDTO.setPercentage1Star(0);
			ratingDTO.setPercentage2Star(0);
			ratingDTO.setPercentage3Star(0);
			ratingDTO.setPercentage4Star(0);
			ratingDTO.setPercentage5Star(0);
		}

		
		return Response.ok(ratingDTO).build();
	}
}
