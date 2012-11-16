package org.robot.gtf.dblayer.mongodb;

import org.robot.gtf.dblayer.ProjectRepository;
import org.robot.gtf.dblayer.to.ProjectTO;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;

public class MongoProjectRepository implements ProjectRepository {

	private static final String COLLECTION_NAME_PROJECTS = "gtf_projects";
	
	private MongoHandler handler ;
	

	public MongoProjectRepository(MongoHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public ProjectTO read(String id) {
		DBCollection collection = handler.getCollection(COLLECTION_NAME_PROJECTS);
		
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put("_id", id);
		
		DBObject jsonDoc = collection.findOne(dbObject);
		ProjectDocument projectDocument = new ProjectDocument(jsonDoc);
		
		return projectDocument.getProjectTO();
	}

	@Override
	public void write(ProjectTO projectTO) {
		DBCollection collection = handler.getCollection(COLLECTION_NAME_PROJECTS);

		ProjectDocument projectDocument = new ProjectDocument(projectTO);
		DBObject dbObject = projectDocument.getJsonDocument();
		
		collection.insert(dbObject, WriteConcern.SAFE);
	}
}