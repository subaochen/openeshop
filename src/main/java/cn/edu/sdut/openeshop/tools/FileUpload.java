package cn.edu.sdut.openeshop.tools;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

@Named
@SessionScoped
public class FileUpload implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject Logger log;
    private ArrayList<UploadedFile> files = new ArrayList<UploadedFile>();
    
    public void paint(OutputStream stream, Object object) throws IOException {
        stream.write(getFiles().get((Integer) object).getData());
        stream.close();
    }
 
    public void listener(FileUploadEvent event) throws Exception {
        UploadedFile item = event.getUploadedFile();
        log.info("item name=" + item.getName() + ",item size=" + item.getSize() + ",item type=" + item.getContentType());
        files.add(item);
    }
 
    public String clearUploadData() {
        files.clear();
        return null;
    }
 
    public int getSize() {
        if (getFiles().size() > 0) {
            return getFiles().size();
        } else {
            return 0;
        }
    }
 
    public long getTimeStamp() {
        return System.currentTimeMillis();
    }
 
    public ArrayList<UploadedFile> getFiles() {
        return files;
    }
 
    public void setFiles(ArrayList<UploadedFile> files) {
        this.files = files;
    }
    
}
