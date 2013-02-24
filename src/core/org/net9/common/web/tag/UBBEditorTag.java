package org.net9.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class UBBEditorTag extends TagSupport {

    private boolean withEmotions = true;
    private boolean withGalleries = true;

    public void setWithEmotions(boolean withEmotions) {
        this.withEmotions = withEmotions;
    }

    public void setWithGalleries(boolean withGalleries) {
        this.withGalleries = withGalleries;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.print("<div class=\"ubb\">");
            out.print("<img src=\"images/newUbb/bar.gif\">&nbsp;");
            out.print("<a href=\"javascript: bold();\"><img title=\"粗体\" src=\"images/newUbb/bold.gif\"  border=\"0\"></a> &nbsp;");
            out.print("<a href='javascript: italicize();'><img title='斜体' src='images/newUbb/italic.gif' alt='' border='0'>" + "	</a>&nbsp;" + "	<a href='javascript: underline();'><img title='下划线'" + "			src='images/newUbb/underline.gif' alt='下划线'>" + "	</a> &nbsp;" + "	<img src='images/newUbb/bar.gif' alt=''>" + "	&nbsp;" + "	<a href='javascript: image();'><img title='插入图片'" + "			src='images/newUbb/insertimage.gif' alt='插入图片'>" + "	</a> &nbsp;" + "	<a href='javascript: hyperlink();'><img" + "			title='插入超链接' src='images/newUbb/url.gif' alt=''" + "			border='0'>" + "	</a>&nbsp;" + "	<a href='javascript: email();'><img title='插入邮件地址'" + "			src='images/newUbb/email.gif' alt='插入邮件地址'>" + "	</a>&nbsp;" + "	<a href='javascript:flash();'><img title='插入视频'" + "			src='images/newUbb/flash.gif' alt='插入视频'>" + "	</a>&nbsp;" + "	<a href='javascript: quoteme();'><img title='插入引用文字'" + "			src='images/newUbb/quote.gif' alt='插入引用文字'>" + "	</a>");
            if (withEmotions) {
                out.print("&nbsp; <a href='emotionFrame.jsp' class='boxy'  title='插入表情'><img title='插入表情' src='images/newUbb/emot.png' alt='插入表情'></a>");
            }
            if (withGalleries) {
                out.print("&nbsp; <a href='img.shtml?method=miniImageForm&type=ubb' class='boxy' title='插入相册图片'><img title='插入相册图片' src='images/newUbb/photos.png' alt='插入相册图片'></a>");
            }

            // out
            // .print(" <select name='addbbcodefontcolor'"
            // + " id='addbbcodefontcolor'"
            // + " onChange='bbfontstyle('[color=' "+
            // " this.options[this.selectedIndex].value + ']',"+
            // " '[/color]');this.selectedIndex=0;'>"
            // + " <option"
            // + " style='color: black; background-color: rgb(250, 250, 250);'"
            // + " value='#444444' class='genmed'>"
            // + " 标准颜色"
            // + " </option>"
            // + " <option"
            // + " style='color: darkred; background-color: rgb(250, 250,
            // 250);'"
            // + " value='darkred' class='genmed'>"
            // + " 深红"
            // + " </option>"
            // + " <option"
            // + " style='color: red; background-color: rgb(250, 250, 250);'"
            // + " value='red' class='genmed'>"
            // + " 红色"
            // + " </option>"
            // + " <option"
            // + " style='color: orange; background-color: rgb(250, 250, 250);'"
            // + " value='orange' class='genmed'>"
            // + " 橙色"
            // + " </option>"
            // + " <option"
            // + " style='color: brown; background-color: rgb(250, 250, 250);'"
            // + " value='brown' class='genmed'>"
            // + " 棕色"
            // + " </option>"
            // + " <option"
            // + " style='color: yellow; background-color: rgb(250, 250, 250);'"
            // + " value='yellow' class='genmed'>"
            // + " 黄色"
            // + " </option>"
            // + " <option"
            // + " style='color: green; background-color: rgb(250, 250, 250);'"
            // + " value='green' class='genmed'>"
            // + " 绿色"
            // + " </option>"
            // + " <option"
            // + " style='color: olive; background-color: rgb(250, 250, 250);'"
            // + " value='olive' class='genmed'>"
            // + " 橄榄"
            // + " </option>"
            // + " <option"
            // + " style='color: cyan; background-color: rgb(250, 250, 250);'"
            // + " value='cyan' class='genmed'>"
            // + " 青色"
            // + " </option>"
            // + " <option"
            // + " style='color: blue; background-color: rgb(250, 250, 250);'"
            // + " value='blue' class='genmed'>"
            // + " 蓝色"
            // + " </option>"
            // + " <option"
            // + " style='color: darkblue; background-color: rgb(250, 250,
            // 250);'"
            // + " value='darkblue' class='genmed'>"
            // + " 深蓝"
            // + " </option>"
            // + " <option"
            // + " style='color: indigo; background-color: rgb(250, 250, 250);'"
            // + " value='indigo' class='genmed'>"
            // + " 靛蓝"
            // + " </option>"
            // + " <option"
            // + " style='color: violet; background-color: rgb(250, 250, 250);'"
            // + " value='violet' class='genmed'>"
            // + " 紫色"
            // + " </option>"
            // + " <option"
            // + " style='color: white; background-color: rgb(250, 250, 250);'"
            // + " value='white' class='genmed'>"
            // + " 白色"
            // + " </option>"
            // + " <option"
            // + " style='color: black; background-color: rgb(250, 250, 250);'"
            // + " value='black' class='genmed'>"
            // + " 黑色"
            // + " </option>"
            // + " </select>");
            // out
            // .print("<select name='addbbcodefontsize'
            // onChange=\"bbfontstyle('[size='+this.options[this.selectedIndex].value+']',
            // '[/size]')\">"
            // + " <option value='7' class='genmed'>最小 </option>"
            // + " <option value='9' class='genmed'>小</option>"
            // + " <option value='12' selected='selected'
            // class='genmed'>标准字体</option>"
            // + " <option value='18' class='genmed'> 大 </option>"
            // + " <option value='24' class='genmed'> 最大 </option>"
            // + " </select>");
            out.print("	</div>");
        } catch (Exception e) {
            e.printStackTrace();
            throw new JspTagException(e.getMessage());
        }
        // and skip the body
        return SKIP_BODY;
    }
}
