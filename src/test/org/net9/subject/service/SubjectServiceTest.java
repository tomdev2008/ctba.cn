/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.net9.subject.service;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.subject.Subject;
import org.net9.domain.model.subject.SubjectTemplate;
import org.net9.domain.model.subject.SubjectTopic;
import org.net9.test.TestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class SubjectServiceTest extends TestBase {

	SubjectService instance;

	@Before
	public void setUp() {
		instance = (SubjectService) Guice.createInjector(new ServiceModule())
				.getInstance(SubjectService.class);
	}

	/**
	 * Test of delSubject method, of class SubjectService.
	 */
	@Test
	public void testDelSubject() {
		System.out.println("delSubject");
		List<Subject> list = instance.findSubjects(null, null, 0, 10000);
		Assert.assertTrue(list.size() > 0);
		instance.delSubject(list.get(0));
		List<Subject> list1 = instance.findSubjects(null, null, 0, 10000);
		Assert.assertTrue(list.size() - 1 == list1.size());

	}

	/**
	 * Test of delSubjectTemplate method, of class SubjectService.
	 */
	@Test
	public void testDelSubjectTemplate() {
		System.out.println("delSubjectTemplate");
		List<SubjectTemplate> list = instance.findSubjectTemplates(0, 10000);
		Assert.assertTrue(list.size() > 0);
		instance.delSubjectTemplate(list.get(0));
		List<SubjectTemplate> list1 = instance.findSubjectTemplates(0, 10000);
		Assert.assertTrue(list.size() - 1 == list1.size());
	}

	/**
	 * Test of delSubjectTopic method, of class SubjectService.
	 */
	@Test
	public void testDelSubjectTopic() {
		System.out.println("delSubjectTopic");
		// prepare
		List<Subject> list = instance.findSubjects(null, null, 0, 1);
		Assert.assertTrue(list.size() > 0);
		Integer id = list.get(0).getId();

		SubjectTopic model = new SubjectTopic();
		model.setAuthor("gladstone");
		model.setContent("content");
		model.setCreateTime("2009-00-00");
		model.setDescription("dummy_de");
		model.setHits(0);
		model.setSubjectId(id);
		model.setId(null);
		model.setType("ee");
		model.setTitle("dummy_title");
		instance.saveSubjectTopic(model);

		List<SubjectTopic> modelList = instance.findSubjectTopics(null, null,
				0, 1);
		Assert.assertTrue(modelList.size() > 0);

		instance.delSubjectTopic(modelList.get(0));
		// List<SubjectTopic> list1 = instance.findSubjectTopics(null, null, 0,
		// 10000);
		// Assert.assertTrue(list.size() - 1 == list1.size());
	}

	/**
	 * Test of findHotSubjectTopics method, of class SubjectService.
	 */
	@Test
	public void testFindHotSubjectTopics() {
		System.out.println("findHotSubjectTopics");
		Integer start = 0;
		Integer limit = 10;
		List<SubjectTopic> result = instance.findHotSubjectTopics(start, limit);
		System.out.println(result);
	}

	/**
	 * Test of findSubjects method, of class SubjectService.
	 */
	@Test
	public void testFindSubjects() {
		System.out.println("findSubjects");
		String title = "";
		String type = "";
		Integer start = 0;
		Integer limit = 10;
		List<Subject> result = instance.findSubjects(title, type, start, limit);
		System.out.println(result);
	}

	/**
	 * Test of findSubjectTemplates method, of class SubjectService.
	 */
	@Test
	public void testFindSubjectTemplates() {
		System.out.println("findSubjectTemplates");
		Integer start = 0;
		Integer limit = 10;

		List<SubjectTemplate> result = instance.findSubjectTemplates(start,
				limit);
		System.out.println(result);
	}

	/**
	 * Test of findSubjectTopics method, of class SubjectService.
	 */
	@Test
	public void testFindSubjectTopics() {
		System.out.println("findSubjectTopics");
		Integer subjectId = null;
		String type = "";
		Integer start = 0;
		Integer limit = 10;
		List<SubjectTopic> result = instance.findSubjectTopics(subjectId, type,
				start, limit);
		System.out.println(result);
	}

	/**
	 * Test of getNewSubjectTopic method, of class SubjectService.
	 */
	@Test
	public void testGetNewSubjectTopic() {
		System.out.println("getNewSubjectTopic");
		Integer sid = 1;
		SubjectTopic result = instance.getNewSubjectTopic(sid);
		System.out.println(result);
	}

	/**
	 * Test of getNextSubjectTopic method, of class SubjectService.
	 */
	@Test
	public void testGetNextSubjectTopic() {
		System.out.println("getNextSubjectTopic");
		// prepare
		List<Subject> list = instance.findSubjects(null, null, 0, 1);
		Assert.assertTrue(list.size() > 0);
		Integer id = list.get(0).getId();

		SubjectTopic model = new SubjectTopic();
		model.setAuthor("gladstone");
		model.setContent("content");
		model.setCreateTime("2009-00-00");
		model.setDescription("dummy_de");
		model.setHits(0);
		model.setSubjectId(id);
		model.setId(null);
		model.setType("ee");
		model.setTitle("dummy_title");
		instance.saveSubjectTopic(model);

		List<SubjectTopic> modelList = instance.findSubjectTopics(null, null,
				0, 1);
		Assert.assertTrue(modelList.size() > 0);

		SubjectTopic currTopic = modelList.get(0);
		SubjectTopic result = instance.getNextSubjectTopic(currTopic);
		System.out.println(result);
	}

	/**
	 * Test of getPrevSubjectTopic method, of class SubjectService.
	 */
	@Test
	public void testGetPrevSubjectTopic() {
		System.out.println("getPrevSubjectTopic");
		// prepare
		List<Subject> list = instance.findSubjects(null, null, 0, 1);
		Assert.assertTrue(list.size() > 0);
		Integer id = list.get(0).getId();

		SubjectTopic model = new SubjectTopic();
		model.setAuthor("gladstone");
		model.setContent("content");
		model.setCreateTime("2009-00-00");
		model.setDescription("dummy_de");
		model.setHits(0);
		model.setSubjectId(id);
		model.setId(null);
		model.setType("ee");
		model.setTitle("dummy_title");
		instance.saveSubjectTopic(model);

		List<SubjectTopic> modelList = instance.findSubjectTopics(null, null,
				0, 1);
		Assert.assertTrue(modelList.size() > 0);

		SubjectTopic currTopic = modelList.get(0);
		SubjectTopic result = instance.getPrevSubjectTopic(currTopic);
		System.out.println(result);
	}

	/**
	 * Test of getSubject method, of class SubjectService.
	 */
	@Test
	public void testGetSubject() {
		System.out.println("getSubject");
		List<Subject> list = instance.findSubjects(null, null, 0, 1);
		Assert.assertTrue(list.size() > 0);
		Integer id = list.get(0).getId();
		Subject result = instance.getSubject(id);
		System.out.println(result);
	}

	/**
	 * Test of getSubjectCnt method, of class SubjectService.
	 */
	@Test
	public void testGetSubjectCnt() {
		System.out.println("getSubjectCnt");
		String title = "";
		String type = "";
		Integer result = instance.getSubjectCnt(title, type);
		System.out.println(result);
	}

	/**
	 * Test of getSubjectTemplate method, of class SubjectService.
	 */
	@Test
	public void testGetSubjectTemplate() {
		System.out.println("getSubjectTemplate");
		Integer id = 122;
		SubjectTemplate result = instance.getSubjectTemplate(id);
		System.out.println(result);
	}

	/**
	 * Test of getSubjectTemplateByCode method, of class SubjectService.
	 */
	@Test
	public void testGetSubjectTemplateByCode() {
		System.out.println("getSubjectTemplateByCode");
		String code = "";

		SubjectTemplate expResult = null;
		SubjectTemplate result = instance.getSubjectTemplateByCode(code);
		assertEquals(expResult, result);

	}

	/**
	 * Test of getSubjectTemplateCnt method, of class SubjectService.
	 */
	@Test
	public void testGetSubjectTemplateCnt() {
		System.out.println("getSubjectTemplateCnt");
		Integer result = instance.getSubjectTemplateCnt();
		System.out.println(result);
		System.out.println(result);

	}

	/**
	 * Test of getSubjectTopic method, of class SubjectService.
	 */
	@Test
	public void testGetSubjectTopic() {
		System.out.println("getSubjectTopic");
		// prepare
		List<Subject> list = instance.findSubjects(null, null, 0, 1);
		Assert.assertTrue(list.size() > 0);
		Integer id = list.get(0).getId();

		SubjectTopic model = new SubjectTopic();
		model.setAuthor("gladstone");
		model.setContent("content");
		model.setCreateTime("2009-00-00");
		model.setDescription("dummy_de");
		model.setHits(0);
		model.setSubjectId(id);
		model.setId(null);
		model.setType("ee");
		model.setTitle("dummy_title");
		instance.saveSubjectTopic(model);

		List<SubjectTopic> modelList = instance.findSubjectTopics(null, null,
				0, 1);
		Assert.assertTrue(modelList.size() > 0);
		SubjectTopic result = instance
				.getSubjectTopic(modelList.get(0).getId());
		System.out.println(result);

	}

	/**
	 * Test of getSubjectTopicCnt method, of class SubjectService.
	 */
	@Test
	public void testGetSubjectTopicCnt() {
		System.out.println("getSubjectTopicCnt");
		Integer subjectId = 9;
		String type = "";
		Integer result = instance.getSubjectTopicCnt(subjectId, type);
		System.out.println(result);

	}

	/**
	 * Test of saveSubject method, of class SubjectService.
	 */
	@Test
	public void testSaveSubject() {
		System.out.println("saveSubject");
		Subject model = new Subject();
		model.setAuthor("gladstone");
		model.setCreateTime("2009-00-00");
		model.setDescription("dumy");
		model.setImage("image");
		model.setTemplate("dummy_t");
		model.setTitle("dummy_title");
		model.setType("d");
		model.setUpdateTime("2009-99-99");
		instance.saveSubject(model);

	}

	/**
	 * Test of saveSubjectTemplate method, of class SubjectService.
	 */
	@Test
	public void testSaveSubjectTemplate() {
		System.out.println("saveSubjectTemplate");
		SubjectTemplate model = new SubjectTemplate();
		model.setAuthor("gladstone");
		model.setCode("dd");
		model.setContent("dummy_content");
		model.setCreateTime("2009-00-00");
		model.setTitle("title_d");
		model.setUpdateTime("2009-00-00");
		model.setUrl("dummy_url");
		instance.saveSubjectTemplate(model);
	}

	/**
	 * Test of saveSubjectTopic method, of class SubjectService.
	 * 
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Test
	public void testSaveSubjectTopic() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		System.out.println("saveSubjectTopic");
		List<Subject> list = instance.findSubjects(null, null, 0, 1);
		Assert.assertTrue(list.size() > 0);
		Integer id = list.get(0).getId();

		SubjectTopic model = new SubjectTopic();
		model.setAuthor("gladstone");
		model.setContent("content");
		model.setCreateTime("2009-00-00");
		model.setDescription("dummy_de");
		model.setHits(0);
		model.setSubjectId(id);
		model.setId(null);
		model.setType("ee");
		model.setTitle("dummy_title");
		instance.saveSubjectTopic(model);

	}

}