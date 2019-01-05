
package com.revature.caliber.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.revature.caliber.beans.Note;
import com.revature.caliber.beans.NoteType;

/**
 * Handles retrieving notes from a database
 * 
 * @author
 *
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

	/**
	 * Updating content, week columns of note table.
	 * 
	 * @param content
	 * @param weekNumber
	 * @param noteId
	 * @return 1 if success
	 */
	@Modifying
	@Query("UPDATE Note n SET n.content = :noteContent, n.week = :weekNumber where n.noteId = :noteId")
	int updateWeekForNote(@Param("noteContent") String content, @Param("weekNumber") short weekNumber,
			@Param("noteId") int noteId);

	/**
	 * 
	 * @param batchId
	 * @param week
	 * @return
	 */
	@Query("SELECT n FROM Note n WHERE n.batchId = :batchId AND n.week = :week AND n.type = :type")
	public List<Note> findQCNotesByBatchAndWeek(
			@Param("batchId") Integer batchId,
			@Param("week") Short week,
			@Param("type") NoteType type);

	@Query("SELECT n FROM Note n WHERE n.batchId = :batchId AND n.week = :week AND n.type = :type")
	public Note findQCBatchNotes(@Param("batchId") Integer batchId, @Param("week") Short week,
			@Param("type") NoteType type);

	@Query("SELECT n FROM Note n WHERE n.traineeId = :traineeId")
	public List<Note> findByTraineeId(@Param("traineeId") int traineeId, Sort sort);

}
