package org.net9.gallery.web.struts.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * 多文件上传
 * 
 * @author gladstone
 * 
 */
@SuppressWarnings("serial")
public class MultiImageFileForm extends ActionForm {

	Integer gid;

	FormFile uploadFile1;

	FormFile uploadFile2;

	FormFile uploadFile3;

	FormFile uploadFile4;

	FormFile uploadFile5;

	FormFile uploadFile6;

	public Integer getGid() {
		return gid;
	}

	public FormFile getUploadFile1() {
		return uploadFile1;
	}

	public FormFile getUploadFile2() {
		return uploadFile2;
	}

	public FormFile getUploadFile3() {
		return uploadFile3;
	}

	public FormFile getUploadFile4() {
		return uploadFile4;
	}

	public FormFile getUploadFile5() {
		return uploadFile5;
	}

	public FormFile getUploadFile6() {
		return uploadFile6;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public void setUploadFile1(FormFile uploadFile1) {
		this.uploadFile1 = uploadFile1;
	}

	public void setUploadFile2(FormFile uploadFile2) {
		this.uploadFile2 = uploadFile2;
	}

	public void setUploadFile3(FormFile uploadFile3) {
		this.uploadFile3 = uploadFile3;
	}

	public void setUploadFile4(FormFile uploadFile4) {
		this.uploadFile4 = uploadFile4;
	}

	public void setUploadFile5(FormFile uploadFile5) {
		this.uploadFile5 = uploadFile5;
	}

	public void setUploadFile6(FormFile uploadFile6) {
		this.uploadFile6 = uploadFile6;
	}

}
