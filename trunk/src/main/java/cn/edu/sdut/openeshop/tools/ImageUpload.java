package cn.edu.sdut.openeshop.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.codec.digest.DigestUtils;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

@Named
@SessionScoped
// TODO 这里使用SessionScoped不合适，应该使用ConversationScoped
public class ImageUpload implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// TODO 暂时写死路径
	private String baseLocation = "/opt/data/";

	@Inject
	Logger log;
	
	// 相对路径名
	private ArrayList<String> files = new ArrayList<String>();

	public void listener(FileUploadEvent event) throws Exception {
		UploadedFile item = event.getUploadedFile();
		log.info("item name=" + item.getName() + ",item size=" + item.getSize()
				+ ",item type=" + item.getContentType());
		saveImage(item);
	}

	private void saveImage(UploadedFile item) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateDir = sdf.format(new Date());
		File baseDir = new File(baseLocation + dateDir);
		if(!baseDir.isDirectory()) {
			baseDir.mkdirs();
		}
		String fileName = item.getName();
		String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		// 当前时间经过md5后作为文件名
		String relName = createNewName() + "." + fileSuffix;
		String name = baseLocation + dateDir + "/" + relName;
		InputStream fis = item.getInputStream();
		FileOutputStream out = new FileOutputStream(name);

		try {
			int bytes = 0;
			byte[] bteFile = new byte[1024];
			while ((bytes = fis.read(bteFile)) != -1) {
				out.write(bteFile, 0, bytes);
			}
			out.flush();
		} finally {
			fis.close();
			out.close();
		}
		
		getFiles().add(relName);
	}

	private String createNewName() {
		String md5str = DigestUtils.md5Hex(new Date().toString());
		return md5str;
	}

	public String clearUploadData() {
		getFiles().clear();
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

	public ArrayList<String> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<String> files) {
		this.files = files;
	}

}
