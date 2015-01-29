package aic2014.tuwien.ac.at.dao;

import java.io.File;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import aic2014.tuwien.ac.at.beans.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

public class DocumentStoreDAOImpl implements IDocumentStoreDAO {

	MongoClient mongoClient;
	ServerAddress serverAddress;

	public DocumentStoreDAOImpl() {

		try {
			serverAddress = new ServerAddress("localhost", 27017);
			MongoCredential creds = MongoCredential.createPlainCredential("", "aic2014", "".toCharArray());
			List<MongoCredential> auths = new ArrayList<MongoCredential>();
			auths.add(creds);

			mongoClient = new MongoClient(serverAddress);
		} catch (UnknownHostException e) {

			e.printStackTrace();
		}

	}

	public boolean createDocument(Document document) {
		try {
			DB dbs = mongoClient.getDB("aic2014");
			DBCollection dbCollection = dbs.getCollection("documents");
			BasicDBObject dbo = new BasicDBObject("name", document.getName());
			dbo.append("company", document.getCompany());
			dbo.append("homepage", document.getHomepage());
			dbo.append("category", document.getCategory());
			dbo.append("filepath", document.getFilepath());
			dbCollection.insert(dbo);
			// saveImage(document.getFilepath(), document.getName());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public void saveImage(String filepath, String name) throws Exception {
		DB dbs = mongoClient.getDB("aic2014");
		File file = new File(filepath);
		GridFS gFS = new GridFS(dbs, "photos");
		GridFSInputFile gfsFile = gFS.createFile(file);
		gfsFile.setFilename(name);
		gfsFile.save();
	}

	public boolean deleteDocument(String name) {
		try {
			DB dbs = mongoClient.getDB("aic2014");
			DBCollection dbCollection = dbs.getCollection("documents");
			BasicDBObject db = new BasicDBObject();
			db.put("name", name);
			Cursor curs = dbCollection.find(db);
			while (curs.hasNext()) {
				dbCollection.remove(curs.next());
			}

			GridFS gridFS = new GridFS(dbs, "photos");
			gridFS.remove(gridFS.findOne(name));

			return true;
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}

	}

	public boolean deleteDocumentStore() {
		try {
			DB dbs = mongoClient.getDB("aic2014");
			DBCollection dbCollection = dbs.getCollection("docment");
			dbCollection.drop();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public Document findDocument(String name) {

		try {

			DB dbs = mongoClient.getDB("aic2014");
			DBCollection dbCollection = dbs.getCollection("documents");
			BasicDBObject db = new BasicDBObject();
			db.put("name", name);
			Cursor curs = dbCollection.find(db);
			Document doc;
			while (curs.hasNext()) {
				DBObject dbObject = curs.next();
				doc = new Document((String) dbObject.get("name"), (String) dbObject.get("company"), "", (String) dbObject.get("category"), (String) dbObject.get("homepage"));

				return doc;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	public ArrayList<Document> findAddsForKeywords(ArrayList<String> keywords) {

		ArrayList<Document> docList = new ArrayList<Document>();
		try {

			DB dbs = mongoClient.getDB("aic2014");
			DBCollection dbCollection = dbs.getCollection("documents");
			BasicDBObject db = new BasicDBObject();

			for (String keyword : keywords) {

				System.out.println(keyword);
				Pattern pattern;

				pattern = Pattern.compile(keyword);

				db.put("category", new BasicDBObject("$regex", pattern));
				Cursor curs = dbCollection.find(db);

				while (curs.hasNext()) {
					DBObject dbObject = curs.next();
					Document doc = new Document((String) dbObject.get("name"), (String) dbObject.get("company"), (String) dbObject.get("filepath"), (String) dbObject.get("category"), (String) dbObject.get("homepage"));
					docList.add(doc);

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return docList;
	}

	public void getImage(String filename, String destination) {
		try {
			DB dbs = mongoClient.getDB("aic2014");
			GridFS gridFS = new GridFS(dbs, "photos");
			GridFSDBFile gfile = gridFS.findOne(filename);
			gfile.writeTo(destination + "/" + filename);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void printAllImages() {
		DB dbs = mongoClient.getDB("aic2014");
		GridFS gridFS = new GridFS(dbs, "photos");
		DBCursor curs = gridFS.getFileList();
		while (curs.hasNext()) {
			System.out.println(curs.next());
		}
	}

	public void deleteImageStore(String name) {
		DB dbs = mongoClient.getDB("aic2014");
		GridFS gridFS = new GridFS(dbs, "photos");
		BasicDBObject dbo = new BasicDBObject();
		dbo.put("filename", name);
		gridFS.remove(dbo);
	}

}
