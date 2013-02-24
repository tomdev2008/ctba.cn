package org.net9.core.config.menu;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.net9.core.config.menu.AdminMenuHandler;
import org.net9.core.config.menu.MenuItem;
import org.net9.domain.model.core.SysAdminGroup;
import org.net9.test.TestBase;

/**
 * system menu builder test
 * 
 * @author gladstone
 * 
 */
public class AdminMenuHandlerTest  extends  TestBase {

	@Test
	public void testFromFile() {
		MenuItem topItem = AdminMenuHandler.getTopItemsFromFile();
		System.out.println(topItem.getChildren().size());
		Assert.assertTrue(topItem.getChildren().size() > 0);
		for (MenuItem item : topItem.getChildren()) {
			System.out.println(item.toString());
		}

		MenuItem adminmenu = AdminMenuHandler.buildRootMenuItem("admin");
		System.out.println(adminmenu.getChildren().size());
		Assert.assertTrue(adminmenu.getChildren().size() > 0);
		System.out.println(adminmenu.toString());

		SysAdminGroup group = new SysAdminGroup();
		group.setOptionStr("N");
		List<SysAdminGroup> gList = new ArrayList<SysAdminGroup>();
		gList.add(group);
		adminmenu = AdminMenuHandler.validateItem(gList, adminmenu);
		Assert.assertTrue(adminmenu.getChildren().size() > 0);

		MenuItem gladstoneMenu = AdminMenuHandler
				.buildRootMenuItem("gladstone");
		Assert.assertTrue(gladstoneMenu.getChildren().size() > 0);
		for (MenuItem item : gladstoneMenu.getChildren()) {
			System.out.println(item.toString());
		}
	}
}
