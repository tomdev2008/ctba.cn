<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<ul>
    <li>
        <a href="messages"><bean:message key="user.mail.all" />
        </a>
    </li>
    <li>
        <a href="message.action?method=list&read=false"><bean:message key="user.mail.unread" />
        </a>
    </li>
    <li>
        <a href="message.action?method=list&marked=true"><bean:message key="user.mail.marked" />
        </a>
    </li>
    <li>
        <a href="message.action?method=list&box=outbox"><bean:message key="user.mail.outbox" />
        </a>
    </li>
    <li class="mail_post">
        <a href="message.action?method=form"><bean:message key="user.post.mail" />
        </a>
    </li>
</ul>