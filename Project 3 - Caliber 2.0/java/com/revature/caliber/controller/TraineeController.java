package com.revature.caliber.controller;

import java.util.List;

import javax.validation.Valid;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.caliber.beans.Trainee;
import com.revature.caliber.intercomm.TraineeClient;

/**
 * Controller for handling all requests having to do with trainees.
 * 
 * @author
 *
 */
@RestController
@RequestMapping("/trainee")
@CrossOrigin(origins = "*")
public class TraineeController {

	@Autowired
	private TraineeClient client;

	private static final Logger log = Logger.getLogger(TraineeController.class);

	/**
	 * Handles get request for returning all trainees with the given batch id as a
	 * request parameter. defaultValue = 1
	 * 
	 * @param batch The batch id representing the batch to get all the trainees from
	 * @return The list of trainees with the correct batch id.
	 */
	@GetMapping("/trainees")
	public ResponseEntity<List<Trainee>> findAllByBatch(@RequestParam(defaultValue = "1") Integer batch) {

		return client.findAllByBatch(batch);
	}
	
	

	/**
	 * Handles put request for updating a trainee in a batch
	 * 
	 * @param trainee The trainee to be updated
	 * @return The updated Trainee object and an accepted http-status code
	 */
	@PutMapping("/update")
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public ResponseEntity<Trainee> updateTrainee(@Valid @RequestBody Trainee trainee) {
		return client.updateTrainee(trainee);
	}

}
