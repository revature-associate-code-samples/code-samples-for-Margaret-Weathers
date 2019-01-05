package com.revature.caliber.service;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.revature.caliber.beans.Note;
import com.revature.caliber.beans.NoteType;
import com.revature.caliber.beans.QCStatus;
import com.revature.caliber.beans.Trainee;
import com.revature.caliber.beans.TraineeFlag;
import com.revature.caliber.dao.NoteRepository;
import com.revature.caliber.intercomm.TraineeClient;

import feign.RetryableException;

@Service
public class EvaluationService {

	private static final Logger log = Logger.getLogger(EvaluationService.class);

	@Autowired
	NoteRepository noteRepo;
	@Autowired
	TraineeClient traineeClient;

	/**
	 * 
	 * Auto flags a trainee if they receive more than 1 "Poor" QC status or 
	 * consecutive "Average" and "Poor." 
	 * 
	 * @param note - The Trainee's updated Note, which contains their QC Status
	 * @return Trainee - The Trainee object with the updated FlagStatus and FlagNotes.
	 * 		Returns the original Trainee if no update was made.
	 * 
	 */
	public Trainee checkIfTraineeShouldBeFlagged(Note note) {
		if (note != null && note.getType().equals(NoteType.QC_TRAINEE)) {
			// Cannot be auto flagged on week 1
			if (note.getWeek() < 2) return note.getTrainee();

			// Retrieve a list of all notes in week ASC order
			List<Note> notes = noteRepo.findByTraineeId(note.getTraineeId(), new Sort("week"));
			System.out.println("LIST OF NOTES: " + notes);
			try {
				QCStatus currentStatus = note.getQcStatus();
				QCStatus prevStatus = notes.get(note.getWeek() - 2).getQcStatus();
				// If trainee receives consecutive Poor-->Average, Average-->Poor, or Poor-->Poor
				// 	then consecutive = true.
				boolean consecutive = ( (prevStatus.equals(QCStatus.Poor) && currentStatus.equals(QCStatus.Average)) 
						|| (prevStatus.equals(QCStatus.Average) && currentStatus.equals(QCStatus.Poor))
						|| (prevStatus.equals(QCStatus.Poor) && currentStatus.equals(QCStatus.Poor)))
						? true : false;
				int poor = 0;
				for (Note n : notes) {
					if (n.getQcStatus() == QCStatus.Poor) {
						poor++;
					}
				}
				// Auto flag
				Trainee trainee = note.getTrainee();
				if (consecutive || (poor >= 2)) {				
					trainee.setFlagStatus(TraineeFlag.RED);
					// If auto generated note does not exist, create one.
					if (trainee.getFlagNotes() == null) {
						trainee.setFlagNotes("Trainee was automatically flagged by Caliber on week "
							+ note.getWeek() + ". ");
					}
					// Else if custom note exists, append auto generated note to the end.
					else if (!trainee.getFlagNotes().contains("Trainee was automatically flagged by Caliber")) {
						trainee.setFlagNotes(trainee.getFlagNotes() + " Trainee was automatically flagged by "
								+ "Caliber on week " + note.getWeek() + ". ");
					}
					traineeClient.updateTrainee(trainee);
				}
				// Else if trainee was accidentally auto flagged, remove flag
				else if (trainee.getFlagNotes().contains("Trainee was automatically flagged by Caliber")){
					trainee.setFlagStatus(TraineeFlag.NONE);
					trainee.setFlagNotes(trainee.getFlagNotes()
							.substring(0, trainee.getFlagNotes().lastIndexOf("Trainee was automatically flagged by Caliber")));
					traineeClient.updateTrainee(trainee);
				}
				
				return trainee;
				
			} catch(RetryableException e) {
				log.debug("Failed to connect to User Service");
				return note.getTrainee();
			} catch (Exception e) {
				log.debug("Failed to autoflag associate with note: " + note);
				log.debug(e);
				return note.getTrainee();
			}
		}
		return note.getTrainee();
	}


	public void calculateAverage(short weekId, Integer batchId) {
		if(batchId !=  null) {
			Note overallNote = noteRepo.findQCBatchNotes(batchId, weekId, NoteType.QC_BATCH);
			log.trace("calculating average of overall note: " + overallNote);
			double average = 0.0f;
			List<Note> traineeNoteList = noteRepo.findQCNotesByBatchAndWeek(batchId, weekId, NoteType.QC_TRAINEE);
			int denominator = traineeNoteList.size();
			for(Note note : traineeNoteList) {
				switch(note.getQcStatus()) {
				case Superstar:
					average += 4;
					break;
				case Good:
					average += 3;
					break;
				case Average:
					average += 2;
					break;
				case Poor:
					average += 1;
					break;
				default:
					denominator--;
				}
			}
			if(denominator != 0) {
				average /= denominator;
			}else {
				average = 0.0f;
			}
			if(average > 2.5) {
				overallNote.setQcStatus(QCStatus.Good);
			} else if(average >= 2 && average <= 2.5) {
				overallNote.setQcStatus(QCStatus.Average);
			} else if (average > 0 && average < 2) {
				overallNote.setQcStatus(QCStatus.Poor);
			} else {
				overallNote.setQcStatus(QCStatus.Undefined);
			}
			log.trace("The calculated average is: " + overallNote.getQcStatus());
			noteRepo.save(overallNote);
		}
	}


}
