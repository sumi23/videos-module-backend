package com.example.video.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.io.File;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.video.exception.DBException;
import com.example.video.model.Video;
import com.example.video.model.Category;
import com.example.video.model.Level;
import com.example.video.model.ReferenceArtifact;
import com.example.video.model.ReferenceUrl;
import com.example.video.model.SampleProgram;

@Repository
public class VideoDAOImpl implements IVideoDAO {
	@Autowired
	SessionFactory sessionFactory;

	 private  static final String UPLOAD_DIRECTORY ="E:\\videomodule\\files";
	
	@Override
	public List<Video> getAllVideos() throws DBException {
		Session session = null;
		Transaction transaction = null;
		List<Video> videos = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "FROM Video";
			TypedQuery<Video> query = session.createQuery(hql);
			videos = query.getResultList();
			transaction.commit();
		}

		catch (Exception e) {
			throw new DBException("Error in fetching records");
		} finally {
			session.close();
		}
		return videos;
	}
	
	@Override
	public List<Level> getAllLevels() throws DBException {
		Session session = null;
		Transaction transaction = null;
		List<Level> videos = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "FROM Level";
			TypedQuery<Level> query = session.createQuery(hql);
			videos = query.getResultList();
			transaction.commit();
		}

		catch (Exception e) {
			throw new DBException("Error in fetching level records");
		} finally {
			session.close();
		}
		return videos;
	}
	
	@Override
	public List<Category> getAllCategories() throws DBException {
		Session session = null;
		Transaction transaction = null;
		List<Category> videos = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "FROM Category";
			TypedQuery<Category> query = session.createQuery(hql);
			videos = query.getResultList();
			transaction.commit();
		}

		catch (Exception e) {
			throw new DBException("Error in fetching level records");
		} finally {
			session.close();
		}
		return videos;
	}
	

	@Override
	public List<Video> getVideoById(int videoId) throws DBException {
		Session session = null;
		Transaction t = null;
		List<Video> video = null;
		try {
			session = sessionFactory.getCurrentSession();
			t = session.beginTransaction();
			String hql = "FROM Video video where video.id=:videoId";
			TypedQuery<Video> query = session.createQuery(hql);
			query.setParameter("videoId", videoId);
			video = query.getResultList();
			t.commit();
		} catch (Exception e) {
			throw new DBException("Error in fetching records");
		} finally {
			session.close();
		}
		return video;
	}

	@Override
	public List<Video> getActivatedVideos() throws DBException {
		Session session = null;
		Transaction transaction = null;
		List<Video> videos = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "Select v.name,v.displayName,v.url,"
					+ "v.duration,v.status "
					+ "from Video v where v.status=true";
			TypedQuery<Video> query = session.createQuery(hql);
			videos = query.getResultList();
			transaction.commit();
		}

		catch (Exception e) {
			throw new DBException("Error in fetching activated video records");
		} finally {
			session.close();
		}
		return videos;
	}

	@Override
	public void toggleStatus(int videoId) throws DBException{
		Session session = null;
		Transaction transaction = null;
		Video video=null;
		Boolean status1;
		try
		{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "Select v.status from Video v where v.id=:id";
			TypedQuery<Video> query = session.createQuery(hql);
			query.setParameter("id",videoId);
		    List stat=query.getResultList();
			if(stat.get(0).equals(true))
			{
				stat.add(0,false);
			}
			else
			{
				stat.add(0,true);
			}
			Boolean new1=(Boolean) stat.get(0);
			String hql1 = "update Video v  set v.status=:val where v.id=:id";
			TypedQuery<Video> query1 = session.createQuery(hql1);
			query1.setParameter("val",new1);
			query1.setParameter("id",videoId);				
			int res= query1.executeUpdate();
			transaction.commit();
		}

		catch (Exception e) {
			throw new DBException("Error in fetching activated video records");
		} finally {
			session.close();
		}
		
	}
	
	@Override
	public List<Video> getDeactivatedVideos() throws DBException {
		Session session = null;
		Transaction transaction = null;
		List<Video> videos = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			String hql = "Select v.name,v.displayName,v.url,"
					+ "v.duration,v.status "
					+ "from Video v where v.status=false";
			TypedQuery<Video> query = session.createQuery(hql);
			videos = query.getResultList();
			transaction.commit();
		}

		catch (Exception e) {
			throw new DBException("Error in fetching activated video records");
		} finally {
			session.close();
		}
		return videos;
	}

	@Override
	public List<Video> deleteVideoById(int videoId) throws DBException {
		Session session = null;
		//Video v;
		Transaction t = null;
		List<Video> video = null;
		try {
			session = sessionFactory.getCurrentSession();
			t = session.beginTransaction();
			/*
			 * String hql = "delete from Video video where video.id=:videoId";
			 * TypedQuery<Video> query = session.createQuery(hql);
			 * query.setParameter("videoId", videoId); int update = query.executeUpdate();
			 * if (update == 0 || update == 1) System.out.println(update + " row affected");
			 * else System.out.println(update + " rows affected");
			 */
			session.delete(session.get(Video.class, videoId));
			System.out.println("Deleted Records Successfully");
			t.commit();
		}

		catch (Exception e) {
			throw new DBException("Error in deleting records");
		} finally {
			session.close();
		}
		return video;
	}

	@Override
	public List<Video> deleteReferenceArtifactById(int Id) throws DBException {
		Session session = null;
		Transaction t = null;
		List<Video> video = null;
		try {
			session = sessionFactory.getCurrentSession();
			t = session.beginTransaction();
			String hql = "delete from reference_artifacts where id=:Id";
			TypedQuery<Video> query = session.createQuery(hql);
			query.setParameter("Id", Id);
			int update = query.executeUpdate();
			if (update == 0 || update == 1)
				System.out.println(update + " row affected");
			else
				System.out.println(update + " rows affected");

			System.out.println("Deleted reference artifact Records Successfully");
			t.commit();
		}

		catch (Exception e) {
			throw new DBException("Error in deleting reference artifact records");
		} finally {
			session.close();
		}
		return video;
	}
	
	
	@Override
	public List<Video> deleteSampleProgramById(int Id) throws DBException {
		Session session = null;
		Transaction t = null;
		List<Video> video = null;
		try {
			session = sessionFactory.getCurrentSession();
			t = session.beginTransaction();
			String hql = "delete from sample_programs where id=:Id";
			TypedQuery<Video> query = session.createQuery(hql);
			query.setParameter("Id", Id);
			int update = query.executeUpdate();
			if (update == 0 || update == 1)
				System.out.println(update + " row affected");
			else
				System.out.println(update + " rows affected");

			System.out.println("Deleted sample program Records Successfully");
			t.commit();
		}

		catch (Exception e) {
			throw new DBException("Error in deleting sample program records");
		} finally {
			session.close();
		}
		return video;
	}
	
	@Override
	public List<Video> deleteReferenceUrlById(int Id) throws DBException {
		Session session = null;
		Transaction t = null;
		List<Video> video = null;
		try {
			session = sessionFactory.getCurrentSession();
			t = session.beginTransaction();
			String hql = "delete from reference_urls where id=:Id";
			TypedQuery<Video> query = session.createQuery(hql);
			query.setParameter("Id", Id);
			int update = query.executeUpdate();
			if (update == 0 || update == 1)
				System.out.println(update + " row affected");
			else
				System.out.println(update + " rows affected");

			System.out.println("Deleted reference url Records Successfully");
			t.commit();
		}

		catch (Exception e) {
			throw new DBException("Error in deleting reference url records");
		} finally {
			session.close();
		}
		return video;
	}
	
	
	@Override
	public Video addVideo(Video video) throws DBException {
		String file,fileName,refArtFile,refArtFileName,samProgFile,samProgFileName;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
		/*	List<ReferenceArtifact> referenceArtifactList = video.getReferenceArtifact();
			if(referenceArtifactList!=null&&referenceArtifactList.size()>0)
			{
				for(int i=0;i<referenceArtifactList.size();i++) {
					referenceArtifactList.get(i).setVideo(video);
					System.out.println(referenceArtifactList.get(i));
				}
				
				video.setReferenceArtifact(referenceArtifactList);
			}
			List<SampleProgram> sampleProgramList = video.getSampleProgram();
			if(sampleProgramList!=null&&sampleProgramList.size()>0)
			{
				for(int i=0;i<sampleProgramList.size();i++) {
					sampleProgramList.get(i).setVideo(video);
				}
					
				video.setSampleProgram(sampleProgramList);
			}
			List<ReferenceUrl> referenceurlList = video.getReferenceUrl();
			if(referenceurlList!=null&&referenceurlList.size()>0)
			{
				for(int i=0;i<referenceurlList.size();i++) {
					referenceurlList.get(i).setVideo(video);
				}
					
				video.setReferenceUrl(referenceurlList);
			}
			
			Iterator<ReferenceArtifact> itr = referenceArtifactList.iterator();
			// traversing elements of ArrayList object
			while (itr.hasNext()) {
				ReferenceArtifact st = itr.next();
				System.out.println(st.getName() + " " + st.getFile() + " " + st.getDescription());
			}
			Timestamp ts=new Timestamp(System.currentTimeMillis());
			video.setCreatedOn(ts);
			session.save(video);*/
			System.out.println("video object"+video);
		String videoIns = "insert into videos(name,display_name,url,duration,tags,status,description,"
					+ "created_by,category_id,level_id,transcript) "
					+ "values(:name,:display_name,:url,:duration,:tags,:status,:description,"
					+ ":created_by,:category_id,:level_id,:transcript)";
			TypedQuery<Video> query = session.createSQLQuery(videoIns);
			query.setParameter("name", video.getName());
			query.setParameter("display_name", video.getDisplayName());
			query.setParameter("url", video.getUrl());
			query.setParameter("duration", video.getDuration());
			query.setParameter("tags", video.getTags());
			query.setParameter("status", true);
			query.setParameter("description", video.getDescription());
			query.setParameter("created_by", "abc");
		    query.setParameter("category_id", video.getCategory().getId());
            query.setParameter("level_id", video.getLevel().getId());
            file=video.getTranscript();
            fileName=file.substring(file.lastIndexOf(File.separator)+1);
            System.out.println("filename is"+fileName);
			query.setParameter("transcript",fileName);
			
			int update = query.executeUpdate();
			if (update == 0 || update == 1)
				System.out.println(update + " row affected");
			else
				System.out.println(update + " rows affected");

			System.out.println("Inserted Records Successfully");
			Long lastId = ((BigInteger) session.createSQLQuery("SELECT LAST_INSERT_ID() from videos LIMIT 1")
					.uniqueResult()).longValue();
			System.out.println("last id " + lastId);
			String refDetail = "";
			int count = 0;
			List<ReferenceArtifact> referenceArtifactList = video.getReferenceArtifact();
			if( referenceArtifactList.isEmpty()!=true) 
			{
			System.out.println("ref object"+referenceArtifactList);
			for (ReferenceArtifact ra : referenceArtifactList) {
				
				refArtFile=ra.getFile();
	            refArtFileName=refArtFile.substring(refArtFile.lastIndexOf(File.separator)+1);
				
				refDetail = refDetail + "(\"" + ra.getName() + "\"," + "\"" + refArtFileName + "\"" + ",\""
						+ ra.getDescription() + "\"" + "," + lastId + ")";
				count++;
				if (count < referenceArtifactList.size()) {
					refDetail = refDetail + ",";
				}

			}
			String hql1 = "insert into reference_artifacts(name,file,description,video_id) values" + refDetail;
			TypedQuery<Video> query1 = session.createSQLQuery(hql1);

			video.setReferenceArtifact(referenceArtifactList);
			int update1 = query1.executeUpdate();
			if (update1 == 0 || update1 == 1)
				System.out.println(update1 + " row affected");
			else
				System.out.println(update1 + " rows affected");

			System.out.println("Inserted  reference Records Successfully");
			
			}
			String samDetail = "";
			int count1 = 0;
			int size1;
			List<SampleProgram> sampleProgramList = video.getSampleProgram();
			if(sampleProgramList.isEmpty()!=true) {
			for (SampleProgram ra : sampleProgramList) {
				samProgFile=ra.getFile();
	            samProgFileName=samProgFile.substring(samProgFile.lastIndexOf(File.separator)+1);
				samDetail = samDetail + "(\"" + ra.getName() + "\"," + "\"" +samProgFileName + "\"" + ",\""
						+ ra.getDescription() + "\"" + "," + lastId + ")";
				count1++;
				System.out.println("count is" + count1);
				size1 = sampleProgramList.size();
				System.out.println("sample program  size is " + size1);
				if (count1 < sampleProgramList.size()) {
					System.out.println("new count is" + count1);
					size1 = sampleProgramList.size();
					System.out.println("new sample program size is " + size1);
					samDetail = samDetail + ",";
				}

			}
			String hql2 = "insert into sample_programs(name,file,description,video_id) values" + samDetail;
			TypedQuery<Video> query2 = session.createSQLQuery(hql2);

			video.setSampleProgram(sampleProgramList);
			int update2 = query2.executeUpdate();
			if (update2 == 0 || update2 == 1)
				System.out.println(update2 + " row affected");
			else
				System.out.println(update2 + " rows affected");

			System.out.println("Inserted  reference Records Successfully");
			Iterator<SampleProgram> itr1 = sampleProgramList.iterator();
			// traversing elements of ArrayList object
			while (itr1.hasNext()) {
				SampleProgram st = itr1.next();
				System.out.println(st.getName() + " " + st.getFile() + " " + st.getDescription());
			}
			}
			String refuDetail = "";
			int count3 = 0;
			int size3;
			List<ReferenceUrl> referenceurlList = video.getReferenceUrl();
			if(referenceurlList.isEmpty()!=true)
			{
			for (ReferenceUrl ra : referenceurlList) {
				refuDetail = refuDetail + "(\"" + ra.getName() + "\"," + "\"" + ra.getUrl() + "\"" + ",\""
						+ ra.getDescription() + "\"" + "," + lastId + ")";
				count3++;
				System.out.println("count is" + count3);
				size3 = referenceurlList.size();
				System.out.println("refernce artifact size is " + size3);
				if (count3 < referenceurlList.size()) {
					System.out.println("new count is" + count3);
					size3 = referenceurlList.size();
					System.out.println("new refernce artifact size is " + size3);
					refuDetail = refuDetail + ",";
				}

			}
			String hql3 = "insert into reference_urls(name,url,description,video_id) values" + refuDetail;
			TypedQuery<Video> query3 = session.createSQLQuery(hql3);

			video.setReferenceUrl(referenceurlList);
			int update3 = query3.executeUpdate();
			if (update3 == 0 || update3 == 1)
				System.out.println(update3 + " row affected");
			else
				System.out.println(update3 + " rows affected");
			
			}
			transaction.commit();
		} catch (Exception e) {
			throw new DBException("name and url must be unique");
		} finally {
			session.close();
		}
		return video;
	}
	
	@Override
	public Video updateVideo(Video video) throws DBException {
		Session session = null;
		Transaction transaction = null;
	    Video v;
	    String ufile="",ufileName="";
		try {
		    v=new Video();
		    session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();	
			String hql = "update videos set name=:name,display_name=:display_name,"
					+ "url=:url,tags=:tags,status=:status,"
					+ "description=:description,modified_by=:modified_by,"
					+ "level_id=:level_id,category_id=:category_id,"
					+ "transcript=:transcript "
					+ "where id=:id";	
			TypedQuery<Video> query = session.createSQLQuery(hql);
			query.setParameter("id",video.getId());
			query.setParameter("name", video.getName());
			query.setParameter("display_name", video.getDisplayName());
			query.setParameter("url", video.getUrl());
			query.setParameter("tags", video.getTags());
			query.setParameter("status", video.getStatus());
			query.setParameter("description", video.getDescription());
			query.setParameter("modified_by", "subhalakshmi");
			//Timestamp ts=new Timestamp(System.currentTimeMillis());
			//query.setParameter("modified_on",ts);
			query.setParameter("level_id", video.getLevel().getId());
			query.setParameter("category_id", video.getCategory().getId());
			ufile=video.getTranscript();
            ufileName=ufile.substring(ufile.lastIndexOf(File.separator)+1);
            System.out.println("filename is"+ufileName);
			query.setParameter("transcript",ufileName);
			int update = query.executeUpdate();
			if (update == 0 || update == 1)
				System.out.println(update + " row affected");
			else
				System.out.println(update + " rows affected");

			System.out.println("updated Records Successfully");

			int vid=video.getId();
		
			List<Integer> idlist = session.createSQLQuery("SELECT id from reference_artifacts where video_id="+vid).getResultList();
			int[] array = idlist.stream().mapToInt(i->i).toArray();
			int len=array.length;
			int s=0;
			System.out.println("id array length is "+len);
			for(int i=0;i<len;i++) {
				System.out.println("id value is"+array[i]);
			}
			
			String urefArtFile="",urefArtFileName="";
			String hql1="";
			String upstr="update reference_artifacts ";
			String namestr="set name=case ";
			String filestr=" file=case ";
			String descstr=" description=case ";
			String idstr="when id=";
			String end="end ";
			String condition="where video_id="+vid;
			List<ReferenceArtifact> referenceArtifactList = video.getReferenceArtifact();
			for(ReferenceArtifact ra:referenceArtifactList) {
				urefArtFile=ra.getFile();
	            urefArtFileName=urefArtFile.substring(urefArtFile.lastIndexOf(File.separator)+1);
				namestr=namestr+idstr+array[s]+" then \""+ra.getName()+"\" ";
				filestr=filestr+idstr+array[s]+" then \""+urefArtFileName+"\" ";
				descstr=descstr+idstr+array[s]+" then \""+ra.getDescription()+"\" ";
				s++;
				if(s==len)
					break;
			}
			
			hql1=upstr+namestr+end+","+filestr+end+","+descstr+end+condition+";";		
			TypedQuery<Video> query1 = session.createSQLQuery(hql1);
			video.setReferenceArtifact(referenceArtifactList);
			int update1 = query1.executeUpdate();
			if (update1 == 0 || update1 == 1)
				System.out.println(update1 + " row affected");
			else
				System.out.println(update1 + " rows affected");
            if(update1>0)
			   System.out.println("Updated  reference Records Successfully");
	

            
            String namestr1="set name=case ";
			String filestr1=" file=case ";
			String descstr1=" description=case ";
			String idstr1="when id=";
            
            List<Integer> idlist1 = session.createSQLQuery("SELECT id from sample_programs where video_id="+vid).getResultList();
			//int[] idarr=idlist.toArray();
			int[] array1 = idlist1.stream().mapToInt(i->i).toArray();
			int len1=array1.length;
			int s1=0;
			System.out.println("id array length is "+len1);
			for(int i=0;i<len1;i++) {
				System.out.println("id value is"+array1[i]);
			}
            
            String usamProgfile="",usamProgFileName="";
            String updateSampleProgram="";
			String updatequerysp="update sample_programs ";
			List<SampleProgram> sampleProgramList = video.getSampleProgram();
			for(SampleProgram sp:sampleProgramList) {
				usamProgfile=sp.getFile();
	            usamProgFileName=usamProgfile.substring(usamProgfile.lastIndexOf(File.separator)+1);
	            System.out.println(usamProgFileName);
				namestr1=namestr1+idstr1+array1[s1]+" then \""+sp.getName()+"\" ";
				filestr1=filestr1+idstr1+array1[s1]+" then \""+ usamProgFileName+"\" ";
				descstr1=descstr1+idstr1+array1[s1]+" then \""+sp.getDescription()+"\" ";
				s1++;
				if(s1==len1)
					break;
			}			
			updateSampleProgram=updatequerysp+namestr1+end+","+filestr1+end+","+descstr1+end+condition+";";		
			TypedQuery<Video> query2 = session.createSQLQuery(updateSampleProgram);
			video.setSampleProgram(sampleProgramList);
			int update2 = query2.executeUpdate();
			if (update2 == 0 || update2 == 1)
				System.out.println(update2 + " row affected");
			else
				System.out.println(update2 + " rows affected");
            if(update2>0)
			   System.out.println("Updated sampleprogram Records Successfully");
            
            
            
            
            List<Integer> idlist2 = session.createSQLQuery("SELECT id from reference_urls where video_id="+vid).getResultList();
			//int[] idarr=idlist.toArray();
			int[] array2 = idlist2.stream().mapToInt(i->i).toArray();
			int len2=array2.length;
			int s2=0;
			System.out.println("id array length is "+len2);
			for(int i=0;i<len2;i++) {
				System.out.println("id value is"+array2[i]);
			}
 
         //Update reference url  
			String namestr3="set name=case ";
			String descstr3=" description=case ";
			String idstr3="when id=";
            String hql3="";
			String upstrrefUrl="update reference_urls ";
	
			String urlstr=" url=case ";	
			List<ReferenceUrl> referenceUrlList = video.getReferenceUrl();
			for(ReferenceUrl ru:referenceUrlList) {
				namestr3=namestr3+idstr3+array2[s2]+" then \""+ru.getName()+"\" ";
				urlstr=urlstr+idstr+array2[s2]+" then \""+ru.getUrl()+"\" ";
				descstr3=descstr3+idstr3+array2[s2]+" then \""+ru.getDescription()+"\" ";
				s2++;
				if(s2==len2)
					break;
			}	
			hql3=upstrrefUrl+namestr3+end+","+urlstr+end+","+descstr3+end+condition+";";		
			TypedQuery<Video> query3 = session.createSQLQuery(hql3);
			video.setReferenceUrl(referenceUrlList);
			int update3 = query3.executeUpdate();
			if (update3 == 0 || update3 == 1)
				System.out.println(update3 + " row affected");
			else
				System.out.println(update1 + " rows affected");
            if(update3>0)
			   System.out.println("Updated  reference url Records Successfully");
            
			transaction.commit();
		} catch (Exception e) {
			throw new DBException("name and url must be unique");
		} finally {
			session.close();
		}
		return video;
	}
	
	@Override
	public List<Video> deactivateVideo(int videoId) throws DBException {
		Session session = null;
		Transaction t = null;
		List<Video> video = null;
		try {
			session = sessionFactory.getCurrentSession();
			t = session.beginTransaction();
			String hql = "update Video v set v.status=false where v.id=:videoId";
			TypedQuery<Video> query = session.createQuery(hql);
			query.setParameter("videoId", videoId);
			int update = query.executeUpdate();
			if (update == 0 || update == 1)
				System.out.println(update + " row affected");
			else
				System.out.println(update + " rows affected");

			System.out.println("Deleted reference url Records Successfully");
			t.commit();
		}

		catch (Exception e) {
			throw new DBException("Error in deleting reference url records");
		} finally {
			session.close();
		}
		return video;
	}
	
	
	
}