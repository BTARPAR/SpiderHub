package spiderhub.model.dao;

import java.util.List;

import spiderhub.model.File;

public interface FileDao {
	
	File getFile(Integer id);
	
	File saveFile(File file);
	
	List<File> getFiles();
	
	List<File> getFilesAssignedToTask(Integer id);
	
}
