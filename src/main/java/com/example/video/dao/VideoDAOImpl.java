package com.example.video.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.video.constants.DataConstant;
import com.example.video.exception.DBException;
import com.example.video.model.Video;
import com.example.video.model.Category;
import com.example.video.model.Level;
import com.example.video.model.ReferenceArtifact;
import com.example.video.model.ReferenceUrl;
import com.example.video.model.SampleProgram;

@Repository
public class VideoDAOImpl implements IVideoDAO{

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public List<Video> getAllVideos() throws DBException {
		List<Video> videoList;
		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "FROM Video";
			Query<Video> query = session.createQuery(hql, Video.class);
			videoList = query.getResultList();
		} catch (Exception e) {
			throw new DBException("Error in fetching video records");
		}
		return videoList;
	}

	@Override
	public List<Level> getAllLevels() throws DBException {
		List<Level> levelList;
		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "FROM Level";
			Query<Level> query = session.createQuery(hql, Level.class);
			levelList = query.getResultList();
		} catch (Exception e) {
			throw new DBException("Error in fetching level records");
		}
		return levelList;
	}

	@Override
	public List<Category> getAllCategories() throws DBException {
		List<Category> categoryList;
		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "FROM Category";
			Query<Category> query = session.createQuery(hql, Category.class);
			categoryList = query.getResultList();
		} catch (Exception e) {
			throw new DBException("Error in fetching category records");
		}
		return categoryList;
	}

	@Override
	public Video getVideoById(int videoId) throws DBException {
		Video video;
		try {
			Session session = sessionFactory.getCurrentSession();
			video = session.get(Video.class, videoId);
		} catch (Exception e) {
			throw new DBException("Error in fetching video record");
		}
		return video;
	}

	@Override
	public List<Video> getActivatedVideos() throws DBException {

		List<Video> videos = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "From Video v where v.status=true";
			Query<Video> query = session.createQuery(hql, Video.class);
			videos = query.getResultList();

		} catch (Exception e) {
			throw new DBException("Error in fetching activated video records");
		}
		return videos;
	}

	@Override
	public void toggleStatus(int videoId) throws DBException {
		Video video;
		try {
			Session session = sessionFactory.getCurrentSession();
			Transaction transaction = session.beginTransaction();
			String hql = "FROM Video v where v.id = :videoId";
			Query<Video> query = session.createQuery(hql, Video.class);
			query.setParameter("videoId", videoId);
			video = query.getSingleResult();
			video.setStatus(!video.getStatus());
			session.update(video);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			throw new DBException("Error in fetching activated video records");
		}
	}

	@Override
	public List<Video> getDeactivatedVideos() throws DBException {
		List<Video> videos = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Transaction transaction = session.beginTransaction();
			String hql ="From Video v where v.status=false";
			Query<Video> query = session.createQuery(hql, Video.class);
			videos = query.getResultList();
			transaction.commit();
			session.close();
		} catch (Exception e) {
			throw new DBException("Error in fetching activated video records");
		}
		return videos;
	}

	@Override
	public void deleteVideoById(int videoId) throws DBException {
		Video video;
		try {

			Session session = sessionFactory.getCurrentSession();
			Transaction transaction = session.beginTransaction();
			video = session.get(Video.class, videoId);
			session.delete(video);
			transaction.commit();
			session.close();
		}

		catch (Exception e) {
			throw new DBException("Error in deleting records");
		}

	}

	@Override
	public ReferenceArtifact getReferenceArtifactById(int id) throws DBException {
		ReferenceArtifact refArt;
		try {
			Session session = sessionFactory.getCurrentSession();
			refArt = session.get(ReferenceArtifact.class, id);
		} catch (Exception e) {
			throw new DBException("Error in fetching records");
		}
		return refArt;
	}

	@Override
	public void deleteReferenceArtifactById(int id) throws DBException {
		try {
			Session session = sessionFactory.getCurrentSession();
			Transaction transaction = session.beginTransaction();
			String hql = "delete from ReferenceArtifact r where r.id=:id";
			Query<ReferenceArtifact> query = session.createQuery(hql);
			query.setParameter("id", id);
			query.executeUpdate();
			transaction.commit();
			session.close();
		}

		catch (Exception e) {
			throw new DBException("Error in deleting reference artifact records");
		}
	}

	@Override
	public SampleProgram getSampleProgramById(int id) throws DBException {
		SampleProgram samProg;
		try {
			Session session = sessionFactory.getCurrentSession();
			samProg = session.get(SampleProgram.class, id);
		} catch (Exception e) {
			throw new DBException("Error in fetching records");
		}
		return samProg;
	}

	@Override
	public void deleteSampleProgramById(int Id) throws DBException {
		try {
			Session session = sessionFactory.getCurrentSession();
			Transaction transaction = session.beginTransaction();
			String hql = "delete from SampleProgram s where s.id=:Id";
			Query<SampleProgram> query = session.createQuery(hql);
			query.setParameter("Id", Id);
			query.executeUpdate();
			transaction.commit();
			session.close();
		} catch (Exception e) {
			throw new DBException("Error in deleting sample program records");
		}
	}

	@Override
	public ReferenceUrl getReferenceUrlById(int id) throws DBException {
		ReferenceUrl refUrl;
		try {
			Session session = sessionFactory.getCurrentSession();
			refUrl = session.get(ReferenceUrl.class, id);
		} catch (Exception e) {
			throw new DBException("Error in fetching records");
		}
		return refUrl;
	}

	@Override
	public void deleteReferenceUrlById(int Id) throws DBException {
		try {
			Session session = sessionFactory.getCurrentSession();
			Transaction transaction = session.beginTransaction();
			String hql = "delete from ReferenceUrl r where r.id=:Id";
			Query<ReferenceUrl> query = session.createQuery(hql);
			query.setParameter("Id", Id);
			query.executeUpdate();
			transaction.commit();
			session.close();
		} catch (Exception e) {
			throw new DBException("Error in deleting reference url records");
		}
	}

	public String getFilePath(String file) {
		String fileName = file.substring(file.lastIndexOf("\\") + 1);
		String fileStorePath = DataConstant.UPLOAD_DIRECTORY.concat(fileName);
		return fileStorePath;
	}

	@Override
	public void addVideo(Video video) throws DBException {
		Transaction transaction = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			List<ReferenceArtifact> referenceArtifactList = video.getReferenceArtifact();
			ReferenceArtifact refArt[] = new ReferenceArtifact[referenceArtifactList.size()];
			if (!referenceArtifactList.isEmpty()) {
				for (int i = 0; i < referenceArtifactList.size(); i++) {

					refArt[i] = referenceArtifactList.get(i);
					refArt[i].setFile(getFilePath(refArt[i].getFile()));
					referenceArtifactList.set(i, refArt[i]);
					referenceArtifactList.get(i).setVideo(video);
				}

				video.setReferenceArtifact(referenceArtifactList);
			}
			List<SampleProgram> sampleProgramList = video.getSampleProgram();
			SampleProgram samProg[] = new SampleProgram[sampleProgramList.size()];
			if (!sampleProgramList.isEmpty()) {
				for (int i = 0; i < sampleProgramList.size(); i++) {

					samProg[i] = sampleProgramList.get(i);
					samProg[i].setFile(getFilePath(samProg[i].getFile()));
					sampleProgramList.set(i, samProg[i]);
					sampleProgramList.get(i).setVideo(video);
				}

				video.setSampleProgram(sampleProgramList);
			}
			List<ReferenceUrl> referenceurlList = video.getReferenceUrl();
			if (!referenceurlList.isEmpty()) {
				for (int i = 0; i < referenceurlList.size(); i++) {
					referenceurlList.get(i).setVideo(video);
				}

				video.setReferenceUrl(referenceurlList);
			}
			Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
			video.setStatus(true);
			video.setCreatedOn(timeStamp);
			video.setCreatedBy("subhalakshmi");
			session.save(video);
			transaction.commit();
			session.close();
		}

		catch (Exception e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				throw new DBException("Video already exists");
			}
			throw new DBException("Unable to insert video records");
		}

	}

	@Override
	public void updateVideo(Video video) throws DBException {
		try {

			Session session = sessionFactory.getCurrentSession();
			Transaction transaction = session.beginTransaction();
			List<ReferenceArtifact> referenceArtifactList = video.getReferenceArtifact();
			ReferenceArtifact refArt[] = new ReferenceArtifact[referenceArtifactList.size()];
			if (!referenceArtifactList.isEmpty()) {
				for (int i = 0; i < referenceArtifactList.size(); i++) {

					refArt[i] = referenceArtifactList.get(i);
					refArt[i].setFile(getFilePath(refArt[i].getFile()));
					referenceArtifactList.set(i, refArt[i]);
					referenceArtifactList.get(i).setVideo(video);
				}

				video.setReferenceArtifact(referenceArtifactList);
			}
			List<SampleProgram> sampleProgramList = video.getSampleProgram();
			SampleProgram samProg[] = new SampleProgram[sampleProgramList.size()];
			if (!sampleProgramList.isEmpty()) {
				for (int i = 0; i < sampleProgramList.size(); i++) {

					samProg[i] = sampleProgramList.get(i);
					samProg[i].setFile(getFilePath(samProg[i].getFile()));
					sampleProgramList.set(i, samProg[i]);
					sampleProgramList.get(i).setVideo(video);
				}

				video.setSampleProgram(sampleProgramList);
			}
			List<ReferenceUrl> referenceurlList = video.getReferenceUrl();
			if (!referenceurlList.isEmpty()) {
				for (int i = 0; i < referenceurlList.size(); i++) {
					referenceurlList.get(i).setVideo(video);
				}

				video.setReferenceUrl(referenceurlList);
			}
			Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
			video.setCreatedOn(video.getCreatedOn());
			video.setCreatedBy(video.getCreatedBy());
			video.setModifiedOn(timeStamp);
			video.setModifiedBy("subhalakshmi");
			session.saveOrUpdate(video);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				throw new DBException("Video already exists");
			}
			throw new DBException("Unable to update video records");
		}
	}

}