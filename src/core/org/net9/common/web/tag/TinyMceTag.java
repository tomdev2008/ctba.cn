package org.net9.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * tinymce tag
 * 
 * @author gladstone
 * 
 */
public class TinyMceTag extends TagSupport {

	private static final long serialVersionUID = -907077535989593404L;

	String target;

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	private Boolean withTag = false;

	public Boolean isWithTag() {
		return withTag;
	}

	public void setWithTag(Boolean withTag) {
		this.withTag = withTag;
	}

	private Integer width = 420;

	private Integer height = 250;

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Boolean getWithTag() {
		return withTag;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			if (withTag)
				out
						.print("<script language=\"javascript\" type=\"text/javascript\">");
			out
					.print("tinyMCE.init({ theme : \"advanced\",	 language : \"zh_cn\",			 width:\""
							+ width
							+ "px\",			 plugins:\"bbcode,emotions,advimage,advlink,inlinepopups,zoom\",			 height:\""
							+ height
							+ "px\",			 mode : \"exact\",			 remove_linebreaks:false,			 elements : \""
							+ target
							+ "\",			 force_br_newlines:true,theme_advanced_toolbar_location : \"top\", theme_advanced_toolbar_align : \"left\",			 theme_advanced_buttons1 : \"bold,italic,underline,separator,image,emotions,cleanup,undo,redo,link,unlink,forecolor,code,styleselect,removeformat\", theme_advanced_styles : \"Quote=quoteStyle\",theme_advanced_buttons2 : \"\", theme_advanced_buttons3 : \"\",add_form_submit_trigger : false,convert_fonts_to_spans : false, content_css : \"theme/default/general.css\", entity_encoding : \"raw\", add_unload_trigger : false});	");
			if (withTag)
				out.print("	</script>");
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException(e.getMessage());
		}
		// and skip the body
		return SKIP_BODY;
	}

}
