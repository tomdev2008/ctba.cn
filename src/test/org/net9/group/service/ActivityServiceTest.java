package org.net9.group.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.StringUtils;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.group.ActivityComment;
import org.net9.domain.model.group.ActivityModel;
import org.net9.group.service.ActivityService;
import org.net9.test.TestBase;

import com.google.inject.Guice;

public class ActivityServiceTest extends TestBase {

	ActivityService service;

	@Before
	public void setUp() throws Exception {
		service = Guice.createInjector(new ServiceModule()).getInstance(
				ActivityService.class);
	}

	@Test
	public void testFindActivityComments() {
		service.findActivityComments(1, null, 0, 100);
		List<ActivityModel> aList = service.findActivities(null, null, null,
				null, 0, 10);
		System.out.println(aList.size());
		if (aList.size() > 0) {
			List<ActivityComment> cList = service.findActivityComments(aList
					.get(0).getId(), null, 0, 100);
			System.out.println(aList.get(0).getId() + "__" + cList.size());
		}

	}

	@Test
	public void testGetActivityCommentsCnt() {
		service.findActivityComments(1, null, 0, 100);
		List<ActivityModel> aList = service.findActivities(null, null, null,
				null, 0, 10);
		System.out.println(aList.size());
		if (aList.size() > 0) {
			System.out.println(service.getActivityCommentsCnt(aList.get(0)
					.getId(), null));
		}
	}

	@Test
	public void testSaveActivityComment() {
		List<ActivityModel> aList = service.findActivities(null, null, null,
				null, 0, 10);
		if (aList.size() > 0) {
			ActivityComment model = new ActivityComment();
			model.setActivity(aList.get(0));
			model.setAuthor("gladstone");
			model.setBody("hahahh");
			model.setIp("102.11.11.1");
			model.setUpdateTime(StringUtils.getTimeStrByNow());
			this.service.saveActivityComment(model);
			Integer cnt = service.getActivityCommentsCnt(aList.get(0).getId(),
					null);
			System.out.println(cnt);
			Assert.assertTrue(cnt > 0);
		}

	}

    /**
     * Test of delActivity method, of class ActivityService.
     */
    @Test
    public void testDelActivity() {
    }

    /**
     * Test of delActivityComment method, of class ActivityService.
     */
    @Test
    public void testDelActivityComment() {
    }

    /**
     * Test of delActivityUser method, of class ActivityService.
     */
    @Test
    public void testDelActivityUser() {
    }

    /**
     * Test of findActivities method, of class ActivityService.
     */
    @Test
    public void testFindActivities() {
    }

    /**
     * Test of findActivityComments method, of class ActivityService.
     */
    @Test
    public void testFindActivityComments_4args() {
    }

    /**
     * Test of findActivityUsers method, of class ActivityService.
     */
    @Test
    public void testFindActivityUsers() {
    }

    /**
     * Test of getActivitiesCnt method, of class ActivityService.
     */
    @Test
    public void testGetActivitiesCnt() {
    }

    /**
     * Test of getActivity method, of class ActivityService.
     */
    @Test
    public void testGetActivity() {
    }

    /**
     * Test of getActivityComment method, of class ActivityService.
     */
    @Test
    public void testGetActivityComment() {
    }

    /**
     * Test of getActivityCommentsCnt method, of class ActivityService.
     */
    @Test
    public void testGetActivityCommentsCnt_Integer_String() {
    }

    /**
     * Test of getActivityUser method, of class ActivityService.
     */
    @Test
    public void testGetActivityUser_Integer() {
    }

    /**
     * Test of getActivityUser method, of class ActivityService.
     */
    @Test
    public void testGetActivityUser_3args() {
    }

    /**
     * Test of getActivityUsersCnt method, of class ActivityService.
     */
    @Test
    public void testGetActivityUsersCnt() {
    }

    /**
     * Test of saveActivity method, of class ActivityService.
     */
    @Test
    public void testSaveActivity() {
    }

    /**
     * Test of saveActivityComment method, of class ActivityService.
     */
    @Test
    public void testSaveActivityComment_ActivityComment() {
    }

    /**
     * Test of saveActivityUser method, of class ActivityService.
     */
    @Test
    public void testSaveActivityUser() {
    }

}
